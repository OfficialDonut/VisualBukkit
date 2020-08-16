package us.donut.visualbukkit.blocks;

import us.donut.visualbukkit.blocks.syntax.BlockParameter;
import us.donut.visualbukkit.util.DataConfig;

import java.util.ArrayList;
import java.util.List;

public interface CodeBlock {

    String toJava();

    List<BlockParameter> getParameters();

    default void update() {
        for (BlockParameter parameter : getParameters()) {
            parameter.update();
        }
    }

    default String arg(int i) {
        return getParameters().get(i).toJava();
    }

    default void saveTo(DataConfig config) {
        List<DataConfig> parameterConfigs = new ArrayList<>(getParameters().size());
        for (BlockParameter parameter : getParameters()) {
            DataConfig parameterConfig = new DataConfig();
            parameter.saveTo(parameterConfig);
            parameterConfigs.add(parameterConfig);
        }
        config.set("parameters", parameterConfigs);
    }

    default void loadFrom(DataConfig config) {
        List<DataConfig> parameterConfigs = config.getConfigList("parameters");
        List<BlockParameter> parameters = getParameters();
        for (int i = 0; i < parameterConfigs.size(); i++) {
            if (parameters.size() <= i) {
                break;
            }
            parameters.get(i).loadFrom(parameterConfigs.get(i));
        }
    }

    default String getIdentifier() {
        return getClass().getName().replace("us.donut.visualbukkit.blocks.", "");
    }
}
