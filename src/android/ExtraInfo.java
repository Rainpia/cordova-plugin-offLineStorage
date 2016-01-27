package org.apache.cordova.offLineStorage;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ryp on 1/22/16.
 */
public class ExtraInfo extends CordovaPlugin {


    /**
     * judge web return method
     * params action           methodName
     * params args             params
     * params callbackContext  callback
     * **/
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext)
            throws JSONException {

        if (action.equals("saveData")) {
            System.out.println("saveData");
            saveData(args,callbackContext);
        }else if(action.equals("getData")){
            System.out.println("getData");
            getData(args,callbackContext);
        }else{
            return false;
        }
        return true;
    }
    /**
     * save data
     * params args             params
     * params callbackContext  callback
     * **/
    public void saveData(JSONArray args,CallbackContext callbackContext)
             throws JSONException{
        JSONObject returnRes = new  JSONObject();


        if(saveObject(args)){
            returnRes.put("code",200);
            returnRes.put("message","save success");
            callbackContext.success(returnRes);
        }else{
            returnRes.put("code",404);
            returnRes.put("message","not save success");
            callbackContext.error(returnRes);
        }

    }
    /**
     * get data
     * params args             params
     * params callbackContext  callback
     * **/
    public void getData(JSONArray args,CallbackContext callbackContext)
            throws JSONException{

        JSONObject returnRes = new  JSONObject();

        DBUtil dbUtil=new DBUtil();
        ArrayList<JSONObject> resData=dbUtil.query(args.get(0).toString());
        dbUtil.closeDatabase();

        if(resData.size()>0){
            returnRes.put("code",200);
            returnRes.put("message","success return data");
            returnRes.put("data",resData);
            callbackContext.success(returnRes);
        }else{
            returnRes.put("code",404);
            returnRes.put("message","not have data");
            callbackContext.error(returnRes);
        }
    }
    /**
     * save object insert data
     * params args  params
     * **/
    public boolean saveObject(JSONArray args) throws JSONException{
        DBUtil dbUtil=new DBUtil();


        JSONObject key= (JSONObject) args.get(0);
        System.out.println(key.get("url"));

        if(key.get("url").equals("api/login")){
            dbUtil.delete();//执行删除操作
        }

        if(dbUtil.insert(args.get(0).toString(),args.get(1).toString())){
            dbUtil.closeDatabase();
            return true;
        }else{
            return false;
        }

    }
}