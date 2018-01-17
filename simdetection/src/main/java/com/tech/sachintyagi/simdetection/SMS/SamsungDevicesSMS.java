package com.tech.sachintyagi.simdetection.SMS;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import com.tech.sachintyagi.simdetection.Database.SIMDBManger;
import com.tech.sachintyagi.simdetection.Database.SIMinfo;
import com.tech.sachintyagi.simdetection.Model.SMSInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SachinTyagi on 9/21/17.
 */

public class SamsungDevicesSMS {



    public String samsungSIMSMS(Context context, int MAX) {


        String ValueOfSim = "";
        SIMDBManger db = new SIMDBManger(context);
        try {
            Cursor curCall = context.getContentResolver().query(
//                    Telephony.Sms.Inbox,
//                    Uri.parse("content://sms/conversations"),
                    Uri.parse("content://sms/"),
//                    CallLog.Calls.CONTENT_URI,
                    null, null, null, CallLog.Calls._ID + " DESC");


            if (curCall != null && curCall.getCount() > 0) {

                curCall.moveToNext();
                String msg = "";
                for (int i = 0; i < MAX; i++) {

                    for (String s : new String[]{"sim_id", "simid", "subscription_id", "sub_id"}) {
                        int id = curCall.getColumnIndex(s);
                        msg = curCall.getString(curCall.getColumnIndex("body"));
                        if (msg.length() > 40) {
                            msg = msg.substring(0, 40);
                            msg = msg + "...";
                        }
                        if (id >= 0) {

                            String value = curCall.getString(id);
                            if (value != null)
                                if (value.length() > 4) {
                                    value = value.replace("f", "");
                                    List<SIMinfo> sim = db.getAllContacts(value, "sub");
                                    if (sim.size() > 0) {
                                        Log.d("SIMTEST",
                                                msg + "\n" + curCall.getString(curCall.getColumnIndex("address")) +
                                                        " SIM " + sim.get(0).get_sim_no());

                                        ValueOfSim = ValueOfSim + "\n\n" +
                                                curCall.getString(curCall.getColumnIndex("address")) +
                                                "        SIM " + sim.get(0).get_sim_no();
                                        break;
                                    }

                                } else {

                                    List<SIMinfo> sim = db.getAllContacts(value,  "s");
                                    if (sim.size() > 0) {
                                        Log.d("SIMTEST", msg + "\n" + curCall.getString(curCall.getColumnIndex("address")) +
                                                " SIM " + sim.get(0).get_sim_no());

                                        ValueOfSim = ValueOfSim + "\n\n" +
                                                curCall.getString(curCall.getColumnIndex("address")) +
                                                "        SIM " + sim.get(0).get_sim_no();
                                        break;
                                    }

                                }

                        }
                    }

                    curCall.moveToNext();
                }

                return ValueOfSim;


            }
        } catch (Exception e) {
            Log.d("SIMTEST", "error " + e.getMessage());
        }finally {
            db.close();
        }
        return "";


    }



    public List<SMSInfo> samsungSIMSMS(Context context, int MAX, int temp) {

        List<SMSInfo> smsInfo = new ArrayList<>();


        SIMDBManger db = new SIMDBManger(context);
        try {
            Cursor curCall = context.getContentResolver().query(
//                    Telephony.Sms.Inbox,
//                    Uri.parse("content://sms/conversations"),
                    Uri.parse("content://sms/"),
//                    CallLog.Calls.CONTENT_URI,
                    null, null, null, "date DESC");


            if (curCall != null && curCall.getCount() > 0) {

                curCall.moveToNext();

                String msg = "";
                for (int i = 0; i < MAX; i++) {

                    for (String s : new String[]{"sim_id", "simid", "subscription_id", "sub_id"}) {
                        int id = curCall.getColumnIndex(s);
                        msg = curCall.getString(curCall.getColumnIndex("body"));
                        if (msg.length() > 40) {
                            msg = msg.substring(0, 40);
                            msg = msg + "...";
                        }
                        if (id >= 0) {

                            String value = curCall.getString(id);
                            if (value != null)
                                if (value.length() > 4) {
                                    value = value.replace("f", "");
                                    List<SIMinfo> sim = db.getAllContacts(value, "sub");
                                    if (sim.size() > 0) {
                                        Log.d("SIMTEST",
                                                msg + "\n" + curCall.getString(curCall.getColumnIndex("address")) +
                                                        " SIM " + sim.get(0).get_sim_no());

//                                        ValueOfSim = ValueOfSim + "\n\n" +
//                                                curCall.getString(curCall.getColumnIndex("address")) +
//                                                "        SIM " + sim.get(0).get_sim_no();

                                        smsInfo.add(

                                                new SMSInfo(Integer.parseInt(sim.get(0).get_sim_no()),
                                                        curCall.getInt(curCall.getColumnIndex("_id")),//CallLog.Calls._ID)),
                                                        curCall.getString(curCall.getColumnIndex("address")))
                                        );
                                        break;
                                    }

                                } else {

                                    List<SIMinfo> sim = db.getAllContacts(value,  "s");
                                    if (sim.size() > 0) {
                                        Log.d("SIMTEST", msg + "\n" + curCall.getString(curCall.getColumnIndex("address")) +
                                                " SIM " + sim.get(0).get_sim_no());

//                                        ValueOfSim = ValueOfSim + "\n\n" +
//                                                curCall.getString(curCall.getColumnIndex("address")) +
//                                                "        SIM " + sim.get(0).get_sim_no();
                                        smsInfo.add(

                                                new SMSInfo(Integer.parseInt(sim.get(0).get_sim_no()),
                                                        curCall.getInt(curCall.getColumnIndex("_id")),
//                                                        curCall.getInt(curCall.getColumnIndex(CallLog.Calls._ID)),
                                                        curCall.getString(curCall.getColumnIndex("address")))
                                        );

                                        break;
                                    }

                                }

                        }
                    }

                    curCall.moveToNext();
                }

                return smsInfo;


            }
        } catch (Exception e) {
            Log.d("SIMTEST", "error " + e.getMessage());
        }
        finally {
            db.close();
        }
        return smsInfo;


    }

}
