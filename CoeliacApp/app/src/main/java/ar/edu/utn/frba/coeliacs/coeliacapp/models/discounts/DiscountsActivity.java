package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.ErrorHandling;
import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Discount;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapter;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapterModelImpl;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

public class DiscountsActivity extends AppCompatActivity {

    private static final String DISCOUNTS_TAG = "DISCOUNTS";
    private ListView discountsListView;
    private ArrayList<DiscountIconArrayAdapterModel> discounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);
        setTitle(R.string.title_near_discounts);

        discountsListView = (ListView) findViewById(R.id.discounts_list_view);
        discountsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO hacer algo
                Toast.makeText(DiscountsActivity.this, "Click!", Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState != null) {
            discounts = (ArrayList<DiscountIconArrayAdapterModel>) savedInstanceState.getSerializable(DISCOUNTS_TAG);
            if (discounts != null) {
                updateUI();
                return;
            }
        }

        // TODO get current position
        double currentLat = -34.5442824021754;
        double currentLong = -58.5560560226441;
        // TODO get radius from user config
        int radiusKm = 200;

        // TODO lazy loading?
        // TODO recycled view?

        WebServicesEntryPoint.getShopsByRadius(currentLat, currentLong, radiusKm, new WebServiceCallback<List<Shop>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                if (webServiceResponse.getEx() != null) {
                    ErrorHandling.showWebServiceError(DiscountsActivity.this);
                } else {
                    discounts = new ArrayList<DiscountIconArrayAdapterModel>();
                    for (Shop shop : webServiceResponse.getBodyAsObject()) {
                        for (Discount discount : shop.getDiscounts()) {
                            discounts.add(new DiscountIconArrayAdapterModel(discount, shop));
                        }
                    }
                    updateUI();
                }
            }
        });

    }

    private void updateUI() {
        discountsListView.setAdapter(new IconArrayAdapter<DiscountIconArrayAdapterModel>(DiscountsActivity.this, discounts));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DISCOUNTS_TAG, discounts);
        super.onSaveInstanceState(outState);
    }
}
