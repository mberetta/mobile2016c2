package ar.com.mobile2016.frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                WebServiceCallTask<Product> w = new WebServiceCallTask<Product>(Product.class, "http://mberetta.com.ar:3000/search/product?query=by_barcode&barcode=RJ45", "GET");
                w.setWebServiceCallback(new WebServiceCallback<Product>() {
                    @Override
                    public void onFinished(WebServiceResponse<Product> webServiceResponse) {
                        resultadoTextView.setText(webServiceResponse.getBodyAsObject().getName());
                    }
                });
                w.execute();
            }
        });

        resultadoTextView = (TextView) findViewById(R.id.text_view_resultado);
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
