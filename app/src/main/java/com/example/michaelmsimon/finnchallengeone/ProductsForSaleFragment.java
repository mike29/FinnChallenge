package com.example.michaelmsimon.finnchallengeone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.michaelmsimon.finnchallengeone.Accessor.InternalStorageControl;
import com.example.michaelmsimon.finnchallengeone.Accessor.OnClientApiExcuted;
import com.example.michaelmsimon.finnchallengeone.Model.Product;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

public class ProductsForSaleFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GridView gridview;
    private OnFragmentInteractionListener mListener;
    private String FILENAME = "favorite_products";
    View rootView;

    public ProductsForSaleFragment() {
        // Required empty public constructor
    }

    public static ProductsForSaleFragment newInstance(String param1, String param2) {
        ProductsForSaleFragment fragment = new ProductsForSaleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_products_for_sale, container, false);
        gridview = (GridView) rootView.findViewById(R.id.gridview);

        Bundle bundle;
        ArrayList<Product> listOfProductsFromMain = new ArrayList<>();

        if (this.getArguments() != null) {
            bundle = this.getArguments();
            //Receive values sent from MainActivity, the main activity taskCompleted method sent the values
            listOfProductsFromMain = Parcels.unwrap(bundle.getParcelable("prods"));
        }
        //If already liked product is available in the list toggle to liked in the view
        CheckIfLikedProductIsAvailable(listOfProductsFromMain);

        //TODO
        //take all favorite products fom memory
        //set them inside a list
        //compare the favorite products isfavorite is a same as the listOfProductsFromMain
        //is they are same replace the listFromProductMain with the fav product from memory

        //set values to adapter
        final GridViewAdapter productAdapter = new GridViewAdapter(rootView.getContext().getApplicationContext(), listOfProductsFromMain);
        gridview.setAdapter(productAdapter);
        //Update viewAdapter
        productAdapter.notifyDataSetChanged();

        //A final list of products loaded to the fragment
        final ArrayList<Product> finalHb = listOfProductsFromMain;
        //Listen to the clicked product and save the favorite
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product favored_prods = finalHb.get(position);
                //Change ICON to liked
                favored_prods.toggleFavorite();

                //TODO
                //Get the children value of the grid
                //Create intent to navigate to the selected product
                //Save selected item as favorite
                //Maybe set the onClick listener in to onCreate method

                //load favorite to internal memory

                //write favorite product to memory(favored_prods,position);

                //Add favorite to memory when it's clicked
                if(favored_prods.getIsFavorite()) {
                    InternalStorageControl.WriteFavProduct(getContext(), favored_prods, position, FILENAME);
                }
                else{
                    try {
                        InternalStorageControl.DeleteDislikedProduct(getContext(),favored_prods.getId(),FILENAME);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                //Update viewAdapter
                productAdapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    private void CheckIfLikedProductIsAvailable(ArrayList<Product> listOfProductsFromMain) {
        ArrayList<Product> markFavs = new ArrayList<>();
        try {
            markFavs = InternalStorageControl.ReadFavProduct(getContext(), FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (Product p : listOfProductsFromMain){
            for(Product k: markFavs){
                if(p.getImageURL().equals(k.getImageURL())){
                    p.toggleFavorite();
                  //  Log.d("..FROM FRAGMENt..",k.getDescription() + " : "+ p.getDescription());
                }
            }
        }
    }
    //end on click

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

