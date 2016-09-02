package ar.com.mobile2016.frontend.webservice;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mberetta on 01/09/2016.
 */
public class WebServiceCallTask<ResponseType> extends AsyncTask<Void, Void, WebServiceResponse<ResponseType>> {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private Class<ResponseType> responseClass;
    private URL url;
    private String method;
    private WebServiceCallback<ResponseType> webServiceCallback;

    public WebServiceCallTask(Class<ResponseType> responseClass, String url, String method) {
        this.responseClass = responseClass;
        this.method = method;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected WebServiceResponse<ResponseType> doInBackground(Void... params) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            String responseBody = readStringFromInputStream(conn.getInputStream(), DEFAULT_CHARSET);
            return new WebServiceResponse<ResponseType>(conn.getResponseCode(), responseBody, responseClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private String readStringFromInputStream(InputStream is, String charset) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedInputStream bis = new BufferedInputStream(is);
        InputStreamReader isr = new InputStreamReader(bis, charset);
        int c;
        while ((c = isr.read()) != -1) {
            sb.append((char) c);
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(WebServiceResponse<ResponseType> webServiceResponse) {
        if (webServiceCallback != null) webServiceCallback.onFinished(webServiceResponse);
    }

    public void setWebServiceCallback(WebServiceCallback<ResponseType> webServiceCallback) {
        this.webServiceCallback = webServiceCallback;
    }

}
