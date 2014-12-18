package com.bradenhart.navigation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bradenhart on 14/12/14.
 */
public class MapMarker implements Parcelable {

    //private LatLng location; may not need this
    private double lat, lng;
    private String title;

    // Constructors
    public MapMarker() {
    }

    public MapMarker(double lat, double lng, String title) {
        this.lat = lat;
        this.lng = lng;
        this.title = title;
    }

    // Getter and setter methods
    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Constructor from Parcel, reads back fields IN THE ORDER they were written
     */
    public MapMarker(Parcel parcel) {
        this.lat = parcel.readDouble();
        this.lng = parcel.readDouble();
        this.title = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeString(title);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Creator<MapMarker> CREATOR =
            new Creator() {
                public MapMarker createFromParcel (Parcel pc){
                    return new MapMarker(pc);
                }

                public MapMarker[] newArray ( int size){
                    return new MapMarker[size];
                }

            };

}
