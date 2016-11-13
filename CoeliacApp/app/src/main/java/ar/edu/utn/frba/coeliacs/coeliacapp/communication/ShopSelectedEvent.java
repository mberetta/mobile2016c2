package ar.edu.utn.frba.coeliacs.coeliacapp.communication;

import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;

/**
 * Created by max on 8/11/2016.
 */

public class ShopSelectedEvent {
    private Shop item;

    public ShopSelectedEvent(Shop item) {
        this.item = item;
    }

    public Shop getItem() {
        return item;
    }
}
