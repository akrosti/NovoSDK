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
import com.novopayment.tokenizationlib.domain.models.vtsClientConfiguration.VTSClientConfiguration;
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

        if (action.equals("deleteTokensByPan")) {
            this.deleteTokensByPan(args, callbackContext);
            return true;
        }

        return false;
    }

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();        
        TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
        FragmentManager supportFragmentManager= ((MainActivity) this.cordova.getActivity()).getSupportManager();
        receiver = tokenizationVisa.getReceiver(supportFragmentManager);
    }

    @Override
    public void onPause(boolean multitasking) {
        super.onPause(multitasking);
        
        try {
            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.clearNotification(this.cordova.getActivity(), receiver);
            // tokenizationVisa.unregisterBroadcastEvents(this.cordova.getActivity(),receiver);
            // tokenizationVisa.unregisterBroadcastNetwork(this.cordova.getActivity());
        }catch(Exception e){

        }
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        
        TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
        tokenizationVisa.registerBroadcastEvents(this.cordova.getActivity(), receiver);
        tokenizationVisa.registerBroadcastNetworkReceiver((this.cordova.getActivity()));
        tokenizationVisa.getConfigurations().asString(this.cordova.getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        
        try {    
            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.clearNotification(this.cordova.getActivity(), receiver);
            // tokenizationVisa.unregisterBroadcastEvents(this.cordova.getActivity(),receiver);
            // tokenizationVisa.unregisterBroadcastNetwork(this.cordova.getActivity());
        }catch(Exception e){

        }
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
            }, new VTSClientConfiguration(
                //"Z3J1cG9nZW50ZQ==", //Beto y MM CR
                //"Z3J1cG9nZW50ZUdU", //Impulsat
                //"Z3J1cG9nZW50ZVNW", //MM SV
                "USUARIO",
                "PASSWORD"
            )); 
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
                    ArrayList<DataTokenizationCard> cardsList = tokenizationVisa.getConfigurations().getVTSCards(cordova.getActivity());
                    tokenizationVisa.getConfigurations().saveVTSFavoriteCard(cardsList.get(cardsList.size() - 1).getVProvisionedToken(), cordova.getActivity());

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

            //int position = 0;

            FragmentManager supportFragmentManager= ((MainActivity) this.cordova.getActivity()).getSupportManager();

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;

            DataTokenizationCard card = tokenizationVisa.getCardByToken(dataConfiguration.getTokenData().getTokenID(), this.cordova.getActivity().getApplicationContext());
            
            tokenizationVisa.selectCardVisa(this.cordova.getActivity().getApplicationContext(), dataConfiguration, card, supportFragmentManager, new TokenizationVisaCallback.VTSCallback() {
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

    private void deleteTokensByPan( JSONArray message, CallbackContext callbackContext ) {

        try {                  
            Gson gson = new Gson();        
            JSONObject json = message.getJSONObject(0);

            String panCard = json.getString("panCard");    
        
            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.deleteTokensByPan(panCard, this.cordova.getActivity(), new TokenizationVisaCallback.VTSCallback() {
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
            callbackContext.error("deleteTokensByPan=> " + ex);
        } 
    }

}