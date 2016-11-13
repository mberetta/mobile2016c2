package ar.edu.utn.frba.coeliacs.coeliacapp.models.map;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.City;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Continent;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Country;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Entity;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.State;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.map.component.MapPreferencesManager;
import ar.edu.utn.frba.coeliacs.coeliacapp.models.search.SearchActivity;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceCallback;
import ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServiceResponse;

import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getAllContinents;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getCitiesByState;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getCountriesByContinent;
import static ar.edu.utn.frba.coeliacs.coeliacapp.webservices.WebServicesEntryPoint.getStatesByCountry;

public class MapSettingsActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView seekText;
    private Button locationButton;
    private Button productButton;

    List<Continent> continents;

    private boolean useLocation;
    private int actualRadius;
    private MapPreferencesManager preferences;
    private Entity selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = new MapPreferencesManager(this);

        useLocation = preferences.getUseLocation();
        actualRadius = preferences.getRadius();
        selectedLocation = preferences.getLocation();

        locationSwitch();
        locationButton();
        productButton();

        seekBar(actualRadius);

        if (useLocation) {
            showLocation();
        } else {
            showList();
        }
    }

    private void productButton() {
        productButton = (Button) findViewById(R.id.products_button);
        productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapSettingsActivity.this, SearchActivity.class);
                startActivityForResult(intent, 1002);
            }
        });
    }

    private void locationButton() {
        getAllContinents(new WebServiceCallback<List<Continent>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Continent>> webServiceResponse) {
                continents = webServiceResponse.getBodyAsObject();
            }
        });

        if (selectedLocation != null) {
            ((TextView) findViewById(R.id.location_text)).setText("Location: " + selectedLocation);
        }

        locationButton = (Button) findViewById(R.id.location_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(continents, true, null, new ElementCallback<Continent>() {
                    @Override
                    public void apply(Continent value, final DialogInterface parent) {
                        countryDialog(value, parent);
                    }
                });

            }
        });
    }

    private void countryDialog(Continent value, final DialogInterface parent) {
        getCountriesByContinent(value, new WebServiceCallback<List<Country>>() {
            @Override
            public void onFinished(WebServiceResponse<List<Country>> webServiceResponse) {
                List<Country> countries = webServiceResponse.getBodyAsObject();
                showAlertDialog(countries, true, parent, new ElementCallback<Country>() {
                    @Override
                    public void apply(Country value, final DialogInterface parent) {
                        stateDialog(value, parent);
                    }
                });
            }
        });
    }

    private void stateDialog(Country value, final DialogInterface parent) {
        getStatesByCountry(value, new WebServiceCallback<List<State>>() {
            @Override
            public void onFinished(WebServiceResponse<List<State>> webServiceResponse) {
                showAlertDialog(webServiceResponse.getBodyAsObject(), true, parent, new ElementCallback<State>() {
                    @Override
                    public void apply(State value, final DialogInterface parent) {
                        cityDialog(value, parent);
                    }
                });
            }
        });
    }

    private void cityDialog(State value, final DialogInterface parent) {
        getCitiesByState(value, new WebServiceCallback<List<City>>() {
            @Override
            public void onFinished(WebServiceResponse<List<City>> webServiceResponse) {
                showAlertDialog(webServiceResponse.getBodyAsObject(), false, parent, null);
            }
        });
    }

    private void finishDialog(Entity value, DialogInterface parent) {
        selectedLocation = value;
        ((TextView) findViewById(R.id.location_text)).setText("Location: " + value.toString());
        if (parent != null) {
            parent.dismiss();
        }
    }

    private <T extends Entity> void showAlertDialog(final List<T> values, boolean hasNext, final DialogInterface parent, final ElementCallback<T> elementCallback) {
        final Integer[] position = new Integer[1];
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(MapSettingsActivity.this, android.R.layout.select_dialog_singlechoice, values);

        AlertDialog.Builder builder = new AlertDialog.Builder(MapSettingsActivity.this)
                .setCancelable(false)
                .setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        position[0] = which;
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishDialog(values.get(position[0]), parent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        if (hasNext) {
            builder = builder.setNeutralButton("More", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (parent != null) {
                        parent.dismiss();
                    }
                    elementCallback.apply(values.get(position[0]), dialog);
                }
            });
        }

        final AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void seekBar(int actualProgress) {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(400);
        seekBar.setProgress(actualProgress);

        seekText = (TextView) findViewById(R.id.seek_text);
        updateRadiusText();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MapSettingsActivity.this.actualRadius = progress;
                updateRadiusText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void locationSwitch() {
        Switch locationSwitch = (Switch) findViewById(R.id.location_switch);
        locationSwitch.setChecked(useLocation);
        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                useLocation = isChecked;
                if (isChecked) {
                    showLocation();
                } else {
                    showList();
                }
            }
        });
    }

    private void showLocation() {
        locationButton.setEnabled(false);
        seekBar.setEnabled(true);

    }

    private void showList() {
        locationButton.setEnabled(true);
        seekBar.setEnabled(false);
    }

    private void updateRadiusText() {
        seekText.setText("Selected Radius: " + actualRadius + "Km");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.ok_settings:
                saveSettingsChanged();
                break;
        }

        return true;
    }

    private void saveSettingsChanged() {
        preferences.saveRadius(actualRadius);
        preferences.saveUseLocation(useLocation);
        if (selectedLocation != null) {
            preferences.saveLocation(selectedLocation);
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.map_settings_menu, menu);
        return true;
    }

    private interface ElementCallback<T extends Entity> {
        void apply(T value, DialogInterface parent);
    }
}
