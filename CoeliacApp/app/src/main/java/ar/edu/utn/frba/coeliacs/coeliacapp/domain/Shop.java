package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

import java.util.List;

/**
 * Created by mberetta on 14/10/2016.
 */
public class Shop extends Entity {

    private String name;
    private String telnum;
    private String address;
    private Boolean exclusive;
    private CoordinatesLocation location;
    private List<Discount> discounts;

    public String getName() {
        return name;
    }

    public String getTelnum() {
        return telnum;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getExclusive() {
        return exclusive;
    }

    public CoordinatesLocation getLocation() {
        return location;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    @Override
    public String toString() {
        return name;
    }

}
