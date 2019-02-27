package xyz.nuel.righttime;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int promptPasscode = getSavedAccount();

        if (promptPasscode == 1) {
            changeLoginB();
        }

    }

    public void changeSignUp(View v){
        Intent intentitoSU = new Intent(this, SignUpActivity.class);
        startActivity(intentitoSU);
    }

    public void changeLogin(View v){
        Intent intentito = new Intent(this, LoginActivity.class);
        startActivity(intentito);
    }
    public void changeLoginB(){
        Intent intentitoB = new Intent(this, PasscodeActivity.class);
        startActivity(intentitoB);
    }

    private int getSavedAccount(){

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

            //Toast.makeText(this, savedAccount.get("remember") + "", Toast.LENGTH_SHORT).show();

            String result = savedAccount.get("remember") + "";
            return Integer.parseInt(result);

        }

        return -1;
    }
}
