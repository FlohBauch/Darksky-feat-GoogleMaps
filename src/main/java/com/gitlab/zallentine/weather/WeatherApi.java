package com.gitlab.zallentine.weather;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIODaily;
import com.github.dvdme.ForecastIOLib.ForecastIO;
import com.gitlab.zallentine.ConfigGetter;
import com.gitlab.zallentine.maps.GMapsApi;
import com.google.maps.errors.ApiException;

import java.io.IOException;

public class WeatherApi {
    private String fsapikey = null;
    ConfigGetter cfg = new ConfigGetter("config/api.cfg");
    GMapsApi gMapsApi = new GMapsApi();

    public WeatherApi() throws IOException {
    }

    public void setFsapikey() throws IOException {
        this.fsapikey = cfg.settings("darkskyapi");
    }

    public ForecastIO getFio(String address) throws IOException, ApiException, InterruptedException {
        setFsapikey();
        ForecastIO fio = new ForecastIO(fsapikey);
        fio.setUnits(ForecastIO.UNITS_SI);
        fio.getForecast("" + gMapsApi.getLat(address),"" + gMapsApi.getLng(address));
        return fio;
    }

    public Double getCurrentTemp(String address) throws InterruptedException, ApiException, IOException {
        FIOCurrently currently = new FIOCurrently(getFio(address));
        return currently.get().temperature();
    }

    public Double getDayMaxTemp(String address) throws InterruptedException, ApiException, IOException {
        FIODaily daily = new FIODaily(getFio(address));
        return daily.getDay(0).temperatureMax();
    }

    public Double getCurrentWindSpeed(String address) throws InterruptedException, ApiException, IOException {
        FIOCurrently currently = new FIOCurrently(getFio(address));
        return currently.get().windSpeed() * 3.6;
    }

    public Double getDayPreProb(String address) throws InterruptedException, ApiException, IOException {
        FIODaily daily = new FIODaily(getFio(address));
        return daily.getDay(0).precipProbability() * 100;
    }

    public String getForeCast(String address) throws InterruptedException, ApiException, IOException {
        FIODaily daily = new FIODaily(getFio(address));
        return daily.getDay(0).summary();
    }

    public String getCurrentWeather(String address) throws InterruptedException, ApiException, IOException {
        FIOCurrently currently = new FIOCurrently(getFio(address));
        return currently.get().summary();
    }



}
