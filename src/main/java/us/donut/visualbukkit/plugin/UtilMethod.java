package us.donut.visualbukkit.plugin;

public enum UtilMethod {

    ADD_TO_OBJECT("addToObject"),
    CHECK_EQUALS("checkEquals"),
    CREATE_POTION("createPotion"),
    GET_RANDOM_ELEMENT("getRandomElement"),
    GET_SHAPED_RECIPE("getShapedRecipe"),
    IS_NUMBER("isNumber"),
    IS_TYPE("isType"),
    REMOVE_FROM_OBJECT("removeFromObject"),
    SET_DURABILITY("setDurability"),
    SET_ITEM_BLOCK_STATE("setItemBlockState"),
    SET_ITEM_LORE("setItemLore"),
    SET_ITEM_NAME("setItemName"),
    SET_OWNING_PLAYER("setOwningPlayer"),
    SET_SKIN("setSkin");

    private String methodName;

    UtilMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}
