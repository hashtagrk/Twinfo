package com.example.mahe.bnavtutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class feedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ImageButton bck=findViewById(R.id.gobck1);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name=findViewById(R.id.edtname);
                String nm=name.getText().toString();
                EditText em=findViewById(R.id.edtemail);
                String email=em.getText().toString();
                RadioGroup rg1=findViewById(R.id.rg1);
                int SelectedId1=rg1.getCheckedRadioButtonId();
                RadioButton rdb1=findViewById(SelectedId1);

                RadioGroup rg2=findViewById(R.id.rg2);
                int SelectedId2=rg2.getCheckedRadioButtonId();
                RadioButton rdb2=findViewById(SelectedId2);

                RadioGroup rg3=findViewById(R.id.rg3);
                int SelectedId3=rg3.getCheckedRadioButtonId();
                RadioButton rdb3=findViewById(SelectedId3);

                EditText fdb=findViewById(R.id.edtfdb);
                String fdbck=fdb.getText().toString();
                if(nm.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"ENTER NAME",Toast.LENGTH_SHORT).show();
                }
                else if(email.indexOf("@")==-1)
                {
                    Toast.makeText(getApplicationContext(),"ENTER A VALID email",Toast.LENGTH_SHORT).show();
                }
                else if(SelectedId1==-1 || SelectedId2==-1 || SelectedId3==-1)
                {
                    Toast.makeText(getApplicationContext(),"Please answer all questions",Toast.LENGTH_SHORT).show();
                }
                else if(fdbck.length()>140)
                {
                    Toast.makeText(getApplicationContext(),"feedback exceeds 140 char",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String finaltext=nm+"\n"+email+"\n"+rdb1.getText()+"\n"+rdb2.getText()+"\n"+rdb3.getText()+"\n"+fdbck;
                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"raj.khanzode1998@gmail.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, finaltext);
                    emailIntent.setType("message/rfc822");
                    try {
                        startActivity(Intent.createChooser(emailIntent,
                                "Send email using..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(),
                                "No email clients installed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
