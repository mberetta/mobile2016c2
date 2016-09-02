package ar.com.mobile2016.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ar.com.mobile2016.frontend.webservice.WebServiceCallTask;
import ar.com.mobile2016.frontend.webservice.WebServiceCallback;
import ar.com.mobile2016.frontend.webservice.WebServiceResponse;

public class MainActivity extends AppCompatActivity {

    private Button llamarWebServiceButton;
    private TextView resultadoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llamarWebServiceButton = (Button) findViewById(R.id.button_llamar_webservice);
        llamarWebServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator zxing = new IntentIntegrator(MainActivity.this);
                zxing.initiateScan();
            }
        });

        resultadoTextView = (TextView) findViewById(R.id.text_view_resultado);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String url = "http://mberetta.com.ar:3000/search/product?query=by_barcode&barcode=" + scanResult.getContents();
            WebServiceCallTask<Product> w = new WebServiceCallTask<Product>(Product.class, url, "GET");
            w.setWebServiceCallback(new WebServiceCallback<Product>() {
                @Override
                public void onFinished(WebServiceResponse<Product> webServiceResponse) {
                    resultadoTextView.setText(webServiceResponse.getBodyAsObject().getName());
                }
                @Override
                public void onError(Throwable ex) {
                    resultadoTextView.setText("Producto no encontrado");
                }
            });
            w.execute();
        }
    }

    public class Product {

        private String _id;
        private String name;

        public String get_id() {
            return _id;
        }

        public String getName() {
            return name;
        }

    }

}
