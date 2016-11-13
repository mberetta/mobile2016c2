package ar.edu.utn.frba.coeliacs.coeliacapp.models;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.configuration.CfgManager;

public class ConfigActivity extends AppCompatActivity {

    private SeekBar searchDistanceSeekBar;
    private TextView searchDistanceTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        setTitle(R.string.show_config);

        searchDistanceTitleTextView = (TextView) findViewById(R.id.search_distance_title);

        searchDistanceSeekBar = (SeekBar) findViewById(R.id.distance);
        int distance = CfgManager.getSearchDistance(this);
        searchDistanceSeekBar.setProgress(distance);
        updateUI(distance);
        searchDistanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private int progress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
                updateUI(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                CfgManager.setSearchDistance(ConfigActivity.this, progress);
            }
        });
    }

    private void updateUI(int distance) {
        searchDistanceTitleTextView.setText(getResources().getString(R.string.search_distance) + " (" + distance + " " + getResources().getString(R.string.distance_unit) + ")");
    }

}
