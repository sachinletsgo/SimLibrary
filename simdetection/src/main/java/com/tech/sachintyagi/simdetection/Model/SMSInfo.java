package com.tech.sachintyagi.simdetection.Model;

/**
 * Created by SachinTyagi on 9/21/17.
 */

public class SMSInfo {

    public int getSimNo() {
        return simNo;
    }

    public void setSimNo(int simNo) {
        this.simNo = simNo;
    }

    public int getSmsId() {
        return smsId;
    }

    public void setSmsId(int smsId) {
        this.smsId = smsId;
    }

    public String getSmsAddress() {
        return smsAddress;
    }

    public void setSmsAddress(String smsAddress) {
        this.smsAddress = smsAddress;
    }




    int simNo;
    int smsId;
    String smsAddress;

    public SMSInfo(int simNo, int smsId, String smsAddress ){
        this.smsAddress = smsAddress;
        this.smsId= smsId;
        this.simNo = simNo;


    }

}
