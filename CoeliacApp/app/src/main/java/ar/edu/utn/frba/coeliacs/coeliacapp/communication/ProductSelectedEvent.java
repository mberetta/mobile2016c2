package ar.edu.utn.frba.coeliacs.coeliacapp.communication;

import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;

/**
 * Created by max on 8/11/2016.
 */

public class ProductSelectedEvent {
    private Product item;

    public ProductSelectedEvent(Product item) {
        this.item = item;
    }

    public Product getItem() {
        return item;
    }
}
