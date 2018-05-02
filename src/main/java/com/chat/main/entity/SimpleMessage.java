package com.chat.main.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SimpleMessage {
    private String id;

    private String message;

    private String sender;

    private String receiver;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT +8")
    private Date createDt;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT +8")
    private Date updateDt;
    
    /*--------额外字段--------*/
    private Boolean isSelf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

	public Boolean getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(Boolean isSelf) {
		this.isSelf = isSelf;
	}
    
}