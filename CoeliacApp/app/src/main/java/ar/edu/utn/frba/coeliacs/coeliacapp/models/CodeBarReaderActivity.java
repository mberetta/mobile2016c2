package ar.edu.utn.frba.coeliacs.coeliacapp.models;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ar.edu.utn.frba.coeliacs.coeliacapp.ErrorHandling;
import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.search.SearchActivity;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint;

public class CodeBarReaderActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_bar_reader);

        IntentIntegrator zxing = new IntentIntegrator(CodeBarReaderActivity.this);
        zxing.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        finish();

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null && scanResult.getContents() != null) {
            WebServicesEntryPoint.getProductByBarcode(scanResult.getContents(), new WebServiceCallback<Product>() {
                @Override
                public void onFinished(WebServiceResponse<Product> webServiceResponse) {
                    if (webServiceResponse.getEx() != null) {
                        ErrorHandling.showWebServiceError(CodeBarReaderActivity.this);
                    } else {
                        Intent intent = new Intent(CodeBarReaderActivity.this, SearchActivity.class);//TODO: no se debe enviar al SeachActivity, esto se separo en mas activities
                        intent.putExtra(EXTRA_PRODUCT, webServiceResponse.getBodyAsObject());
                        startActivity(intent);
                    }
                }
            });
        }
    }

}
