package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.ErrorHandling;
import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.configuration.CfgManager;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Discount;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.location.MapLocationProvider;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapter;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

public class DiscountsActivity extends AppCompatActivity {

    private static final String DISCOUNTS_TAG = "DISCOUNTS";
    private ListView discountsListView;
    private ArrayList<DiscountIconArrayAdapterModel> discounts;
    private ProgressBar progressBar;
    private MapLocationProvider locProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);
        setTitle(R.string.title_near_discounts);

        progressBar = (ProgressBar) findViewById(R.id.progress_discounts);
        progressBar.setVisibility(View.VISIBLE);

        discountsListView = (ListView) findViewById(R.id.discounts_list_view);
        discountsListView.setVisibility(View.GONE);
        discountsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DiscountsActivity.this, ViewDiscountActivity.class);
                intent.putExtra(ViewDiscountActivity.EXTRA_DISCOUNT, discounts.get(position));
                startActivity(intent);
            }
        });

        if (savedInstanceState != null) {
            discounts = (ArrayList<DiscountIconArrayAdapterModel>) savedInstanceState.getSerializable(DISCOUNTS_TAG);
            if (discounts != null) {
                updateUI();
                return;
            }
        }

        locProvider = new MapLocationProvider(this, new MapLocationProvider.MapLocationProviderListener() {
            @Override
            public void changeLocation(Location location) {
                if (location != null) {
                    int radiusKm = CfgManager.getSearchDistance(DiscountsActivity.this);

                    locProvider.disconnect();

                    WebServicesEntryPoint.getShopsByRadius(location.getLatitude(), location.getLongitude(), radiusKm, new WebServiceCallback<List<Shop>>() {
                        @Override
                        public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                            if (webServiceResponse.getEx() != null) {
                                finish();
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
            }
        });

        locProvider.resume();

    }

    private void updateUI() {
        progressBar.setVisibility(View.GONE);
        discountsListView.setVisibility(View.VISIBLE);
        discountsListView.setAdapter(new IconArrayAdapter<DiscountIconArrayAdapterModel>(DiscountsActivity.this, discounts));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DISCOUNTS_TAG, discounts);
        super.onSaveInstanceState(outState);
    }

}
