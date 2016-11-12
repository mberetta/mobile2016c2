package ar.edu.utn.frba.coeliacs.coeliacapp.models.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.ErrorHandling;
import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.ViewDiscountActivity;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

/**
 * Created by max on 8/11/2016.
 */

public class ShopDetailsActivity extends AppCompatActivity {
    //UI
    private ListView productListView;
    private ProductAdapter adapter;
    private TextView nameView;
    private TextView addressView;
    private TextView phoneView;

    //Internal Data
    private Shop shop;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        productListView = (ListView) findViewById(R.id.productsYouCanBuyHereListView);
        nameView = (TextView)findViewById(R.id.shopNameView);
        addressView = (TextView)findViewById(R.id.shopAddressView);
        phoneView = (TextView)findViewById(R.id.shopPhoneView);
        Bundle bundle = this.getIntent().getExtras();
        shop = (Shop) bundle.getSerializable(ViewDiscountActivity.EXTRA_SHOP);
        nameView.setText(shop.getName());
        phoneView.setText(shop.getTelnum());
        addressView.setText(shop.getAddress());

        fillListWithProducts();
    }

    private void fillListWithProducts(){
        WebServicesEntryPoint.getProductsSoldByShop(shop, new WebServiceCallback<List<Product>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Product>> webServiceResponse) {
                if (webServiceResponse.getEx() != null) {
                    ErrorHandling.showWebServiceError(ShopDetailsActivity.this);
                } else {
                    products = webServiceResponse.getBodyAsObject();
                    adapter = new ProductAdapter(ShopDetailsActivity.this, products);
                    productListView.setAdapter(adapter);
                }
            }
        });
    }


}
