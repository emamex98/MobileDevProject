package xyz.nuel.righttime;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private TextView email, passcode, forgotPass;
    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";

    private Intent intentHome;
    private String currentUID, currentName, currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.tfEmailExisting);
        passcode = findViewById(R.id.tfPasscodeExisting);
        forgotPass = findViewById(R.id.recoverPass);

        intentHome = new Intent(this, HomeActivity.class);
    }

    public void logInToAccount(View v){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), passcode.getText().toString()).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUID = firebaseAuth.getCurrentUser().getUid();
                            setSavedAccount();
                        } else {
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
        currentName = "PAJARITA";

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("userdata/" + currentUID + "/name");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    String yes = "1";

                    currentName = dataSnapshot.getValue().toString();

                    savedAccount.put("remember", yes);
                    savedAccount.put("name", currentName);
                    savedAccount.put("email", email.getText().toString());
                    savedAccount.put("passcode", passcode.getText().toString());
                    savedAccount.put("uid", currentUID);
                    //savedAccount.put("minutes", "0");
                    wrFile();

                    //startActivity(intentHome);

                } else {
                    Toast.makeText(LoginActivity.this,"Error logging in.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mReference = mDatabase.getReference("userdata/" + currentUID + "/time");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    currentTime = dataSnapshot.getValue().toString();
                    savedAccount.put("minutes", currentTime + "");
                    wrFile();

                    startActivity(intentHome);

                } else {
                    Toast.makeText(LoginActivity.this,"Error loading time.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        Toast.makeText(this, "ACCOUNT SAVED", Toast.LENGTH_SHORT).show();


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

    public void onClickReset(View v) {
            Intent intentito = new Intent(this, ForgotPassword.class);
            startActivity(intentito);
    }
}
