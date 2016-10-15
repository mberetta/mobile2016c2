package ar.edu.utn.frba.coeliacs.coeliacapp.webservices;

/**
 * Created by mberetta on 02/09/2016.
 */
public interface WebServiceCallback<ResponseType> {

    void onFinished(WebServiceResponse<ResponseType> webServiceResponse);

}
