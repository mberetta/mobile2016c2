package ar.edu.utn.frba.coeliacs.coeliacapp.webservices;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mberetta on 01/09/2016.
 */
public class WebServiceCallTask<ResponseType> extends AsyncTask<Void, Void, WebServiceResponse<ResponseType>> {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private Class<ResponseType> responseClass;
    private Type type;
    private URL url;
    private String method;
    private WebServiceCallback<ResponseType> webServiceCallback;

    public WebServiceCallTask(String url, String method, WebServiceCallback<ResponseType> webServiceCallback) {
        this.method = method;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.webServiceCallback = webServiceCallback;
    }

    public WebServiceCallTask(Class<ResponseType> responseClass, String url, String method, WebServiceCallback<ResponseType> webServiceCallback) {
        this(url, method, webServiceCallback);
        this.responseClass = responseClass;
    }

    public WebServiceCallTask(Type type, String url, String method, WebServiceCallback<ResponseType> webServiceCallback) {
        this(url, method, webServiceCallback);
        this.type = type;
    }

    @Override
    protected WebServiceResponse<ResponseType> doInBackground(Void... params) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            // TODO consider response code
            String responseBody = IOUtils.toString(conn.getInputStream(), DEFAULT_CHARSET);
            // TODO consider body = null or {error:true}
            return new WebServiceResponse<ResponseType>(bodyToObject(responseBody));
        } catch (Throwable e) {
            return new WebServiceResponse<ResponseType>(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(WebServiceResponse<ResponseType> webServiceResponse) {
        webServiceCallback.onFinished(webServiceResponse);
    }

    private ResponseType bodyToObject(String body) {
        Gson gson = new Gson();
        if (responseClass != null) {
            return gson.fromJson(body, responseClass);
        } else if (type != null) {
            return gson.fromJson(body, type);
        }
        throw new RuntimeException("Can't convert json to object.");
    }

}
