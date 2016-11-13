package ar.edu.utn.frba.coeliacs.coeliacapp.helpers;

import android.media.Image;

/**
 * Created by max on 10/27/16.
 */
public class SearchListItem {

    private String  title;
    private String  shortDescription;
    private Image   image;

    public SearchListItem(String title, String shortDescription, Image image){
        this.title = title;
        this.shortDescription = shortDescription;
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public String getShortDescription(){
        return shortDescription;
    }

    public Image getImage(){
        return image;
    }



}
