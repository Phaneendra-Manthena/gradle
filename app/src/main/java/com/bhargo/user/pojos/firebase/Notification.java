package com.bhargo.user.pojos.firebase;

public class Notification {


    private String MessageId;
    private String SenderId;
    private String SenderName;
    private String GroupId;
    private String GroupName;
    private String GroupIcon;
    private String SessionId;
    private String SessionName;
    private String Message;
    private String MessageType;
    private String Title;
    private String FileName;
    private String FilePath;
    private String TimeStamp;
    private String Status;
    private String UserID;
    private String PostId;
    private String UnreadCount;


    private String TrDate;
    private String IsActive;

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupIcon() {
        return GroupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        GroupIcon = groupIcon;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getSessionName() {
        return SessionName;
    }

    public void setSessionName(String sessionName) {
        SessionName = sessionName;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getUnreadCount() {
        return UnreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        UnreadCount = unreadCount;
    }

    public String getTrDate() {
        return TrDate;
    }

    public void setTrDate(String trDate) {
        TrDate = trDate;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    @Override
    public String toString() {

        return SessionName;
    }
}
