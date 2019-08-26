package cordova.plugin.novosdk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentManager;
import com.novopayment.tokenizationlib.Dominian.Model.Configuration.DataConfiguration;
import com.novopayment.tokenizationlib.Dominian.Model.Configuration.UserInfo;
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
            String message = args.getString(0);
            this.enrollDeviceVisa(message, callbackContext);
            return true;
        }

        if (action.equals("enrollCardVisa")) {
            String message = args.getString(0);
            this.enrollCardVisa(message, callbackContext);
            return true;
        }

        if (action.equals("getContentCard")) {
            String message = args.getString(0);
            this.getContentCard(message, callbackContext);
            return true;
        }

        if (action.equals("lifecycleManagerTokenVisa")) {
            String message = args.getString(0);
            this.lifecycleManagerTokenVisa(message, callbackContext);
            return true;
        }

        if (action.equals("selectCardVisa")) {
            String message = args.getString(0);
            this.selectCardVisa(message, callbackContext);
            return true;
        }

        if (action.equals("getTransactionHistory")) {
            String message = args.getString(0);
            this.getTransactionHistory(message, callbackContext);
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

    private void enrollDeviceVisa(String message, CallbackContext callbackContext) {
        
        try {

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.enrollDeviceVisa(this.cordova.getActivity(), dataConfiguration, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    callbackContext.success(responseTokenization.toString());
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    callbackContext.error(responseTokenization.toString());
                }
            }); 
        } catch (Exception ex) {
            callbackContext.error("enrollDeviceVisa=> " + ex);
        }        
    }

    private void enrollCardVisa(String message, CallbackContext callbackContext) {
        
        try {

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.enrollCardVisa(this.cordova.getActivity(), dataConfiguration, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    callbackContext.success(responseTokenization.toString());
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    callbackContext.error(responseTokenization.toString());
                }
            });
        } catch (Exception ex) {
            callbackContext.error("enrollCardVisa=> " + ex);
        } 
    }

    private void getContentCard(String message, CallbackContext callbackContext) {

        try {

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);
            String requiredContent = "";

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.getContentCard(this.cordova.getActivity(), dataConfiguration, requiredContent, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    callbackContext.success(responseTokenization.toString());
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    callbackContext.error(responseTokenization.toString());
                }
            });
        } catch (Exception ex) {
            callbackContext.error("getContentCard=> " + ex);
        } 
    }

    private void lifecycleManagerTokenVisa(String message, CallbackContext callbackContext) {

        try {

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.lifecycleManagerTokenVisa(this.cordova.getActivity(), dataConfiguration, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    callbackContext.success(responseTokenization.toString());
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    callbackContext.error(responseTokenization.toString());
                }
            }); 
        } catch (Exception ex) {
            callbackContext.error("lifecycleManagerTokenVisa=> " + ex);
        } 
    }

    private void selectCardVisa(String message, CallbackContext callbackContext) {

        try {            

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);
            int position = 0;
            FragmentManager supportFragmentManager= null;

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.selectCardVisa(this.cordova.getActivity(), dataConfiguration, position, supportFragmentManager, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    callbackContext.success(responseTokenization.toString());
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    callbackContext.error(responseTokenization.toString());
                }
            }); 
        } catch (Exception ex) {
            callbackContext.error("selectCardVisa=> " + ex);
        } 
    }

    private void getTransactionHistory(String message, CallbackContext callbackContext) {

        try {

            DataConfiguration dataConfiguration = new DataConfiguration("", null, null, null, null);

            TokenizationVisa tokenizationVisa = TokenizationVisa.INSTANCE;
            tokenizationVisa.getTransactionHistory(this.cordova.getActivity(), dataConfiguration, new TokenizationVisaCallback.VTSCallback() {
                @Override
                public void onSuccessResponse(ResponseTokenization responseTokenization) {
                    callbackContext.success(responseTokenization.toString());
                }

                @Override
                public void onFailedResponse(ResponseTokenization responseTokenization) {
                    callbackContext.error(responseTokenization.toString());
                }
            });
        } catch (Exception ex) {
            callbackContext.error("getTransactionHistory=> " + ex);
        } 
    }
}
