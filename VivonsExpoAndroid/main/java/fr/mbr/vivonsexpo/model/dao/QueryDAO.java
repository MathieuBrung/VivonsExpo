package fr.mbr.vivonsexpo.model.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class QueryDAO {

    public JSONArray QueryToJSONArray (String controllerUrl) throws IOException, JSONException {

        URL url = new URL(controllerUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        InputStream inputStream = http.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null)
        {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        inputStream.close();
        http.disconnect();

        JSONArray jsonArray = new JSONArray(stringBuilder.toString());

        return jsonArray;
    }

    public JSONArray QueryToJSONArrayWithParam (String controllerUrl, String param, String value) throws  IOException, JSONException {
        URL url = new URL(controllerUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        OutputStream outputStream = http.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String data = URLEncoder.encode(param, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");

        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();

        InputStream inputStream = http.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null)
        {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        inputStream.close();
        http.disconnect();

        JSONArray jsonArray = new JSONArray(stringBuilder.toString());

        return jsonArray;
    }

    public JSONArray QueryToJSONArrayWith2Param (String controllerUrl, String param1, String value1, String param2, String value2) throws  IOException, JSONException {
        URL url = new URL(controllerUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        OutputStream outputStream = http.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String data = URLEncoder.encode(param1, "UTF-8") + "=" + URLEncoder.encode(value1, "UTF-8") +
                "&&" + URLEncoder.encode(param2, "UTF-8") + "=" + URLEncoder.encode(value2, "UTF-8");

        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();

        InputStream inputStream = http.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null)
        {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        inputStream.close();
        http.disconnect();

        JSONArray jsonArray = new JSONArray(stringBuilder.toString());

        return jsonArray;
    }

    public JSONArray QueryConnexion(String controllerUrl, String login, String password) throws IOException, JSONException {
        URL url = new URL(controllerUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        OutputStream outputStream = http.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(login, "UTF-8") +
                "&&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();

        InputStream inputStream = http.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = bufferedReader.readLine();
        StringBuilder stringBuilder = new StringBuilder();

        if (line != null) {
            stringBuilder.append(line);
        } else {
            stringBuilder.append("[{\"Connexion\": false}]");
        }

        bufferedReader.close();
        inputStream.close();
        http.disconnect();

        JSONArray jsonArray = new JSONArray(stringBuilder.toString());

        return jsonArray;
    }

    public Boolean QueryToBooleanWith2Param (String controllerUrl, String param1, String value1, String param2, String value2) throws  IOException, JSONException {
        URL url = new URL(controllerUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        OutputStream outputStream = http.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String data = URLEncoder.encode(param1, "UTF-8") + "=" + URLEncoder.encode(value1, "UTF-8") +
                "&&" + URLEncoder.encode(param2, "UTF-8") + "=" + URLEncoder.encode(value2, "UTF-8");

        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();

        InputStream inputStream = http.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = "";
        line = bufferedReader.readLine();

        bufferedReader.close();
        inputStream.close();
        http.disconnect();

        if (line.equals("true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean QueryInscription(String controllerUrl, String login, String password, String secteurLibelle) throws IOException {
        URL url = new URL(controllerUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        OutputStream outputStream = http.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(login, "UTF-8") +
                "&&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") +
                "&&" + URLEncoder.encode("secteurLibelle", "UTF-8") + "=" + URLEncoder.encode(secteurLibelle, "UTF-8");

        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();

        InputStream inputStream = http.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = "";
        line = bufferedReader.readLine();

        bufferedReader.close();
        inputStream.close();
        http.disconnect();

        if (line.equals("true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean QueryUpdateExposantInfos(String controllerUrl, String exposantId, String raisonSociale, String activite, String nom, String prenom, String telephone, String mail) throws IOException {
        URL url = new URL(controllerUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        OutputStream outputStream = http.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String data = URLEncoder.encode("ExposantId", "UTF-8") + "=" + URLEncoder.encode(exposantId, "UTF-8") +
                "&&" + URLEncoder.encode("RaisonSociale", "UTF-8") + "=" + URLEncoder.encode(raisonSociale, "UTF-8") +
                "&&" + URLEncoder.encode("Activite", "UTF-8") + "=" + URLEncoder.encode(activite, "UTF-8") +
                "&&" + URLEncoder.encode("Nom", "UTF-8") + "=" + URLEncoder.encode(nom, "UTF-8") +
                "&&" + URLEncoder.encode("Prenom", "UTF-8") + "=" + URLEncoder.encode(prenom, "UTF-8") +
                "&&" + URLEncoder.encode("Telephone", "UTF-8") + "=" + URLEncoder.encode(telephone, "UTF-8") +
                "&&" + URLEncoder.encode("Mail", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8");

        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();

        InputStream inputStream = http.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = "";
        line = bufferedReader.readLine();

        bufferedReader.close();
        inputStream.close();
        http.disconnect();

        if (line.equals("true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
