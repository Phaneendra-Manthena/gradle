package com.bhargo.user.pojos.firebase;

import com.google.firebase.database.PropertyName;

public class ChatDetails {

    private String ReceiverID;

    private String ReceiverMobile;

    private String SenderName;

    private Object CreatedDate;

    private boolean IsMsgSeen;

    private String Text;

    private String ISTestRimage;

    private String SenderID;

    private String ReceiverName;

    private String UserType;

    private String SenderMobile;

    private String MessageID;

    private String filePath;
    private String fileName;
    private String msgType;

    private String status;


    public ChatDetails() {
    }

    public ChatDetails(String receiverID, String receiverMobile, String senderName, String createdDate, boolean isMsgSeen, String text, String ISTestRimage, String senderID, String receiverName, String userType, String senderMobile, String messageID) {
        ReceiverID = receiverID;
        ReceiverMobile = receiverMobile;
        SenderName = senderName;
        CreatedDate = createdDate;
        this.IsMsgSeen = isMsgSeen;
        Text = text;
        this.ISTestRimage = ISTestRimage;
        SenderID = senderID;
        ReceiverName = receiverName;
        UserType = userType;
        SenderMobile = senderMobile;
        MessageID = messageID;
    }

    public ChatDetails(String senderName, String createdDate, boolean isMsgSeen, String text) {

        SenderName = senderName;
        CreatedDate = createdDate;
        IsMsgSeen = isMsgSeen;
        Text = text;

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
    @PropertyName("SenderName")
    public String getSenderName() {
        return SenderName;
    }
    @PropertyName("SenderName")
    public void setSenderName(String senderName) {
        SenderName = senderName;
    }
    @PropertyName("CreatedDate")
    public Object getCreatedDate() {
        return CreatedDate;
    }
    @PropertyName("CreatedDate")
    public void setCreatedDate(Object createdDate) {
        CreatedDate = createdDate;
    }
    @PropertyName("IsMsgSeen")
    public boolean isMsgSeen() {
        return IsMsgSeen;
    }

    @PropertyName("IsMsgSeen")
    public void setMsgSeen(boolean msgSeen) {
        IsMsgSeen = msgSeen;
    }
    @PropertyName("Text")
    public String getText() {
        return Text;
    }
    @PropertyName("Text")
    public void setText(String text) {
        Text = text;
    }
    @PropertyName("ISTestRimage")
    public String getISTestRimage() {
        return ISTestRimage;
    }
    @PropertyName("ISTestRimage")
    public void setISTestRimage(String ISTestRimage) {
        this.ISTestRimage = ISTestRimage;
    }
    @PropertyName("SenderID")
    public String getSenderID() {
        return SenderID;
    }
    @PropertyName("SenderID")
    public void setSenderID(String senderID) {
        SenderID = senderID;
    }
    @PropertyName("ReceiverName")
    public String getReceiverName() {
        return ReceiverName;
    }
    @PropertyName("ReceiverName")
    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }
    @PropertyName("UserType")
    public String getUserType() {
        return UserType;
    }
    @PropertyName("UserType")
    public void setUserType(String userType) {
        UserType = userType;
    }
    @PropertyName("SenderMobile")
    public String getSenderMobile() {
        return SenderMobile;
    }
    @PropertyName("SenderMobile")
    public void setSenderMobile(String senderMobile) {
        SenderMobile = senderMobile;
    }
    @PropertyName("MessageID")
    public String getMessageID() {
        return MessageID;
    }
    @PropertyName("MessageID")
    public void setMessageID(String messageID) {
        MessageID = messageID;
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
    @PropertyName("status")
    public String getStatus() {
        return status;
    }

    @PropertyName("status")
    public void setStatus(String status) {
        this.status = status;
    }

}
