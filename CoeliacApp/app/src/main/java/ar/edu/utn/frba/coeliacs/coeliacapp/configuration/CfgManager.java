package ar.edu.utn.frba.coeliacs.coeliacapp.configuration;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mberetta on 09/11/2016.
 */
public class CfgManager {

    public static int getSearchDistance(Context context) {
        SharedPreferences sp = getPreferences(context);
        return sp.getInt("search_distance", 5);
    }

    public static void setSearchDistance(Context context, int distance) {
        SharedPreferences sp = getPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("search_distance", distance);
        editor.commit();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("CoeliacAppCfg", Context.MODE_PRIVATE);
    }

}
