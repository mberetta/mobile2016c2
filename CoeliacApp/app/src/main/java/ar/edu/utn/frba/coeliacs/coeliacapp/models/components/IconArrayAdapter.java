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

    private List<T> objects;
    LayoutInflater layoutInflater;

    public IconArrayAdapter(Context context, List<T> objects) {
        super(context, -1, objects);
        // Buscamos el LayoutInflater una sola vez.
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T object = objects.get(position);
        final ViewHolder vh;
        // convertView es una vista a reutilizar.
        if (convertView == null) {
            // No hay para reutilizar, inflamos una nueva y creamos el ViewHolder.
            convertView = layoutInflater.inflate(R.layout.icon_array_adapter, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else {
            // Hay una para reutilizar, buscamos el ViewHolder.
            vh = (ViewHolder) convertView.getTag();
        }
        // Completamos la info.
        vh.titleTextView.setText(object.getTitle());
        vh.subtitleTextView.setText(object.getSubtitle());
        vh.iconImageView.setImageResource(object.getIconResId());
        return convertView;
    }

    // El ViewHolder sirve para guardar las referencias a las vistas y buscarlas una sola vez.
    private class ViewHolder {
        TextView titleTextView;
        TextView subtitleTextView;
        ImageView iconImageView;

        ViewHolder(View view) {
            titleTextView = (TextView) view.findViewById(R.id.titleTextView);
            subtitleTextView = (TextView) view.findViewById(R.id.subtitleTextView);
            iconImageView = (ImageView) view.findViewById(R.id.iconImageView);
        }
    }
}