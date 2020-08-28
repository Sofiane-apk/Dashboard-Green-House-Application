package com.example.smart_green_house;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends AppCompatActivity {


    DatabaseReference   dref ;
    DatabaseReference   myRef ;
    TextView text_temperature, text_humidity, text_pump,text_soilmoisture;
    String temp;
    String hum;
    String mpump;
    String soilmoisture;
    int col1 = Color.parseColor("#00ff2a"), col2 = Color.parseColor("#f00505"),col3 = Color.parseColor("#626e6a"),col4 = Color.parseColor("#386374");
    int cnt;
    private Button mFirebaseBtn;
    private Button mFirebaseBtnFan;
    private Button mFirebaseBtnLamp;
    private Switch mFirebaseSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_temperature = findViewById(R.id.temperature);
        text_humidity = findViewById(R.id.humidity);
        text_pump = findViewById(R.id.pump);
        mFirebaseBtn  = findViewById(R.id.firebaseBtn);
        mFirebaseBtnFan  = findViewById(R.id.firebaseBtnFan);
        mFirebaseBtnLamp  = findViewById(R.id.firebaseBtnLamp);
        mFirebaseSwitch  = findViewById(R.id.switch1);
        text_soilmoisture = findViewById(R.id.textSoil);


//-------------------------------------------Switch Manual ----------------------------
        dref = FirebaseDatabase.getInstance().getReference();
        myRef = dref.child("Manual");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists()) {
                    String Manual = dataSnapshot.getValue(String.class);
                    if (Manual.equals("1")) {
                        mFirebaseSwitch.setChecked(true);
                    } else {
                        mFirebaseSwitch.setChecked(false);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
        mFirebaseSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( mFirebaseSwitch.isChecked()){
                    dref.child("Manual").setValue("1");
                                                 }
                else{
                    dref.child("Manual").setValue("0");
                    }
            }});
//-------------------------------------------Btn Light ----------------------------

        myRef = dref.child("onlight");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists()) {
                    String light = dataSnapshot.getValue(String.class);
                    if (light.equals("1")) {
                        mFirebaseBtnLamp.setTextColor(col1);
                    } else {
                        mFirebaseBtnLamp.setTextColor(col2);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
        mFirebaseBtnLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dref = FirebaseDatabase.getInstance().getReference();


                if((cnt % 2) == 0 ){   dref.child("onlight").setValue("1") ;

                    mFirebaseBtnLamp.setTextColor(col1);
                    cnt++;
                }
                else {
                    dref.child("onlight").setValue("0") ;

                    mFirebaseBtnLamp.setTextColor(col2);
                    cnt++;
                }

            }
        });



//-------------------------------------------Btn FAN  ----------------------------
        dref = FirebaseDatabase.getInstance().getReference();
        myRef = dref.child("onfan");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists()) {
                    String fan = dataSnapshot.getValue(String.class);
                    if (fan.equals("1")) {
                        mFirebaseBtnFan.setTextColor(col1);
                    } else {
                        mFirebaseBtnFan.setTextColor(col2);
                    }
                                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
        mFirebaseBtnFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((cnt % 2) == 0) {
                    dref.child("onfan").setValue("1");
                    mFirebaseBtnFan.setTextColor(col1);
                    cnt++;
                } else {
                    dref.child("onfan").setValue("0");

                    mFirebaseBtnFan.setTextColor(col2);
                    cnt++;
                }
            }
        });
//-------------------------------------------Btn pump  ----------------------------
        dref = FirebaseDatabase.getInstance().getReference();
        myRef = dref.child("onpump");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists()) {
                    String pump = dataSnapshot.getValue(String.class);
                    if (pump.equals("1")) {
                        mFirebaseBtn.setTextColor(col1);
                    } else {
                        mFirebaseBtn.setTextColor(col2);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((cnt % 2) == 0 ){   dref.child("onpump").setValue("1") ;

                                       mFirebaseBtn.setTextColor(col1);
                                      cnt++;
                                  }
                else {
                    dref.child("onpump").setValue("0") ;

                    mFirebaseBtn.setTextColor(col2);
                    cnt++;
                     }

            }
        });
// -------------------------------------------humidity status ----------------------------
        dref = FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                hum = dataSnapshot.child("h").getValue().toString();
                text_humidity.setText(hum + "%");

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });

//-------------------------------------------pump status ----------------------------
        dref = FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                mpump = dataSnapshot.child("pump").getValue().toString();
                int pump = Integer.parseInt(mpump);
                if (pump == 0) {
                    text_pump.setText("OFF");
                    text_pump.setTextColor(col2);
                }
                else {
                    text_pump.setText("ON");
                    text_pump.setTextColor(col1);
                }
            }
            @Override
            public void onCancelled( DatabaseError databaseError) {
            }
        });
//-------------------------------------------soil status ----------------------------
        dref = FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                soilmoisture = dataSnapshot.child("pump").getValue().toString();
                int pump = Integer.parseInt(soilmoisture);
                if (pump == 0) {
                    text_soilmoisture.setText("No watering required");
                    text_soilmoisture.setTextColor(col4);

                }
                else {
                    text_soilmoisture.setText("Watering required");
                    text_soilmoisture.setTextColor(col3);
                }
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });

//-------------------------------------------temperature status ----------------------------
        dref = FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                temp = dataSnapshot.child("t").getValue().toString();
                text_temperature.setText(temp + "C");
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
//-----------------------------------exit when back button is pressed-------------------------
    }
        private boolean doubleBackToExitPressedOnce = false;

        @Override
        public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();

                finishAffinity();

            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }


}