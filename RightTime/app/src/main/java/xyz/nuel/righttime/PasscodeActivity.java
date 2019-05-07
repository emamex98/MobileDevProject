package xyz.nuel.righttime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class PasscodeActivity extends AppCompatActivity {

    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";
    private TextView passcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        passcode = findViewById(R.id.txtPasscodeB);

    }

    public void logInWithPasscode(View v){
        if(Integer.parseInt(passcode.getText().toString()) == getSavedPasscode()){
            Intent intentHome = new Intent(this, HomeActivity.class);
            startActivity(intentHome);
        } else {
            Toast.makeText(this,"Incorrect passcode.", Toast.LENGTH_SHORT).show();
        }
    }

    private int getSavedPasscode(){

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

            String result = savedAccount.get("passcode") + "";
            return Integer.parseInt(result);

        }

        return -1;
    }

    public void onClickReset(View v){
        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        if(file.delete())
        {
            Toast.makeText(this,"Logged out successfully.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(this,"Error logging out.", Toast.LENGTH_SHORT).show();
        }
    }
}
