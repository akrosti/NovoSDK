package cordova.plugin.novosdk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import android.support.v4.app.FragmentManager;
import com.novopayment.tokenizationlib.Dominian.Model.Configuration.*;
import com.novopayment.tokenizationlib.Dominian.Model.ResponseTokenization;
import com.novopayment.tokenizationlib.TokenizationVisa;
import com.novopayment.tokenizationlib.TokenizationVisaCallback;

public class novosdk extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        
        if (action.equals("test")) {
            String message = args.getString(0);
            this.test(message, callbackContext);
            return true;
        }

        if (action.equals("enrollDeviceVisa")) {
            this.enrollDeviceVisa(args, callbackContext);
            return true;
        }

        if (action.equals("enrollCardVisa")) {
            this.enrollCardVisa(args, callbackContext);
            return true;
        }

        if (action.equals("getContentCard")) {
            this.getContentCard(args, callbackContext);
            return true;
        }

        if (action.equals("lifecycleManagerTokenVisa")) {
            this.lifecycleManagerTokenVisa(args, callbackContext);
            return true;
        }

        if (action.equals("selectCardVisa")) {
            this.selectCardVisa(args, callbackContext);
            return true;
        }

        if (action.equals("getTransactionHistory")) {
            this.getTransactionHistory(args, callbackContext);
            return true;
        }

        return false;
    }

    private void test(String message, CallbackContext callbackContext) {

        if (message != null && message.length() > 0) {
            callbackContext.success("test success!");
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void enrollDeviceVisa(JSONArray message, CallbackContext callbackContext) {
        
        try {
            Gson gson = new Gson();            

            JSONObject data = message.getJSONObject(0);
            JSONObject userInfo = data.getJSONObject("UserInfo");

            DataConfiguration dataConfiguration = new DataConfiguration(
                data.getString("clientID"),
                null, 
                null, 
                new UserInfo(
                    userInfo.getString("userID"), 
                    userInfo.getString("email"),
                    null), 
                null);

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.enrollDeviceVisa(this.cordova.getActivity(), dataConfiguration, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.success(result);
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.error(result);
                }
            }); 
        } catch (Exception ex) {
            callbackContext.error("enrollDeviceVisa=> " + ex);
        }        
    }

    private void enrollCardVisa(JSONArray message, CallbackContext callbackContext) {
        
        try {
            Gson gson = new Gson();            

            JSONObject data = message.getJSONObject(0);
            JSONObject userInfo = data.getJSONObject("UserInfo");
            JSONObject panCardData = data.getJSONObject("PanCardData");
            JSONObject expirationDate = panCardData.getJSONObject("ExpirationDate");

            DataConfiguration dataConfiguration = new DataConfiguration(
                data.getString("clientID"),
                null, 
                new PanCardData(
                    panCardData.getString("accountNumber"),
                    panCardData.getString("name"),
                    panCardData.getString("cvv2"),
                        new ExpirationDate(
                            expirationDate.getString("month"),
                            expirationDate.getString("year")
                        )   
                ), 
                new UserInfo(
                    userInfo.getString("userID"), 
                    userInfo.getString("email"),
                    userInfo.getString("clientWalletAccountId")
                    ),
                null);

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.enrollCardVisa(this.cordova.getActivity(), dataConfiguration, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.success(result);
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.error(result);
                }
            });
        } catch (Exception ex) {
            callbackContext.error("enrollCardVisa=> " + ex);
        } 
    }

    private void getContentCard(JSONArray message, CallbackContext callbackContext) {

        try {
            Gson gson = new Gson();            

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);
            String requiredContent = "";

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.getContentCard(this.cordova.getActivity(), dataConfiguration, requiredContent, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.success(result);
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.error(result);
                }
            });
        } catch (Exception ex) {
            callbackContext.error("getContentCard=> " + ex);
        } 
    }

    private void lifecycleManagerTokenVisa(JSONArray message, CallbackContext callbackContext) {

        try {
            Gson gson = new Gson();            

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.lifecycleManagerTokenVisa(this.cordova.getActivity(), dataConfiguration, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.success(result);
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.error(result);
                }
            }); 
        } catch (Exception ex) {
            callbackContext.error("lifecycleManagerTokenVisa=> " + ex);
        } 
    }

    private void selectCardVisa(JSONArray message, CallbackContext callbackContext) {

        try {            
            Gson gson = new Gson();

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);
            int position = 0;
            FragmentManager supportFragmentManager= null;

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.selectCardVisa(this.cordova.getActivity(), dataConfiguration, position, supportFragmentManager, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.success(result);
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.error(result);
                }
            }); 
        } catch (Exception ex) {
            callbackContext.error("selectCardVisa=> " + ex);
        } 
    }

    private void getTransactionHistory(JSONArray message, CallbackContext callbackContext) {

        try {
            Gson gson = new Gson();

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.getTransactionHistory(this.cordova.getActivity(), dataConfiguration, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.success(result);
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.error(result);
                }
            });
        } catch (Exception ex) {
            callbackContext.error("getTransactionHistory=> " + ex);
        } 
    }
}