package ar.edu.utn.frba.coeliacs.coeliacapp.models.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.ErrorHandling;
import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.communication.BusProvider;
import ar.edu.utn.frba.coeliacs.coeliacapp.communication.ItemSelectedEvent;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.components.IconArrayAdapter;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

public class SearchListActivity extends AppCompatActivity {
    private static final String TAG = SearchListActivity.class.getSimpleName();

    //DEFINIR ESTOS STRINGS DE MANERA GLOBAL. DONDE SERIA MEJOR???
    public final static String EXTRA_ITEM_TYPE = "ar.edu.utn.frba.coeliacs.coeliacapp.ITEM_TYPE";
    public final static String EXTRA_ITEM_TYPE_PRODUCT = "ar.edu.utn.frba.coeliacs.coeliacapp.ITEM_TYPE_PRODUCT";
    public final static String EXTRA_ITEM_TYPE_SHOP = "ar.edu.utn.frba.coeliacs.coeliacapp.ITEM_TYPE_SHOP";

    private Bus bus = BusProvider.getInstance();

    private ListView itemsListView;
    private EditText searchBoxView;
    private ArrayList<SearchIconArrayAdapterModel> listItems; //used to store products or shops from web services
    private ArrayAdapter<SearchIconArrayAdapterModel> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bus.register(this);

        setContentView(R.layout.activity_search_list);

        itemsListView = (ListView) findViewById(R.id.itemsList);
        searchBoxView = (EditText)  findViewById(R.id.searchBox);

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
                    setAdapter();
                    enableSearchBox();
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
                    setAdapter();
                    enableSearchBox();
                }
            }
        });
    }

    private void setAdapter(){
        adapter = new IconArrayAdapter<SearchIconArrayAdapterModel>(SearchListActivity.this, listItems);
        itemsListView.setAdapter(adapter);
    }

    private void enableSearchBox() {
        searchBoxView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchListActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            @Override
            public void afterTextChanged(Editable arg0) {}
        });
    }

    @Subscribe
    public void onItemSelectedEvent(ItemSelectedEvent e) {
        Log.i(TAG, "ItemSelectedEvent with id: " + e.getItem().get_id());
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        Bundle b = new Bundle();
        b.putString("ITEM_ID", e.getItem().get_id());
        b.putString("TITLE", e.getItem().getTitle());
        b.putString("SUBTITLE", e.getItem().getSubtitle());
        intent.putExtras(b);
        startActivity(intent);
    }



    public void onDestroy() {
        bus.unregister(this);
    }
}
