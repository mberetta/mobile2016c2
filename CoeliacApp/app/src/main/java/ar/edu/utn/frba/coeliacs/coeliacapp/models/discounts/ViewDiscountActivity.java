package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapterModel;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapterModelImpl;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.NoScrollIconListView;

public class ViewDiscountActivity extends AppCompatActivity {

    public static final String EXTRA_DISCOUNT = "EXTRA_DISCOUNT";
    private DiscountIconArrayAdapterModel discountModel;
    private NoScrollIconListView productsList;
    private NoScrollIconListView shopContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_discount);
        setTitle(R.string.title_view_discount);

        shopContainer = (NoScrollIconListView) findViewById(R.id.shop_container);
        productsList = (NoScrollIconListView) findViewById(R.id.product_list);

        if (savedInstanceState != null) {
            discountModel = (DiscountIconArrayAdapterModel) savedInstanceState.getSerializable(EXTRA_DISCOUNT);
            if (discountModel != null) {
                updateUI();
                return;
            }
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        discountModel = (DiscountIconArrayAdapterModel) bundle.getSerializable(EXTRA_DISCOUNT);

        updateUI();
    }

    private void updateUI() {
        List<IconArrayAdapterModel> products = new ArrayList<IconArrayAdapterModel>();
        products.add(discountModel);
        products.add(discountModel);
        products.add(discountModel);
        products.add(discountModel);
        products.add(discountModel);
        products.add(discountModel);
        productsList.setItems(products);

        List<IconArrayAdapterModel> shop = new ArrayList<IconArrayAdapterModel>();
        shop.add(new IconArrayAdapterModelImpl<Shop>(discountModel.getOwnerShop()) {
            @Override
            public String getTitle() {
                return getObject().getName();
            }
            @Override
            public String getSubtitle() {
                return getObject().getAddress();
            }
            @Override
            public int getIconResId() {
                return R.drawable.shop;
            }
        });
        shopContainer.setItems(shop);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_DISCOUNT, discountModel);
        super.onSaveInstanceState(outState);
    }

}
