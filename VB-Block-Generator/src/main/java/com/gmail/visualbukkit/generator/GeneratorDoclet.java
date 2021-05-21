package com.gmail.visualbukkit.generator;

import com.gmail.visualbukkit.generator.options.BlacklistOption;
import com.gmail.visualbukkit.generator.options.IncludeDeprecatedOption;
import com.gmail.visualbukkit.generator.options.OutputDirectoryOption;
import com.gmail.visualbukkit.generator.options.PluginModuleOption;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class GeneratorDoclet implements Doclet {

    private BlockGenerator generator = new BlockGenerator();

    @Override
    public void init(Locale locale, Reporter reporter) {
        generator.setReporter(reporter);
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        try {
            generator.generate(environment);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getName() {
        return "VB-Block-Generator";
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Set.of(
                new OutputDirectoryOption() {
                    @Override
                    public boolean process(String option, List<String> arguments) {
                        generator.setOutputDir(Paths.get(arguments.get(0)));
                        return true;
                    }
                },
                new IncludeDeprecatedOption() {
                    @Override
                    public boolean process(String option, List<String> arguments) {
                        generator.setIncludeDeprecated(true);
                        return true;
                    }
                },
                new PluginModuleOption() {
                    @Override
                    public boolean process(String option, List<String> arguments) {
                        generator.setPluginModule(arguments.get(0));
                        return true;
                    }
                },
                new BlacklistOption() {
                    @Override
                    public boolean process(String option, List<String> arguments) {
                        generator.setBlacklistFile(Paths.get(arguments.get(0)));
                        return true;
                    }
                }
        );
    }
}
