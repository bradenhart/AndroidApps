package com.example.bradenhart.myunitimetable;

import android.os.Parcel;
import android.os.Parcelable;

public class TimetableEntry implements Parcelable {

    private String paper, room, type;
    private int day, startTime, duration;

    // Constructors
    public TimetableEntry() {}

    public TimetableEntry(int day, String paper, String room, String type, int startTime, int duration) {
        this.day = day;
        this.paper = paper;
        this.room = room;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
    }

    // Getter and setter methods
    public int getDay() { return day; }
    public String getPaper() { return paper; }
    public String getRoom() { return room; }
    public String getType() { return type; }
    public int getStartTime() { return startTime; }
    public int getDuration() { return duration; }
    public int getEndTime() { return (this.getDuration() - this.getStartTime()); }

    public String getFullString() {
        return String.format("%s\n%s\n%s", this.getPaper(), this.getRoom(), this.getType());
    }

    public String getAllEntryInfo() {
        return String.format("%d %s %s %s %d %d", this.getDay(), this.getPaper(), this.getRoom(),
                this.getType(), this.getStartTime(), this.getDuration());
    }

    /** Constructor from Parcel, reads back fields IN THE ORDER they were written */
    public TimetableEntry(Parcel parcel) {
        this.day = parcel.readInt();
        this.paper = parcel.readString();
        this.room = parcel.readString();
        this.type = parcel.readString();
        this.startTime = parcel.readInt();
        this.duration = parcel.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(day);
        parcel.writeString(paper);
        parcel.writeString(room);
        parcel.writeString(type);
        parcel.writeInt(startTime);
        parcel.writeInt(duration);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<TimetableEntry> CREATOR =
            new Parcelable.Creator() {
                public TimetableEntry createFromParcel (Parcel pc){
                    return new TimetableEntry(pc);
                }

                public TimetableEntry[] newArray ( int size){
                    return new TimetableEntry[size];
                }

     };

}