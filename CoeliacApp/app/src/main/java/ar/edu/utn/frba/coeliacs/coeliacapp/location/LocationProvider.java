package ar.edu.utn.frba.coeliacs.coeliacapp.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by mberetta on 29/10/2016.
 */
public abstract class LocationProvider implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;

    public LocationProvider(Context context) {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        onLastKnownLocationAvailable(location.getLatitude(), location.getLongitude());
        googleApiClient.disconnect();
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

    public abstract void onLastKnownLocationAvailable(double latitude, double longitude);

    public abstract void onError();

}
