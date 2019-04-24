package com.example.voiceassistant;

import android.support.v4.util.Consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AI {
    public static void getAnswer(String userQuestion, final Consumer<String> callback){
        Map<String, String> database = new HashMap<String, String>() {{
            put("привет", "И вам здрасте");
            put("как дела", "Да вроде ничего");
            put("чем занимаешься", "Отвечаю на дурацкие вопросы");
            put("как тебя зовут", "Я - голосовой помощник Иннокентий");
            put("кто тебя создал", "Мой создатель");
        }};

        userQuestion = userQuestion.toLowerCase();

        final ArrayList<String> answers = new ArrayList<>();

        for (String databaseQuestion : database.keySet()){
            if (userQuestion.contains(databaseQuestion))
                answers.add(database.get(databaseQuestion));
        }

        Pattern cityPattern = Pattern.compile("какая погода в городе (\\p{L}+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = cityPattern.matcher(userQuestion);
        if (matcher.find()){
            String cityName = matcher.group(1);
            Weather.get(cityName, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    answers.add(s);
                    callback.accept(String.join(", ", answers));
                }
            });
        }
        else {
            if (answers.isEmpty()){
                callback.accept("Ок");
                return;
            }
            callback.accept(String.join(", ", answers));
        }
    }
}
