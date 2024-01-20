package com.gmail.visualbukkit.doclet;

import com.google.common.base.Throwables;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
import org.json.JSONObject;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonGenerator {

    private final Path outputDirectory;
    private final boolean prettyPrint;
    private final Reporter reporter;

    public JsonGenerator(Path outputDirectory, boolean prettyPrint, Reporter reporter) {
        this.outputDirectory = outputDirectory;
        this.prettyPrint = prettyPrint;
        this.reporter = reporter;
    }

    public boolean generate(DocletEnvironment environment) {
        try {
            Files.createDirectories(outputDirectory);
            for (Element element : environment.getIncludedElements()) {
                if (element instanceof TypeElement clazz && clazz.getModifiers().contains(Modifier.PUBLIC)) {
                    Path outputFile = outputDirectory.resolve(clazz.getQualifiedName() + ".json");
                    JSONObject json = generateClass(environment, clazz);
                    Files.writeString(outputFile, prettyPrint ? json.toString(2) : json.toString(), StandardCharsets.UTF_8);
                    reporter.print(Diagnostic.Kind.NOTE, "Created " + outputFile.getFileName());
                }
            }
            return true;
        } catch (IOException e) {
            reporter.print(Diagnostic.Kind.ERROR, Throwables.getStackTraceAsString(e));
            return false;
        }
    }

    private JSONObject generateClass(DocletEnvironment environment, TypeElement clazz) {
        JSONObject json = new JSONObject();
        json.put("name", clazz.getQualifiedName().toString());
        json.put("simple-name", clazz.getSimpleName().toString());
        json.put("package", environment.getElementUtils().getPackageOf(clazz));
        for (Element member : environment.getElementUtils().getAllMembers(clazz)) {
            if (member.getModifiers().contains(Modifier.PUBLIC)) {
                if (member instanceof VariableElement field) {
                    json.append("fields", generateField(environment, field));
                }
                if (member instanceof ExecutableElement executableElement) {
                    if (member.getKind() == ElementKind.METHOD) {
                        json.append("methods", generateMethod(environment, executableElement));
                    }
                    if (member.getKind() == ElementKind.CONSTRUCTOR) {
                        json.append("constructors", generateConstructor(environment, executableElement));
                    }
                }
            }
        }
        return json;
    }

    private JSONObject generateField(DocletEnvironment environment, VariableElement field) {
        JSONObject json = new JSONObject();
        json.put("name", field.getSimpleName().toString());
        json.put("type", typeToString(environment, field.asType()));
        if (field.getModifiers().contains(Modifier.STATIC)) {
            json.put("static", true);
        }
        return json;
    }

    private JSONObject generateMethod(DocletEnvironment environment, ExecutableElement method) {
        JSONObject json = new JSONObject();
        json.put("name", method.getSimpleName());
        if (method.getReturnType().getKind() != TypeKind.VOID) {
            json.put("return", typeToString(environment, method.getReturnType()));
        }
        if (method.getModifiers().contains(Modifier.STATIC)) {
            json.put("static", true);
        }
        generateParameters(environment, method, json);
        return json;
    }

    private JSONObject generateConstructor(DocletEnvironment environment, ExecutableElement constructor) {
        JSONObject json = new JSONObject();
        json.put("name", constructor.getSimpleName().toString());
        generateParameters(environment, constructor, json);
        return json;
    }

    private void generateParameters(DocletEnvironment environment, ExecutableElement element, JSONObject out) {
        for (VariableElement parameter : element.getParameters()) {
            JSONObject json = new JSONObject();
            json.put("name", parameter.getSimpleName().toString());
            json.put("type", typeToString(environment, parameter.asType()));
            out.append("parameters", json);
        }
    }

    private String typeToString(DocletEnvironment environment, TypeMirror type) {
        type = environment.getTypeUtils().erasure(type);
        return type instanceof DeclaredType t ? ((TypeElement) t.asElement()).getQualifiedName().toString() : type.toString().replaceAll("@.+?\\s+", "");
    }
}
