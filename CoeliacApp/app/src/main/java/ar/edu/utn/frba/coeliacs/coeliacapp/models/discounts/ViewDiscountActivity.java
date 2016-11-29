package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Discount;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter.DetailElement;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter.HeaderElement;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter.MixAdapter;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter.MixElement;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter.ProductElement;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter.ShopElement;

public class ViewDiscountActivity extends AppCompatActivity {

    public static final String EXTRA_DISCOUNT = "EXTRA_DISCOUNT";
    public static final String EXTRA_SHOP = "EXTRA_SHOP";
    public static final String EXTRA_PRODUCT = "EXTRA_PRODUCT";

    private DiscountIconArrayAdapterModel discountModel;
    private ListView mixListView;
    private List<MixElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_discount);
        setTitle(R.string.title_view_discount);

        mixListView = (ListView) findViewById(R.id.mixList);
        mixListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MixElement me = elements.get(position);
                me.handleClick(ViewDiscountActivity.this);
            }
        });

        if (savedInstanceState != null) {
            discountModel = (DiscountIconArrayAdapterModel) savedInstanceState.getSerializable(EXTRA_DISCOUNT);
            if (discountModel != null) {
                updateUI();
                return;
            }
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        discountModel = (DiscountIconArrayAdapterModel) bundle.getSerializable(EXTRA_DISCOUNT);

        updateUI();
    }

    private void updateUI() {
        elements = new ArrayList<MixElement>();

        elements.add(new HeaderElement("Comercio"));

        elements.add(new ShopElement(discountModel.getOwnerShop()));

        elements.add(new HeaderElement("Productos"));

        // TODO this list is hardcoded and not loaded from webservice
        Product hardcodedProduct = new Product();
        hardcodedProduct.set_id("RJ45");
        hardcodedProduct.setName("Barra de cereal para celíaco");
        elements.add(new ProductElement(hardcodedProduct));
        elements.add(new ProductElement(hardcodedProduct));
        elements.add(new ProductElement(hardcodedProduct));
        elements.add(new ProductElement(hardcodedProduct));
        elements.add(new ProductElement(hardcodedProduct));
        elements.add(new ProductElement(hardcodedProduct));
        elements.add(new ProductElement(hardcodedProduct));

        elements.add(new HeaderElement("Detalles"));

        Discount discount = discountModel.getObject();
        elements.add(new DetailElement("Descripción", discount.getDescription()));
        elements.add(new DetailElement("Términos y condiciones", discount.getTermsAndConditions()));
        elements.add(new DetailElement("Cantidad disponible", Integer.toString(discount.getAvailableQuantity()) + " " + getResources().getString(R.string.units)));

        mixListView.setAdapter(new MixAdapter(this, elements));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_DISCOUNT, discountModel);
        super.onSaveInstanceState(outState);
    }

}
