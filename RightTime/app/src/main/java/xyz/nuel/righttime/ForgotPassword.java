package xyz.nuel.righttime;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView emailInput;
    private String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailInput = findViewById(R.id.emailForgotPass);
    }

    public void resetPassword(View v){

        emailAddress = emailInput.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // do something when mail was sent successfully.
                    Toast.makeText(ForgotPassword.this, "An email has been sent to " + emailAddress + " with instructions on how to recover your password.", Toast.LENGTH_LONG).show();
                } else {
                    // ...
                    Toast.makeText(ForgotPassword.this, "No account associated with the specified email address was found.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
