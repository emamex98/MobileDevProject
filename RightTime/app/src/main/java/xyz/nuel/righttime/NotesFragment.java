package xyz.nuel.righttime;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    private static final String NOTE_PARAM = "time";
    private long startTime;
    private int timeR;
    private TextView textTimer;
    private Button startB, resetB;
    private CountDownTimer countDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillies;
    private OnFragmentInteractionListener mListener;


    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance(int time){
        NotesFragment nf = new NotesFragment();
        Bundle args = new Bundle();
        args.putInt(NOTE_PARAM, time);
        nf.setArguments(args);
        return nf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
           timeR = getArguments().getInt(NOTE_PARAM);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notes, container, false);
        textTimer = v.findViewById(R.id.timer);
        startB = v.findViewById(R.id.bStart);
        resetB = v.findViewById(R.id.reset);
        //textTimer.setText(notas);

        startTime = timeR*60000;
        mTimeLeftInMillies = startTime;
        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimerRunning){
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        resetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(mTimeLeftInMillies, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillies = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                startB.setText("Start");
                startB.setVisibility(View.INVISIBLE);
                resetB.setVisibility(View.VISIBLE);
                mListener.fragmentCallback(true);
            }
        }.start();

        mTimerRunning = true;
        startB.setText("Pause");
        resetB.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        mTimerRunning = false;
        startB.setText("Start");
        resetB.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTimeLeftInMillies = startTime;
        updateCountDownText();
        resetB.setVisibility(View.INVISIBLE);
        startB.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillies/1000) / 60;
        int seconds = (int) (mTimeLeftInMillies/1000)% 60;

        String timeLeftFormat = String.format(Locale.getDefault()
        , "%02d:%02d", minutes, seconds);

        textTimer.setText(timeLeftFormat);
    }

    public interface OnFragmentInteractionListener {
        void fragmentCallback(boolean done);
    }
}
