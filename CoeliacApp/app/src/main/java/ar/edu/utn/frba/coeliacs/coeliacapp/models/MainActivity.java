package ar.edu.utn.frba.coeliacs.coeliacapp.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.DiscountsActivity;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.MapActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton mapImageButton;
    private ImageButton codebarReaderImageButton;
    private ImageButton searchImageButton;
    private ImageButton discountImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get image buttons references
        mapImageButton = (ImageButton) findViewById(R.id.mapImageButton);
        codebarReaderImageButton = (ImageButton) findViewById(R.id.codebarReaderImageButton);
        searchImageButton = (ImageButton) findViewById(R.id.searchImageButton);
        discountImageButton = (ImageButton) findViewById(R.id.discountImageButton);

        //Get parameters from login
        Bundle bundle = this.getIntent().getExtras();

        //Define image buttons navigation

        //NAVIGATE TO MAP ACTIVITY
        mapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                /* PASS BUNDLE IF ARGUMENTS NEEDED
                Bundle b = new Bundle();
                b.putString("NOMBRE", txtNombre.getText().toString());
                intent.putExtras(b);
                */
                startActivity(intent);
            }
        });

        //NAVIGATE TO CODEBAR READER ACTIVITY
        codebarReaderImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CodeBarReaderActivity.class);
                /* PASS BUNDLE IF ARGUMENTS NEEDED
                Bundle b = new Bundle();
                b.putString("NOMBRE", txtNombre.getText().toString());
                intent.putExtras(b);
                */
                startActivity(intent);
            }
        });

        //NAVIGATE TO SEARCH ACTIVITY
        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                /* PASS BUNDLE IF ARGUMENTS NEEDED
                Bundle b = new Bundle();
                b.putString("NOMBRE", txtNombre.getText().toString());
                intent.putExtras(b);
                */
                startActivity(intent);
            }
        });

        //NAVIGATE TO DISCOUNTS ACTIVITY
        discountImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DiscountsActivity.class);
                /* PASS BUNDLE IF ARGUMENTS NEEDED
                Bundle b = new Bundle();
                b.putString("NOMBRE", txtNombre.getText().toString());
                intent.putExtras(b);
                */
                startActivity(intent);
            }
        });

        /*
        ==============================================
        = Ejemplo de como usar webservices
        ==============================================

        WebServicesEntryPoint.getShopsByRadius(-34.5442824021754, -58.5560560226441, 5, new WebServiceCallback<List<Shop>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Shop>> webServiceResponse) {
                String logTag = "COELIAC";
                if (webServiceResponse.getEx() != null) {
                    Log.d(logTag, webServiceResponse.getEx().toString());
                } else {
                    for (Shop s : webServiceResponse.getBodyAsObject()) {
                        Log.d(logTag, s.toString());
                    }
                }
            }
        });
        */

    }
}
