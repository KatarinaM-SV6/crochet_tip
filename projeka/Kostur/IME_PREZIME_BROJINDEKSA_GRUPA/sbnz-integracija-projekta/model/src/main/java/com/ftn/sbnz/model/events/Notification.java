package com.ftn.sbnz.model.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;


@Role(Role.Type.EVENT)
// @Timestamp("executionTime")
@Expires("24h")
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date executionTime;
    private Integer userId;
    private String type;
    private String notificationText;


    public Notification() {
    }

    public Notification(Integer userId, String notificationText, String type) {
        this.executionTime = new Date();
        this.userId = userId;
        this.notificationText = notificationText;
        this.type = type;
    }

    public Date getExecutionTime() {
        return this.executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNotificationText() {
        return this.notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
