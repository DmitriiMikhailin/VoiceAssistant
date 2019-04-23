package com.example.voiceassistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AI {
    public static String getAnswer(String userQuestion){
        Map<String, String> database = new HashMap<String, String>() {{
            put("привет", "И вам здрасте");
            put("как дела", "Да вроде ничего");
            put("чем занимаешься", "Отвечаю на дурацкие вопросы");
            put("как тебя зовут", "Я - голосовой помощник Иннокентий");
            put("кто тебя создал", "Мой создатель");
        }};

        userQuestion = userQuestion.toLowerCase();

        ArrayList<String> answers = new ArrayList<>();

        for (String databaseQuestion : database.keySet()){
            if (userQuestion.contains(databaseQuestion))
                answers.add(database.get(databaseQuestion));
        }

        if (answers.isEmpty())
            return "Ок, понял";

        return String.join(", ", answers);
    }
}
