package xyz.nuel.righttime;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoutineDetailActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceExercises;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Exercise exercise;
    private ListView listRoutineDet;
    private ExerciseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_detail);

        mDatabase = FirebaseDatabase.getInstance();
        mReferenceExercises = mDatabase.getReference("exercise");

        exercises = new ArrayList<>();
        adapter = new ExerciseAdapter(exercises, this);
        listRoutineDet = findViewById(R.id.listRoutineDet);

        exercise = new Exercise();

        mReferenceExercises.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                exercises.clear();
                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                    exercise = keyNode.getValue(Exercise.class);
                    exercises.add(exercise);
                }
                listRoutineDet.setAdapter(adapter);
                listRoutineDet.setOnItemClickListener(RoutineDetailActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Exercise ejercicio = exercises.get(position);
        Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra("ejercicio", ejercicio);
        startActivity(intent);
    }
}
