package com.intecore.angularcontacts.dto;

import java.util.List;

public class UiResponse {
    private Object data;
    private List<String> infoMsgs;
    private List<String> warningMsgs;
    private List<String> errorMsgs;
    private UiResponseStatus status;
    
    public UiResponse() {
    }
    
    public UiResponse(Object data, UiResponseStatus status) {
        this.data = data;
        this.status = status;
    }
    
    public UiResponse(Object data, UiResponseStatus status, List<String> errorMsgs) {
        this.data = data;
        this.status = status;
        this.errorMsgs = errorMsgs;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<String> getInfoMsgs() {
        return this.infoMsgs;
    }

    public void setInfoMsgs(List<String> infoMsgs) {
        this.infoMsgs = infoMsgs;
    }

    public List<String> getWarningMsgs() {
        return this.warningMsgs;
    }

    public void setWarningMsgs(List<String> warningMsgs) {
        this.warningMsgs = warningMsgs;
    }

    public List<String> getErrorMsgs() {
        return this.errorMsgs;
    }

    public void setErrorMsgs(List<String> errorMsgs) {
        this.errorMsgs = errorMsgs;
    }
    
    public UiResponseStatus getStatus() {
        return this.status;
    }

    public void setStatus(UiResponseStatus status) {
        this.status = status;
    }
}
