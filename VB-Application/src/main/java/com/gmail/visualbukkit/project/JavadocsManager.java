package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.Block;
import com.gmail.visualbukkit.blocks.parameters.ClassParameter;
import com.gmail.visualbukkit.blocks.parameters.FieldParameter;
import com.gmail.visualbukkit.blocks.parameters.MethodParameter;
import org.json.JSONObject;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class JavadocsManager {

    public static void getStatJavadocs(ClassParameter classParameter, MethodParameter methodParameter) {
        getJavadocs(classParameter.getValue().getName(), methodParameter.getValue().getSignature());
    }

    public static void getExprJavadocs(ClassParameter classParameter, MethodParameter methodParameter) {
        getJavadocs(classParameter.getValue().getName(), methodParameter.getValue().getSignature());
    }

    public static void getExprJavadocs(ClassParameter classParameter, FieldParameter fieldParameter) {
        getJavadocs(classParameter.getValue().getName(), fieldParameter.getValue().getName());
    }

    public static void getCompJavaDocs(PluginComponent pluginComponent) {
        pluginComponent.getBlock().ifPresentOrElse(
                Block::openJavadocs,
                () -> VisualBukkitApp.getLogger().warning(
                        "No Javadocs available for this component: " + pluginComponent.getBlockType().orElse("Unknown")
                )
        );
    }

    private static void getJavadocs(String className, String methodName) {
        JSONObject remapData = Project.getRemapData();
        if (remapData != null) {
            String key = className + "#" + methodName;
            if (remapData.has(key)) {
                String remapped = remapData.getString(key);
                String[] parts = remapped.split("#");
                if (parts.length == 2) {
                    className = parts[0];
                    methodName = parts[1];
                } else {
                    methodName = remapped;
                }
            }
        }

        for (Map.Entry<String, List<String>> entry : Project.getJavadocsMap().entrySet()) {
            String baseUrl = entry.getKey();
            List<String> packages = entry.getValue();

            for (String packageName : packages) {
                if (className.startsWith(packageName)) {
                    String[] segments = className.split("\\.");
                    StringBuilder packagePathBuilder = new StringBuilder();
                    StringBuilder classPartBuilder = new StringBuilder();
                    boolean inClass = false;
                    for (String segment : segments) {
                        if (!inClass && !segment.isEmpty() && Character.isUpperCase(segment.charAt(0))) {
                            inClass = true;
                        }
                        if (inClass) {
                            if (!classPartBuilder.isEmpty()) {
                                classPartBuilder.append('.');
                            }
                            classPartBuilder.append(segment);
                        } else {
                            if (!packagePathBuilder.isEmpty()) {
                                packagePathBuilder.append('/');
                            }
                            packagePathBuilder.append(segment);
                        }
                    }
                    if (classPartBuilder.isEmpty() && segments.length > 0) {
                        classPartBuilder.append(segments[segments.length - 1]);
                    }
                    String url = baseUrl + packagePathBuilder + "/" + classPartBuilder + ".html";
                    if (!methodName.isBlank()) {
                        url += "#" + methodName;
                    }
                    VisualBukkitApp.openURI(URI.create(url));
                    VisualBukkitApp.getLogger().info("Opening Javadocs: " + url);
                    return;
                }
            }
        }
    }

}
