package us.donut.visualbukkit.blocks.syntax;

import javafx.scene.text.Text;

public class Syntax {

    public static final Object LINE_SEPARATOR = new Object();
    private Object[] components;

    public Syntax(Object... components) {
        this.components = components;
        for (int i = 0; i < components.length; i++) {
            Object component = components[i];
            if (component instanceof String) {
                components[i] = new Text((String) component);
            } else if (component instanceof String[]) {
                String[] strings = (String[]) component;
                components[i] = strings.length == 2 ? new BinaryChoiceParameter(strings[0], strings[1]) : new ChoiceParameter(strings);
            } else if (component instanceof Class) {
                components[i] = new ExpressionParameter((Class<?>) component);
            }
        }
    }

    public Object[] getComponents() {
        return components;
    }
}
