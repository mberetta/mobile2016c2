package ar.com.mobile2016.frontend.webservice;

import com.google.gson.Gson;

/**
 * Created by mberetta on 02/09/2016.
 */
public class WebServiceResponse<ResponseType> {

    private int code;
    private ResponseType bodyAsObject;

    public WebServiceResponse(int code, String body, Class<ResponseType> responseClass) {
        this.code = code;
        this.bodyAsObject = new Gson().fromJson(body, responseClass);
    }

    public int getCode() {
        return code;
    }

    public ResponseType getBodyAsObject() {
        return bodyAsObject;
    }

}
