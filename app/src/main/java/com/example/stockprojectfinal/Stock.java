package com.example.stockprojectfinal;

import android.os.Parcel;
import android.os.Parcelable;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Stock implements Parcelable{
    private String ticker;
    private String docID;

    public Stock(String ticker) {
        this.ticker = ticker;
        docID = "none yet";
    }

    public Stock() {
        this.ticker = "";
        docID = "none yet";
    }

    public Stock(Parcel parcel) {
        ticker = parcel.readString();
        docID = parcel.readString();
    }
//    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(ticker);
        parcel.writeString(docID);
    }
    public static final Parcelable.Creator<Stock> CREATOR = new
            Parcelable.Creator<Stock>() {

                @Override
                public Stock createFromParcel(Parcel parcel) {
                    return new Stock(parcel);
                }

                @Override
                public Stock[] newArray(int size) {
                    return new Stock[0];
                }
            };

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getDocId() {
        return docID;
    }

    public void setDocId(String docID) {
        this.docID = docID;
    }
}
