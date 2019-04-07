package xyz.nuel.righttime;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseFragment extends Fragment {

    private static final String DESC_PARAM = "desc";
    private String descripcion;
    private TextView textDesc;


    public ExerciseFragment() {
        // Required empty public constructor
    }

    public static ExerciseFragment newInstance(String description){
        ExerciseFragment ef = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString(DESC_PARAM, description);
        ef.setArguments(args);
        return ef;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            descripcion = getArguments().getString(DESC_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercise, container, false);
        textDesc = v.findViewById(R.id.description);
        textDesc.setText(descripcion);
        return v;
    }

}
