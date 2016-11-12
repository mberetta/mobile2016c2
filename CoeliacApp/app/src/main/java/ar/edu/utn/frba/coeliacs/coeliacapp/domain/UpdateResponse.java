package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

import java.io.Serializable;

/**
 * Created by mberetta on 15/10/2016.
 */
public class UpdateResponse implements Serializable {

    private int ok;
    private int nModified;
    private int n;

    public int getOk() {
        return ok;
    }

    public int getnModified() {
        return nModified;
    }

    public int getN() {
        return n;
    }

}
