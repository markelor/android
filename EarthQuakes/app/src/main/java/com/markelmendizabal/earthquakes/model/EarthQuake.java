package com.markelmendizabal.earthquakes.model;

import java.util.Date;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthQuake {
    private String _id;
    private String place;
    private Date time;
    private Coordinate coords;
    private double magnitude;
    private  String url;

    public EarthQuake(String _id, String place, Date date, Coordinate coords, double magnitude) {
        this._id = _id;
        this.place = place;
        this.time = date;
        this.coords = coords;
        this.magnitude = magnitude;

    }

    public EarthQuake() {

    }


    public String toString(){
        return this.getPlace();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = new Date(time);
    }

    public Coordinate getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
