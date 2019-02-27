package xyz.nuel.righttime;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class ExerciseAdapter extends BaseAdapter {

    private ArrayList<Exercise> exercises;
    private Activity activity;

    public ExerciseAdapter(ArrayList<Exercise> source, Activity activity){
        this.exercises = source;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // view - the actual specific row
        if(convertView == null){
            // inflate - from xml to java object
            convertView = activity.getLayoutInflater().inflate(R.layout.exercise_view, null);
        }

        TextView name = convertView.findViewById(R.id.txtExerciseName);
        TextView duration = convertView.findViewById(R.id.txtDuration);
        TextView sets = convertView.findViewById(R.id.txtSets);
        TextView reps = convertView.findViewById(R.id.txtReps);

        Exercise exercise = exercises.get(position);
        name.setText(exercise.getName());
        duration.setText(exercise.getDuration() + "min.");
        sets.setText(exercise.getSets());
        reps.setText(exercise.getReps());

        return convertView;
    }
}
