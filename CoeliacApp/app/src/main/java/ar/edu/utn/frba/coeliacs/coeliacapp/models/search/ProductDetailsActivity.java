package ar.edu.utn.frba.coeliacs.coeliacapp.models.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.ErrorHandling;
import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapter;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

public class ProductDetailsActivity extends AppCompatActivity {

    private ListView shopsListView;
    private ArrayAdapter<SearchIconArrayAdapterModel> adapter;
    private TextView titleView;
    private TextView subTitleView;

    private String productId;
    private String productTitle;
    private String productSubtitle;
    private ArrayList<SearchIconArrayAdapterModel> listItems; //used to store products or shops from web services

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);
        shopsListView = (ListView) findViewById(R.id.recomendedShopList);
        titleView = (TextView)findViewById(R.id.detailsTitleView);
        subTitleView = (TextView)findViewById(R.id.subtitleTextView);
        Bundle bundle = this.getIntent().getExtras();
        productId = bundle.getString("ITEM_ID");
        productTitle = bundle.getString("TITLE");
        productSubtitle = bundle.getString("SUBTITLE");
        titleView.setText(productTitle);
        subTitleView.setText(productSubtitle);
        fillListWithShops();
    }

    private void fillListWithShops(){
        WebServicesEntryPoint.getShopsThatSellAProduct(productId, new WebServiceCallback<List<Shop>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                if (webServiceResponse.getEx() != null) {
                    ErrorHandling.showWebServiceError(ProductDetailsActivity.this);
                } else {
                    listItems = new ArrayList<SearchIconArrayAdapterModel>();
                    for (Shop shop : webServiceResponse.getBodyAsObject()) {
                        listItems.add(new SearchIconArrayAdapterModel<Shop>(shop));
                    }
                    setAdapter();
                }
            }
        });
    }

    private void setAdapter(){
        adapter = new IconArrayAdapter<SearchIconArrayAdapterModel>(this, listItems);
        shopsListView.setAdapter(adapter);
    }
}
