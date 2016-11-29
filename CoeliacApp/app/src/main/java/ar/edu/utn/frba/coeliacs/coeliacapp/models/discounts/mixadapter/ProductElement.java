package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.ViewDiscountActivity;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.search.ProductDetailsActivity;

/**
 * Created by mberetta on 28/11/2016.
 */
public class ProductElement implements MixElement {

    private Product product;

    public ProductElement(Product product) {
        this.product = product;
    }

    @Override
    public int getItemViewType() {
        return MixAdapter.TYPE_PRODUCT;
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
        vh.theTitle.setText(product.getName());
        vh.theSubtitle.setText("Producto");
        vh.theImage.setImageResource(R.drawable.product);
        return convertView;
    }

    @Override
    public void handleClick(Context context) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(ViewDiscountActivity.EXTRA_PRODUCT, product);
        context.startActivity(intent);
    }
}
