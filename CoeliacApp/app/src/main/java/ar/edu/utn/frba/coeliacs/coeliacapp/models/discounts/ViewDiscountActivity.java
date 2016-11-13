package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Discount;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.FilterIconArrayAdapter;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapter;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapterModel;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapterModelImpl;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.UIUtils;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.search.ProductDetailsActivity;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.search.ShopDetailsActivity;

public class ViewDiscountActivity extends AppCompatActivity {

    public static final String EXTRA_DISCOUNT = "EXTRA_DISCOUNT";
    public static final String EXTRA_SHOP = "EXTRA_SHOP";
    public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";
    private DiscountIconArrayAdapterModel discountModel;
    private TextView termsAndConditionsTextView;
    private TextView availableQuantityTextView;
    private TextView descriptionTextView;
    private List<IconArrayAdapterModel> products;
    private List<IconArrayAdapterModel> shop;
    /*
    private NoScrollIconListView productsList;
    private NoScrollIconListView shopContainer;
    */
    private ListView productsList;
    private ListView shopContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_discount);
        setTitle(R.string.title_view_discount);

        termsAndConditionsTextView = (TextView) findViewById(R.id.terms_conditions);
        descriptionTextView = (TextView) findViewById(R.id.description);
        availableQuantityTextView = (TextView) findViewById(R.id.available_quantity);
        /*
        shopContainer = (NoScrollIconListView) findViewById(R.id.shop_container);
        productsList = (NoScrollIconListView) findViewById(R.id.product_list);
        */
        shopContainer = (ListView) findViewById(R.id.shop_container);
        productsList = (ListView) findViewById(R.id.product_list);

        shopContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewDiscountActivity.this, ShopDetailsActivity.class);
                intent.putExtra(ViewDiscountActivity.EXTRA_SHOP, ((IconArrayAdapterModelImpl<Shop>)shop.get(position)).getObject());
                startActivity(intent);
            }
        });

        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ViewDiscountActivity.this, ProductDetailsActivity.class);
                intent.putExtra(ViewDiscountActivity.EXTRA_PRODUCT, ((IconArrayAdapterModelImpl<Product>)products.get(position)).getObject());
                startActivity(intent);
            }
        });

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
        shop = new ArrayList<IconArrayAdapterModel>();
        shop.add(new IconArrayAdapterModelImpl<Shop>(discountModel.getOwnerShop()) {
            @Override
            public String get_id() {
                return getObject().get_id();
            }

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
        //shopContainer.setItems(shop);
        shopContainer.setAdapter(new IconArrayAdapter<IconArrayAdapterModel>(this, shop));

        // TODO this list is hardcoded and not loaded from webservice
        products = new ArrayList<IconArrayAdapterModel>();

        Product hardcodedProduct = new Product();
        hardcodedProduct.set_id("RJ45");
        hardcodedProduct.setName("Barra de cereal para celíaco");

        IconArrayAdapterModelImpl<Product> productModel = new IconArrayAdapterModelImpl<Product>(hardcodedProduct) {
            @Override
            public String get_id() {
                return getObject().get_id();
            }
            @Override
            public String getTitle() {
                return getObject().getName();
            }
            @Override
            public String getSubtitle() {
                return "Producto";
            }
            @Override
            public int getIconResId() {
                return R.drawable.product;
            }
        };

        products.add(productModel);
        products.add(productModel);
        products.add(productModel);
        products.add(productModel);
        products.add(productModel);
        products.add(productModel);
        //productsList.setItems(products);
        productsList.setAdapter(new IconArrayAdapter<IconArrayAdapterModel>(this, products));

        UIUtils.justifyListViewHeightBasedOnChildren(shopContainer);
        UIUtils.justifyListViewHeightBasedOnChildren(productsList);

        Discount discount = discountModel.getObject();
        availableQuantityTextView.setText(Integer.toString(discount.getAvailableQuantity()) + " " + getResources().getString(R.string.units));
        descriptionTextView.setText(discount.getDescription());
        termsAndConditionsTextView.setText(discount.getTermsAndConditions());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_DISCOUNT, discountModel);
        super.onSaveInstanceState(outState);
    }

}
