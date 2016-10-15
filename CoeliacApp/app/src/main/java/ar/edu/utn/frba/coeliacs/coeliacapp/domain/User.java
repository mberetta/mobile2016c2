package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

/**
 * Created by mberetta on 14/10/2016.
 */
public class User extends Entity {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int defaultRadius;
    private boolean defaultExclusive;
    private Location defaultLocation;

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getDefaultRadius() {
        return defaultRadius;
    }

    public boolean isDefaultExclusive() {
        return defaultExclusive;
    }

    public Location getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultRadius(int defaultRadius) {
        this.defaultRadius = defaultRadius;
    }

    public void setDefaultExclusive(boolean defaultExclusive) {
        this.defaultExclusive = defaultExclusive;
    }

    public void setDefaultLocation(Location defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
