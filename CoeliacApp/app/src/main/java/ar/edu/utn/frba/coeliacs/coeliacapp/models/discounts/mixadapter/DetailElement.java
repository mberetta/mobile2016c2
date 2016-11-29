package ar.edu.utn.frba.coeliacs.coeliacapp.models.discounts.mixadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.utn.frba.coeliacs.coeliacapp.R;

/**
 * Created by mberetta on 28/11/2016.
 */
public class DetailElement implements MixElement {

    private String title;
    private String subTitle;

    public DetailElement(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public int getItemViewType() {
        return MixAdapter.TYPE_DETAIL;
    }

    @Override
    public View getView(View convertView, ViewGroup parent, LayoutInflater layoutInflater) {
        // TODO refactor so that this behavior is cross to all MixElements
        DefaultViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.icon_array_adapter, parent, false);
            vh = new DefaultViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (DefaultViewHolder) convertView.getTag();
        }
        vh.theTitle.setText(title);
        vh.theSubtitle.setText(subTitle);
        vh.theImage.setImageResource(R.drawable.ic_dns_200dp);
        return convertView;
    }

    @Override
    public void handleClick(Context context) {
        //
    }
}
