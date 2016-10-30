package ar.edu.utn.frba.coeliacs.coeliacapp.models.components;

import java.io.Serializable;

/**
 * Created by mberetta on 27/10/16.
 */
public interface IconArrayAdapterModel extends Serializable {

    String getTitle();
    String getSubtitle();
    int getIconResId();

}
