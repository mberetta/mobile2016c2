package ar.edu.utn.frba.coeliacs.coeliacapp.models.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.City;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Continent;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Country;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Entity;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.State;
import ar.edu.utn.frba.coeliacs.coeliacapp.location.MapLocationProvider;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.component.MapPreferencesManager;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment.MapFragment;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment.MapFragment.MapFragmentListener;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByCity;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByContinent;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByCountry;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByRadius;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getShopsByState;

public class MapActivity extends AppCompatActivity implements MapFragmentListener, MapLocationProvider.MapLocationProviderListener {

    private static final int REQUEST_CODE = 1001;

    private MapFragment mapFragment;
    private MapLocationProvider locationProvider;
    private MapPreferencesManager preferences;

    private Integer radius;
    private Entity location;
    private boolean useLocation;
    private Location lastKnownLocation;
    private Product selectedProduct;

    private boolean messageShown = false;

    @Override
    public void mapReady() {
        updateMap();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.map));
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
        if (useLocation) {
            locationProvider.pause();
        }
        super.onPause();
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
    public void connected(Location location) {
        if (location != null) {
            mapFragment.updateCameraByLocation(location, 14);
            locationChanged(location);
        }
    }

    @Override
    public void changeLocation(Location location) {
        if (location != null) {
            locationChanged(location);
        }
    }

    private void locationChanged(Location location) {
        if (location != null) {
            lastKnownLocation = location;
            preferences.saveLastKnownLocation(location);
            getShopsByRadius(location.getLatitude(), location.getLongitude(), radius, getCallback(14));
        }
    }

    @NonNull
    private WebServiceCallback<List<Shop>> getCallback(final int cameraView) {
        return new WebServiceCallback<List<Shop>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                final List<Shop> shops;
                if (webServiceResponse.getBodyAsObject() == null) {
                    shops = new ArrayList<>();
                } else {
                    shops = webServiceResponse.getBodyAsObject();
                }

                if (selectedProduct != null) {
                    WebServicesEntryPoint.getShopsThatSellAProduct(selectedProduct, new WebServiceCallback<List<Shop>>() {
                        @Override
                        public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                            List<Shop> body = webServiceResponse.getBodyAsObject();
                            if (body == null) {
                                body = new ArrayList<>();
                            }
                            shops.retainAll(body);
                            processResponse(shops, cameraView);
                        }
                    });
                } else {
                    processResponse(shops, cameraView);
                }
            }
        };
    }

    private void processResponse(List<Shop> shops, int cameraView) {
        if (shops.isEmpty()) {
            if(!messageShown) {
                makeText(MapActivity.this, R.string.no_results_found, LENGTH_SHORT).show();
                messageShown = true;
            }
        } else if (mapFragment.isAdded()) {
            messageShown = false;
            mapFragment.setMarkers(shops);
            if (!useLocation) {
                mapFragment.updateCameraByShops(cameraView);
            }
        }

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

        switch (requestCode) {
            case REQUEST_CODE:
                updatePreferences();
                if (RESULT_OK == resultCode) {
                    makeText(this, getString(R.string.cfg_set), LENGTH_SHORT).show();
                }
                updateMap();
                break;
            case MapLocationProvider.LOCATION_PROMPT_USER:
                if (resultCode == 0) {
                    updateMap();
                }
                break;
        }
    }

    private void updatePreferences() {
        useLocation = preferences.getUseLocation();
        radius = preferences.getRadius();
    }

    private void updateMap() {
        mapFragment.clearMarkers();
        selectedProduct = preferences.getProduct();
        location = preferences.getLocation();
        if (!useLocation) {
            locationProvider.disconnect();
            showByLocation(location);
        } else {
            locationProvider.resume();
            Location currentLocation = locationProvider.currentLocation();
            if (currentLocation != null) {
                lastKnownLocation = currentLocation;
            }
            locationChanged(lastKnownLocation);
        }
    }

    private void showByLocation(Entity location) {
        if (location == null) {
            return;
        }

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
