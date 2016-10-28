package ar.edu.utn.frba.coeliacs.coeliacapp.models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

    private ListView discountsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);
        setTitle(R.string.title_near_discounts);

        // TODO reload previous state

        // TODO get current position
        double currentLat = -34.5442824021754;
        double currentLong = -58.5560560226441;
        // TODO get radius from user config
        int radiusKm = 200;

        // TODO recycled view?

        WebServicesEntryPoint.getShopsByRadius(currentLat, currentLong, radiusKm, new WebServiceCallback<List<Shop>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                if (webServiceResponse.getEx() != null) {
                    ErrorHandling.showWebServiceError(DiscountsActivity.this);
                } else {
                    discountsListView = (ListView) findViewById(R.id.discounts_list_view);
                    List<IconArrayAdapterModelImpl> discounts = new ArrayList<IconArrayAdapterModelImpl>();
                    for (Shop shop : webServiceResponse.getBodyAsObject()) {
                        for (Discount discount : shop.getDiscounts()) {
                            discounts.add(new IconArrayAdapterModelImpl(discount.getDescription(), shop.getName(), R.drawable.discount));
                        }
                    }
                    discountsListView.setAdapter(new IconArrayAdapter<IconArrayAdapterModelImpl>(DiscountsActivity.this, discounts));
                }
            }
        });
    }
}
