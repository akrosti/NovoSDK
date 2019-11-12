package cordova.plugin.novosdk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import com.google.gson.Gson;

import android.app.FragmentManager;
import com.novopayment.tokenizationlib.dominian.model.Configuration.*;
import com.novopayment.tokenizationlib.dominian.model.ResponseTokenization;
import com.novopayment.tokenizationlib.TokenizationVisa;
import com.novopayment.tokenizationlib.TokenizationVisaCallback;
import com.novopayment.tokenizationlib.dominian.model.cardData.DataTokenizationCard;
//////////////////////////////////////
//DESCOMENTAR SEGUN EL APP A UTILIZAR
/////////////////////////////////////
//import com.betotepresta.beto.cr.MainActivity;
//import com.multimoney.multimoney.cr.MainActivity;
//import com.impulsat.impulsat.gt.MainActivity;

public class novosdk extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        
        if (action.equals("test")) {            
            this.test(args, callbackContext);
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

        if (action.equals("getTokenizationCards")) {
            this.getTokenizationCards(callbackContext);
            return true;
        }

        return false;
    }

    private void test(JSONArray message, CallbackContext callbackContext) {

        try {
            Gson gson = new Gson();            

            JSONObject json = message.getJSONObject(0);            

            DataConfiguration dataConfiguration = gson.fromJson(json.toString(), DataConfiguration.class);

            String result = gson.toJson(dataConfiguration);

            callbackContext.success(result);

        } catch (Exception ex) {
            callbackContext.error("enrollDeviceVisa=> " + ex);
        }    
    }

    private void enrollDeviceVisa(JSONArray message, CallbackContext callbackContext) {
        
        try {
            Gson gson = new Gson();            

            JSONObject json = message.getJSONObject(0);            

            DataConfiguration dataConfiguration = gson.fromJson(json.toString(), DataConfiguration.class);
            dataConfiguration.setTokenData(null);
            dataConfiguration.setPanCardData(null);
            dataConfiguration.setCards(null);
            dataConfiguration.getUserInfo().setClientWalletAccountId(null);        

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

            JSONObject json = message.getJSONObject(0);            

            DataConfiguration dataConfiguration = gson.fromJson(json.toString(), DataConfiguration.class);
            dataConfiguration.setCards(new ArrayList());

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

            JSONObject json = message.getJSONObject(0);            

            DataConfiguration dataConfiguration = gson.fromJson(json.toString(), DataConfiguration.class);

            final String requiredContent = "CONTENT_CARD_KEY";

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

            JSONObject json = message.getJSONObject(0);            

            DataConfiguration dataConfiguration = gson.fromJson(json.toString(), DataConfiguration.class);

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

            JSONObject json = message.getJSONObject(0);            

            DataConfiguration dataConfiguration = gson.fromJson(json.toString(), DataConfiguration.class);

            int position = 0;

            FragmentManager supportFragmentManager= ((MainActivity) this.cordova.getActivity()).getSupportManager();

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

            JSONObject json = message.getJSONObject(0);            

            DataConfiguration dataConfiguration = gson.fromJson(json.toString(), DataConfiguration.class);

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

    private void getTokenizationCards(CallbackContext callbackContext) {

        try {
            Gson gson = new Gson();

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            ArrayList<DataTokenizationCard> cardsList = tokenizationVisa.getTokenizationCards(this.cordova.getActivity());
            
            String result = gson.toJson(cardsList);
            callbackContext.success(result);

        } catch (Exception ex) {
            callbackContext.error("getTokenizationCards=> " + ex);
        } 
    }
}