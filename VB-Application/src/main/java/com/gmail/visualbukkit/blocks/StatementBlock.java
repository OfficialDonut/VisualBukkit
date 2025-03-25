package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.definitions.core.*;
import com.gmail.visualbukkit.blocks.definitions.gui.StatOpenGUI;
import com.gmail.visualbukkit.blocks.definitions.gui.StatSetGUISlot;
import com.gmail.visualbukkit.project.*;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import java.net.URI;

public non-sealed abstract class StatementBlock extends Block {

    public StatementBlock() {
        getStyleClass().add("statement-block");

        ActionMenuItem copyItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.copy"), e -> CopyPasteManager.copyStatement(this));
        ActionMenuItem cutItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.cut"), e -> {
            CopyPasteManager.copyStatement(this);
            UndoManager.current().execute(this::delete);
        });
        ActionMenuItem deleteItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.delete"), e -> UndoManager.current().execute(this::delete));
        ActionMenuItem copyStackItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.copy_stack"), e -> CopyPasteManager.copyStatements(getParentStatementHolder().getStack(this)));
        ActionMenuItem cutStackItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.cut_stack"), e -> {
            CopyPasteManager.copyStatements(getParentStatementHolder().getStack(this));
            UndoManager.current().execute(() -> getParentStatementHolder().removeStack(this));
        });
        ActionMenuItem deleteStackItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.delete_stack"), e -> UndoManager.current().execute(() -> getParentStatementHolder().removeStack(this)));
        ActionMenuItem pasteBeforeItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.paste_before"), e -> UndoManager.current().execute(() -> getParentStatementHolder().addBefore(this, CopyPasteManager.pasteStatement())));
        ActionMenuItem pasteAfterItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.paste_after"), e -> UndoManager.current().execute(() -> getParentStatementHolder().addAfter(this, CopyPasteManager.pasteStatement())));
        ActionMenuItem javadocsItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.javadocs"), e -> {
            switch (this) {
                case StatAdvancedNumberLoop statAdvancedNumberLoop ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html"));
                case StatAttemptExecution statAttemptExecution ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/essential/exceptions/try.html"));
                case StatBreakLoop statBreakLoop ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/branch.html"));
                case StatCancelScheduledTask statCancelScheduledTask ->
                        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/scheduler/BukkitScheduler.html#cancelTask(int)"));
                case StatCommandReturn statCommandReturn ->
                        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/command/CommandSender.html#sendMessage(java.lang.String)"));
                case StatComment statComment ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/comments.html"));
                case StatContinueLoop statContinueLoop ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/branch.html"));
                case StatElseIfStatement statElseIfStatement ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html"));
                case StatElseStatement statElseStatement ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html"));
                case StatExecuteProcedure statExecuteProcedure ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html"));
                case StatFunctionReturn statFunctionReturn ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Function.html"));
                case StatHandleException statHandleException ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html"));
                case StatIfStatement statIfStatement ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html"));
                case StatJavaCode statJavaCode ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/index.html"));
                case StatListLoop statListLoop ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/8/docs/technotes/guides/language/foreach.html"));
                case StatMethod statMethod -> JavadocsManager.getStatJavadocs(statMethod);
                case StatNumberLoop statNumberLoop ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html"));
                case StatScheduleTask statScheduleTask ->
                        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/scheduler/BukkitScheduler.html#runTask(org.bukkit.plugin.java.JavaPlugin,java.lang.Runnable)"));
                case StatSetGlobalVariable statSetGlobalVariable ->
                        VisualBukkitApp.openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Variables"));
                case StatSetLocalVariable statSetLocalVariable ->
                        VisualBukkitApp.openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Variables"));
                case StatSetPersistentVariable statSetPersistentVariable ->
                        VisualBukkitApp.openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Variables"));
                case StatSetPlayerHeadSkin statSetPlayerHeadSkin ->
                        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/inventory/meta/SkullMeta.html#setOwner(java.lang.String)"));
                case StatSetPlayerSkin statSetPlayerSkin ->
                        VisualBukkitApp.openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/blob/master/VB-Application/src/main/java/com/gmail/visualbukkit/blocks/definitions/core/StatSetPlayerSkin.java"));
                case StatTabCompleteReturn statTabCompleteReturn ->
                        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/command/TabCompleter.html#onTabComplete(org.bukkit.command.CommandSender,org.bukkit.command.Command,java.lang.String,java.lang.String[])"));
                case StatWhileLoop statWhileLoop ->
                        VisualBukkitApp.openURI(URI.create("https://docs.oracle.com/javase/tutorial/java/nutsandbolts/while.html"));
                case StatOpenGUI statOpenGUI ->
                        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/entity/HumanEntity.html#openInventory(org.bukkit.inventory.Inventory)"));
                case StatSetGUISlot statSetGUISlot ->
                        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/inventory/Inventory.html#setItem(int,org.bukkit.inventory.ItemStack)"));
                default ->
                        VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.unavailable_javadocs"));
            }
        });
        pasteBeforeItem.disableProperty().bind(CopyPasteManager.statementCopiedProperty().not());
        pasteAfterItem.disableProperty().bind(pasteBeforeItem.disableProperty());
        getContextMenu().getItems().addAll(copyItem, cutItem, deleteItem, new SeparatorMenuItem(), copyStackItem, cutStackItem, deleteStackItem, new SeparatorMenuItem(), pasteBeforeItem, pasteAfterItem, new SeparatorMenuItem(), javadocsItem);

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
                setVisible(false);
                setManaged(false);
                e.consume();
            }
        });

        setOnDragDone(e -> {
            StatementConnector.hideCurrent();
            setVisible(true);
            setManaged(true);
            e.consume();
        });

        setOnDragOver(e -> {
            if (e.getGestureSource() instanceof StatementSource || e.getGestureSource() instanceof StatementBlock) {
                getParentStatementHolder().showConnector(this, 2 * e.getY() > getHeight());
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        setOnDragDropped(e -> {
            StatementBlock block = e.getGestureSource() instanceof StatementSource s ? s.getFactory().newBlock() : (StatementBlock) e.getGestureSource();
            StatementConnector.current().accept(block);
            e.setDropCompleted(true);
            e.consume();
        });
    }

    @SafeVarargs
    public final void checkForPrevious(Class<? extends StatementBlock>... classes) {
        StatementBlock block = getParentStatementHolder().getPrevious(this);
        if (block != null) {
            for (Class<?> clazz : classes) {
                if (clazz.isAssignableFrom(block.getClass())) {
                    return;
                }
            }
        }
        pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
    }

    public String generateDebugJava(BuildInfo buildInfo) {
        String exceptionVar = RandomStringUtils.randomAlphanumeric(16);
        String reportMethod = "Class.forName(\"com.gmail.visualbukkit.plugin.VisualBukkitPlugin\").getDeclaredMethod(\"reportException\", String.class, Throwable.class)";
        return "try { %s } catch (Exception $%s) { %s.invoke(null, \"%s\", $%s); }".formatted(generateJava(buildInfo), exceptionVar, reportMethod, getUUID(), exceptionVar);
    }

    public abstract String generateJava(BuildInfo buildInfo);

    @Override
    public void delete() {
        if (getParentStatementHolder() != null) {
            getParentStatementHolder().remove(this);
        }
    }

    public StatementHolder getParentStatementHolder() {
        return (StatementHolder) getParent();
    }

    @BlockDefinition(id = "unknown-statement", name = "Unknown Statement")
    public static class Unknown extends StatementBlock {

        private JSONObject json = new JSONObject();

        @Override
        public void updateState() {
            super.updateState();
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }

        @Override
        public String generateJava(BuildInfo buildInfo) {
            return "";
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
