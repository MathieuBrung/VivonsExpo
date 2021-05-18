package fr.mbr.vivonsexpo.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import fr.mbr.vivonsexpo.R;
import fr.mbr.vivonsexpo.model.dao.ParamIp;
import fr.mbr.vivonsexpo.model.dao.QueryDAO;
import fr.mbr.vivonsexpo.view.AdapterUnivers;

public class VisiteurMenuActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Integer> mLogo = new ArrayList<Integer>();
    private ArrayList<String> mLibelle = new ArrayList<String>();
    private JSONArray mJSONArray;

    public static final String URL_UNIVERS = "http://" + ParamIp.ip + "/PPE/VivonsExpo/controllers/controllerUnivers.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiteur_menu);

        mRecyclerView = findViewById(R.id.recyclerView_menu_univers);

    // Mise en place du RecyclerView
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryDAO queryDAO = new QueryDAO();

                try {
                // Récupération du libellé des Univers
                    mJSONArray = queryDAO.QueryToJSONArray(URL_UNIVERS);

                // Boucle pour initialiser les ArrayList qui serviront à l'adaptater
                    for (int i = 0; i < mJSONArray.length(); i++) {
                        String universLibelle = imgName(mJSONArray.getJSONObject(i).getString("UniversLibelle"));
                        int logo = getResources().getIdentifier(universLibelle, "drawable", getPackageName());

                        mLibelle.add(mJSONArray.getJSONObject(i).getString("UniversLibelle"));
                        mLogo.add(logo);
                    }

                // Initialisation de l'adapater
                    AdapterUnivers adapterUnivers = new AdapterUnivers(VisiteurMenuActivity.this, mLogo, mLibelle);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        // Set de l'adaptater et du LayoutManager
                            mRecyclerView.setAdapter(adapterUnivers);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(VisiteurMenuActivity.this));
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

// Fonction pour obtenir le nom de l'image en fonction du nom de l'univers
    private String imgName (String univers) {
        String name = univers.toLowerCase().replaceAll(" ", "_");
        name = name.replaceAll("-", "_");
        name = name.replaceAll("é", "e");
        name = name.replaceAll("è", "e");
        name = name.replaceAll("ê", "e");

        return name;
    }

}