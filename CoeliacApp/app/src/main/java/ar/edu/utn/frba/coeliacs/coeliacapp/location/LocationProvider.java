package ar.edu.utn.frba.coeliacs.coeliacapp.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by mberetta on 29/10/2016.
 */
public abstract class LocationProvider implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int LOCATION_PROVIDER_PERM_REQ = 1;
    private GoogleApiClient googleApiClient;
    private Context context;
    private Activity activity;

    public LocationProvider(Context context) {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        this.context = context;
        this.activity = (Activity) context;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (checkPermission()) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            onLastKnownLocationAvailable(location.getLatitude(), location.getLongitude());
            googleApiClient.disconnect();
        }
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, LOCATION_PROVIDER_PERM_REQ);
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        /*
        Called when the client is temporarily in a disconnected state.
        This can happen if there is a problem with the remote service (e.g. a crash or resource problem causes it to be killed by the system).
        When called, all requests have been canceled and no outstanding listeners will be executed.
        GoogleApiClient will automatically attempt to restore the connection.
        Applications should disable UI components that require the service, and wait for a call to onConnected(Bundle) to re-enable them.
        */
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        onError();
    }

    public boolean onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        return grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    public abstract void onLastKnownLocationAvailable(double latitude, double longitude);

    public abstract void onError();

}
