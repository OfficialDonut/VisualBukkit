package us.donut.visualbukkit.blocks;

import org.apache.commons.lang.WordUtils;

public enum StatementCategory {

    STRUCTURES, CONTROLS, MODIFIERS, PLAYER, WORLD, IO("I/O"), MISC, ALL;

    private String label;

    StatementCategory() {
        label = WordUtils.capitalize(name().toLowerCase());
    }

    StatementCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
