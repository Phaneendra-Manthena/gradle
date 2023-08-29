package com.bhargo.user.pojos.firebase;

public class Data {

    private String user;
    private int icon;
    private String body;
    private String title;
    private String sent;
    private String chat_type;

    public Data(String user, int icon, String body, String title, String sent,String chat_type) {
        this.user = user;
        this.icon = icon;
        this.body = body;
        this.title = title;
        this.sent = sent;
        this.chat_type = chat_type;
    }

    public Data() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getChat_type() {
        return chat_type;
    }

    public void setChat_type(String chat_type) {
        this.chat_type = chat_type;
    }
}
