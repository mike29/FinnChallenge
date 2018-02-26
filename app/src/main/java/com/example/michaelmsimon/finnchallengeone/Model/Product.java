package com.example.michaelmsimon.finnchallengeone.Model;

import android.os.Parcelable;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by Michael M. Simon on 2/12/2018.
 */
//Product class that represent the products imported from the API

public class Product implements Parcelable, Serializable{
    private String id;
    private String description;
    private  int price;
    private  String location;
    private  boolean isFavorite = false;
    private  String imageURL;
    private  String  imageResource;
    private static long idCounter;

    public Product(String description, String location, int price, String imageURL, String imageResource) {
        this.description=description;
        this.location = location;
        this.price = price;
        this.imageURL = imageURL;
        this.imageResource = imageResource;
    }
    public Product(String description, String location, int price, String imageURL, String imageResource, String id) {
        this.description=description;
        this.location = location;
        this.price = price;
        this.imageURL = imageURL;
        this.imageResource = imageResource;
        this.id = id;
    }
    public Product(){

    }

    public String getId() { return id;}

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    public String getLocation() {
        return location;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }

    public String getImageURL() {
        return imageURL;
    }


    /*public void setPrice(int price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getImageResource() {
        return imageResource;
    }*/
    public static synchronized String createID()
    {
        return String.valueOf(idCounter++);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeInt(this.price);
        dest.writeString(this.location);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
        dest.writeString(this.imageURL);
        dest.writeString(this.imageResource);
    }

    protected Product(android.os.Parcel in) {
        this.description = in.readString();
        this.price = in.readInt();
        this.location = in.readString();
        this.isFavorite = in.readByte() != 0;
        this.imageURL = in.readString();
        this.imageResource = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(android.os.Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
