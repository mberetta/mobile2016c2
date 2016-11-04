package ar.edu.utn.frba.coeliacs.coeliacapp.models.map.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;

import static ar.edu.utn.frba.coeliacs.coeliacapp.models.map.MapActivity.RADIUS_KEY;

public class MapSettingsActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView seekText;
    int actualProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seekBar = (SeekBar) findViewById(R.id.seekBar2);
        seekBar.setMax(400);
        actualProgress = getIntent().getExtras().getInt(RADIUS_KEY);
        seekBar.setProgress(actualProgress);

        seekText = (TextView) findViewById(R.id.seek_text);
        updateText();

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                actualProgress = progress;
                updateText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateText() {
        seekText.setText("Selected Radius: " + actualProgress + "Km");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }


}
