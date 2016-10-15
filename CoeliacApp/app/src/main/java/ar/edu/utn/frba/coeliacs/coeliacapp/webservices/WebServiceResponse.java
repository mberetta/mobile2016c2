package ar.edu.utn.frba.coeliacs.coeliacapp.webservices;

import com.google.gson.Gson;

/**
 * Created by mberetta on 02/09/2016.
 */
public class WebServiceResponse<ResponseType> {

    private ResponseType bodyAsObject;
    private Throwable ex;

    public WebServiceResponse(ResponseType bodyAsObject) {
        this.bodyAsObject = bodyAsObject;
    }

    public WebServiceResponse(Throwable ex) {
        this.ex = ex;
    }

    public ResponseType getBodyAsObject() {
        return bodyAsObject;
    }

    public Throwable getEx() {
        return ex;
    }

}
