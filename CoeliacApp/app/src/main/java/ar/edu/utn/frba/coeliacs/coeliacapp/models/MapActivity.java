package ar.edu.utn.frba.coeliacs.coeliacapp.models;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import java.io.Console;
import java.util.List;
import java.util.Map;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.fragment.MapFragment;
import ar.edu.utn.frba.coeliacs.coeliacapp.fragment.MapLocationListener;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByRadius;

public class MapActivity extends AppCompatActivity implements MapFragment.MapFragmentListener {

    private MapFragment mapFragment;
    private LocationManager locationManager;
    private MapLocationListener gpsListener = new MapLocationListener(this);
    private MapLocationListener networkListener = new MapLocationListener(this);

    public void onChangeLocation() {
        boolean gpsEnabled = locationManager.isProviderEnabled(GPS_PROVIDER);
        boolean networkEnabled = locationManager.isProviderEnabled(NETWORK_PROVIDER);

        if (!networkEnabled && !gpsEnabled) {
            showSettingsAlert();
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            changeLocation(location);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(NETWORK_PROVIDER, 1, 10, networkListener);
        locationManager.requestLocationUpdates(GPS_PROVIDER, 1, 10, gpsListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(gpsListener);
        locationManager.removeUpdates(networkListener);
    }

    public void changeLocation(Location location) {
        getShopsByRadius(location.getLatitude(), location.getLongitude(), 10, new WebServiceCallback<List<Shop>>() {
        @Override
        public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
            List<Shop> shops = webServiceResponse.getBodyAsObject();
            mapFragment.setMarkers(shops);
        }
    });
        mapFragment.changeLocation(location);
    }

    private void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
}
