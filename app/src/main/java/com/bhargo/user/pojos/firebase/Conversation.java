package com.bhargo.user.pojos.firebase;

import java.util.ArrayList;

public class Conversation {

    private ArrayList<ChatDetails> listMessageData;

    public Conversation(){
        listMessageData = new ArrayList<>();
    }


    public ArrayList<ChatDetails> getListMessageData() {
        return listMessageData;
    }

    public void setListMessageData(ArrayList<ChatDetails> listMessageData) {
        this.listMessageData = listMessageData;
    }
}
