package xyz.nuel.righttime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class SuscriptionActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscription);
    }

    public void pay(View v){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("userdata/" + getSavedUID() + "/paid");
        mReference.setValue("true");
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    // Codigo se repite
    private String getSavedUID(){

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

            return savedAccount.get("uid") + "";

        }

        return null;
    }
}
