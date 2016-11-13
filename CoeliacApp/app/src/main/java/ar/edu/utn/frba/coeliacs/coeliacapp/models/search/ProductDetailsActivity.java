package ar.edu.utn.frba.coeliacs.coeliacapp.models.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

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

public class ProductDetailsActivity extends AppCompatActivity {

    //UI
    private ListView shopsListView;
    private ShopAdapter adapter;
    private TextView titleView;
    private TextView subTitleView;

    //Internal Data
    private Product product;
    private List<Shop> shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.product_detail_title);
        setContentView(R.layout.activity_product_details);
        shopsListView = (ListView) findViewById(R.id.recomendedShopList);
        titleView = (TextView)findViewById(R.id.detailsTitleView);
        subTitleView = (TextView)findViewById(R.id.subtitleTextView);
        Bundle bundle = this.getIntent().getExtras();
        product = (Product) bundle.getSerializable(ViewDiscountActivity.EXTRA_PRODUCT);
        titleView.setText(product.getName());
        subTitleView.setText(product.getShortDescription());
        fillListWithShops();
    }

    private void fillListWithShops(){
        WebServicesEntryPoint.getShopsThatSellAProduct(product, new WebServiceCallback<List<Shop>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                if (webServiceResponse.getEx() != null) {
                    ErrorHandling.showWebServiceError(ProductDetailsActivity.this);
                } else {
                    shops = webServiceResponse.getBodyAsObject();
                    adapter = new ShopAdapter(ProductDetailsActivity.this, shops);
                    shopsListView.setAdapter(adapter);
                }
            }
        });
    }

}
