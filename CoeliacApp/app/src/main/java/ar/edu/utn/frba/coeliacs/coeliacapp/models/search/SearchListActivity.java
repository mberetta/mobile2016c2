package ar.edu.utn.frba.coeliacs.coeliacapp.models.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.ErrorHandling;
import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Entity;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapter;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

public class SearchListActivity extends AppCompatActivity {

    //DEFINIR ESTOS STRINGS DE MANERA GLOBAL. DONDE SERIA MEJOR???
    public final static String EXTRA_ITEM_TYPE = "ar.edu.utn.frba.coeliacs.coeliacapp.ITEM_TYPE";
    public final static String EXTRA_ITEM_TYPE_PRODUCT = "ar.edu.utn.frba.coeliacs.coeliacapp.ITEM_TYPE_PRODUCT";
    public final static String EXTRA_ITEM_TYPE_SHOP = "ar.edu.utn.frba.coeliacs.coeliacapp.ITEM_TYPE_SHOP";

    private ListView itemsList;
    private ArrayList<SearchIconArrayAdapterModel> listItems; //used to store products or shops from web services


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        itemsList = (ListView) findViewById(R.id.itemsList);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();
        String listItemType = bundle.getString(EXTRA_ITEM_TYPE);

        switch(listItemType){
            case EXTRA_ITEM_TYPE_PRODUCT:
                fillListWithProducts();
                break;
            case EXTRA_ITEM_TYPE_SHOP:
                fillListWithShops();
                break;
            default:
                break;
        }

    }

    private void fillListWithProducts(){
        WebServicesEntryPoint.getAllProducts(new WebServiceCallback<List<Product>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Product>> webServiceResponse) {
                if (webServiceResponse.getEx() != null) {
                    ErrorHandling.showWebServiceError(SearchListActivity.this);
                } else {
                    listItems = new ArrayList<SearchIconArrayAdapterModel>();
                    for (Product product : webServiceResponse.getBodyAsObject()) {
                        listItems.add(new SearchIconArrayAdapterModel<Product>(product));
                    }
                    updateUI();
                }
            }
        });
    }

    private void fillListWithShops(){
        WebServicesEntryPoint.getAllShops(new WebServiceCallback<List<Shop>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                if (webServiceResponse.getEx() != null) {
                    ErrorHandling.showWebServiceError(SearchListActivity.this);
                } else {
                    listItems = new ArrayList<SearchIconArrayAdapterModel>();
                    for (Shop shop : webServiceResponse.getBodyAsObject()) {
                        listItems.add(new SearchIconArrayAdapterModel<Shop>(shop));
                    }
                    updateUI();
                }
            }
        });
    }

    private void updateUI(){
        itemsList.setAdapter(new IconArrayAdapter<SearchIconArrayAdapterModel>(SearchListActivity.this, listItems));
    }
}
