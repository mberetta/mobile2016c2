package ar.edu.utn.frba.coeliacs.coeliacapp.location;

import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static com.google.android.gms.common.api.CommonStatusCodes.RESOLUTION_REQUIRED;
import static com.google.android.gms.common.api.CommonStatusCodes.SUCCESS;
import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;
import static com.google.android.gms.location.LocationServices.SettingsApi;
import static com.google.android.gms.location.LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE;

public class MapLocationProvider implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {

    public static final int LOCATION_PROMPT_USER = 1000;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult((AppCompatActivity) context, 9000);
            Toast.makeText(context, "Cannot connect with Location Services", Toast.LENGTH_SHORT).show();
        } catch (IntentSender.SendIntentException e) {
            Log.i("Connection error", "onConnectionFailed: Cannot connect to location services");
        }
    }

    public interface MapLocationProviderListener {
        void connected(Location location);

        void changeLocation(Location location);
    }

    private GoogleApiClient googleApiClient;
    private MapLocationProviderListener listener;
    private Context context;
    private LocationRequest locationRequest;

    public MapLocationProvider(Context _context, MapLocationProviderListener _listener) {
        listener = _listener;
        context = _context;

        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (checkSelfPermission(context, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            Toast.makeText(context, "Cannot get location", Toast.LENGTH_SHORT).show();
            return;
        }

        updateLocationRequest();

        PendingResult<LocationSettingsResult> settingsResult = SettingsApi.checkLocationSettings(googleApiClient, new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build());
        settingsResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case SUCCESS:
                        Location location = FusedLocationApi.getLastLocation(googleApiClient);
                        listener.connected(location);
                        break;
                    case RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult((AppCompatActivity) context, LOCATION_PROMPT_USER);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }

    private void updateLocationRequest() {
        if (checkSelfPermission(context, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            return;
        }

        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
//        locationRequest.setPriority(PRIORITY_HIGH_ACCURACY);
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
        if (checkSelfPermission(context, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            return null;
        }
        return FusedLocationApi.getLastLocation(googleApiClient);
    }

    public void connect() {
        googleApiClient.connect();
    }

    public void disconnect() {
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    public void resume() {
        if (googleApiClient.isConnected()) {
            updateLocationRequest();
        } else {
            googleApiClient.connect();
        }
    }

    public void pause() {
        if (googleApiClient.isConnected()){
            FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

}
