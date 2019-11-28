package cordova.plugin.novosdk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import com.google.gson.Gson;

import android.app.FragmentManager;
import com.novopayment.tokenizationlib.domain.models.configuration.*;
import com.novopayment.tokenizationlib.domain.models.ResponseTokenization;
import com.novopayment.tokenizationlib.TokenizationVisa;
import com.novopayment.tokenizationlib.TokenizationVisaCallback;
import com.novopayment.tokenizationlib.domain.models.cardData.DataTokenizationCard;
import com.novopayment.tokenizationlib.payment.VcpcsService;
import android.nfc.cardemulation.CardEmulation;
import android.content.*;
//////////////////////////////////////
//PARA VER LOGS EN ANDROID STUDIO
//import android.util.Log;
//Uso=> Log.d("VTS", "SelectSDK");
/////////////////////////////////////
//////////////////////////////////////
//DESCOMENTAR SEGUN EL APP A UTILIZAR
/////////////////////////////////////
//import com.betotepresta.beto.cr.MainActivity;
//import com.multimoney.multimoney.cr.MainActivity;
//import com.impulsat.impulsat.gt.MainActivity;

public class novosdk extends CordovaPlugin {
    
    private BroadcastReceiver receiver= null;

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

        if (action.equals("clearNotification")) {
            this.clearNotification(callbackContext);
            return true;
        }

        return false;
    }

    public void setDefaultWallet() {
        Intent intent = new Intent();
        intent.setAction(CardEmulation.ACTION_CHANGE_DEFAULT);
        intent.putExtra(CardEmulation.EXTRA_SERVICE_COMPONENT, new ComponentName(this.cordova.getActivity().getApplicationContext() , VcpcsService.class));
        intent.putExtra(CardEmulation.EXTRA_CATEGORY, CardEmulation.CATEGORY_PAYMENT);
        this.cordova.getActivity().startActivity(intent);
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
            FragmentManager supportFragmentManager= ((MainActivity) this.cordova.getActivity()).getSupportManager();

            DataConfiguration dataConfiguration = gson.fromJson(json.toString(), DataConfiguration.class);
            dataConfiguration.setTokenData(null);
            dataConfiguration.setPanCardData(null);
            dataConfiguration.setCards(null);
            dataConfiguration.getUserInfo().setClientWalletAccountId(null);        

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.enrollDeviceVisa(this.cordova.getActivity(), dataConfiguration, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    tokenizationVisa.getReceiver(supportFragmentManager);

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

            if (!((MainActivity) this.cordova.getActivity()).getBanderaSelectCard())
            {
                return;
            }
            
            ((MainActivity) this.cordova.getActivity()).setBanderaSelectCard(false);

            Gson gson = new Gson();

            JSONObject json = message.getJSONObject(0);            

            DataConfiguration dataConfiguration = gson.fromJson(json.toString(), DataConfiguration.class);

            int position = 0;

            FragmentManager supportFragmentManager= ((MainActivity) this.cordova.getActivity()).getSupportManager();

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.selectCardVisa(this.cordova.getActivity().getApplicationContext(), dataConfiguration, position, supportFragmentManager, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    String result = gson.toJson(responseTokenization);
                    callbackContext.success(result);
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    ((MainActivity) cordova.getActivity()).setBanderaSelectCard(true);
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
            setDefaultWallet();

            Gson gson = new Gson();

            FragmentManager supportFragmentManager= ((MainActivity) this.cordova.getActivity()).getSupportManager();

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            
            receiver = tokenizationVisa.getReceiver(supportFragmentManager);
            tokenizationVisa.registerBroadcastEvents(this.cordova.getActivity(), receiver);
            tokenizationVisa.registerBroadcastNetworkReceiver((this.cordova.getActivity()));
            ArrayList<DataTokenizationCard> cardsList = tokenizationVisa.getTokenizationCards(this.cordova.getActivity());
            
            String result = gson.toJson(cardsList);
            callbackContext.success(result);

        } catch (Exception ex) {
            callbackContext.error("getTokenizationCards=> " + ex);
        } 
    }

    private void clearNotification(CallbackContext callbackContext) {

        try {
        
            Gson gson = new Gson();

            if ( receiver != null )
            {
                TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
                tokenizationVisa.clearNotification(this.cordova.getActivity(), receiver);
            }

            callbackContext.success("ok");

        } catch (Exception ex) {
            callbackContext.error("clearNotification=> " + ex);
        } 
    }

}