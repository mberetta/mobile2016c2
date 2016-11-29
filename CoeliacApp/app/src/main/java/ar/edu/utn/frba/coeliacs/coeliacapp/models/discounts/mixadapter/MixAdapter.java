package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by mberetta on 28/11/2016.
 */
public class MixAdapter extends ArrayAdapter<MixElement> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_DETAIL = 1;
    public static final int TYPE_SHOP = 2;
    public static final int TYPE_PRODUCT = 3;

    private List<MixElement> elements;
    private Context context;
    private LayoutInflater layoutInflater;

    public MixAdapter(Context context, List<MixElement> objects) {
        super(context, -1, objects);
        this.elements = objects;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return elements.get(position).getItemViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("COELIAC", "Getting view for " + elements.get(position).getClass().getName());
        return elements.get(position).getView(convertView, parent, layoutInflater);
    }

}
