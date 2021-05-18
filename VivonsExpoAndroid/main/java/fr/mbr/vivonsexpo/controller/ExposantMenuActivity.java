package fr.mbr.vivonsexpo.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fr.mbr.vivonsexpo.R;

public class ExposantMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonConnexion;
    private Button mButtonInscription;

    public static final String CONNEXION = "Connexion";
    public static final String INSCRIPTION = "Inscription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposant_menu);

        mButtonConnexion = findViewById(R.id.exposant_menu_btn_connexion);
        mButtonInscription = findViewById(R.id.exposant_menu_btn_inscription);

    // SetTag pour donner un "nom" au bouton, un "id"
        mButtonConnexion.setTag(CONNEXION);
        mButtonInscription.setTag(INSCRIPTION);

        mButtonConnexion.setOnClickListener(this);
        mButtonInscription.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String target = (String) v.getTag();
        Intent intent = new Intent();

// Switch pour créer l'intention de redirection
        switch (target)
        {
            case CONNEXION:
                intent = new Intent(ExposantMenuActivity.this, ConnexionActivity.class);
                break;
            case INSCRIPTION:
                intent = new Intent(ExposantMenuActivity.this, InscriptionActivity.class);
                break;
            default:
                Toast.makeText(this, "Erreur sur le lien.", Toast.LENGTH_SHORT);
        }
        startActivity(intent);
    }
}