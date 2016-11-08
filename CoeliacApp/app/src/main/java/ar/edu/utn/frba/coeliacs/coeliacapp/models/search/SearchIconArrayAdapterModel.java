package ar.edu.utn.frba.coeliacs.coeliacapp.models.search;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapterModelImpl;

/**
 * Created by max on 10/30/16.
 */
public class SearchIconArrayAdapterModel<T> extends IconArrayAdapterModelImpl<T> {

    public SearchIconArrayAdapterModel(T listItem) {
        super(listItem);
    }

    @Override
    public String get_id() {
        T listItem = getObject();
        String result = "No id";

        if (listItem instanceof Product){
            result = ((Product) listItem).get_id();
        }else if(listItem instanceof Shop){
            result = ((Shop) listItem).get_id();
        }

        return result;
    }

    @Override
    public String getTitle() {
        T listItem = getObject();
        String result = "No Name";

        if (listItem instanceof Product){
            result = ((Product) listItem).getName();
        }else if(listItem instanceof Shop){
            result = ((Shop) listItem).getName();
        }

        return result;
    }

    @Override
    public String getSubtitle() {
        T listItem = getObject();
        String result = "No Name";

        if (listItem instanceof Product){
            result = ((Product) listItem).getShortDescription();
        }else if(listItem instanceof Shop){
            result = ((Shop) listItem).getAddress();
        }

        return result;
    }

    @Override
    public int getIconResId() {
        return R.drawable.search;
    }
}
