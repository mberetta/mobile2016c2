package ar.edu.utn.frba.coeliacs.coeliacapp.models.map;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.MapLocationProvider.MapLocationProviderListener;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment.MapFragment;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment.MapFragment.MapFragmentListener;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;

import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByRadius;

public class MapActivity extends AppCompatActivity implements MapFragmentListener, MapLocationProviderListener {

    private MapFragment mapFragment;
    private MapLocationProvider locationProvider;

    @Override
    public void mapReady() {
    Location location = locationProvider.currentLocation();
        if (location != null) {
            locationChanged(location);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        locationProvider = new MapLocationProvider(this, this);
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    protected void onStart() {
        locationProvider.connect();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationProvider.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationProvider.pause();
    }

    protected void onStop() {
        locationProvider.disconnect();
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public void changeLocation(Location location) {
        locationChanged(location);
    }

    private void locationChanged(Location location) {
        if (location != null) {
            getShopsByRadius(location.getLatitude(), location.getLongitude(), 10, new WebServiceCallback<List<Shop>>() {
                @Override
                public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                    List<Shop> shops = webServiceResponse.getBodyAsObject();
                    mapFragment.setMarkers(shops);
                }
            });
            mapFragment.changeLocation(location);
        }
    }

}
