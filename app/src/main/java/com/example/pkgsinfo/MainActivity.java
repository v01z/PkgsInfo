package com.example.pkgsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.example.pkgsinfo.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";

    // Used to load the 'pkgsinfo' library on application startup.
    static {
        System.loadLibrary("pkgsinfo");
    }

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(getFileTextBuffer("/proc/cpuinfo"));

        final PackageManager pm = getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Log.d(TAG, "Installed package :" + packageInfo.packageName);
            Log.d(TAG, "Source dir : " + packageInfo.sourceDir);
            Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
        }
        //
        tv.append("\nxxxxxxxxxxxxxxxxxxxxxx\n");
        tv.append("\nCount of installed pkgs: " + packages.size());
        tv.append("\nxxxxxxxxxxxxxxxxxxxxxx\n");
        //
        for (ApplicationInfo packageInfo : packages) {
            tv.append("\n\nInstalled package :" + packageInfo.packageName);
            tv.append("\nSource dir : " + packageInfo.sourceDir);
            tv.append("\nLaunch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
            tv.append("\n***********************\n");
        }
        tv.append("\n\n********* Have a nice day! *****************\n\n");

// the getLaunchIntentForPackage returns an intent that you can use with startActivity()

    }

    /**
     * A native method that is implemented by the 'pkgsinfo' native library,
     * which is packaged with this application.
     */
    public native String getFileTextBuffer(String str);
}