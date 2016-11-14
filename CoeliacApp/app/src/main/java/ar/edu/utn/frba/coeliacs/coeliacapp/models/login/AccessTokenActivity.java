package ar.edu.utn.frba.coeliacs.coeliacapp.models.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.login.PrefUtil;

public class AccessTokenActivity extends AppCompatActivity {

    private TextView tokenTV;
    private PrefUtil prefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);

        init();

    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefUtil = new PrefUtil(this);

        tokenTV = (TextView) findViewById(R.id.token_tv);
        tokenTV.setText(prefUtil.getToken());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
