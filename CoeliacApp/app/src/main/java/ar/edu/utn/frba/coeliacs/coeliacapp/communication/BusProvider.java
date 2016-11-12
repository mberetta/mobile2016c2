package ar.edu.utn.frba.coeliacs.coeliacapp.communication;

import com.squareup.otto.Bus;

/**
 * Created by max on 8/11/2016.
 */

public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {return BUS;}

    private BusProvider() {
        //No instances.
    }
}
