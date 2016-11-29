package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter;

import android.view.View;
import android.widget.TextView;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;

/**
 * Created by mberetta on 28/11/2016.
 */
public class HeaderViewHolder {

    TextView title;

    public HeaderViewHolder(View view) {
        title = (TextView) view.findViewById(R.id.titleTextView);
    }

}
