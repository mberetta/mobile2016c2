package ar.edu.utn.frba.coeliacs.coeliacapp.communication;

import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapterModel;

/**
 * Created by max on 8/11/2016.
 */

public class ItemSelectedEvent<T extends IconArrayAdapterModel> {
    private T item;

    public ItemSelectedEvent(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}
