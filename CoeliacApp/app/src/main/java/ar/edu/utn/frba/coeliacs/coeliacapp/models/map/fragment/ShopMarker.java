package ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment;

import com.google.android.gms.maps.model.Marker;

import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;

public class ShopMarker {

    private Marker marker;
    private Shop shop;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }
}
