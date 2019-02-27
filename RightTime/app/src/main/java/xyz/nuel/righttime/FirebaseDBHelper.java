package xyz.nuel.righttime;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDBHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceExercises;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    /*public interface DataStatus{
        void DataIsLoaded(List<Exercise> exercises, List<String> keys);
        void DataIsInserted();
        void DatsIsUpdated();
        void DataIsDeleted();
    }*/

    public FirebaseDBHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceExercises = mDatabase.getReference("exercise");

        mReferenceExercises.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                exercises.clear();
                List<String> keys = new ArrayList<>();

                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Exercise exercise = keyNode.getValue(Exercise.class);
                    exercises.add(exercise);
                }
                //dataStatus.DataIsLoaded(exercises, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void readExercises(){//final DataStatus dataStatus){

    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }
}

















