package ar.edu.utn.frba.coeliacs.coeliacapp.models.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;
import ar.edu.utn.frba.coeliacs.coeliacapp.communication.BusProvider;
import ar.edu.utn.frba.coeliacs.coeliacapp.communication.ShopSelectedEvent;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.utils.TextUtils;

/**
 * Created by max on 8/11/2016.
 */

public class ShopAdapter extends ArrayAdapter<Shop> {
    private static final String TAG = ShopAdapter.class.getSimpleName();
    final private Bus bus = BusProvider.getInstance();

    private Context context;
    private List<Shop> objects;
    private List<Shop> filteredObjects;

    public ShopAdapter(Context context, List<Shop> objects) {
        super(context, -1, objects);
        this.context = context;
        this.objects = objects;
        this.filteredObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.item_shop, parent, false);
        TextView nameView = (TextView) rowView.findViewById(R.id.shopNameView);
        TextView addressView = (TextView) rowView.findViewById(R.id.shopAddressView);
        TextView phoneView = (TextView) rowView.findViewById(R.id.shopPhoneView);
        ImageView iconImageView = (ImageView) rowView.findViewById(R.id.shopItemIconView);
        Log.d(TAG, "getView - size:" + objects.size() + " position:" + String.valueOf(position));
        final Shop shop = filteredObjects.get(position);
        nameView.setText(shop.getName());
        addressView.setText(shop.getAddress());
        phoneView.setText(shop.getTelnum());
        iconImageView.setImageResource(R.drawable.search);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bus.post(new ShopSelectedEvent(shop));
            }
        });
        return rowView;
    }

    @Override
    public int getCount() {
        return filteredObjects.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                String cleanConstraint = TextUtils.getRideOfAccents(constraint.toString().trim().toLowerCase());
                ArrayList<Shop> tempList = new ArrayList<Shop>();

                if(constraint != null && objects != null) {

                    for(int i = 0; i < objects.size(); i ++) {
                        Shop item = objects.get(i);

                        String cleanTitle = TextUtils.getRideOfAccents(item.getName().toLowerCase());
                        Log.d(TAG, "Comparing: " + cleanConstraint + " and " + cleanTitle);
                        if (cleanTitle.contains(cleanConstraint)) {
                            tempList.add(item);
                        }
                    }

                    //following two lines are for publishResults method
                    filterResults.values = tempList;
                    filterResults.count = tempList.size();
                }
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                ShopAdapter.this.filteredObjects = (ArrayList<Shop>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
