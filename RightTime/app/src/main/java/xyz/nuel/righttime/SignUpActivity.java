package xyz.nuel.righttime;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private TextView name, email, passcode;
    private Button btSU;

    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";

    private Intent intentHome;
    private String currentUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        intentHome = new Intent(this, HomeActivity.class);

        name = findViewById(R.id.tfName);
        email = findViewById(R.id.tfEmailNew);
        passcode = findViewById(R.id.tfPasscodeNew);
        btSU = findViewById(R.id.btSU);

    }

    public void createAccount(View v){
        firebaseAuth = firebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), passcode.getText().toString()).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            currentUID = firebaseAuth.getCurrentUser().getUid();
                            setSavedAccount();
                            intentHome.putExtra("scr", "signup");
                            startActivity(intentHome);
                            finish();
                        }
                        else{
                            String error = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), error + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void setSavedAccount(){

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        String yes = "1";

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("userdata/" + currentUID + "/name");
        mReference.setValue(name.getText().toString());

        mReference = mDatabase.getReference("userdata/" + currentUID + "/score");
        mReference.setValue(0);

        mReference = mDatabase.getReference("userdata/" + currentUID + "/time");
        mReference.setValue("0");

        mReference = mDatabase.getReference("userdata/" + currentUID + "/paid");
        mReference.setValue("false");

        savedAccount.put("remember", yes);
        savedAccount.put("name", name.getText().toString());
        savedAccount.put("email", email.getText().toString());
        savedAccount.put("passcode", passcode.getText().toString());
        savedAccount.put("uid", currentUID);
        savedAccount.put("minutes", "0");
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
