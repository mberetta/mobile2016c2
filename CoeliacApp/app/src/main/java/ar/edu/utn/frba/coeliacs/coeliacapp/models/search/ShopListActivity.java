package ar.edu.utn.frba.coeliacs.coeliacapp.models.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.ErrorHandling;
import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.communication.BusProvider;
import ar.edu.utn.frba.coeliacs.coeliacapp.communication.ShopSelectedEvent;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.ViewDiscountActivity;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

/**
 * Created by max on 8/11/2016.
 */

public class ShopListActivity  extends AppCompatActivity {
    private static final String TAG = ShopListActivity.class.getSimpleName();

    private Bus bus = BusProvider.getInstance();

    private ListView itemsListView;
    private EditText searchBoxView;

    private List<Shop> shops;
    private ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.search_shop_title);

        bus.register(this);

        setContentView(R.layout.activity_shop_list);

        itemsListView = (ListView) findViewById(R.id.shopList);
        searchBoxView = (EditText)  findViewById(R.id.shopSearchBox);

        fillList();
    }

    private void fillList(){
        WebServicesEntryPoint.getAllShops(new WebServiceCallback<List<Shop>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                if (webServiceResponse.getEx() != null) {
                    ErrorHandling.showWebServiceError(ShopListActivity.this);
                } else {
                    shops = webServiceResponse.getBodyAsObject();
                    adapter = new ShopAdapter(ShopListActivity.this, shops);
                    itemsListView.setAdapter(adapter);
                    enableSearchBox();
                }
            }
        });
    }

    private void enableSearchBox() {
        searchBoxView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                ShopListActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            @Override
            public void afterTextChanged(Editable arg0) {}
        });
    }

    @Subscribe
    public void onShopSelectedEvent(ShopSelectedEvent e) {
        Shop item = e.getItem();
        Log.i(TAG, "ItemSelectedEvent with id: " + e.getItem().get_id());

        Intent intent = new Intent(this, ShopDetailsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(ViewDiscountActivity.EXTRA_SHOP, item);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
