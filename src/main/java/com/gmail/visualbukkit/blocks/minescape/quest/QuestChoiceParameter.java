package com.gmail.visualbukkit.blocks.minescape.quest;

import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.util.JsonUrlReader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class QuestChoiceParameter extends ChoiceParameter {

    private static Map<String, List<String>> questValueParameters = new HashMap();
    private static List<String> questListValues = createQuestChoiceParameter();

    private static List<String> createQuestChoiceParameter(){
        List<String> questListValues = new ArrayList<>();
        try {
            JSONObject questData = JsonUrlReader.readJsonFromUrl("https://api.minescape.me/1.0/quests.php");
            JSONObject quests = questData.getJSONObject("quests");
            questListValues.addAll(quests.keySet());
            for (String quest : quests.keySet()) {
                List<String> values = quests.getJSONArray(quest).toList().stream().map(v -> quest + ":" + v).collect(Collectors.toList());
                questValueParameters.put(quest, values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questListValues;
    }

    public static List<String> getChoiceParameter(String quest){
        return questValueParameters.getOrDefault(quest, Collections.emptyList());
    }

    public static List<String> getQuestListValues() {
        return questListValues;
    }

    public QuestChoiceParameter() {
        super(questListValues, false);
    }
}
