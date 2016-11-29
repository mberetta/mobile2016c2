package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;

/**
 * Created by mberetta on 28/11/2016.
 */
public class HeaderElement implements MixElement {

    private String header;

    public HeaderElement(String header) {
        this.header = header;
    }

    @Override
    public int getItemViewType() {
        return MixAdapter.TYPE_HEADER;
    }

    @Override
    public View getView(View convertView, ViewGroup parent, LayoutInflater layoutInflater) {
        // TODO refactor so that this behavior is cross to all MixElements
        HeaderViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.header, parent, false);
            vh = new HeaderViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (HeaderViewHolder) convertView.getTag();
        }
        vh.title.setText(header);
        return convertView;
    }

    @Override
    public void handleClick(Context context) {
        //
    }
}
