package com.gitlab.zallentine.maps;

import com.gitlab.zallentine.ConfigGetter;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.PlaceDetails;

import java.io.IOException;
import java.util.Random;


public class GMapsApi {

    private String gApiKey = null;
    private ConfigGetter cfg = new ConfigGetter("config/api.cfg");
    private GeoApiContext geoApiContext = new GeoApiContext().setApiKey(cfg.settings("googleapi"));

    public GMapsApi() throws IOException {
    }

    private void setgApiKey() throws IOException {
        gApiKey = cfg.settings("googleapi");
    }

    public double getLat(String address) throws InterruptedException, ApiException, IOException {
        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();
        return results[0].geometry.location.lat;
    }

    public double getLng(String address) throws InterruptedException, ApiException, IOException {
        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();
        return results[0].geometry.location.lng;
    }

    public String getPlacePicUrl(String address) throws InterruptedException, ApiException, IOException {
        GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();
        String placeID = results[0].placeId;
        PlaceDetails placeDetails = PlacesApi.placeDetails(geoApiContext, placeID).await();

        Random rnd = new Random();
        int i = rnd.nextInt(placeDetails.photos.length);

        String photoRef = placeDetails.photos[i].photoReference;

        String imgUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photoRef+"&key=AIzaSyCnkT3C-zxepO1tJC9j_PXozHxQPiCXIw0";

        return imgUrl;
    }
}
