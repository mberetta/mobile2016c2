package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

import java.util.List;

/**
 * Created by mberetta on 15/10/2016.
 */
public class Discount extends Entity {

    public static final int TYPE_PROMO = 1;
    public static final int TYPE_PERCENT = 2;

    private int type;
    private int availableQuantity;
    private String termsAndConditions;
    private String description;
    private List<String> products;

    public int getType() {
        return type;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return description;
    }

}
