package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Name("Else If Statement")
@Description("Runs code if the previous if statement failed and a condition is true")
@Category(StatementCategory.CONTROLS)
public class StatElseIf extends StatIf {

    @Override
    protected Syntax init() {
        return new Syntax("else if", boolean.class);
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        if (previous != null && !(previous instanceof StatIf)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public String toJava() {
        return "else if (" + arg(0) + ") " + "{" + getChildJava() + "}";
    }
}
