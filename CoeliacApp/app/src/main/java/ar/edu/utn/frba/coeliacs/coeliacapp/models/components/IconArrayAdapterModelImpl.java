package ar.edu.utn.frba.coeliacs.coeliacapp.models.components;

/**
 * Created by mberetta on 27/10/16.
 */
public class IconArrayAdapterModelImpl implements IconArrayAdapterModel {

    private String title;
    private String subtitle;
    private int iconResId;

    public IconArrayAdapterModelImpl(String title, String subtitle, int iconResId) {
        this.title = title;
        this.subtitle = subtitle;
        this.iconResId = iconResId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public int getIconResId() {
        return iconResId;
    }

}
