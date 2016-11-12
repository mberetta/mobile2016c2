package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

import android.media.Image;

import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapterModel;

/**
 * Created by mberetta on 14/10/2016.
 */
public class Product extends Entity {

    private String name;
    private String shortDescription = "Short Description Default";
    private Image image;
    private List<Shop> shops;//Shops que venden este producto

    public String getName() {return name;}

    public String getShortDescription() {return shortDescription;}

    public Image getImage() {return image;}

    public List<Shop> getShops() {
        return shops;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
