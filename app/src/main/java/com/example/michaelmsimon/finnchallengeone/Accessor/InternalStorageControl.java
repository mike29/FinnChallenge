package com.example.michaelmsimon.finnchallengeone.Accessor;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.michaelmsimon.finnchallengeone.MainActivity;
import com.example.michaelmsimon.finnchallengeone.Model.Product;

import org.parceler.Parcels;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Michael M. Simon on 2/15/2018.
 */

public final class InternalStorageControl {
    static ArrayList<Product> favProductPackage = new ArrayList<Product>();
    private static ArrayList<?> returnedFavsFromInternalStorage = new ArrayList<>();
    private static ArrayList<Product> tobeRewritten;

    private InternalStorageControl() {
    }

    //The actual saving of liked products list to internal memory
    public static void WriteFavProduct(Context context, Product favoriteProduct, int favoriteProductPosition, String FILENAME) {
        //NOTE
        //favoriteProductPosition is not used
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            //Add liked products from memory into TobeWritten ArrayList
            //This should be done before writing to memory, ReadFavProduct will getall the objects stored on memory
              tobeRewritten = ReadFavProduct(context, FILENAME);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }

        //Add the new liked product which the user clicked on to the list that will be written in memory
        tobeRewritten.add(favoriteProduct);

        //After getting the objects stored in memory and ave them inside ToBeWritten List, Delete all objects from memory using
        //Delete all values from file, NOT very secured solution. If something happens between the delete and rewriting user may
        //loose the data stored
        DeleteAllFavProduct(context, FILENAME);

        try {
            //Get a file stream to check if the file name existexisted
            File file = context.getFileStreamPath(FILENAME);

            //If file path DO NOT exist create
            if (file == null || !file.exists()) {

                //If file path DO EXIST append to it
                fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            }
            //Add object to arrayList(Can be deleted)
            favProductPackage.add(favoriteProduct);

            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            // Log.d("WRITE FAVPROD", String.valueOf(fos.getChannel()));
            try {
                oos = new ObjectOutputStream(fos);
                //     oos.writeObject(favoriteProduct);
                oos.writeObject(tobeRewritten);

            } catch (Exception e) {
                e.getMessage();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Close Stream
        try {
            fos.close();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    } //End write to internal storage

    public static void DeleteAllFavProduct(Context context, String FILENAME) {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            //Get a file stream to check if the file name existexisted
            File file = context.getFileStreamPath(FILENAME);

            //If file path DO NOT exist create
            if (file == null || !file.exists()) {
                //If file path DO EXIST append to it
              //  fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            }
            else {
                fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            }
            // Log.d("WRITE FAVPROD", String.valueOf(fos.getChannel()));
            try {
                oos = new ObjectOutputStream(fos);
                //     oos.writeObject(favoriteProduct);
                oos.reset();

            } catch (Exception e) {
                e.getMessage();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Close Stream
        try {
            fos.close();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    } //End write to internal storage

    //Read product reads all favorite products from internal storage CLEAN duplicates and return a list.
    public static ArrayList<Product> ReadFavProduct(Context context, String FILENAME) throws IOException, ClassNotFoundException {

       File file = context.getFileStreamPath(FILENAME);
        ArrayList<Product> holdProducts = new ArrayList<>();
            //Check if the file exist
            if(!file.exists()){
                //if the file don't exist make a new file,
                // triggers when the user install the app for first time just to reserve file
               file.createNewFile();
            }
                //Assign file path to FileinputStream
                FileInputStream fis = context.openFileInput(FILENAME);
            try {
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
                //Get all data from internal storage
                //Coudn't cast the returned value to Product
                //Because of that I should use (ArrayList<?>) and cast the objects in when sorting duplicates
                returnedFavsFromInternalStorage = (ArrayList<?>) ois.readObject();

                //Temporarily hold the values in seenValues
                ArrayList<String> seenValues = new ArrayList<>();
                for (Object p : returnedFavsFromInternalStorage) {
                    //Cast object to product
                    Product pp = (Product) p;
                    if (seenValues.contains(pp.getId())) {
                        returnedFavsFromInternalStorage.remove(p);
                    } else {
                        seenValues.add(pp.getId());
                        //holdProducts holds objects without duplicate values
                        holdProducts.add(pp);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fis.close();
            }
        //Make the last liked favorite product on the top
        //
        Collections.reverse(holdProducts);
        //Return the final list of favorite products
        //
        return holdProducts;
    }

    public static void DeleteDislikedProduct(Context context, String dislikedProductId, String filename) throws IOException, ClassNotFoundException {

        ArrayList<Product> holdAllValues;
        //Read all from memory
        holdAllValues = ReadFavProduct(context,filename);
        ArrayList<Product> holdOnlyLiked = new ArrayList<>();
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        for (Product p : holdAllValues) {
            if (p.getId().equals(dislikedProductId)) {
               // holdAllValues.remove(p);
            } else {
                //Holds only the values that are liked
                holdOnlyLiked.add(p);
            }
                try {
                    //Get a file stream to check if the file name existexisted
                    File file = context.getFileStreamPath(filename);

                    //If file path DO NOT exist create
                    if (file != null || file.exists()) {
                        //If file path DO EXIST append to it
                        fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
                    }
                    try {
                        oos = new ObjectOutputStream(fos);
                        oos.writeObject(holdOnlyLiked);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }
}
