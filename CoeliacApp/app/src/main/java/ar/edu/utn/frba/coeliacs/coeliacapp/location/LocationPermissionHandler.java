package ar.edu.utn.frba.coeliacs.coeliacapp.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by mberetta on 15/11/2016.
 */
public class LocationPermissionHandler {

    public static final int PERM_REQUEST = 1;
    private Context context;

    public LocationPermissionHandler(Context context) {
        this.context = context;
    }

    public boolean checkAndRequestIfNeeded() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PERM_REQUEST);
            return false;
        }
        return true;
    }

    public boolean permissionGranted(int[] grantResults) {
        return grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    /*

    Del lado del activity se debe implementar el siguiente método para hacer el trabajo
    una vez aceptado el permiso (o no hacerlo si fue denegado).



        @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == LocationPermissionHandler.PERM_REQUEST) {
            if (locPermHandler.permissionGranted(grantResults)) {
                getAndUseLocation();
            } else {
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

     */

}
