package ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.domain.CoordinatesLocation;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;

public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap map;
    private List<ShopMarker> markers = new ArrayList<>();

    public interface MapFragmentListener {

        void mapReady();
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getMapAsync(this);
    }

    public void setMarkers(List<Shop> shops) {
        map.clear();
        markers.clear();
        Iterator<Shop> iterator = shops.iterator();

        while (iterator.hasNext()) {
            Shop shop = iterator.next();
            CoordinatesLocation location = shop.getLocation();
            MarkerOptions position = new MarkerOptions()
                    .title(shop.getName())
                    .snippet(shop.getTelnum())
                    .position(new LatLng(location.getLat(), location.getLong()));

            Marker marker = map.addMarker(position);

            ShopMarker shopMarker = new ShopMarker();
            shopMarker.setMarker(marker);
            shopMarker.setShop(shop);
            markers.add(shopMarker);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        ((MapFragmentListener) this.getActivity()).mapReady();
    }

    public void changeLocation(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition camera = new CameraPosition.Builder().target(latLng).zoom(12).build();

        map.moveCamera(CameraUpdateFactory.newCameraPosition(camera));
    }
}
