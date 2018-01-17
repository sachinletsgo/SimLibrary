package com.tech.sachintyagi.simdetection.Master;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import com.tech.sachintyagi.simdetection.Call.GenralDevicesCall;
import com.tech.sachintyagi.simdetection.Call.SamsungDevicesCall;
import com.tech.sachintyagi.simdetection.Database.SIMDBManger;
import com.tech.sachintyagi.simdetection.Database.SIMinfo;
import com.tech.sachintyagi.simdetection.Model.CallInfo;
import com.tech.sachintyagi.simdetection.Model.SMSInfo;
import com.tech.sachintyagi.simdetection.SMS.GenralDevicesSMS;
import com.tech.sachintyagi.simdetection.SMS.SamsungDevicesSMS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SachinTyagi on 9/20/17.
 */

public class MultiSIM {
    Context context;
    public static SharedPreferences sharedpreferences;
    String MyPREFERENCES = "SIMDetection";

    public void init(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        setup();
    }

    public void setup() {
         SIMInfoDB();

    }

    public void SIMInfoDB() {


        SIMDBManger db = new SIMDBManger(context);

        // delete
        db.deleteContactAll();

        try {
            Cursor curCall = context.getContentResolver().query(
                    Uri.parse("content://telephony/siminfo"),
                    null, null, null, CallLog.Calls._ID + " DESC");


            /*
     _id
	icc_id
	sim_id
            * */

            if (curCall != null && curCall.getCount() > 0) {

                String id = "0", simid = "", iccValue = "";

                curCall.moveToNext();
                for (int i = 0; i < curCall.getCount(); i++) {


                    try {
                        int idColumn = curCall.getColumnIndex("_id");
                        id = curCall.getString(idColumn);

                    } catch (Exception e) {

                    }

                    try {
                        int idColumn = curCall.getColumnIndex("icc_id");
                        String icc = curCall.getString(idColumn);
                        icc = icc.replace("f", "");
                        iccValue = icc;//curCall.getString(idColumn);

                    } catch (Exception e) {

                    }
                    try {
                        int idColumn = curCall.getColumnIndex("sim_id");
                        simid = curCall.getString(idColumn);

                    } catch (Exception e) {

                    }

                    db.addContact(new SIMinfo(Integer.parseInt(id), simid, iccValue));

                    curCall.moveToNext();
                }


            }
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        } finally {
            db.close();
        }


    }


    public String letsStartSIM(int countMax) {

        SIMInfoDB();
        String SIM = "";

        String deviceMan = android.os.Build.MANUFACTURER;
        deviceMan = deviceMan.toLowerCase();
        if (deviceMan.contains("samsung")) {
            SIM = new SamsungDevicesCall().samsungDeviceSIM(context, countMax);
        } else
            SIM = new GenralDevicesCall().genralDeviceSIM(context, countMax);

        return SIM;
    }


    public List<CallInfo> letsStartSIM(int countMax, int test) {

        SIMInfoDB();
        List<CallInfo> SIM = new ArrayList<>();

        String deviceMan = android.os.Build.MANUFACTURER;
        deviceMan = deviceMan.toLowerCase();
        if (deviceMan.contains("samsung")) {
            SIM = new SamsungDevicesCall().samsungDeviceSIM(context, countMax, test);
        } else
            SIM = new GenralDevicesCall().genralDeviceSIM(context, countMax, test);

        return SIM;
    }


    public String letsStartSIMSMS(int countMax) {

        SIMInfoDB();
        String SIM = "";


        String deviceMan = android.os.Build.MANUFACTURER;
        deviceMan = deviceMan.toLowerCase();
        if (deviceMan.contains("samsung")) {
            new SamsungDevicesSMS();
        } else
            SIM = new GenralDevicesSMS().generalDeviceSIMSMS(context, countMax);

        return SIM;
    }

    public List<SMSInfo> letsStartSIMSMS(int countMax, int test) {

        SIMInfoDB();
        List<SMSInfo> SIM = new ArrayList<>();

        String deviceMan = android.os.Build.MANUFACTURER;
        deviceMan = deviceMan.toLowerCase();
        if (deviceMan.contains("samsung")) {
//            new SamsungDevicesCall();//.samsungDeviceSIM(context,countMax,test);
            SIM = new SamsungDevicesSMS().samsungSIMSMS(context, countMax, test);
        } else
            SIM = new GenralDevicesSMS().generalDeviceSIMSMS(context, countMax, test);

        return SIM;
    }

}
