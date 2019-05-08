package xyz.nuel.righttime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class ExerciseActivity extends AppCompatActivity implements NotesFragment.OnFragmentInteractionListener {

    private Exercise ejercicio;
    private TextView nombre, set, rep, tiempo;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private Properties savedAccount;

    private static final String SAVED_ACCOUNT = "savedAccount.xml";
    private int currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        currentScore = -20;

        nombre = findViewById(R.id.name);
        set = findViewById(R.id.sets);
        rep = findViewById(R.id.repetitions);
        tiempo = findViewById(R.id.duration);

        Intent intent = getIntent();
        ejercicio = (Exercise) intent.getSerializableExtra("ejercicio");

        nombre.setText(ejercicio.getName());
        set.setText(ejercicio.getSets());
        rep.setText(ejercicio.getReps());
        tiempo.setText(ejercicio.getDuration() + " min.");

        ExerciseFragment ef = ExerciseFragment.newInstance(ejercicio.getDescription());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, ef, "ExerciseFragment");
        transaction.commit();

    }

    public void loadDescription(View v){
        FragmentManager manager = getSupportFragmentManager();
        Fragment nf = manager.findFragmentByTag("NotesFragment");
        if(nf != null){
            ExerciseFragment ef = ExerciseFragment.newInstance(ejercicio.getDescription());
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(nf);
            transaction.add(R.id.container, ef, "ExerciseFragment");
            transaction.commit();
        }
    }

    public void loadNotes(View v){
        FragmentManager manager = getSupportFragmentManager();
        Fragment ef = manager.findFragmentByTag("ExerciseFragment");
        if(ef != null){
            NotesFragment nf = NotesFragment.newInstance(Integer.parseInt(ejercicio.getDuration()));
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(ef);
            transaction.add(R.id.container, nf, "NotesFragment");
            transaction.commit();
        }

    }

    @Override
    public void fragmentCallback(boolean done) {
        if(done){

            mDatabase = FirebaseDatabase.getInstance();
            mReference = mDatabase.getReference("userdata/" + getSavedUID() + "/score");
            mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    currentScore = Integer.parseInt(dataSnapshot.getValue().toString());
                    mReference.setValue(currentScore + ejercicio.getPoints());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            addMinutes(Integer.parseInt(ejercicio.getDuration()));

            Toast.makeText(this, "You've earned " + ejercicio.getPoints() + " points!", Toast.LENGTH_SHORT).show();

            Intent retIntent = new Intent();
            setResult(RESULT_OK, retIntent);
            finish();

        }
    }

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

    private void addMinutes(int duration){

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);
        int minutes = 0;



        if(file.exists()) {
            try {
                FileInputStream fis = openFileInput(SAVED_ACCOUNT);
                savedAccount.loadFromXML(fis);
                minutes = Integer.parseInt(savedAccount.get("minutes").toString());
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        savedAccount.put("minutes", (minutes + duration) + "");
        wrFile();
    }

    private void wrFile(){
        try {
            FileOutputStream fos = openFileOutput(SAVED_ACCOUNT, Context.MODE_PRIVATE);
            savedAccount.storeToXML(fos, null);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}