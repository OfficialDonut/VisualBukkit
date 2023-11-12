package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.ui.PopOverSelector;

import java.util.Collection;

public abstract class ClassElementParameter<T> extends PopOverSelector<T> implements BlockParameter {

    public ClassElementParameter(ClassParameter classParameter) {
        disableProperty().bind(classParameter.valueProperty().isNull());
        setSelectAction(t -> UndoManager.current().execute(() -> setValue(t)));
        classParameter.valueProperty().addListener((observable, oldValue, newValue) -> {
            setValue(null);
            if (newValue != null) {
                getItemList().setAll(generateItems(newValue));
            }
        });
    }

    public abstract Collection<T> generateItems(ClassInfo classInfo);
}
