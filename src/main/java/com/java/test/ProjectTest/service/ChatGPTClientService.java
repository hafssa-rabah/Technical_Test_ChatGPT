package com.java.test.ProjectTest.service;

import java.io.FileWriter;
import java.io.IOException;

import com.java.test.ProjectTest.repository.ChatGPTClientRepository;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Service;

@Service
public class ChatGPTClientService implements ChatGPTClientRepository {

    private final String API_KEY = "your apiKey";
    private final String API_URL = "https://api.openai.com/v1/completions";
    private final String FilePath = "output.csv";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public ChatGPTClientService() {
    }

    @Override
    public String getResponse(String prompt) throws IOException {
        HttpPost request = new HttpPost(API_URL);
        StringEntity requestBody = new StringEntity("{\"model\": \"text-davinci-003\",\"prompt\": \"" + prompt + "\",\"max_tokens\": 4000,\"temperature\": 1.0}");
        request.setEntity(requestBody);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseStr = EntityUtils.toString(entity);
                return responseStr;
            } else {
                throw new IOException("Response from ChatGPT API is empty");
            }
        }
    }

    @Override
    public void getResponseAndWriteToCSV(String prompt) throws IOException {
        HttpPost request = new HttpPost(API_URL);
        StringEntity requestBody = new StringEntity("{\"model\": \"text-davinci-003\",\"prompt\": \"" + prompt + "\",\"max_tokens\": 4000,\"temperature\": 1.0}");
        request.setEntity(requestBody);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseStr = EntityUtils.toString(entity);
                JSONObject responseJson = new JSONObject(responseStr);
                JSONArray choices = responseJson.getJSONArray("choices");
                String answer = choices.getJSONObject(0).getString("text");

                FileWriter csvWriter = new FileWriter(FilePath, true);
                csvWriter.append(prompt);
                csvWriter.append(";");
                csvWriter.append(answer.replace("\n", ""));
                csvWriter.append("\n");
                csvWriter.flush();
                csvWriter.close();
            } else {
                throw new IOException("Response from ChatGPT API is empty");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
