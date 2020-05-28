package com.example.komal.withu;

public class portalDetails {

    String authorName;
    String Tag;
    String Question;
    String text;

    portalDetails(String user, String tag, String question,String link){
        this.authorName=user;
        this.Tag=tag;
        this.Question=question;
        this.text=link;
    }

    public String getQuestion() {
        return Question;
    }

    public String getUserName() {
        return authorName;
    }

    public String getTag() {
        return Tag;
    }

    public String getText() {
        return text;
    }
}
