package fr.mbr.vivonsexpo.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.mbr.vivonsexpo.R;
import fr.mbr.vivonsexpo.model.dao.ParamIp;
import fr.mbr.vivonsexpo.model.dao.QueryDAO;
import fr.mbr.vivonsexpo.view.AdapterExposant;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class UniversActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<String> mRaisonSociale = new ArrayList<String>();
    private ArrayList<String> mActivite = new ArrayList<String>();
    private ArrayList<String> mStand = new ArrayList<String>();
    private ArrayList<String> mAllee = new ArrayList<String>();
    private ArrayList<String> mTravee = new ArrayList<String>();
    private ArrayList<String> mHall = new ArrayList<String>();
    private ArrayList<Integer> mLogo = new ArrayList<Integer>();
    private JSONArray mJSONArray;

    private Spinner mSpinnerSecteurs;

    public static final String URL_EXPOSANTS_BY_UNIVERS = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerGetExposantByUnivers.php";
    public static final String URL_EXPOSANTS_BY_SECTEUR = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerGetExposantBySecteur.php";
    public static final String URL_SECTEUR = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerSecteur.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univers);

        mRecyclerView = findViewById(R.id.recyclerView_listeExposants);

        mSpinnerSecteurs = findViewById(R.id.spinner_secteur);

        try {
            // Récupération de la liste des secteurs en fonction de l'univers choisi
            listeSecteurs(getIntent().getStringExtra("Libelle univers"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mSpinnerSecteurs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        QueryDAO queryDAO = new QueryDAO();

                    // Clear des ArrayList sinon les anciennes données s'ajoutent avec les nouvelles
                        mRaisonSociale.clear();
                        mActivite.clear();
                        mStand.clear();
                        mAllee.clear();
                        mTravee.clear();
                        mHall.clear();
                        mLogo.clear();

                        try {

                            if (mSpinnerSecteurs.getSelectedItem().toString().equals("Tous")) {
                                // Si l'utilisateur veut voir tous les secteurs alors requête en fonction de l'univers
                                mJSONArray = queryDAO.QueryToJSONArrayWithParam(URL_EXPOSANTS_BY_UNIVERS, "UniversLibelle", getIntent().getStringExtra("Libelle univers"));
                            } else {
                                // Sinon requête en fonction du secteur
                                mJSONArray = queryDAO.QueryToJSONArrayWithParam(URL_EXPOSANTS_BY_SECTEUR, "SecteurLibelle", mSpinnerSecteurs.getSelectedItem().toString());
                            }

                        // Boucle pour initialiser les ArrayList
                            for (int i = 0; i < mJSONArray.length(); i++) {
                                mRaisonSociale.add(mJSONArray.getJSONObject(i).getString("RaisonSociale"));
                                mActivite.add(mJSONArray.getJSONObject(i).getString("Activite"));
                                mStand.add(mJSONArray.getJSONObject(i).getString("StandId"));
                                mAllee.add(mJSONArray.getJSONObject(i).getString("AlleeId"));
                                mTravee.add(mJSONArray.getJSONObject(i).getString("TraveeId"));
                                mHall.add(mJSONArray.getJSONObject(i).getString("HallId_ALLEE"));

                                String imgName = imgName(mJSONArray.getJSONObject(i).getString("RaisonSociale"));
                                int logo = getResources().getIdentifier(imgName, "drawable", getPackageName());
                                mLogo.add(logo);
                            }

                        // Initialisation de l'adapter
                            AdapterExposant adapterExposant = new AdapterExposant(UniversActivity.this, mRaisonSociale, mActivite, mStand, mAllee, mTravee, mHall, mLogo);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                // Set de l'adaptater et du LayoutManager
                                    mRecyclerView.setAdapter(adapterExposant);
                                    mRecyclerView.setLayoutManager(new LinearLayoutManager(UniversActivity.this));
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

// Fonction de récupération de la liste des secteurs en fonction de l'univers
    private void listeSecteurs(String universLibelle) throws IOException {

        ArrayList arrayListSecteurs = new ArrayList<String>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    QueryDAO queryDAO = new QueryDAO();
                    JSONArray jsonArrayQuery = queryDAO.QueryToJSONArrayWithParam(URL_SECTEUR, "UniversLibelle", universLibelle);

                    String s = "{\"SecteurLibelle\":\"Tous\"}";
                    JSONObject jsonObjectTous = new JSONObject(s);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(jsonObjectTous);

                    for (int i = 0; i < jsonArrayQuery.length(); i++) {
                        jsonArray.put(jsonArrayQuery.getJSONObject(i));
                    }

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
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UniversActivity.this, R.layout.spinner_style, arrayListSecteurs);
                        mSpinnerSecteurs.setAdapter(arrayAdapter);
                    }
                });

            }
        }).start();
    }

// Fonction pour obtenir le nom de l'image en fonction de la raison sociale de l'exposant
    private String imgName (String raisonSociale) {
        String name = raisonSociale.toLowerCase().replaceAll(" ", "_");
        name = name.replaceAll("-", "_");
        name = name.replaceAll("é", "e");
        name = name.replaceAll("è", "e");
        name = name.replaceAll("ê", "e");

        return name;
    }
}