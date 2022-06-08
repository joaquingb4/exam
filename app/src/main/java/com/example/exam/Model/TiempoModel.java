package com.example.exam.Model;

import android.opengl.Visibility;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TiempoModel {
    @SerializedName(value="coord")
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    public Main main;
    public long visibility;
    public Wind wind;
    public  Clouds clouds;
    public double dt;
    public Sys sys;
    public long timezone;
    public long id;
    public String name;
    public int cod;


    public Coord getCoord() {
        return coord;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public long getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public double getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public long getTimezone() {
        return timezone;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }
    public String toString(){
        String result = coord.lon +"\n";
        result+= coord.lat +"\n";

        for (int i  = 0 ; i < weather.size(); i++){
            result+=" "+weather.get(i).getId()+" \n";
            result+=" "+weather.get(i).getMain()+" \n";;
            result+=" "+weather.get(i).getDescription()+" \n";;
            result+=" "+weather.get(i).getIcon()+" \n";;
        }
        result+= getBase() +"\n";
        result+= getMain().temp +"\n";
        result+= getMain().feels_like +"\n";
        result+= getMain().temp_min +"\n";
        result+= getMain().temp_max +"\n";
        result+= getMain().pressure +"\n";
        result+= getMain().humidity +"\n";

        result+= getVisibility() +"\n";

        result+= getWind().speed +"\n";
        result+= getWind().deg +"\n";

        result+= getClouds().all +"\n";

        result+= getDt() +"\n";

        result+= getSys().type+"\n";
        result+= getSys().id+"\n";
        result+= getSys().country+"\n";
        result+= getSys().sunrise+"\n";
        result+= getSys().sunset+"\n";

        result+= getTimezone()+"\n";
        result+= getId()+"\n";
        result+= getName()+"\n";
        result+= getCod()+"\n";
       return result;
    }

}
