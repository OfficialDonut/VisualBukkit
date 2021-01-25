package com.gmail.visualbukkit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.json.JSONTokener;

public class MasterText {

	private Map<String, File> languagesFiles = new HashMap<>();

	private static Map<String, String> texts = new HashMap<>();

	private static File languageFile;

	public MasterText() {
		fillLanguageHashMap();
		languageFile = languagesFiles.get("en_us");
		fillLanguageTextMap();
	}

	private void fillLanguageHashMap() {
		URI uri = new File("./src/main/resources/lang").toURI();
		Path myPath;
		if (uri.getScheme().equals("jar")) {
			FileSystem fileSystem = null;
			try {
				fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
			myPath = fileSystem.getPath("./src/main/resources/lang");
		} else {
			myPath = Paths.get(uri);
		}
		Stream<Path> walk = null;
		try {
			walk = Files.walk(myPath, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Path> pathList = getListFromIterator(walk.iterator());
		for (Path p : pathList) {
			File file = p.toFile();
			if (file.getName().endsWith("json")) {
				String name = file.getName().replace(".json", "");
				languagesFiles.put(name, file);
				System.out.println("LANGUAGE FILE ~ " + name);
			}
		}
	}

	private List<Path> getListFromIterator(Iterator<Path> p) {
		List<Path> list = new ArrayList<Path>();
		while (p.hasNext()) {
			list.add(p.next());
		}
		return list;
	}

	private static void fillLanguageTextMap() {
		texts.clear();
		JSONTokener tokener = null;
		try {
			tokener = new JSONTokener(new BufferedReader(new FileReader(languageFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = new JSONObject(tokener);
		for (String name : jsonObj.keySet()) {
			texts.put(name, jsonObj.getString(name));
		}
	}

	public void setLanguageFile(String name) {
		String actualName = "";
		switch (name) {
		case "English":
			actualName = "en_us";
			break;
		}
		languageFile = languagesFiles.get(actualName);
		fillLanguageTextMap();
	}

	public File getLanguageFile() {
		return languageFile;
	}

	public static String getText(String key) {
		return texts.get(key);
	}

}
