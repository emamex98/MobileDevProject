package xyz.nuel.righttime;


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
public class NotesFragment extends Fragment {

    private static final String NOTE_PARAM = "note";
    private String notas;
    private TextView textNote;


    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance(String notas){
        NotesFragment nf = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(NOTE_PARAM, notas);
        nf.setArguments(args);
        return nf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            notas = getArguments().getString(NOTE_PARAM);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notes, container, false);
        textNote = v.findViewById(R.id.notes);
        textNote.setText(notas);
        return v;
    }

}
