package ar.edu.utn.frba.coeliacs.coeliacapp.models.map;

import android.content.Intent;
import android.os.Bundle;

import com.squareup.otto.Subscribe;

import ar.edu.utn.frba.coeliacs.coeliacapp.communication.ProductSelectedEvent;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.search.ProductListActivity;

public class MapProductListActivity extends ProductListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @Subscribe
    public void onProductSelectedEvent(ProductSelectedEvent e) {
        Intent response = new Intent();
        response.putExtra("PRODUCT", e.getItem());
        setResult(RESULT_OK,response);
        finish();
    }
}
