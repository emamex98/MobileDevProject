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
    private DatabaseReference mReferenceExercises;

    private TextView email, passcode;
    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";

    private Intent intentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.tfEmailExisting);
        passcode = findViewById(R.id.tfPasscodeExisting);

        intentHome = new Intent(this, HomeActivity.class);
    }

    public void logInToAccount(View v){
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), passcode.getText().toString()).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setSavedAccount();
                            startActivity(intentHome);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(), error + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void setSavedAccount(){

        mDatabase = FirebaseDatabase.getInstance();
        mReferenceExercises = mDatabase.getReference("userData");
        //String userData;

        /*mReferenceExercises.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                    keyNode.getValue(String.class);
                    Log.wtf("DATA", keyNode + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        String yes = "1";

        savedAccount.put("remember", yes);
        savedAccount.put("name", "Pajarita");
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
