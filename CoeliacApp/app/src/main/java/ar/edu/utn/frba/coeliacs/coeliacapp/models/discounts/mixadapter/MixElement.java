package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mberetta on 28/11/2016.
 */
public interface MixElement {

    int getItemViewType();

    View getView(View convertView, ViewGroup parent, LayoutInflater layoutInflater);

    void handleClick(Context context);

}
