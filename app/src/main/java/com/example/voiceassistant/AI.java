package com.example.voiceassistant;

import android.support.v4.util.Consumer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
            put("есть ли жизнь на марсе", "Науке это неизвестно...");
            put("кто президент России", "Не знаю, посмотрите телевизор");
            put("какого цвета небо", "Цвета счастья :)");
        }};

        userQuestion = userQuestion.toLowerCase();

        final ArrayList<String> answers = new ArrayList<>();

        for (String databaseQuestion : database.keySet()){
            if (userQuestion.contains(databaseQuestion))
                answers.add(database.get(databaseQuestion));
        }

        Pattern cityPattern = Pattern.compile("какая погода в городе (\\p{L}+)", Pattern.CASE_INSENSITIVE);
        Pattern datePattern = Pattern.compile("какой сегодня день", Pattern.CASE_INSENSITIVE);
        Pattern timePattern = Pattern.compile("сколько сейчас времени", Pattern.CASE_INSENSITIVE);
        Pattern aphorismPattern = Pattern.compile("скажи афоризм", Pattern.CASE_INSENSITIVE);

        Matcher matcher = cityPattern.matcher(userQuestion);
        Matcher dateMatcher = datePattern.matcher(userQuestion);
        Matcher timeMatcher = timePattern.matcher(userQuestion);
        Matcher aphorismMatcher = aphorismPattern.matcher(userQuestion);

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
        else if (dateMatcher.find()){
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("ru", "RU"));
            String strDate = dateFormat.format(date);
            callback.accept(strDate);
        }
        else if (timeMatcher.find()){
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm", new Locale("ru", "RU"));
            String strDate = dateFormat.format(date);
            callback.accept(strDate);
        }
        else if (aphorismMatcher.find()){
            Aphorism.get(new Consumer<String>() {
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
