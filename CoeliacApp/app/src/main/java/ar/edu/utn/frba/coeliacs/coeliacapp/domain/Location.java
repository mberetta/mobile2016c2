package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

import java.io.Serializable;

/**
 * Created by mberetta on 14/10/2016.
 */
public class Location implements Serializable{

    private String city;
    private String state;
    private String country;
    private String continent;

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getContinent() {
        return continent;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

}
