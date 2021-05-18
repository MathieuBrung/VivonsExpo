package fr.mbr.vivonsexpo.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import fr.mbr.vivonsexpo.R;
import fr.mbr.vivonsexpo.model.dao.ParamIp;
import fr.mbr.vivonsexpo.model.dao.QueryDAO;

public class ConnexionActivity extends AppCompatActivity {

    private EditText mEditTextLogin;
    private EditText mEditTextPassword;

    private Button mButtonConnexion;

    private boolean mEnableToucheEvents;

    private SharedPreferences mSharedPreferences;
    public static final String KEY_SP_EXPOSANT = "Preferences de l'exposant connecté";
    public static final String KEY_EXPOSANT_ID = "Id de l'exposant";

    public static final String URL_CONNEXION = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerConnexion.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        mEditTextLogin = findViewById(R.id.editText_connexion_login);
        mEditTextPassword = findViewById(R.id.editText_connexion_password);

        mButtonConnexion = findViewById(R.id.btn_connexion);
    // Désactivation du bouton de connexion
        mButtonConnexion.setEnabled(false);

    // Possibilité d'intéragir avec l'écran
        mEnableToucheEvents = true;

    // Récupération des Preferences de l'exposant
        mSharedPreferences = getSharedPreferences(KEY_SP_EXPOSANT, MODE_PRIVATE);

    // Regard sur la saisie de l'utilisateur
        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Activation du bouton sous condition lorsque que l'utilisateur écrit
                mButtonConnexion.setEnabled(mEditTextLogin.getText().length() > 1 && mEditTextPassword.getText().length() > 1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    // On click du bouton de connexion
        mButtonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // Récupération des valeurs saisies
                String login = mEditTextLogin.getText().toString();
                String password = mEditTextPassword.getText().toString();

                String textBtn = "En cours...";
                String textBtnTrue = "Connecté";
                String textBtnFalse = "Connexion";

            // Impossibilité d'intéragir avec l'écrans lors du traitement
                mEnableToucheEvents = false;

            // Changement du text du bouton pour montrer à l'utilisateur que l'application tourne
                mButtonConnexion.setText(textBtn);
                Log.i("Connexion", "En cours");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = connexion(login, password);

                        // Si la requête à bien renvoyé les informations de l'exposant
                            if (jsonArray.getJSONObject(0).length() > 1) {

                            // On garde l'id de l'exposant dans les Preferences
                                mSharedPreferences.edit().putString(KEY_EXPOSANT_ID, String.valueOf(jsonArray.getJSONObject(0).getInt("ExposantId"))).apply();

                                Log.i("Connexion", "True");
                                mButtonConnexion.setText(textBtnTrue);

                            // Si la raison sociale n'est pas rensignée alors on redirige vers la page des informations de l'exposant
                                if (jsonArray.getJSONObject(0).getString("RaisonSociale").isEmpty()) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                        // Possibilité d'intéragir avec l'écran
                                            mEnableToucheEvents = true;

                                        // On supprime le mot de passe qui a été saisie dans l'editText
                                            mEditTextPassword.setText("");

                                        // Création de l'intention de redirection vers les informations de l'exposant
                                            Intent intent = new Intent(ConnexionActivity.this, ExposantInfosActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else {
                            // Sinon redirection vers sa page d'accueil
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                        // Possibilité d'intéragir avec l'écran
                                            mEnableToucheEvents = true;

                                        // On supprime le mot de passe qui a été saisie dans l'editText
                                            mEditTextPassword.setText("");

                                        // Création de l'intention de redirection vers la page d'accueil de l'exposant
                                            Intent intent = new Intent(ConnexionActivity.this, ExposantHomeActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }

                            } else {
                                Log.i("Connexion", "False");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mButtonConnexion.setText(textBtnFalse);

                                    // Possibilité d'intéragir avec l'écran
                                        mEnableToucheEvents = true;

                                    // Affichage d'un message temporaire pour indiquer l'erreur
                                        Toast.makeText(ConnexionActivity.this, "Identifiant ou mot de passe incorrect.", Toast.LENGTH_SHORT).show();
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

// Surcharge de la méthode pour autoriser ou non l'utilisation de l'écran
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableToucheEvents && super.dispatchTouchEvent(ev);

    }

// Appel de la requête de connexion qui renvoie les informations de l'exposant sous format JSON
    private JSONArray connexion(String login, String password) throws IOException, JSONException {
        QueryDAO queryDAO = new QueryDAO();
        return queryDAO.QueryConnexion(URL_CONNEXION, login, password);
    }

}