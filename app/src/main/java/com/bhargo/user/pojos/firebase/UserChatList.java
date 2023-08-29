package com.bhargo.user.pojos.firebase;

import com.google.firebase.database.PropertyName;

public class UserChatList {

    private Object CreatedDate;
    private String LastMessageID;
    private String MessageType;
    private String Name;
    private String ReceiverID;
    private String ReceiverMobile;
    private String SenderID;
    private String SenderMobile;
    private String Text;
    private String UserType;
    private String filePath;
    private String fileName;
    private String msgType;
    private String userID;
    private long unreadcount;
    private String Image;


    public UserChatList() {
    }

    public UserChatList(Object createdDate, String lastMessageID, String messageType, String name, String receiverID, String receiverMobile, String senderID, String senderMobile, String text, String userType, String filePath, String fileName, String msgType, String userID, long unreadcount) {
        CreatedDate = createdDate;
        LastMessageID = lastMessageID;
        MessageType = messageType;
        Name = name;
        ReceiverID = receiverID;
        ReceiverMobile = receiverMobile;
        SenderID = senderID;
        SenderMobile = senderMobile;
        Text = text;
        UserType = userType;
        this.filePath = filePath;
        this.fileName = fileName;
        this.msgType = msgType;
        this.userID = userID;
        this.unreadcount = unreadcount;
    }

    @PropertyName("CreatedDate")
    public Object getCreatedDate() {
        return CreatedDate;
    }

    @PropertyName("CreatedDate")
    public void setCreatedDate(Object createdDate) {
        CreatedDate = createdDate;
    }

    @PropertyName("LastMessageID")
    public String getLastMessageID() {
        return LastMessageID;
    }

    @PropertyName("LastMessageID")
    public void setLastMessageID(String lastMessageID) {
        LastMessageID = lastMessageID;
    }

    @PropertyName("MessageType")
    public String getMessageType() {
        return MessageType;
    }

    @PropertyName("MessageType")
    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    @PropertyName("Name")
    public String getName() {
        return Name;
    }

    @PropertyName("Name")
    public void setName(String name) {
        Name = name;
    }

    @PropertyName("ReceiverID")
    public String getReceiverID() {
        return ReceiverID;
    }

    @PropertyName("ReceiverID")
    public void setReceiverID(String receiverID) {
        ReceiverID = receiverID;
    }

    @PropertyName("ReceiverMobile")
    public String getReceiverMobile() {
        return ReceiverMobile;
    }

    @PropertyName("ReceiverMobile")
    public void setReceiverMobile(String receiverMobile) {
        ReceiverMobile = receiverMobile;
    }

    @PropertyName("SenderID")
    public String getSenderID() {
        return SenderID;
    }

    @PropertyName("SenderID")
    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    @PropertyName("SenderMobile")
    public String getSenderMobile() {
        return SenderMobile;
    }

    @PropertyName("SenderMobile")
    public void setSenderMobile(String senderMobile) {
        SenderMobile = senderMobile;
    }

    @PropertyName("Text")
    public String getText() {
        return Text;
    }

    @PropertyName("Text")
    public void setText(String text) {
        Text = text;
    }

    @PropertyName("UserType")
    public String getUserType() {
        return UserType;
    }

    @PropertyName("UserType")
    public void setUserType(String userType) {
        UserType = userType;
    }

    @PropertyName("filePath")
    public String getFilePath() {
        return filePath;
    }

    @PropertyName("filePath")
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @PropertyName("fileName")
    public String getFileName() {
        return fileName;
    }

    @PropertyName("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @PropertyName("msgType")
    public String getMsgType() {
        return msgType;
    }

    @PropertyName("msgType")
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @PropertyName("userID")
    public String getUserID() {
        return userID;
    }

    @PropertyName("userID")
    public void setUserID(String userID) {
        this.userID = userID;
    }

    @PropertyName("unreadcount")
    public long getUnreadcount() {
        return unreadcount;
    }

    @PropertyName("unreadcount")
    public void setUnreadcount(long unreadcount) {
        this.unreadcount = unreadcount;
    }

    @PropertyName("Image")
    public String getImage() {
        return Image;
    }

    @PropertyName("Image")
    public void setImage(String image) {
        Image = image;
    }
}
