package com.example.voiceassistant;

import java.util.Date;

public class Message {
    public String text;
    public Date date;
    public boolean isSent;

    public Message(String text, boolean isSent) {
        this.text = text;
        this.date = new Date();
        this.isSent = isSent;
    }
}
