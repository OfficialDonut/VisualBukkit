package com.gmail.visualbukkit.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Driver {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length != 3) {
            displayInvocation();
            return;
        }

        Path dir = Paths.get(".");
        Path script = dir.resolve(args[0]);

        if (Files.notExists(script)) {
            throw new IllegalStateException("Script file does not exist");
        }

        BlockGenerator generator = new BlockGenerator(dir, dir.resolve(args[1]), dir.resolve(args[2]));

        System.out.println("Reading script file...");
        List<String> lines = Files.readAllLines(script);
        lines.removeIf(String::isEmpty);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.startsWith("#category ")) {
                generator.setCategory(line.substring(line.indexOf(' ') + 1));
            } else if (line.startsWith("#plugin-module ")) {
                generator.setPluginModule(line.substring(line.indexOf(' ') + 1));
            } else if (line.equals("#reset")) {
                generator.reset();
            } else if (line.equals("#class") || line.equals("#package") || line.equals("#blacklist")) {
                boolean isClass = line.equals("#class");
                boolean isPackage = line.equals("#package");
                for (i++; i < lines.size(); i++) {
                    line = lines.get(i).trim();
                    if (line.startsWith("#")) {
                        i--;
                        break;
                    }
                    if (isClass) {
                        System.out.println("Generating blocks for class: " + line);
                        generator.generate(Class.forName(line));
                    } else if (isPackage) {
                        System.out.println("Generating blocks for package: " + line);
                        generator.generate(line);
                    } else {
                        generator.addToBlackList(line);
                    }
                }
            }
        }

        System.out.println("Writing files...");
        generator.writeFiles();
        System.out.println("Done.");
    }

    private static void displayInvocation() {
        System.out.println("Invocation arguments:");
        System.out.println("\t<script file> <block output file> <lang output file>");
        System.out.println();
        System.out.println("Script:");
        System.out.println("\t#category <category>");
        System.out.println("\t\tThe category of the blocks");
        System.out.println();
        System.out.println("\t#plugin-module <module name>");
        System.out.println("\t\tThe plugin module required for the blocks");
        System.out.println();
        System.out.println("\t#reset");
        System.out.println("\t\tReset the values of #category, #plugin-module");
        System.out.println();
        System.out.println("\t#blacklist");
        System.out.println("\t<blacklisted item 1>");
        System.out.println("\t<blacklisted item 2>");
        System.out.println("\t<...>");
        System.out.println("\t\tBlocks will not be generated for items in the blacklist");
        System.out.println();
        System.out.println("\t#class");
        System.out.println("\t<name.of.Class1>");
        System.out.println("\t<name.of.Class2>");
        System.out.println("\t<...>");
        System.out.println("\t\tGenerate blocks for the specified classes");
        System.out.println();
        System.out.println("\t#package");
        System.out.println("\t<name.of.package1>");
        System.out.println("\t<name.of.package2>");
        System.out.println("\t<...>");
        System.out.println("\t\tGenerate blocks for all classes in the specified packages");
    }
}
