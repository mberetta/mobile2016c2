package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

/**
 * Created by mberetta on 14/10/2016.
 */
public class CoordinatesLocation extends Location {

    private String type;
    private double[] latlong;

    public String getType() {
        return type;
    }

    public double getLong() {
        return latlong[0];
    }

    public double getLat() {
        return latlong[1];
    }

}
