package ar.edu.utn.frba.coeliacs.coeliacapp.models.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;

/**
 * Created by mberetta on 27/10/16.
 */
public class IconArrayAdapter<T extends IconArrayAdapterModel> extends ArrayAdapter<T> {

    private Context context;
    private List<T> objects;

    public IconArrayAdapter(Context context, List<T> objects) {
        super(context, -1, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.icon_array_adapter, parent, false);
        TextView titleTextView = (TextView) rowView.findViewById(R.id.titleTextView);
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.subtitleTextView);
        ImageView iconImageView = (ImageView) rowView.findViewById(R.id.iconImageView);
        T object = objects.get(position);
        titleTextView.setText(object.getTitle());
        subtitleTextView.setText(object.getSubtitle());
        iconImageView.setImageResource(object.getIconResId());
        return rowView;
    }

}