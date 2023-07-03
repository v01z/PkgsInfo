package com.example.pkgsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pkgsinfo.databinding.ActivityMainBinding;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PN_Logs";

    // Used to load the 'pkgsinfo' library on application startup.
    static {
        System.loadLibrary("pkgsinfo");
    }

    private ActivityMainBinding binding;

    TextView tv, editText;
    private void addInfo() {
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        tv.setText("");

        tv.append("\nxxxxxxxxxxxxxxxxxxxxxx\n");
        tv.append("\n" + nativeUname(5) +"\n"); // magic
        tv.append("\nxxxxxxxxxxxxxxxxxxxxxx\n");
        tv.append("\nCount of installed pkgs: " + packages.size());
        tv.append("\nxxxxxxxxxxxxxxxxxxxxxx\n");

        String querriedName = editText.getText().toString().toLowerCase();


        if (querriedName.equals("*")) {

            for (ApplicationInfo packageInfo : packages) {
                tv.append("\n\nInstalled package :" + packageInfo.packageName);
                tv.append("\nSource dir : " + packageInfo.sourceDir);
                tv.append("\nClass name: " + packageInfo.className);
                tv.append("\nLaunch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
                tv.append("\n***********************\n");
            }
        } else {

            for (ApplicationInfo packageInfo : packages) {

                String fullInfo = (packageInfo.packageName + packageInfo.sourceDir +
                        packageInfo.className + pm.getLaunchIntentForPackage(packageInfo.packageName)).toLowerCase();

                if(fullInfo.contains(querriedName))
                 {
                    tv.append("\n\nInstalled package :" + packageInfo.packageName);
                    tv.append("\nSource dir : " + packageInfo.sourceDir);
                    tv.append("\nClass name: " + packageInfo.className);
                    tv.append("\nLaunch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
                    tv.append("\n***********************\n");
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tv = binding.sampleText;
        tv.setMovementMethod(new ScrollingMovementMethod());


        editText = findViewById(R.id.edit_query);
        editText.requestFocus();
        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals(":ls"))
                {
                    Log.d(TAG, "Equals :ls");

                    String fileList = getFileList("/storage/emulated/0");

                    Log.d(TAG, fileList);

                    //tv.setText(getFileList("."));
                    tv.setText(fileList);
                }
                else{
                    addInfo();
                }
            }
        });

        Button androidSettingsButton = findViewById(R.id.android_settings_button);
        androidSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                finish();
            }
        });
        addInfo();

        tv.append("\n\n********* Have a nice day! *****************\n\n");

    }

    /**
     * A native method that is implemented by the 'pkgsinfo' native library,
     * which is packaged with this application.
     */
    public native String getFileList(String path);
    public native String getFileTextBuffer(String filepath);
    public native String nativeUname(int query);
}