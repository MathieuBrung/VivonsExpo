package fr.mbr.vivonsexpo.controller;

import androidx.appcompat.app.AppCompatActivity;
import fr.mbr.vivonsexpo.R;
import fr.mbr.vivonsexpo.model.dao.ParamIp;
import fr.mbr.vivonsexpo.model.dao.QueryDAO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class ExposantHomeActivity extends AppCompatActivity {

    private TableLayout mTableLayoutEmplacement;

    private TextView mTextViewStand;
    private TextView mTextViewAllee;
    private TextView mTextViewTravee;
    private TextView mTextViewHall;

    private Button mButtonInformations;
    private Button mButtonDemande;

    private SharedPreferences mSharedPreferences;
    public static final String KEY_SP_EXPOSANT = "Preferences de l'exposant connecté";
    public static final String KEY_EXPOSANT_ID = "Id de l'exposant";

    public static final String URL_STAND = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerStand.php";
    public static final String URL_GET_DEMANDES = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerGetDemandes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposant_home);

        mTextViewStand = findViewById(R.id.textView_value_stand_exposantHome);
        mTextViewAllee = findViewById(R.id.textView_value_allee_exposantHome);
        mTextViewTravee = findViewById(R.id.textView_value_travee_exposantHome);
        mTextViewHall = findViewById(R.id.textView_value_hall_exposantHome);

    // Récupération des Preferences de l'exposant
        mSharedPreferences = getSharedPreferences(KEY_SP_EXPOSANT, MODE_PRIVATE);

        mButtonInformations = findViewById(R.id.btn_exposantHome_infos);
        mButtonDemande = findViewById(R.id.btn_exposantHome_demande);

    // Appel de la fonction pour obtenir l'affichage de l'emplacement
        getEmplacement();

    // On click du bouton informations
        mButtonInformations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExposantHomeActivity.this, ExposantInfosActivity.class);
                startActivity(intent);
            }
        });

    // On click du bouton demande
        mButtonDemande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExposantHomeActivity.this, ExposantDemandeActivity.class);
                startActivity(intent);
            }
        });


    // Protection pour qu'un exposant ne puisse envoyer que 3 demandes
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int nbDemandes = getNbDemandes();
                    if (nbDemandes > 2) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            // Si le nombre de demande est supérieur à 2 alors on rend le bouton d'accès à la page invisible
                                mButtonDemande.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

// Récupération et affichage de l'emplacement de l'exposant
    private void getEmplacement() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QueryDAO queryDAO = new QueryDAO();
                    String param = "ExposantId";
                    String value = mSharedPreferences.getString(KEY_EXPOSANT_ID, null);
                    JSONArray jsonArray = queryDAO.QueryToJSONArrayWithParam(URL_STAND, param, value);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mTextViewStand.setText(jsonArray.getJSONObject(0).getString("StandId"));
                                mTextViewAllee.setText(jsonArray.getJSONObject(0).getString("AlleeId"));
                                mTextViewTravee.setText(jsonArray.getJSONObject(0).getString("TraveeId"));
                                mTextViewHall.setText(jsonArray.getJSONObject(0).getString("HallId_ALLEE"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

// Récupération du nombre de demande envoyé par l'exposant
    private int getNbDemandes() throws JSONException, IOException {
        QueryDAO queryDAO = new QueryDAO();
        String param = "ExposantId";
        String exposantId = mSharedPreferences.getString(KEY_EXPOSANT_ID, null);
        JSONArray jsonArray = queryDAO.QueryToJSONArrayWithParam(URL_GET_DEMANDES, param, exposantId);
        int nbDemande = jsonArray.getJSONObject(0).getInt("NbDemandes");

        return nbDemande;
    }

}