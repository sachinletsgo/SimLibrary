package com.tech.sachintyagi.simdetection.Call;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.tech.sachintyagi.simdetection.Database.SIMDBManger;
import com.tech.sachintyagi.simdetection.Database.SIMinfo;
import com.tech.sachintyagi.simdetection.Model.CallInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SachinTyagi on 9/20/17.
 */

public class SamsungDevicesCall {




    @TargetApi(23)
    public String samsungDeviceSIM(Context context, int MAX) {


//        if (Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission(context, Manifest.permission_group.PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return "";
//        }

        String ValueOfSim = "";
        SIMDBManger db = new SIMDBManger(context);
        try {
            Cursor curCall = context.getContentResolver().query(
                    Uri.parse("content://logs/call"),
                    null, null, null, CallLog.Calls._ID + " DESC");


            if (curCall != null && curCall.getCount() > 0) {

                curCall.moveToNext();



                for (int i = 0; i < MAX; i++) {

                    for (String s : new String[]{"sim_id", "simid", "subscription_id", "sub_id"}) {
                        int id = curCall.getColumnIndex(s);
                        if (id >= 0) {

                            String value = curCall.getString(id);
                            if (value != null)
                                if (value.length() > 4) {
                                    value = value.replace("f", "");
                                    List<SIMinfo> sim = db.getAllContacts(value, "sub");//getAllContacts();// getAll(value, "sub");
                                    if (sim.size() > 0) {
                                        Log.d("SIMTEST", curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)) +
                                                " SIM " + sim.get(0).get_sim_no());

                                        ValueOfSim = ValueOfSim + "\n" +
                                                curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)) +
                                                "        SIM " + sim.get(0).get_sim_no();
                                        break;
                                    }

                                } else {
                                    List<SIMinfo> sim = db.getAllContacts(value, "sim_id");
//                                    List<SIMinfo> sim = getAll(value, "s");
                                    if (sim.size() > 0) {
                                        Log.d("SIMTEST", curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)) +
                                                " SIM " + sim.get(0).get_sim_no());

                                        ValueOfSim = ValueOfSim + "\n" +
                                                curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)) +
                                                "        SIM " + sim.get(0).get_sim_no();
                                        break;
                                    }

                                }

                        }
                    }

                    curCall.moveToNext();
                }


//                tvSim.setText(ValueOfSim);
                //               value = ValueOfSim;
                return ValueOfSim;

            }
        } catch (Exception e) {
            Log.d("SIMTEST", "error " + e.getMessage());

        }
        finally {
            db.close();
        }

        return "";
    }


    @TargetApi(23)
    public List<CallInfo> samsungDeviceSIM(Context context, int MAX, int returntype) {

        List<CallInfo> callInfos = new ArrayList<>();

//        if (Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission(context, Manifest.permission_group.PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return callInfos;
//        }

        SIMDBManger db = new SIMDBManger(context);
        try {
            Cursor curCall = context.getContentResolver().query(
                    Uri.parse("content://logs/call"),
                    null, null, null, CallLog.Calls._ID + " DESC");


            if (curCall != null && curCall.getCount() > 0) {

                curCall.moveToNext();



                for (int i = 0; i < MAX; i++) {

                    for (String s : new String[]{"sim_id", "simid", "subscription_id", "sub_id"}) {
                        int id = curCall.getColumnIndex(s);
                        if (id >= 0) {

                            String value = curCall.getString(id);
                            if (value != null)
                                if (value.length() > 4) {
                                    value = value.replace("f", "");
                                    List<SIMinfo> sim = db.getAllContacts(value, "sub");
                                    if (sim.size() > 0) {
                                        Log.d("SIMTEST", curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)) +
                                                " SIM " + sim.get(0).get_sim_no());

//                                        ValueOfSim = ValueOfSim + "\n" +
//                                                curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)) +
//                                                "        SIM " + sim.get(0).get_sim_no();
                                        callInfos.add(

                                                new CallInfo(Integer.parseInt(sim.get(0).get_sim_no()),
                                                        curCall.getInt(curCall.getColumnIndex(CallLog.Calls._ID)),
                                                        curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)))
                                        );

                                        break;
                                    }

                                } else {
                                    List<SIMinfo> sim = db.getAllContacts(value, "sim_id");
//                                    List<SIMinfo> sim = getAll(value, "s");
                                    if (sim.size() > 0) {
                                        Log.d("SIMTEST", curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)) +
                                                " SIM " + sim.get(0).get_sim_no());

//                                        ValueOfSim = ValueOfSim + "\n" +
//                                                curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)) +
//                                                "        SIM " + sim.get(0).get_sim_no();

                                        callInfos.add(

                                                new CallInfo(Integer.parseInt(sim.get(0).get_sim_no()),
                                                        curCall.getInt(curCall.getColumnIndex(CallLog.Calls._ID)),
                                                        curCall.getString(curCall.getColumnIndex(CallLog.Calls.NUMBER)))
                                        );
                                        break;
                                    }

                                }

                        }
                    }

                    curCall.moveToNext();
                }


//                tvSim.setText(ValueOfSim);
                //               value = ValueOfSim;
//                return ValueOfSim;
                return callInfos;

            }
        } catch (Exception e) {
            Log.d("SIMTEST", "error " + e.getMessage());

        }finally {
            db.close();
        }

        return callInfos;
    }




}
