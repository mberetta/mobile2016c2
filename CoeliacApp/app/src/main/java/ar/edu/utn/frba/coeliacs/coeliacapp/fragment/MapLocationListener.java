package ar.edu.utn.frba.coeliacs.coeliacapp.fragment;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import ar.edu.utn.frba.coeliacs.coeliacapp.models.MapActivity;

public class MapLocationListener implements LocationListener {

    private MapActivity activity;
    public MapLocationListener(MapActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {
        activity.changeLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
