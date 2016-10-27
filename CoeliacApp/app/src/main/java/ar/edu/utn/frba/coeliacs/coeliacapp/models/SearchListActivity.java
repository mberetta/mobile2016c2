package ar.edu.utn.frba.coeliacs.coeliacapp.models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;

public class SearchListActivity extends AppCompatActivity {

    private ListView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        final String[] datos =
                new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, datos);

        itemsList = (ListView)findViewById(R.id.itemsList);

        itemsList.setAdapter(adaptador);
    }
}
