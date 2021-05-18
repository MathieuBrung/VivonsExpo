package fr.mbr.vivonsexpo.controller;

import androidx.appcompat.app.AppCompatActivity;
import fr.mbr.vivonsexpo.R;
import fr.mbr.vivonsexpo.model.dao.ParamIp;
import fr.mbr.vivonsexpo.model.dao.QueryDAO;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class ExposantDemandeActivity extends AppCompatActivity {

    private TextView mTextViewNumeroDemande;

    private EditText mEditTextMotif;

    private Button mButtonEnvoyer;

    private SharedPreferences mSharedPreferences;
    public static final String KEY_SP_EXPOSANT = "Preferences de l'exposant connecté";
    public static final String KEY_EXPOSANT_ID = "Id de l'exposant";

    public static final String URL_GET_DEMANDES = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerGetDemandes.php";
    public static final String URL_ADD_DEMANDE = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerAddDemande.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exposant_demande);

        mSharedPreferences = getSharedPreferences(KEY_SP_EXPOSANT, MODE_PRIVATE);

        mTextViewNumeroDemande = findViewById(R.id.textView_exposantDemande_numero);
        mEditTextMotif = findViewById(R.id.editText_exposantDemande_motif);
        mButtonEnvoyer = findViewById(R.id.btn_envoyer);

    // Désactivation du bouton envoyer
        mButtonEnvoyer.setEnabled(false);

    // Récupératio net affichage du nombre de demande
        getDemandes();

    // Regard sur la saisie de l'utilisateur
        mEditTextMotif.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Activation du bouton sous condition
                mButtonEnvoyer.setEnabled(s.toString().length() > 10);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    // on click du bouton envoyer
        mButtonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // Récupération du texte saisie
                String motif = mEditTextMotif.getText().toString();

                String textBtn = "En cours...";
                String textBtnTrue = "Retour";
                String textBtnFalse = "Erreur";

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mButtonEnvoyer.setText(textBtn);

                        // Si l'ajout de la demande s'est effectué
                            if (addDemande(motif)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mButtonEnvoyer.setText(textBtnTrue);

                                    // Affichage d'un message pour confirmer l'envoie de la demande
                                        Toast.makeText(ExposantDemandeActivity.this, "Demande envoyée", Toast.LENGTH_SHORT).show();

                                    // Changement du on click pour terminer l'activité
                                        mButtonEnvoyer.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        });
                                    }
                                });
                            } else {
                        // Sinon affichage d'un message temporaire pour indiquer l'erreur à l'utilisateur
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mButtonEnvoyer.setText(textBtnFalse);
                                        Toast.makeText(ExposantDemandeActivity.this, "Erreur lors de l'envoie de la demande", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });
    }

// Récupération et affichage du nombre de demande
    private void getDemandes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QueryDAO queryDAO = new QueryDAO();
                    String param = "ExposantId";
                    String exposantId = mSharedPreferences.getString(KEY_EXPOSANT_ID, null);
                    JSONArray jsonArray = queryDAO.QueryToJSONArrayWithParam(URL_GET_DEMANDES, param, exposantId);

                    int nbDemandes = jsonArray.getJSONObject(0).getInt("NbDemandes");
                    String s = "Demande n°" + (nbDemandes + 1);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewNumeroDemande.setText(s);
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

// Appel de la requête pour ajouter la demande
    private boolean addDemande(String motif) throws IOException, JSONException {
        QueryDAO queryDAO = new QueryDAO();
        String param1 = "ExposantId";
        String param2 = "Motif";
        String exposantId = mSharedPreferences.getString(KEY_EXPOSANT_ID, null);

        return queryDAO.QueryToBooleanWith2Param(URL_ADD_DEMANDE, param1, exposantId, param2, motif);
    }
}