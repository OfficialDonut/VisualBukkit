package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ParentBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Name("Else Statement")
@Description("Runs code if the previous if statement failed")
@Category(StatementCategory.CONTROLS)
public class StatElse extends ParentBlock {

    @Override
    protected Syntax init() {
        return new Syntax("else        ");
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
        return "else {" + getChildJava() + "}";
    }
}
