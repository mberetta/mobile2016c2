package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

/**
 * Created by mberetta on 15/10/2016.
 */
public class Country extends Entity {

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
