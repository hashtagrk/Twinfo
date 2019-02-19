package com.example.mahe.bnavtutorial;

//Make changes to db entry as twitter isnt case sensitive.
//restrict use of enter .
//add fav button.
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class homefragment extends Fragment {

    //private static Context context;
    int flag_valid=0;
    String just="";
    EditText edt;
    ToggleButton tb;
    DatabaseHelper mDatabaseHelper;
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Button b=findVi

        final View vw = inflater.inflate(R.layout.fragment_home, container, false);
        Button b = vw.findViewById(R.id.button_home);
        mDatabaseHelper = new DatabaseHelper(getContext());
        mTextViewResult=vw.findViewById(R.id.tv);
        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
         tb =  vw.findViewById(R.id.togglebutton);

         tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()  {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked && buttonView.isPressed())
                {
                   AddData(just);

                }else if(buttonView.isPressed())
                {
                  mDatabaseHelper.deleteName(just);
                  toastMessage("Fav Removed");
                 // mTextViewResult.append(just);
                }
            }
        });



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewResult.setText("");
                edt = vw.findViewById(R.id.inp);
                String twt = edt.getText().toString();
                //mTextViewResult.append(twt);
                jsonParse(twt);
                if (check(twt) ) {
                    //&&tb.setOnCheckedChangeListener(null);
                    tb.setChecked(true);
                    //toastMessage("alreay");
                    //tb.setOnCheckedChangeListener();
                } else
                    //tb.setOnCheckedChangeListener(null);
                    tb.setChecked(false);
                //toastMessage("NOT there");
            }

        });
        //return inflater.inflate(R.layout.fragment_home,container,false);
        return vw;
    }
    public boolean check(String twitter_handle)
    {
        Cursor data = mDatabaseHelper.getData();
        try{
            while (data.moveToNext()) {

                if (data.getString(1) != null && data.getString(1).equalsIgnoreCase(twitter_handle+"\n")) {

                    return true;
                }

            }return false;
        }
        finally {
            if (data != null && !data.isClosed())
                data.close();
        }
    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            toastMessage("Fav Added");
        } else {
            toastMessage("Something went wrong");
        }
    }
    private void toastMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

        private void jsonParse(String twt) {

            String url = "http://api.peerreach.com/v1/user/lookup.json?screen_name="+twt;
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.has("screen_name")) {
                                    flag_valid=1;
                                    String obs = response.getString("screen_name") + "\n";
                                    mTextViewResult.append("Screen Name -" + obs);
                                    just = String.valueOf(obs);
                                    String lastup = response.getString("lastupdate") + "\n";
                                    mTextViewResult.append("Last Updated " + lastup);
                                    String frnds = response.getString("friends") + "\n";
                                    mTextViewResult.append("Friends - " + frnds);
                                    String gender = response.getString("gender") + "\n";
                                    mTextViewResult.append("Gender/Type - " + gender);


                                    String followers = response.getString("followers") + "\n";
                                    mTextViewResult.append("Followers - " + followers);
                                    mTextViewResult.append("PROFILES:" +"\n");
                                    JSONArray itemArray = response.getJSONArray("profiles");
                                    for (int i = 0; i < itemArray.length(); i++) {
                                        String value = itemArray.getString(i);
                                        mTextViewResult.append(value+"\n");
                                    }
                                    mTextViewResult.append("SUBJECTS TWEETED ON..."+"\n");
                                    JSONArray jsonArray = response.getJSONArray("subjects");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        //System.out.print( jsonArray.getJSONObject(i).toString());
                                        JSONObject pro = jsonArray.getJSONObject(i);
                                        String first = pro.getString("name");
                                        //String sec=pro.getString("region");
                                        mTextViewResult.append(first + "\n");
                                    }
                                }
                                else
                                {
                                    mTextViewResult.append("INVALID TWITTER HANDLE");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);

        }
    }

