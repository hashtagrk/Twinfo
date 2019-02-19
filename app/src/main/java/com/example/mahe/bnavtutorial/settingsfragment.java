package com.example.mahe.bnavtutorial;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class settingsfragment extends Fragment implements View.OnClickListener {
    DatabaseHelper mDatabaseHelper;
    NetworkInfo wifiCheck;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_settings, container, false);
        Button b1=vw.findViewById(R.id.intern_con);
        Button clr= vw.findViewById(R.id.clrall);
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper = new DatabaseHelper(getContext());
                mDatabaseHelper.clearall();
            }
        });
        Button hlp=vw.findViewById(R.id.help);
        hlp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(), helpActivity.class);
                startActivity(i);
            }
        });

        Button fd=vw.findViewById(R.id.fdbck);
        fd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(getContext(),feedbackActivity.class);
                startActivity(j);
            }
        });
        b1.setOnClickListener(this);
        ConnectivityManager connectionManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        ToggleButton toggleButton=vw.findViewById(R.id.toggle);
        if (wifiCheck.isConnected()) {
            toggleButton.setChecked(true);
        }
        else toggleButton.setChecked(false);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
              {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && buttonView.isPressed())
                        {
                            WifiManager wifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            wifiManager.setWifiEnabled(true);
                        }
                        else if(buttonView.isPressed())
                        {
                            WifiManager wifiManager = (WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            wifiManager.setWifiEnabled(false);
                        }
                    }
              }
        );
        return vw;

    }
    protected void createNetErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You need internet connection for this app. Please turn on mobile network or Wi-Fi in Settings.")
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(i);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                settingsfragment.this.onDestroy();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.intern_con:
                createNetErrorDialog();
        }
    }
}
