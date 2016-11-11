package ar.edu.utn.frba.coeliacs.coeliacapp.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by mberetta on 14/10/2016.
 */
public class Entity implements Serializable {

    private String _id;
    private String id;

    public String get_id() {
        return _id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        Entity rhs = (Entity) obj;
        return new EqualsBuilder().append(_id, rhs._id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(_id).toHashCode();
    }

}
