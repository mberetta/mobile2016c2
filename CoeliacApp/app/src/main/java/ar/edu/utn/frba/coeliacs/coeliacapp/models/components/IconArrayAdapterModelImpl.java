package ar.edu.utn.frba.coeliacs.coeliacapp.models.components;

/**
 * Created by mberetta on 27/10/16.
 */
public abstract class IconArrayAdapterModelImpl<T> implements IconArrayAdapterModel {

    private T object;

    public IconArrayAdapterModelImpl(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

}
