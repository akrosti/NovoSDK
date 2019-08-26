package cordova-plugin-novosdk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class novosdk extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("test")) {
            String message = args.getString(0);
            this.test(message, callbackContext);
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
}
