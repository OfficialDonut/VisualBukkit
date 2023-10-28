package com.gmail.visualbukkit.doclet;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class VisualBukkitDoclet implements Doclet {

    private Path outputDirectory;
    private Reporter reporter;

    @Override
    public void init(Locale locale, Reporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public String getName() {
        return "Visual Bukkit";
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Collections.singleton(new OutputDirectoryOption());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_17;
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        return new JsonGenerator(outputDirectory, reporter).generate(environment);
    }

    private class OutputDirectoryOption implements Doclet.Option {

        @Override
        public int getArgumentCount() {
            return 1;
        }

        @Override
        public String getDescription() {
            return "the output directory";
        }

        @Override
        public Kind getKind() {
            return Kind.STANDARD;
        }

        @Override
        public List<String> getNames() {
            return Collections.singletonList("-d");
        }

        @Override
        public String getParameters() {
            return "<string>";
        }

        @Override
        public boolean process(String option, List<String> arguments) {
            outputDirectory = Paths.get(arguments.get(0));
            return true;
        }
    }
}
