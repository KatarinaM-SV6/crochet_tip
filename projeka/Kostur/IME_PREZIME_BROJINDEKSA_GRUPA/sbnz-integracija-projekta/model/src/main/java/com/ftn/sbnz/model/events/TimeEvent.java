package com.ftn.sbnz.model.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import com.ftn.sbnz.model.models.ActionType;

@Role(Role.Type.EVENT)
// @Timestamp("executionTime")
@Expires("24h")
public class TimeEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date executionTime;
    private Integer userId;
    private ActionType actionType;

    public TimeEvent() {
        super();
    }

    public TimeEvent(Integer userId, ActionType actionType) {
        this.executionTime = new Date();
        this.userId = userId;
        this.actionType = actionType;
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

    public ActionType getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

}