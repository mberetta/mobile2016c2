package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

/**
 * Created by mberetta on 14/10/2016.
 */
public class Product extends Entity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
