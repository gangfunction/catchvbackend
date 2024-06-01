package com.catchvbackend.api.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@Service
public class JsonProcessingService {

  public void processJsonPayload(String jsonPayload) {
    try {
      String userEmail = requestImageEmailExtraction(jsonPayload);
      System.out.println("Extracted userEmail: " + userEmail);

      JSONObject jsonObject = new JSONObject(jsonPayload);
      JSONArray urlArray = jsonObject.getJSONArray("urls");
      ArrayList<String> urlList = new ArrayList<>();
      urlListMapping(urlArray, urlList);
      System.out.println("Processed URLs: " + urlList);

    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public static String requestImageEmailExtraction(String userEmail) throws JSONException {
    HashMap<String, String> dict = new HashMap<>();
    JSONObject json = new JSONObject(String.valueOf(userEmail));
    Iterator<?> i = json.keys();
    requestImageEmailFind(i, dict, json);
    return dict.get("userEmail");
  }

  private static void requestImageEmailFind(Iterator<?> i, HashMap<String, String> dict, JSONObject json) {
    while (i.hasNext()) {
      String k = i.next().toString();
      dict.put(k, json.getString(k));
    }
  }

  private static void urlListMapping(JSONArray processUrlLists, ArrayList<String> urlList) {
    for (int i = 0; i < processUrlLists.length(); i++) {
      String tempString = processUrlLists.getString(i);
      String ptempString = tempString.replaceAll("\"", "");
      System.out.println(ptempString);
      urlList.add(ptempString);
    }
  }
}