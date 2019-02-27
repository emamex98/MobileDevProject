package xyz.nuel.righttime;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

public class HomeActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    private Properties savedAccount;
    private static final String SAVED_ACCOUNT = "savedAccount.xml";

    private TextView greeting, txtScore, txtLevel;
    private ListView listRoutine;
    private ArrayAdapter<String> adapter;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        greeting = findViewById(R.id.txtWelcome);
        greeting.setText("Welcome, " + getSavedName() + "!");

        db = new DBHelper(this);

        txtScore = findViewById(R.id.txtScore);
        txtScore.setText("Score: " + db.getScore(getSavedEmail()));

        txtLevel = findViewById(R.id.txtLevel);
        txtLevel.setText("Level: " + db.getLevel(getSavedEmail()) + "");

        listRoutine = findViewById(R.id.listRoutines);

        // data source
        String[] data = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        listRoutine.setAdapter(adapter);
        listRoutine.setOnItemClickListener(this);

    }

    private String getSavedName(){

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

            return savedAccount.get("name") + "";

        }

        return "friend";
    }

    private String getSavedEmail(){

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

            return savedAccount.get("email") + "";

        }

        return null;
    }

    public void logOut(View v){
        savedAccount = new Properties();
        File file = new File(getFilesDir(), SAVED_ACCOUNT);

        if(file.delete())
        {
            Toast.makeText(this,"Logged out successfully.", Toast.LENGTH_SHORT).show();
            Intent intentHome = new Intent(this, MainActivity.class);
            startActivity(intentHome);
        }
        else
        {
            Toast.makeText(this,"Error logging out..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentito = new Intent(this, RoutineDetailActivity.class);
        startActivity(intentito);
        //Toast.makeText(this, "position: " + position + " id: " + id, Toast.LENGTH_SHORT).show();
    }
}
