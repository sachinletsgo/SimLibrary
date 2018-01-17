package sachintyagi.tech.com.simlibrary;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.tech.sachintyagi.simdetection.Master.MultiSIM;

/**
 * Created by sachinvt on 17/1/18.
 */

public class TestApplication extends Application {

        public static SharedPreferences sharedpreferences;
        public  static MultiSIM mSim;



        private static TestApplication sInstance;

        @Override
        public void onCreate() {
            super.onCreate();
            Context mContext = getApplicationContext();
            sInstance = this;
            sharedpreferences = getSharedPreferences("TestApplication", Context.MODE_PRIVATE);
            mSim = new MultiSIM();//
            mSim.init(TestApplication.this);
        }



        public static TestApplication getInstance() {
            return sInstance;
        }

        public static Context getAppContext() {
            return sInstance.getApplicationContext();
        }

        public static boolean isActivityVisible() {
            return !activityVisible;
        }

        public static void activityResumed() {
            activityVisible = true;
        }

        public static void activityPaused() {
            activityVisible = false;
        }

        private static boolean activityVisible;

        @Override
        public void onTerminate() {
            super.onTerminate();
        }

    }

