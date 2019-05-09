package xyz.nuel.righttime;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseFragment extends Fragment {

    private static final String DESC_PARAM = "desc";
    private static final String DESC_PARAM2 = "desc2";
    private String descripcion, imageID;
    private TextView textDesc;
    private ImageView imageV;


    public ExerciseFragment() {
        // Required empty public constructor
    }

    public static ExerciseFragment newInstance(String description, String imageID){
        ExerciseFragment ef = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString(DESC_PARAM, description);
        args.putString(DESC_PARAM2, imageID);
        ef.setArguments(args);
        return ef;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            descripcion = getArguments().getString(DESC_PARAM);
            imageID = getArguments().getString(DESC_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercise, container, false);
        textDesc = v.findViewById(R.id.description);
        imageV = v.findViewById(R.id.imagencita);
        textDesc.setText(descripcion);
        if (imageID.equals("Ejercicios Pubocoxigeos") || imageID.equals("Ejercicios Pubocoxigeos II") || imageID.equals("Ejercicios Pubocoxigeos III")) {
            imageV.setImageResource(R.drawable.appimage1);
        }
        else if (imageID.equals("Ejercicios con Pelicula") || imageID.equals("Ejercicios con Pelicula II") || imageID.equals("Ejercicios con Pelicula III")){
            imageV.setImageResource(R.drawable.appimage2);
        }
        else if (imageID.equals("Ejercicios con Gel") || imageID.equals("Ejercicios con Gel II") || imageID.equals("Ejercicios con Gel III")){
            imageV.setImageResource(R.drawable.appimage4);
        }
        else{
            imageV.setImageResource(R.drawable.appimage3);
        }
        return v;
    }

}
