package ar.edu.utn.frba.coeliacs.coeliacapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle bundle = this.getIntent().getExtras();
        //txtSaludo.setText("Hola " + bundle.getString("NOMBRE"));
    }
}
