package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

/**
 * Created by mberetta on 14/10/2016.
 */
public class CoordinatesLocation extends Location {

    private String type;
    private double[] coordinates;

    public String getType() {
        return type;
    }

    public double getLong() {
        return coordinates[0];
    }

    public double getLat() {
        return coordinates[1];
    }

}
