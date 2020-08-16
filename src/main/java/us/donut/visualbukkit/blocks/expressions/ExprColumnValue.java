package us.donut.visualbukkit.blocks.expressions;

import org.apache.commons.lang.WordUtils;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.statements.StatOpenDatabaseConnection;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.sql.ResultSet;

@Description({"The value in a column of a result set", "Returns: object"})
public class ExprColumnValue extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax(new ChoiceParameter("boolean", "number", "object", "string"), "value in column", String.class, "of", ResultSet.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent(StatOpenDatabaseConnection.class);
    }

    @Override
    public String toJava() {
        String type = WordUtils.capitalize(arg(0));
        if (type.equals("Number")) {
            type = "Double";
        }
        String value = arg(2) + ".get" + WordUtils.capitalize(type) + "(" + arg(1) + ")";
        return type.equals("Object") || type.equals("String") ? value : type + ".valueOf(" + value + ")";
    }
}
