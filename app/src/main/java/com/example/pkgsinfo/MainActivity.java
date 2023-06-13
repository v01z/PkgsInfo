package com.example.pkgsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pkgsinfo.databinding.ActivityMainBinding;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myLogs";

    // Used to load the 'pkgsinfo' library on application startup.
    static {
        System.loadLibrary("pkgsinfo");
    }

    private ActivityMainBinding binding;

    TextView tv, editText;
    private void addInfo(){
       final PackageManager pm = getPackageManager();
       List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

       tv.setText("");

       tv.append("\nxxxxxxxxxxxxxxxxxxxxxx\n");
       tv.append("\nCount of installed pkgs: " + packages.size());
       tv.append("\nxxxxxxxxxxxxxxxxxxxxxx\n");

       String querriedName = editText.getText().toString();


       if(querriedName.equals("*"))
       {

           for(ApplicationInfo packageInfo : packages) {
               tv.append("\n\nInstalled package :" + packageInfo.packageName);
               tv.append("\nSource dir : " + packageInfo.sourceDir);
               tv.append("\nClass name: " + packageInfo.className);
               tv.append("\nLaunch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
               tv.append("\n***********************\n");
           }
       }
       else {

           for (ApplicationInfo packageInfo : packages) {

               String currentPackageName = packageInfo.packageName;
               if (currentPackageName.contains(querriedName)) {
                   tv.append("\n\nInstalled package :" + currentPackageName);
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

        // Example of a call to a native method
        tv = binding.sampleText;
        tv.setMovementMethod(new ScrollingMovementMethod());
        //tv.setText(getFileTextBuffer("/proc/cpuinfo"));
        tv.setText("youtube");


        editText = findViewById(R.id.edit_query);
        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addInfo();
            }
        });

        addInfo();
        /*
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("google.com", 80));
        }catch(IOException e)
        {
            Log.d(TAG, "socket.connect()");
            //
        }

        //System.out.println(socket.getLocalAddress());
        tv.append("\nIP: " + socket.getLocalAddress());

        try {

        socket.close();
        }catch(IOException e)
        {
            Log.d(TAG, "socket.close()");
        }
        */
        //

        tv.append("\n\n********* Have a nice day! *****************\n\n");


    }

    /**
     * A native method that is implemented by the 'pkgsinfo' native library,
     * which is packaged with this application.
     */
    public native String getFileTextBuffer(String str);
}