package fr.mbr.vivonsexpo.controller;

import androidx.appcompat.app.AppCompatActivity;
import fr.mbr.vivonsexpo.R;
import fr.mbr.vivonsexpo.model.dao.ParamIp;
import fr.mbr.vivonsexpo.model.dao.QueryDAO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class ExposantInfosActivity extends AppCompatActivity {

    private EditText mEditTextRaisonSociale;
    private EditText mEditTextActivite;
    private EditText mEditTextNom;
    private EditText mEditTextPrenom;
    private EditText mEditTextTelephone;
    private EditText mEditTextMail;

    private Button mButtonValider;

    private SharedPreferences mSharedPreferences;
    public static final String KEY_SP_EXPOSANT = "Preferences de l'exposant connecté";
    public static final String KEY_EXPOSANT_ID = "Id de l'exposant";

    public static final String URL_EXPOSANT_INFOS = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerExposantInfos.php";
    public static final String URL_EXPOSANT_UPDATE_INFOS = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerExposantUpdateInfos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposant_infos);

    // Récupération des Preferences de l'exposant
        mSharedPreferences = getSharedPreferences(KEY_SP_EXPOSANT, MODE_PRIVATE);

        mEditTextRaisonSociale = findViewById(R.id.editText_infosExposant_raisonSociale);
        mEditTextActivite = findViewById(R.id.editText_infosExposant_activite);
        mEditTextNom = findViewById(R.id.editText_infosExposant_nom);
        mEditTextPrenom = findViewById(R.id.editText_infosExposant_prenom);
        mEditTextTelephone = findViewById(R.id.editText_infosExposant_telephone);
        mEditTextMail = findViewById(R.id.editText_infosExposant_mail);

        mButtonValider = findViewById(R.id.btn_valider);

    // Récupération et affichage des informations de l'exposant
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray jsonArrayExposantInfos = getExposantInfos();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mEditTextRaisonSociale.setText(jsonArrayExposantInfos.getJSONObject(0).getString("RaisonSociale"));
                                mEditTextActivite.setText(jsonArrayExposantInfos.getJSONObject(0).getString("Activite"));
                                mEditTextNom.setText(jsonArrayExposantInfos.getJSONObject(0).getString("Nom"));
                                mEditTextPrenom.setText(jsonArrayExposantInfos.getJSONObject(0).getString("Prenom"));
                                mEditTextTelephone.setText(jsonArrayExposantInfos.getJSONObject(0).getString("Telephone"));
                                mEditTextMail.setText(jsonArrayExposantInfos.getJSONObject(0).getString("Mail"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    // On click du bouton valider pour envoyer les données saisie par l'exposant
        mButtonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // Récupération des données saisies
                String raisonSociale = mEditTextRaisonSociale.getText().toString();
                String activite = mEditTextActivite.getText().toString();
                String nom = mEditTextNom.getText().toString();
                String prenom = mEditTextPrenom.getText().toString();
                String telephone = mEditTextTelephone.getText().toString();
                String mail = mEditTextMail.getText().toString();

            // Récupération de l'id de l'exposant dans les preferences
                String exposantId = mSharedPreferences.getString(KEY_EXPOSANT_ID, null);

                String textBtn = "En cours...";
                String textBtnTrue = "Retour";
                String textBtnFalse = "Erreur";

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mButtonValider.setText(textBtn);
                        // Si la mise à jour des inforamtions s'est bien déroulée alors on l'indique à l'utilisateur et on change le on click du bouton
                            if (updateInfos(URL_EXPOSANT_UPDATE_INFOS, exposantId, raisonSociale, activite, nom, prenom, telephone, mail)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mButtonValider.setText(textBtnTrue);

                                    // Message temporaire pour indiquer à l'utilisateur que la mise à jour s'est bien déroulée
                                        Toast.makeText(ExposantInfosActivity.this, "Informations mises à jour", Toast.LENGTH_SHORT).show();

                                    // Changement du on click du bouton pour terminer l'activité
                                        mButtonValider.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        });
                                    }
                                });

                            } else {
                        // Sinon on affiche un message temporaire pour indiquer l'erreur
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mButtonValider.setText(textBtnFalse);
                                        Toast.makeText(ExposantInfosActivity.this, "Erreur lors du changement des informations.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });


    }

// Appel de la requête pour obtenir les informations de l'exposant
    private JSONArray getExposantInfos() throws IOException, JSONException {
        QueryDAO queryDAO = new QueryDAO();
        String param = "ExposantId";
        String value = mSharedPreferences.getString(KEY_EXPOSANT_ID, null);

        return queryDAO.QueryToJSONArrayWithParam(URL_EXPOSANT_INFOS, param, value);
    }

// Appel de la requête pour la mise à jour des informations
    private boolean updateInfos(String urlController, String exposantId, String raisonSociale, String activite, String nom, String prenom, String telephone, String mail) throws IOException {
        QueryDAO queryDAO = new QueryDAO();

        return queryDAO.QueryUpdateExposantInfos(urlController, exposantId, raisonSociale, activite, nom, prenom, telephone, mail);
    }

}