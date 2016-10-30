package ar.edu.utn.frba.coeliacs.coeliacapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mberetta on 16/10/2016.
 */
public class ErrorHandling {

    public static void showWebServiceError(Context context) {
        Toast.makeText(context, R.string.error_webservice, Toast.LENGTH_LONG).show();
    }

    public static void showLocationError(Context context) {
        Toast.makeText(context, R.string.error_location, Toast.LENGTH_LONG).show();
    }

}
