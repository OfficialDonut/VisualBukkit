package us.donut.visualbukkit.blocks.statements;

import javassist.*;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.PluginBuilder;

import java.time.Duration;
import java.util.UUID;

@Description("Executes code after some time")
public class StatExecuteLater extends ParentBlock {

    private static CtClass runnableClass;

    static {
        try {
            runnableClass = PluginBuilder.getClassPool().get("java.lang.Runnable");
        } catch (NotFoundException e) {
            VisualBukkit.displayException("Failed to get runnable class", e);
        }
    }

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("execute after", Duration.class);
    }

    @Override
    public String toJava() {
        try {
            CtClass mainClass = PluginBuilder.getCurrentMainClass();
            String name = "a" + UUID.randomUUID().toString().replace("-", "");

            CtMethod method = CtMethod.make("private void " + name + "() {" + getChildJava() + "}", mainClass);
            mainClass.addMethod(method);

            CtClass innerClass = mainClass.makeNestedClass(name, true);
            innerClass.addInterface(runnableClass);
            innerClass.addField(CtField.make(mainClass.getName() + " plugin;", innerClass));
            String constructor = "public " + name + "(" + mainClass.getName() + " plugin) {this.plugin=plugin;}";
            innerClass.addConstructor(CtNewConstructor.make(constructor, innerClass));
            innerClass.addMethod(CtMethod.make("public void run() {plugin." + name + "();}", innerClass));

            String instantiation = "new " + innerClass.getName().replace("$", ".") + "(this)";
            return "Bukkit.getScheduler().runTaskLater(this, " + instantiation + "," + arg(0) + ".getSeconds()*20);";
        } catch (CannotCompileException e) {
            throw new IllegalStateException(e);
        }
    }
}
