package xyz.nuel.righttime;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

public class HomeActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private TextView greeting, txtScore, txtLevel;
    private ListView listRoutine;
    private ProgressBar pg;
    private ArrayAdapter<String> adapter;

    private int lastScore, progress;
    private String minutes;
    private long level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lastScore = 0;

        greeting = findViewById(R.id.txtWelcome);
        greeting.setText("Welcome, " + getSavedName() + "!");

        txtScore = findViewById(R.id.txtScore);
        txtLevel = findViewById(R.id.txtLevel);
        pg = findViewById(R.id.progressBar);

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("userdata/" + getSavedUID() + "/score");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String score = dataSnapshot.getValue().toString();
                txtScore.setText("Score: " + score);

                mDatabase = FirebaseDatabase.getInstance();
                mReference = mDatabase.getReference("userdata/" + getSavedUID() + "/time");

                String min = getSavedMinutes();
                mReference.setValue("" + min);

                if(lastScore !=0){
                    progress = Integer.parseInt(score) - lastScore;
                } else {
                    lastScore = Integer.parseInt(score);
                }

                level = Math.round(Math.floor(Integer.parseInt(score)/10));
                txtLevel.setText("Level: " + level);
                pg.setProgress(((int)level * 10)%101);

                getPaidStatus(getSavedUID());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listRoutine = findViewById(R.id.listRoutines);

        String[] data = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        listRoutine.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listRoutine.setItemChecked(3, true);
        listRoutine.setAdapter(adapter);
        listRoutine.setOnItemClickListener(this);

        Button  mon = findViewById(R.id.btMon),
                tue = findViewById(R.id.btTue),
                wed = findViewById(R.id.btWed),
                thu = findViewById(R.id.btThu),
                fri = findViewById(R.id.btFri),
                sat = findViewById(R.id.btSat),
                sun = findViewById(R.id.btSun);

        mon.setVisibility(View.INVISIBLE);
        tue.setVisibility(View.INVISIBLE);
        wed.setVisibility(View.INVISIBLE);
        thu.setVisibility(View.INVISIBLE);
        fri.setVisibility(View.INVISIBLE);
        sat.setVisibility(View.INVISIBLE);
        sun.setVisibility(View.INVISIBLE);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("u");
        String formattedDate = df.format(c);
        int weekday = Integer.parseInt(formattedDate);

        switch (weekday){
            case 1:
                mon.setVisibility(View.VISIBLE);
                break;
            case 2:
                tue.setVisibility(View.VISIBLE);
                break;
            case 3:
                wed.setVisibility(View.VISIBLE);
                break;
            case 4:
                thu.setVisibility(View.VISIBLE);
                break;
            case 5:
                fri.setVisibility(View.VISIBLE);
                break;
            case 6:
                sat.setVisibility(View.VISIBLE);
                break;
            case 7:
                sun.setVisibility(View.VISIBLE);
                break;
        }

    }

    private String getSavedName(){

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        if(file.exists()){
            try {
                FileInputStream fis = openFileInput(SAVED_ACCOUNT);
                savedAccount.loadFromXML(fis);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return savedAccount.get("name") + "";

        }

        return "friend";
    }

    private String getSavedEmail(){

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        if(file.exists()){
            try {
                FileInputStream fis = openFileInput(SAVED_ACCOUNT);
                savedAccount.loadFromXML(fis);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return savedAccount.get("email") + "";

        }

        return null;
    }

    private String getSavedMinutes(){

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        if(file.exists()) {
            try {
                FileInputStream fis = openFileInput(SAVED_ACCOUNT);
                savedAccount.loadFromXML(fis);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return savedAccount.get("minutes") + "";
        }

        return null;
    }

    public void logOut(View v){

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        if(file.delete())
        {
            Toast.makeText(this,"Logged out successfully.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Error logging out.", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentito = new Intent(this, RoutineDetailActivity.class);
        startActivity(intentito);
    }

    // Codigo se repite
    private int getSavedAccount(){

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        if(file.exists()){
            try {
                FileInputStream fis = openFileInput(SAVED_ACCOUNT);
                savedAccount.loadFromXML(fis);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String result = savedAccount.get("remember") + "";
            return Integer.parseInt(result);

        }

        return -1;
    }

    // Codigo se repite
    private String getSavedUID(){

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        if(file.exists()){
            try {
                FileInputStream fis = openFileInput(SAVED_ACCOUNT);
                savedAccount.loadFromXML(fis);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return savedAccount.get("uid") + "";

        }

        return null;
    }

    public void changeLoginB(){
        Intent intentitoB = new Intent(this, PasscodeActivity.class);
        startActivity(intentitoB);
    }

    public void launchStats(View v){
        Intent intent = new Intent(this, StatsActivity.class);
        intent.putExtra("progress", String.valueOf(progress));
        startActivity(intent);
    }

    private void getPaidStatus(String uid){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("userdata/" + uid + "/paid");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.wtf("DEBUG",  "level = " + level);
                Log.wtf("DEBUG", "paid = " + dataSnapshot.getValue().toString());
                if(level > 0){
                    if(Boolean.parseBoolean(dataSnapshot.getValue().toString()) == false){
                        Intent intentPay = new Intent(HomeActivity.this, SuscriptionActivity.class);
                        startActivity(intentPay);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
