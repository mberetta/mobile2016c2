package ar.edu.utn.frba.coeliacs.coeliacapp.models.map;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class MapLocationProvider implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public interface MapLocationProviderListener {
        public void changeLocation(Location location);
    }

    private GoogleApiClient googleApiClient;
    private MapLocationProviderListener listener;

    public MapLocationProvider(Context context, MapLocationProviderListener _listener) {
        listener = _listener;

        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLocationRequest();

        Location location = FusedLocationApi.getLastLocation(googleApiClient);
        listener.changeLocation(location);
    }

    private void updateLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(PRIORITY_HIGH_ACCURACY);
        FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        listener.changeLocation(location);
    }

    public Location currentLocation() {
        return FusedLocationApi.getLastLocation(googleApiClient);
    }

    public void connect() {
        googleApiClient.connect();
    }

    public void disconnect() {
        googleApiClient.disconnect();
    }

    public void resume() {
        if (googleApiClient.isConnected()) {
            updateLocationRequest();
        } else {
            googleApiClient.connect();
        }
    }

    public void pause() {
        FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

}
