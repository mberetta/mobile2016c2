package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Discount;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapterModelImpl;

/**
 * Created by mberetta on 28/10/2016.
 */
public class DiscountIconArrayAdapterModel extends IconArrayAdapterModelImpl<Discount> {

    private Shop ownerShop;

    public DiscountIconArrayAdapterModel(Discount discount, Shop ownerShop) {
        super(discount);
        this.ownerShop = ownerShop;
    }

    @Override
    public String getTitle() {
        return getObject().getDescription();
    }

    @Override
    public String getSubtitle() {
        return ownerShop.getName();
    }

    @Override
    public int getIconResId() {
        return R.drawable.discount;
    }

    public Shop getOwnerShop() {
        return ownerShop;
    }

}
