package fr.mbr.vivonsexpo.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fr.mbr.vivonsexpo.R;
import fr.mbr.vivonsexpo.model.dao.ParamIp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonVisiteur;
    private Button mButtonExposant;
    private Button mButtonStaff;

    public static final String VISITEUR = "Visiteur";
    public static final String EXPOSANT = "Exposant";
    public static final String STAFF = "Staff";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonVisiteur = findViewById(R.id.main_btn_visiteur);
        mButtonExposant = findViewById(R.id.main_btn_exposant);
        mButtonStaff = findViewById(R.id.main_btn_staff);

    // SetTag pour donner un "nom" au bouton, un "id"
        mButtonVisiteur.setTag(VISITEUR);
        mButtonExposant.setTag(EXPOSANT);
        mButtonStaff.setTag(STAFF);

        mButtonVisiteur.setOnClickListener(this);
        mButtonExposant.setOnClickListener(this);
        mButtonStaff.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String target = (String) v.getTag();
        Intent intent = new Intent();

        // Switch pour créer l'intention de redirection
        switch (target)
        {
            case VISITEUR:
                intent = new Intent(MainActivity.this, VisiteurMenuActivity.class);
                break;
            case EXPOSANT:
                intent = new Intent(MainActivity.this, ExposantMenuActivity.class);
                break;
            case STAFF:
                intent = new Intent(MainActivity.this, StaffHomeActivity.class);
                break;
            default:
                Toast.makeText(this, "Erreur sur le lien.", Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
    }
}