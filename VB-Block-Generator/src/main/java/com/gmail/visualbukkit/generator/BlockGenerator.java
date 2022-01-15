package com.gmail.visualbukkit.generator;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
import org.json.JSONArray;

import javax.lang.model.element.*;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BlockGenerator {

    private Path outputDir = Paths.get(".");
    private Reporter reporter;
    private boolean includeDeprecated;
    private String pluginModule;
    private Path blacklistFile;

    private DocletEnvironment environment;
    private Set<GeneratedBlock> generatedBlocks;

    public void setOutputDir(Path outputDir) {
        this.outputDir = outputDir;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    public void setIncludeDeprecated(boolean includeDeprecated) {
        this.includeDeprecated = includeDeprecated;
    }

    public void setPluginModule(String pluginModule) {
        this.pluginModule = pluginModule;
    }

    public void setBlacklistFile(Path blacklistFile) {
        this.blacklistFile = blacklistFile;
    }

    public void generate(DocletEnvironment environment) throws IOException {
        this.environment = environment;
        generatedBlocks = new HashSet<>();
        Set<String> blacklistedClasses = blacklistFile != null && Files.exists(blacklistFile) ? new HashSet<>(Files.readAllLines(blacklistFile)) : Collections.emptySet();

        reporter.print(Diagnostic.Kind.NOTE, "Included Classes:");

        for (Element element : environment.getIncludedElements()) {
            if (element instanceof TypeElement && element.getSimpleName().length() != 0 && !blacklistedClasses.contains(element.toString()) && (includeDeprecated || !environment.getElementUtils().isDeprecated(element))) {
                reporter.print(Diagnostic.Kind.NOTE, "\t" + element);
                TypeElement clazz = (TypeElement) element;
                if (isEvent(clazz)) {
                    if (!clazz.getModifiers().contains(Modifier.ABSTRACT)) {
                        generateBlock(clazz, clazz, true);
                        generateElements(clazz, clazz.getEnclosedElements(), true);
                        ArrayDeque<TypeMirror> supertypes = new ArrayDeque<>(environment.getTypeUtils().directSupertypes(clazz.asType()));
                        while (!supertypes.isEmpty()) {
                            TypeMirror type = supertypes.pop();
                            if (!type.toString().equals("java.lang.Object")) {
                                supertypes.addAll(environment.getTypeUtils().directSupertypes(type));
                                Element superElement = environment.getTypeUtils().asElement(type);
                                if (superElement instanceof TypeElement) {
                                    generateElements(clazz, superElement.getEnclosedElements(), true);
                                }
                            }
                        }
                    }
                } else {
                    generateElements(clazz, element.getEnclosedElements(), false);
                }
            }
        }

        JSONArray blockArray = new JSONArray();
        for (GeneratedBlock generatedBlock : generatedBlocks) {
            if (!generatedBlock.isInvalid()) {
                blockArray.put(generatedBlock.getJson());
            }
        }

        reporter.print(Diagnostic.Kind.NOTE, System.lineSeparator());
        reporter.print(Diagnostic.Kind.NOTE, "Writing output to " + outputDir.toAbsolutePath());

        if (Files.notExists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        Files.writeString(outputDir.resolve("Blocks.json"), blockArray.toString(2), StandardCharsets.UTF_8);
        reporter.print(Diagnostic.Kind.NOTE, "Done.");
        reporter.print(Diagnostic.Kind.NOTE, System.lineSeparator());
    }

    private void generateElements(TypeElement clazz, List<? extends Element> elements, boolean event) {
        for (Element element : elements) {
            if (element.getModifiers().contains(Modifier.PUBLIC) && (includeDeprecated || !environment.getElementUtils().isDeprecated(element))) {
                if (element instanceof VariableElement) {
                    generateField(clazz, (VariableElement) element, generateBlock(clazz, element, event));
                } else if (element instanceof ExecutableElement) {
                    if (element.getKind() == ElementKind.CONSTRUCTOR && !event) {
                        generateConstructor(clazz, (ExecutableElement) element, generateBlock(clazz, element, false));
                    } else if (element.getKind() == ElementKind.METHOD && element.getAnnotation(Override.class) == null) {
                        generateMethod(clazz, (ExecutableElement) element, generateBlock(clazz, element, event));
                    }
                }
            }
        }
    }

    private GeneratedBlock generateBlock(TypeElement clazz, Element element, boolean event) {
        GeneratedBlock block = new GeneratedBlock();
        block.getJson().put("class", clazz.toString());
        block.getJson().putOpt("module", pluginModule);
        if (event) {
            block.getJson().put("event", true);
        }
        if (element.getModifiers().contains(Modifier.STATIC)) {
            block.getJson().put("static", true);
        }
        DocCommentTree docCommentTree = environment.getDocTrees().getDocCommentTree(element);
        if (docCommentTree != null) {
            List<? extends DocTree> docTreeList = docCommentTree.getFullBody();
            if (!docTreeList.isEmpty()) {
                StringBuilder descBuilder = new StringBuilder();
                for (DocTree docTree : docTreeList) {
                    descBuilder.append(docTree.toString()
                            .replaceAll("\\n\\s*", "\\\\n")
                            .replaceAll("<.+>\\s?", "")
                            .replaceAll("\\{@(?:.+?) (.+)}", "$1"));
                }
                block.getJson().put("descr", descBuilder.toString());
            }
        }
        generatedBlocks.add(block);
        return block;
    }

    private void generateField(TypeElement clazz, VariableElement field, GeneratedBlock block) {
        if (field.asType().getKind() == TypeKind.ERROR) {
            block.setInvalid();
            return;
        }
        block.getJson().put("id", clazz + "#" + field);
        block.getJson().put("field", field.getSimpleName().toString());
        block.getJson().put("return", environment.getTypeUtils().erasure(field.asType()).toString());
        generateParameters(clazz, field, block);
    }

    private void generateConstructor(TypeElement clazz, ExecutableElement constructor, GeneratedBlock block) {
        block.getJson().put("id", clazz + "#" + constructor);
        generateParameters(clazz, constructor, block);
    }

    private void generateMethod(TypeElement clazz, ExecutableElement method, GeneratedBlock block) {
        block.getJson().put("id", clazz + "#" + method);
        block.getJson().put("method", method.getSimpleName().toString());
        generateParameters(clazz, method, block);
        if (!(method.getReturnType() instanceof NoType)) {
            if (method.getReturnType().getKind() == TypeKind.ERROR) {
                block.setInvalid();
                return;
            }
            block.getJson().put("return", environment.getTypeUtils().erasure(method.getReturnType()).toString());
        }
    }

    private void generateParameters(TypeElement clazz, Element element, GeneratedBlock block) {
        if (!block.getJson().has("static") && !block.getJson().has("event") && element.getKind() != ElementKind.CONSTRUCTOR) {
            block.getJson().append("param-types", clazz.toString());
            block.getJson().append("param-names", formatClassName(clazz.toString()));
        }
        if (element instanceof ExecutableElement) {
            for (VariableElement parameter : ((ExecutableElement) element).getParameters()) {
                if (parameter.asType().getKind() == TypeKind.ERROR) {
                    block.setInvalid();
                    return;
                }
                block.getJson().append("param-types", environment.getTypeUtils().erasure(parameter.asType()).toString());
                block.getJson().append("param-names", formatLowerCamelCase(parameter.getSimpleName().toString()));
            }
        }
    }

    private static String formatClassName(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return str.substring(i);
            }
        }
        return str;
    }

    private static String formatLowerCamelCase(String str) {
        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            builder.append(c);
            if (i + 1 < str.length() && Character.isUpperCase(str.charAt(i + 1)) && (Character.isLowerCase(c) || (i + 2 < str.length() && Character.isLowerCase(str.charAt(i + 2))))) {
                builder.append(' ');
            }
        }
        return builder.toString();
    }

    private boolean isEvent(TypeElement clazz) {
        TypeMirror type = clazz.asType();
        while (true) {
            String name = type.toString();
            if (name.equals("java.lang.Object")) {
                return false;
            }
            if (name.equals("org.bukkit.event.Event")) {
                return true;
            }
            List<? extends TypeMirror> supertypes = environment.getTypeUtils().directSupertypes(type);
            if (!supertypes.isEmpty()) {
                type = supertypes.get(0);
            } else {
                return false;
            }
        }
    }
}
