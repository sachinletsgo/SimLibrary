package com.tech.sachintyagi.simdetection.Model;

/**
 * Created by SachinTyagi on 9/21/17.
 */

public class CallInfo {

    public int getSimNo() {
        return simNo;
    }

    public void setSimNo(int simNo) {
        this.simNo = simNo;
    }

    public int getCallId() {
        return callId;
    }

    public void setCallId(int callId) {
        this.callId = callId;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    int simNo;
    int callId;
    String callNumber;

    public CallInfo(int simNo, int callId, String callNumber ){
        this.callId = callId;
        this.callNumber= callNumber;
        this.simNo = simNo;


    }


}
