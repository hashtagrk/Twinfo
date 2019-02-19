package com.example.mahe.bnavtutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class splash_home extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_home);
        progressBar =findViewById(R.id.profressbar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        Thread thread=new Thread()
        {
            @Override
            public void run() {
                try
                {
                    for(int i=0;i<100;i++)
                    {
                        progressBar.setProgress(i);
                        sleep(20);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
thread.start();

    }
}
