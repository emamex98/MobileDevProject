package xyz.nuel.righttime;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExerciseActivity extends AppCompatActivity {

    private Exercise ejercicio;
    private TextView nombre, set, rep, tiempo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);


        nombre = findViewById(R.id.name);
        set = findViewById(R.id.sets);
        rep = findViewById(R.id.repetitions);
        tiempo = findViewById(R.id.duration);

        Intent intent = getIntent();
        ejercicio = (Exercise) intent.getSerializableExtra("ejercicio");

        nombre.setText(ejercicio.getName());
        set.setText(ejercicio.getSets());
        rep.setText(ejercicio.getReps());
        tiempo.setText(ejercicio.getDuration());

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
            NotesFragment nf = NotesFragment.newInstance(ejercicio.getNotes());
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(ef);
            transaction.add(R.id.container, nf, "NotesFragment");
            transaction.commit();
        }

    }
}
