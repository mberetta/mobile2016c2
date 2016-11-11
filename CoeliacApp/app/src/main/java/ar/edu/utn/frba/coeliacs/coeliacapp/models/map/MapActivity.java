package ar.edu.utn.frba.coeliacs.coeliacapp.models.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.City;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Continent;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Country;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Entity;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.State;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.MapLocationProvider.MapLocationProviderListener;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.component.MapPreferencesManager;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment.MapFragment;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment.MapFragment.MapFragmentListener;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByCity;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByContinent;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByCountry;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByRadius;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByState;

public class MapActivity extends AppCompatActivity implements MapFragmentListener, MapLocationProviderListener {

    private static final int REQUEST_CODE = 1001;

    private MapFragment mapFragment;
    private MapLocationProvider locationProvider;
    private MapPreferencesManager preferences;

    private Integer radius;
    private Entity location;
    private boolean useLocation;
    private Location lastKnownLocation;

    @Override
    public void mapReady() {
        updateMap();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        locationProvider = new MapLocationProvider(this, this);
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        preferences = new MapPreferencesManager(this);

        radius = preferences.getRadius();
        useLocation = preferences.getUseLocation();
        location = preferences.getLocation();
        lastKnownLocation = preferences.getLastKnownLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (useLocation) {
            locationProvider.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (useLocation) {
            locationProvider.pause();
        }
    }

    protected void onStop() {
        super.onStop();
        locationProvider.disconnect();
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
            lastKnownLocation = location;
            preferences.saveLastKnownLocation(location);
            getShopsByRadius(location.getLatitude(), location.getLongitude(), radius, getCallback(14));
            mapFragment.changeLocation(location);
        }
    }

    @NonNull
    private WebServiceCallback<List<Shop>> getCallback(final int cameraView) {
        return new WebServiceCallback<List<Shop>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                List<Shop> shops = webServiceResponse.getBodyAsObject();
                if (shops != null && !shops.isEmpty()) {
                    mapFragment.setMarkers(shops);
                    mapFragment.updateCamera(cameraView);
                } else {
                    makeText(MapActivity.this, "No results found", LENGTH_SHORT);
                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.show_filters:
                showFilters();
                break;
        }

        return true;
    }

    private void showFilters() {
        Intent intent = new Intent(this, MapSettingsActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE == requestCode) {
            updatePreferences();
            makeText(this, "Settings updated", LENGTH_SHORT).show();
            updateMap();
        }
    }

    private void updatePreferences() {
        useLocation = preferences.getUseLocation();
        radius = preferences.getRadius();
    }

    private void updateMap() {
        mapFragment.clearMarkers();
        location = preferences.getLocation();
        if (!useLocation && location != null) {
            locationProvider.disconnect();
            showByLocation(location);
        } else if (useLocation) {
            locationProvider.resume();
            Location currentLocation = locationProvider.currentLocation();
            if (currentLocation != null) {
                lastKnownLocation = currentLocation;
            }
            locationChanged(lastKnownLocation);
        }
    }

    private void showByLocation(Entity location) {
        if (location instanceof Continent) {
            getShopsByContinent((Continent) location, getCallback(1));
            return;
        }

        if (location instanceof Country) {
            getShopsByCountry((Country) location, getCallback(5));
            return;
        }

        if (location instanceof State) {
            getShopsByState((State) location, getCallback(8));
            return;
        }

        if (location instanceof City) {
            getShopsByCity((City) location, getCallback(13));
            return;
        }
    }
}
