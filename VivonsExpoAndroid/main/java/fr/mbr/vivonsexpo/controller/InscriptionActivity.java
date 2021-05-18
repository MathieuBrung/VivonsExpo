package fr.mbr.vivonsexpo.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import fr.mbr.vivonsexpo.R;
import fr.mbr.vivonsexpo.model.dao.ParamIp;
import fr.mbr.vivonsexpo.model.dao.QueryDAO;

public class InscriptionActivity extends AppCompatActivity {

    private EditText mEditTextLogin;
    private EditText mEditTextPassword;
    private EditText mEditTextPasswordConfirm;

    private Button mButtonInscription;

    private Spinner mSpinnerUnivers;
    private Spinner mSpinnerSecteurs;

    private boolean mEnableToucheEvents;

    public static final String URL_UNIVERS = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerUnivers.php";
    public static final String URL_SECTEUR = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerSecteur.php";
    public static final String URL_INSCRIPTION = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerInscription.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        mEditTextLogin = findViewById(R.id.editText_inscription_login);
        mEditTextPassword = findViewById(R.id.editText_inscription_password);
        mEditTextPasswordConfirm = findViewById(R.id.editText_inscription_password_confirm);

        mButtonInscription = findViewById(R.id.btn_inscription);
    // Désactivation du bouton d'incription
        mButtonInscription.setEnabled(false);

        mSpinnerUnivers = findViewById(R.id.spinner_inscription_univers);
        mSpinnerSecteurs = findViewById(R.id.spinner_inscription_secteur);

    // Possibilité d'intéragir avec l'écran
        mEnableToucheEvents = true;

    // Regard sur la saisie de l'utilisateur
        mEditTextPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Activation du bouton sous condition lorsque que l'utilisateur écrit
                mButtonInscription.setEnabled(s.toString().equals(String.valueOf(mEditTextPassword.getText())) && mEditTextLogin.getText().length() > 1);
                //mButtonInscription.setEnabled(s.toString().length() > 5 && s.toString().equals(String.valueOf(mEditTextPassword.getText())) && mEditTextLogin.getText().length() > 4);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    // Set on click du bouton Inscription
        mButtonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // Récupération des valeurs saisies
                String login = mEditTextLogin.getText().toString();
                String password = mEditTextPassword.getText().toString();
                String secteurLibelle = mSpinnerSecteurs.getSelectedItem().toString();

                String textBtn = "En cours...";

            // Impossibilité d'intéragir avec l'écrans lors du traitement
                mEnableToucheEvents = false;

            // Changement du text du bouton pour montrer à l'utilisateur que l'application tourne
                mButtonInscription.setText(textBtn);
                Log.i("Inscription", "En cours");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                        // Si l'inscription s'est bien déroulée
                            if (inscription(login, password, secteurLibelle)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i("Inscription", "True");

                                    // Possibilité d'intéragir avec l'écran
                                        mEnableToucheEvents = true;

                                    // Création de l'intention de redirection vers la page de connexion
                                        Intent intent = new Intent(InscriptionActivity.this, ConnexionActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                            // Si l'inscription à échouée
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i("Inscription", "False");

                                    // Affichage d'un message temporaire pour signaler l'erreur
                                        Toast.makeText(InscriptionActivity.this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
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

    // Changement de la liste des secteurs en fonction de l'univers choisi
        mSpinnerUnivers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    listeSecteurs(mSpinnerUnivers.getSelectedItem().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            // Initialisation de la liste des univers
            listeUnivers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Surcharge de la méthode pour autoriser ou non l'utilisation de l'écran
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableToucheEvents && super.dispatchTouchEvent(ev);
    }

// Récupération de la liste des univers et implémentation dans le spinner
    private void listeUnivers() throws IOException {

        ArrayList arrayListUnivers = new ArrayList<String>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QueryDAO queryDAO = new QueryDAO();
                    JSONArray jsonArray = queryDAO.QueryToJSONArray(URL_UNIVERS);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = null;
                        jsonObject = jsonArray.getJSONObject(i);
                        arrayListUnivers.add(jsonObject.getString("UniversLibelle"));
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(InscriptionActivity.this, R.layout.spinner_style, arrayListUnivers);
                        mSpinnerUnivers.setAdapter(arrayAdapter);
                    }
                });

            }
        }).start();
    }

// Récupération de la liste des secteurs en fonction de l'univers choisi et implémentation du spinner
    private void listeSecteurs(String universLibelle) throws IOException {

        ArrayList arrayListSecteurs = new ArrayList<String>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QueryDAO queryDAO = new QueryDAO();
                    JSONArray jsonArray = queryDAO.QueryToJSONArrayWithParam(URL_SECTEUR, "UniversLibelle", universLibelle);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = null;
                        jsonObject = jsonArray.getJSONObject(i);
                        arrayListSecteurs.add(jsonObject.getString("SecteurLibelle"));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(InscriptionActivity.this, R.layout.spinner_style, arrayListSecteurs);
                        mSpinnerSecteurs.setAdapter(arrayAdapter);
                    }
                });

            }
        }).start();
    }

// Requête pour l'inscription
    private boolean inscription(String login, String password, String secteurLibelle) throws IOException {
        QueryDAO queryDAO = new QueryDAO();
        return queryDAO.QueryInscription(URL_INSCRIPTION, login, password, secteurLibelle);
    }


}