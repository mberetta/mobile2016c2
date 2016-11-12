package ar.edu.utn.frba.coeliacs.coeliacapp.models.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;

/**
 * Created by mberetta on 30/10/2016.
 */
public class NoScrollIconListView extends LinearLayout {

    public NoScrollIconListView(Context context) {
        super(context);
    }

    public NoScrollIconListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollIconListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NoScrollIconListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setItems(List<IconArrayAdapterModel> items) {
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (IconArrayAdapterModel item : items) {
            View rowView = layoutInflater.inflate(R.layout.icon_array_adapter, null);
            TextView titleTextView = (TextView) rowView.findViewById(R.id.titleTextView);
            TextView subtitleTextView = (TextView) rowView.findViewById(R.id.subtitleTextView);
            ImageView iconImageView = (ImageView) rowView.findViewById(R.id.iconImageView);
            titleTextView.setText(item.getTitle());
            subtitleTextView.setText(item.getSubtitle());
            iconImageView.setImageResource(item.getIconResId());
            addView(rowView);
        }
    }

}
