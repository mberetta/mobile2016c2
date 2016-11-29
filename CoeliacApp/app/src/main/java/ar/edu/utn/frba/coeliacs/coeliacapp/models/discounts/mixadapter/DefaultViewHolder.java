package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;

/**
 * Created by mberetta on 28/11/2016.
 */
public class DefaultViewHolder {

    TextView theTitle;
    TextView theSubtitle;
    ImageView theImage;

    public DefaultViewHolder(View view) {
        theTitle = (TextView) view.findViewById(R.id.titleTextView);
        theSubtitle = (TextView) view.findViewById(R.id.subtitleTextView);
        theImage = (ImageView) view.findViewById(R.id.iconImageView);
    }

}
