package ar.edu.utn.frba.coeliacs.coeliacapp.models.map.component;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Log;

import com.google.gson.Gson;

import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Entity;

import static android.content.Context.MODE_PRIVATE;

public class MapPreferencesManager {

    private static final String USE_LOCATION = "USE_LOCATION";
    private static final String RADIUS = "RADIUS";
    private static final String SETTINGS_PREFERENCES = "SETTINGS_PREFERENCES";
    private static final String LOCATION = "LOCATION";
    private static final String LOCATION_TYPE = "LOCATION_TYPE";
    private static final String LAST_KNOWN_LOCATION = "LAST_KNOWN_LOCATION";

    private final Gson gson = new Gson();

    private SharedPreferences preferences;

    public MapPreferencesManager(Context context) {
        preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
    }

    public void saveRadius(int radius) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(RADIUS, radius);

        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public int getRadius() {
        return preferences.getInt(RADIUS, 10);
    }

    public void saveUseLocation(boolean useLocation) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(USE_LOCATION, useLocation);

        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public boolean getUseLocation() {
        return preferences.getBoolean(USE_LOCATION, true);
    }

    public void saveLocation(Entity location) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOCATION, gson.toJson(location));
        editor.putString(LOCATION_TYPE, location.getClass().getName());

        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public Entity getLocation() {
        String jsonType = preferences.getString(LOCATION_TYPE, null);
        try {
            String locationString = preferences.getString(LOCATION, null);
            if (locationString != null && jsonType != null) {
                return (Entity) gson.fromJson(locationString, Class.forName(jsonType));
            }
        } catch (ClassNotFoundException e) {
            Log.e("Location", "Cannot deserialize location");
        }
        return null;
    }

    public Location getLastKnownLocation() {
        String locationString = preferences.getString(LAST_KNOWN_LOCATION, null);

        if (locationString != null) {
            return gson.fromJson(locationString, Location.class);
        }

        return null;
    }

    public void saveLastKnownLocation(Location location) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LAST_KNOWN_LOCATION, gson.toJson(location));
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }
}
