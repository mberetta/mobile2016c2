package ar.edu.utn.frba.coeliacs.coeliacapp.webservices;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import ar.edu.utn.frba.coeliacs.coeliacapp.domain.City;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Continent;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Country;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Discount;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Product;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.Shop;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.State;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.UpdateResponse;
import ar.edu.utn.frba.coeliacs.coeliacapp.domain.User;

/**
 * Created by mberetta on 14/10/2016.
 */
public class WebServicesEntryPoint {

    private static final String BASE_URL = "http://mberetta.com.ar:3000";

    //===========================================================================
    // Users
    //===========================================================================

    public static void getUserInfo(String _id, WebServiceCallback<User> callback) {
        String url = BASE_URL + "/user/config?id_user=" + _id;
        new WebServiceCallTask<User>(User.class, url, "GET", callback).execute();
    }

    public static void updateUserSettings(User user, WebServiceCallback<UpdateResponse> callback) {
        String url = BASE_URL + "/user/config?id_user=" + user.get_id() + "&default_radius=" + user.getDefaultRadius() + "&default_exclusive=" + user.isDefaultExclusive() + "&id_default_city=" + user.getDefaultLocation().getCity() + "&id_default_country=" + user.getDefaultLocation().getCountry() + "&id_default_state=" + user.getDefaultLocation().getState() + "&id_default_continent=" + user.getDefaultLocation().getContinent();
        new WebServiceCallTask<UpdateResponse>(UpdateResponse.class, url, "PATCH", callback).execute();
    }

    //===========================================================================
    // Products
    //===========================================================================

    public static void getAllProducts(WebServiceCallback<List<Product>> callback) {
        String url = BASE_URL + "/search/product?query=all";
        new WebServiceCallTask<List<Product>>(new TypeToken<List<Product>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getProductsByName(String name, WebServiceCallback<List<Product>> callback) {
        String url = BASE_URL + "/search/product?query=by_name&name=" + name;
        new WebServiceCallTask<List<Product>>(new TypeToken<List<Product>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getProductByBarcode(String barcode, WebServiceCallback<Product> callback) {
        String url = BASE_URL + "/search/product?query=by_barcode&barcode=" + barcode;
        new WebServiceCallTask<Product>(Product.class, url, "GET", callback).execute();
    }

    public static void getProductsSoldByShop(Shop shop, WebServiceCallback<List<Product>> callback) {
        String url = BASE_URL + "/shop/products?id_shop=" + shop.get_id();
        new WebServiceCallTask<List<Product>>(new TypeToken<List<Product>>(){}.getType(), url, "GET", callback).execute();
    }

    //===========================================================================
    // Shops
    //===========================================================================

    public static void getShopById(String _id, WebServiceCallback<Shop> callback) {
        String url = BASE_URL + "/search/shop?query=by_id&id_shop=" + _id;
        new WebServiceCallTask<Shop>(Shop.class, url, "GET", callback).execute();
    }

    public static void getAllShops(WebServiceCallback<List<Shop>> callback) {
        String url = BASE_URL + "/search/shop?query=all";
        new WebServiceCallTask<List<Shop>>(new TypeToken<List<Shop>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getShopsByName(String name, WebServiceCallback<List<Shop>> callback) {
        String url = BASE_URL + "/search/shop?query=by_name&name=" + name;
        new WebServiceCallTask<List<Shop>>(new TypeToken<List<Shop>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getShopsByRadius(double lat, double _long, int radiusKm, WebServiceCallback<List<Shop>> callback) {
        String url = BASE_URL + "/search/shop?query=by_radius&lat=" + lat + "&long=" + _long + "&radiusKm=" + radiusKm;
        new WebServiceCallTask<List<Shop>>(new TypeToken<List<Shop>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getShopsByCity(City city, WebServiceCallback<List<Shop>> callback) {
        String url = BASE_URL + "/search/shop?query=by_city&id_city=" + city.get_id();
        new WebServiceCallTask<List<Shop>>(new TypeToken<List<Shop>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getShopsByState(State state, WebServiceCallback<List<Shop>> callback) {
        String url = BASE_URL + "/search/shop?query=by_state&id_state=" + state.get_id();
        new WebServiceCallTask<List<Shop>>(new TypeToken<List<Shop>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getShopsByCountry(Country country, WebServiceCallback<List<Shop>> callback) {
        String url = BASE_URL + "/search/shop?query=by_country&id_country=" + country.get_id();
        new WebServiceCallTask<List<Shop>>(new TypeToken<List<Shop>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getShopsByContinent(Continent continent, WebServiceCallback<List<Shop>> callback) {
        String url = BASE_URL + "/search/shop?query=by_continent&id_continent=" + continent.get_id();
        new WebServiceCallTask<List<Shop>>(new TypeToken<List<Shop>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getShopsThatSellAProduct(Product product, WebServiceCallback<List<Shop>> callback) {
        String url = BASE_URL + "/product/shops?barcode=" + product.get_id();
        new WebServiceCallTask<List<Shop>>(new TypeToken<List<Shop>>(){}.getType(), url, "GET", callback).execute();
    }

    //===========================================================================
    // Discounts
    //===========================================================================

    public static void getDiscountsByShop(Shop shop, WebServiceCallback<List<Discount>> callback) {
        String url = BASE_URL + "/shop/discounts?id_shop=" + shop.get_id();
        new WebServiceCallTask<List<Discount>>(new TypeToken<List<Discount>>(){}.getType(), url, "GET", callback).execute();
    }

    //===========================================================================
    // Locations
    //===========================================================================

    public static void getAllContinents(WebServiceCallback<List<Continent>> callback) {
        String url = BASE_URL + "/utils/locations?query=get_continents";
        new WebServiceCallTask<List<Continent>>(new TypeToken<List<Continent>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getCountriesByContinent(Continent continent, WebServiceCallback<List<Country>> callback) {
        String url = BASE_URL + "/utils/locations?query=get_countries&id_continent=" + continent.get_id();
        new WebServiceCallTask<List<Country>>(new TypeToken<List<Country>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getStatesByCountry(Country country, WebServiceCallback<List<State>> callback) {
        String url = BASE_URL + "/utils/locations?query=get_states&id_country=" + country.getId();
        new WebServiceCallTask<List<State>>(new TypeToken<List<State>>(){}.getType(), url, "GET", callback).execute();
    }

    public static void getCitiesByState(State state, WebServiceCallback<List<City>> callback) {
        String url = BASE_URL + "/utils/locations?query=get_cities&id_state=" + state.getId();
        new WebServiceCallTask<List<City>>(new TypeToken<List<City>>(){}.getType(), url, "GET", callback).execute();
    }

}
