package org.kisio.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.Context;
import android.widget.Toast;

import org.kisio.NavitiaSDK.NavitiaSDK;
import org.kisio.NavitiaSDK.NavitiaConfiguration;
import org.kisio.NavitiaSDK.invokers.ApiCallback;
import org.kisio.NavitiaSDK.invokers.ApiException;
import org.kisio.NavitiaSDK.models.PlacesResponse;

import com.squareup.okhttp.Call;

import java.util.List;
import java.util.Map;

public class NavitiaSDKBridge extends CordovaPlugin {
  private Toast toast = null;
  
  @Override
  public boolean execute(
    String action, 
    JSONArray args, 
    CallbackContext callbackContext
  ) throws JSONException {
    try {
      if ("places.getPlaces".equals(action)) {
        placesGetPlaces(args.getString(0), callbackContext);
        return true;
      }  
    } catch (JSONException e) {
      throw(e);
    } catch (Exception e) {
      return false;
    }
    
    return false;
  }

  private void placesGetPlaces (
    String msg, 
    CallbackContext callbackContext
  ) throws Exception {
    final CallbackContext currentCallbackContext = callbackContext;
    cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                  NavitiaSDK navitiaSDK = new NavitiaSDK(new NavitiaConfiguration("9e304161-bb97-4210-b13d-c71eaf58961c"));
                  ApiCallback<PlacesResponse> callback = null;
                  
                  callback = new ApiCallback<PlacesResponse>() {
                      @Override
                      public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                          showToast(e.getMessage(), Toast.LENGTH_LONG);
                          currentCallbackContext.error(e.getMessage());
                      }

                      @Override
                      public void onSuccess(PlacesResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                            showToast(result.toString(), Toast.LENGTH_LONG);
                            currentCallbackContext.success(result.toString());
                      }

                      @Override
                      public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                      }

                      @Override
                      public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                      }
                  };
              
                  try {
                    navitiaSDK.getPlacesApi().getPlacesRequestBuilder().withQ("gare").withCount(10).execute(callback);
                  } catch (Exception e) {
                    currentCallbackContext.error(e.getMessage());
                  }
                }
            });
    
  }

  private void showToast(final String message, final int length) {
    cordova.getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        toast = Toast.makeText(cordova.getActivity(), message, length);
        toast.show();
      }
    });
  }
}