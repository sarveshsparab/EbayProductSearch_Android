package com.sarveshparab.ebayproductsearch.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JSONRequest extends Request {

    private Response.Listener responseListener;
    private Map<String, String> params;
    Priority mPriority;

    public JSONRequest(int method, String url, Map<String, String> params,
                       Response.Listener responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.responseListener = responseListener;
        this.params = params;
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        responseListener.onResponse(response);
    }

}
