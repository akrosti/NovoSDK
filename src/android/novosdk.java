package cordova.plugin.novosdk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.novopayment.tokenizationlib.Dominian.Model.Configuration.DataConfiguration;
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
    }
}
