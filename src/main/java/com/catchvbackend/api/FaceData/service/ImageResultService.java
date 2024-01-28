package com.catchvbackend.api.FaceData.service;

import com.catchvbackend.api.FaceData.domain.ImageResult;
import com.catchvbackend.api.FaceData.repository.ImageResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageResultService {
    private final ImageResultRepository serviceImageResultRepository;

    public static String requestImageEmailExtraction(String userEmail) throws JSONException {
        HashMap<String, String> dict= new HashMap<>();
        JSONObject json = new JSONObject(String.valueOf(userEmail));
        Iterator<?> i = json.keys();
        requestImageEmailFind(i, dict, json);
        return dict.get("userEmail");
    }

    private static void requestImageEmailFind(Iterator<?> i, HashMap<String, String> dict, JSONObject json) {
        while(i.hasNext()){
            String k = i.next().toString();
            dict.put(k, json.getString(k));
        }
    }

    public  void resultJsonProcessing(String resultData) {
        try{
            JSONObject jsonObject = new JSONObject(resultData);
            int videoCount = Integer.parseInt(jsonObject.getString("total_inspected_video_count"));
            JSONArray result= jsonObject.getJSONArray("result");
            JSONObject processUserInfo = (JSONObject) result.get(0);
            String userEmail=processUserInfo.getString("requested_user_email");
            JSONArray processUrlLists=processUserInfo.getJSONArray("urls");
            ArrayList<String> urlList= new ArrayList<>();
            if(processUrlLists != null){
                urlListMapping(processUrlLists, urlList);
            }
            int detectCount =urlList.size();
            ImageResult createdImageResult
                    = ImageResult.createServiceResult(videoCount,detectCount,userEmail,urlList);
            saveResult(createdImageResult);
        }catch(Exception e){
            log.error("Error processing result json",e);
        }

    }
    private static void urlListMapping(JSONArray processUrlLists, ArrayList<String> urlList) {
        for(int i = 0; i< processUrlLists.length(); i++){
            String tempString= processUrlLists.getString(i);
            String ptempString = tempString.replaceAll("\"","");
            log.info(ptempString);
            urlList.add(ptempString);
        }
    }

    @Transactional
    public void saveResult(ImageResult imageResultData) {
        serviceImageResultRepository.saveResult(imageResultData);
    }
}
