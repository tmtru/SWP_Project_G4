package model;

import java.sql.Timestamp;

public class Message {

    private int id;
    private int senderId;
    private int receiverId;
    private String content;
    private Timestamp timestamp;
    private boolean isRead;
    private int nhatroID;
    private Account sendAccount;

    private Account receiverAccount;
  
    public Account getSendAccount() {
        return sendAccount;
    }

    public void setSendAccount(Account sendAccount) {
        this.sendAccount = sendAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public int getNhatroID() {
        return nhatroID;
    }

    public void setNhatroID(int nhatroID) {
        this.nhatroID = nhatroID;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId + ", content=" + content + ", timestamp=" + timestamp + ", isRead=" + isRead + '}';
    }

}
