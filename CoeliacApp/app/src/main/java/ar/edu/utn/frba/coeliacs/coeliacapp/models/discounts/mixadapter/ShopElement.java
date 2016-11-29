package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.ViewDiscountActivity;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.search.ShopDetailsActivity;

/**
 * Created by mberetta on 28/11/2016.
 */
public class ShopElement implements MixElement {

    private Shop shop;

    public ShopElement(Shop shop) {
        this.shop = shop;
    }

    @Override
    public int getItemViewType() {
        return MixAdapter.TYPE_SHOP;
    }

    @Override
    public View getView(View convertView, ViewGroup parent, LayoutInflater layoutInflater) {
        // TODO refactor so that this behavior is cross to all MixElements
        DefaultViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.icon_array_adapter, parent, false);
            vh = new DefaultViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (DefaultViewHolder) convertView.getTag();
        }
        vh.theTitle.setText(shop.getName());
        vh.theSubtitle.setText(shop.getAddress());
        vh.theImage.setImageResource(R.drawable.shop);
        return convertView;
    }

    @Override
    public void handleClick(Context context) {
        Intent intent = new Intent(context, ShopDetailsActivity.class);
        intent.putExtra(ViewDiscountActivity.EXTRA_SHOP, shop);
        context.startActivity(intent);
    }
}
