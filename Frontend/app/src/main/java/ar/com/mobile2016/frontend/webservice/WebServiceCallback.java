package ar.com.mobile2016.frontend.webservice;

/**
 * Created by mberetta on 02/09/2016.
 */
public interface WebServiceCallback<ResponseType> {

    void onFinished(WebServiceResponse<ResponseType> webServiceResponse);

}
