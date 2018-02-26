package com.example.michaelmsimon.finnchallengeone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michaelmsimon.finnchallengeone.Model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Michael M. Simon on 2/13/2018.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Product> productList;

    private class ViewHolder {

        TextView description;
        TextView location;
        TextView price;
        private ImageView imageView;
        private ImageView imageViewFavorite;

        private ViewHolder(TextView description, TextView location, TextView price, ImageView imageView, ImageView imageViewFavorite) {
            this.description = description;
            this.location = location;
            this.price = price;
            this.imageView = imageView;
            this.imageViewFavorite = imageViewFavorite;
        }
    }

    public GridViewAdapter(Context context, ArrayList<Product> products) {

        this.mContext = context;
        this.productList = products;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Product products = productList.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.product_view_grid, parent, false);

            final TextView description = (TextView) convertView.findViewById(R.id.product_description);
            final TextView location = (TextView) convertView.findViewById(R.id.product_location);
            final TextView price = (TextView) convertView.findViewById(R.id.product_price);
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview_cover_art);
            final ImageView imageViewFavorite = (ImageView) convertView.findViewById(R.id.imageview_favorite);

            viewHolder = new ViewHolder(description, location, price, imageView, imageViewFavorite);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.description.setText(productList.get(position).getDescription());
        viewHolder.location.setText(productList.get(position).getLocation());
        viewHolder.price.setText(productList.get(position).getPrice());
        Picasso.with(mContext).load(productList.get(position).getImageURL()).into(viewHolder.imageView);

        //Favourite button toggle
        viewHolder.imageViewFavorite.setImageResource(
                products.getIsFavorite() ? R.drawable.ic_favorite_animator_24dp : R.drawable.ic_favorite_border_black_24dp);

        return convertView;
    }
}
