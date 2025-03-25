package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.definitions.core.*;
import com.gmail.visualbukkit.blocks.definitions.gui.*;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.project.CopyPasteManager;
import com.gmail.visualbukkit.project.JavadocsManager;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import org.json.JSONObject;

import java.net.URI;

public non-sealed abstract class ExpressionBlock extends Block {

    private static final PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");
    public static final SimpleBooleanProperty DRAGGING_PROPERTY = new SimpleBooleanProperty();

    public ExpressionBlock() {
        getStyleClass().add("expression-block");

        getContextMenu().getItems().addAll(
                new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.copy"), e -> CopyPasteManager.copyExpression(this)),
                new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.cut"), e -> {
                    CopyPasteManager.copyExpression(this);
                    UndoManager.current().execute(this::delete);
                }),
                new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.delete"), e -> UndoManager.current().execute(this::delete)),
                new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.javadocs"), e -> {
                    switch (this) {
                        case ExprBoolean exprBoolean ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html"));
                        case ExprBooleanLogic exprBooleanLogic ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html"));
                        case ExprColoredString exprColoredString ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/ChatColor.html"));
                        case ExprCommandArgument exprCommandArgument ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/command/Command.html"));
                        case ExprCommandArguments exprCommandArguments ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/command/Command.html"));
                        case ExprCommandSender exprCommandSender ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/command/CommandSender.html"));
                        case ExprConditionalExpression exprConditionalExpression ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html"));
                        case ExprConsumer exprConsumer ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html"));
                        case ExprConsumerInput exprConsumerInput ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html"));
                        case ExprCurrentEvent exprCurrentEvent ->
                                JavadocsManager.getCompJavaDocs(exprCurrentEvent.getPluginComponentBlock().getPluginComponent());
                        case ExprEscapeSequence exprEscapeSequence ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/data/characters.html"));
                        case ExprExecutionException exprExecutionException ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html"));
                        case ExprField exprField -> JavadocsManager.getExprJavadocs(exprField);
                        case ExprFunctionArgument exprFunctionArgument ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html"));
                        case ExprFunctionArguments exprFunctionArguments ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html"));
                        case ExprFunctionValue exprFunctionValue ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html"));
                        case ExprGlobalVariable exprGlobalVariable ->
                                VisualBukkitApp.openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Variables"));
                        case ExprHashMap exprHashMap ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html"));
                        case ExprHashSet exprHashSet ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html"));
                        case ExprHexColoredString exprHexColoredString ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/ChatColor.html"));
                        case ExprImmutableList exprImmutableList ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/List.html"));
                        case ExprIsClass exprIsClass ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html"));
                        case ExprIsEqual exprIsEqual ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html"));
                        case ExprIsNull exprIsNull ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Objects.html#isNull(java.lang.Object)"));
                        case ExprItemStack exprItemStack ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/inventory/ItemStack.html"));
                        case ExprJavaCode exprJavaCode ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/index.html"));
                        case ExprList exprList ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/List.html"));
                        case ExprLocalVariable exprLocalVariable ->
                                VisualBukkitApp.openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Variables"));
                        case ExprLoopNumber exprLoopNumber ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html"));
                        case ExprLoopValue exprLoopValue ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html"));
                        case ExprMath exprMath ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op1.html"));
                        case ExprMethod exprMethod -> JavadocsManager.getExprJavadocs(exprMethod);
                        case ExprMultilineString exprMultilineString ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/data/strings/multiline.html"));
                        case ExprNegateBoolean exprNegateBoolean ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html"));
                        case ExprNewObject exprNewObject ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/javaOO/objectcreation.html"));
                        case ExprNull exprNull ->
                                VisualBukkitApp.openURI(URI.create("https://www.upwork.com/resources/what-is-null-in-java"));
                        case ExprNumber exprNumber ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html"));
                        case ExprNumberComparison exprNumberComparison ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html"));
                        case ExprPersistentVariable exprPersistentVariable ->
                                VisualBukkitApp.openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Variables"));
                        case ExprProcedureArgument exprProcedureArgument ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html"));
                        case ExprProcedureArguments exprProcedureArguments ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html"));
                        case ExprRawString exprRawString ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/data/strings/index.html"));
                        case ExprSerializedItemStack exprSerializedItemStack ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/inventory/ItemStack.html"));
                        case ExprString exprString ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html"));
                        case ExprStringConcatenation exprStringConcatenation ->
                                VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#concat(java.lang.String)"));
                        case ExprThisPlugin exprThisPlugin ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/plugin/java/JavaPlugin.html"));
                        case ExprGUIClickedInventory exprGUIClickedInventory ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/event/inventory/InventoryClickEvent.html#getClickedInventory()"));
                        case ExprGUIClickedItemStack exprGUIClickedItemStack ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/event/inventory/InventoryClickEvent.html#getCurrentItem()"));
                        case ExprGUIClickedSlot exprGUIClickedSlot ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/event/inventory/InventoryClickEvent.html#getSlot()"));
                        case ExprGUIClicker exprGUIClicker ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/event/inventory/InventoryClickEvent.html#getWhoClicked()"));
                        case ExprGUIClickType exprGUIClickType ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/event/inventory/InventoryClickEvent.html#getAction()"));
                        case ExprGUIInventory exprGUIInventory ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/event/inventory/InventoryClickEvent.html#getView()"));
                        case ExprGUIInventoryClickEvent exprGUIInventoryClickEvent ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/event/inventory/InventoryClickEvent.html"));
                        case ExprGUIPlayer exprGUIPlayer ->
                                VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/entity/Player.html"));
                        default ->
                                VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.unavailable_javadocs"));
                    }
                }));

        setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                Image image = snapshot(snapshotParameters, new WritableImage((int) Math.min(getWidth(), 500), (int) Math.min(getHeight(), 500)));
                dragboard.setDragView(image);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragboard.setContent(content);
                setDisable(true);
                DRAGGING_PROPERTY.set(true);
                e.consume();
            }
        });

        setOnDragDone(e -> {
            setDisable(false);
            DRAGGING_PROPERTY.set(false);
            e.consume();
        });
    }

    @Override
    public void updateState() {
        super.updateState();
        int level = 0;
        Parent parent = getParent();
        while (parent != null) {
            if (parent instanceof ExpressionBlock) {
                level++;
            }
            parent = parent.getParent();
        }
        pseudoClassStateChanged(NESTED_STYLE_CLASS, level % 2 == 1);
    }

    public abstract String generateJava(BuildInfo buildInfo);

    public abstract ClassInfo getReturnType();

    @Override
    public void delete() {
        if (getExpressionParameter() != null) {
            getExpressionParameter().deleteExpression();
        }
    }

    public ExpressionParameter getExpressionParameter() {
        return (ExpressionParameter) getParent();
    }

    @BlockDefinition(id = "unknown-expression", name = "Unknown Expression")
    public static class Unknown extends ExpressionBlock {

        private JSONObject json = new JSONObject();

        @Override
        public void updateState() {
            super.updateState();
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }

        @Override
        public String generateJava(BuildInfo buildInfo) {
            return "((Object) null)";
        }

        @Override
        public ClassInfo getReturnType() {
            return ClassInfo.of(Object.class);
        }

        @Override
        public JSONObject serialize() {
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            this.json = json;
        }
    }
}
