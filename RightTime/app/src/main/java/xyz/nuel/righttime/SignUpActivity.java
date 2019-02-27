package xyz.nuel.righttime;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SignUpActivity extends AppCompatActivity {

    private DBHelper db;
    private TextView name, email, passcode;
    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new DBHelper(this);

        name = findViewById(R.id.tfName);
        email = findViewById(R.id.tfEmailNew);
        passcode = findViewById(R.id.tfPasscodeNew);

    }

    public void createAccount(View v){
        db.addUser(name.getText().toString(), email.getText().toString(), Integer.parseInt(passcode.getText().toString()));
        setSavedAccount();
        Intent intentHome = new Intent(this, HomeActivity.class);
        startActivity(intentHome);
    }

    private void setSavedAccount(){

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        String yes = "1";

        savedAccount.put("remember", yes);
        savedAccount.put("name", db.getName(email.getText().toString()));
        savedAccount.put("email", email.getText().toString());
        savedAccount.put("passcode", passcode.getText().toString());
        wrFile();
        Toast.makeText(this, "ACCOUNT SAVED: " + savedAccount.getProperty("email"), Toast.LENGTH_SHORT).show();
    }

    private void wrFile(){
        try {
            FileOutputStream fos = openFileOutput(SAVED_ACCOUNT, Context.MODE_PRIVATE);
            savedAccount.storeToXML(fos, null);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
