package ar.edu.utn.frba.coeliacs.coeliacapp.models.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;

/**
 * Created by max on 8/11/2016.
 */

public class ShopDetailsActivity extends AppCompatActivity {
    private ListView productListView;
    private ArrayAdapter<SearchIconArrayAdapterModel> adapter;
    private TextView nameView;
    private TextView addressView;
    private TextView phoneView;

    private Shop shop;
    private ArrayList<SearchIconArrayAdapterModel> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        productListView = (ListView) findViewById(R.id.productsYouCanBuyHereListView);
        nameView = (TextView)findViewById(R.id.shopNameView);
        addressView = (TextView)findViewById(R.id.shopAddressView);
        phoneView = (TextView)findViewById(R.id.shopPhoneView);
        Bundle bundle = this.getIntent().getExtras();
        shop = (Shop) bundle.getSerializable("SHOP");
        nameView.setText(shop.getName());
        phoneView.setText(shop.getTelnum());
        addressView.setText(shop.getAddress());

        //fillListWithShops();
    }
}
