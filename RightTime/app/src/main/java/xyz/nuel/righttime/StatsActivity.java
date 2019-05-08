package xyz.nuel.righttime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class StatsActivity extends AppCompatActivity {

    private TextView txScore, txMinutes;
    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //Intent intent = new Intent();
        String progress = getIntent().getStringExtra("progress");

        txScore = findViewById(R.id.txPointsEarned);
        txMinutes = findViewById(R.id.txMinutesSpent);

        txScore.setText(progress);

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);
        String minutes;

        if(file.exists()) {
            try {
                FileInputStream fis = openFileInput(SAVED_ACCOUNT);
                savedAccount.loadFromXML(fis);
                minutes =savedAccount.get("minutes").toString();
                txMinutes.setText(minutes);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
