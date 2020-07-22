package us.donut.visualbukkit.blocks;

import javafx.event.Event;

public abstract class ModifierBlock extends StatementBlock {

    protected ModifiableExpressionBlock<?> expressionBlock;

    public void init(ExpressionBlockInfo<?> expressionBlockInfo) {
        expressionBlock = (ModifiableExpressionBlock<?>) expressionBlockInfo.createBlock();
        expressionBlock.setOnContextMenuRequested(this::fireEvent);
        expressionBlock.setOnDragDetected(this::fireEvent);
        expressionBlock.setOnMouseMoved(Event::consume);
    }
}
