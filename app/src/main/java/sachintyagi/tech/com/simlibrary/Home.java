package sachintyagi.tech.com.simlibrary;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tech.sachintyagi.simdetection.Database.SIMinfo;
import com.tech.sachintyagi.simdetection.Model.CallInfo;
import com.tech.sachintyagi.simdetection.Model.SMSInfo;

import java.util.List;

import static sachintyagi.tech.com.simlibrary.TestApplication.mSim;

public class Home extends AppCompatActivity {

    Activity  activity;
   final int MY_PERMISSIONS_REQUEST_READ_CALL=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity= Home.this;
       checkPermission();
    }


    @Override
    protected void onResume() {
        super.onResume();
        testLib();
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_CONTACTS)) {

            } else {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_CALL_LOG},
                        MY_PERMISSIONS_REQUEST_READ_CALL);

            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(Home.this, "Permission is required", Toast.LENGTH_LONG).show();

                }
                return;
            }

        }
    }


    public void testLib() {
        List<SMSInfo> smsInfo = mSim.letsStartSIMSMS(10, 0);
        if (smsInfo.size() > 0) ;
        for (int i = 0; i < smsInfo.size(); i++) {
            Log.d("Library : ", smsInfo.get(i).getSmsAddress() + " " + smsInfo.get(i).getSmsId() + " " +
                    smsInfo.get(i).getSimNo());
        }

        List<CallInfo> simInfo = mSim.letsStartSIM(10, 0);
        if (simInfo.size() > 0) ;
        for (int i = 0; i < simInfo.size(); i++) {
            Log.d("Library : ", simInfo.get(i).getCallId() + " " + simInfo.get(i).getCallNumber() + " " +
                    simInfo.get(i).getSimNo());
        }


    }


}
