package wsit.rentguru.adapter;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.etsy.android.grid.util.DynamicHeightImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import wsit.rentguru.R;
import wsit.rentguru.utility.Utility;

/**
 * Created by workspaceinfotech on 7/28/16.
 */

public class StaggeredAdapter extends ArrayAdapter<String> {


    private static final String TAG = "StaggeredAdapter";
    private ImageLoader imageLoader;
    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private DisplayImageOptions displayImageOptions;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();


    public StaggeredAdapter(Context context, int textViewResourceId, ArrayList<String> productsList) {

        super(context, textViewResourceId, productsList);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRandom = new Random();
        this.imageLoader= ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(context.getApplicationContext()));

        displayImageOptions=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();


    }

    @Override
    public View getView(final int position, View convertView,
                        final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.row_staggered_layout,
                    parent, false);
            vh = new ViewHolder();
            vh.imgView = (DynamicHeightImageView) convertView
                    .findViewById(R.id.imgView);
            vh.titleTextView=(TextView)convertView.findViewById(R.id.title_text_view);
            vh.priceTextView=(TextView)convertView.findViewById(R.id.price_text_view);


            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);

        vh.imgView.setHeightRatio(positionHeight);

        this.imageLoader.displayImage(getItem(position), vh.imgView,displayImageOptions);
        vh.titleTextView.setText(Utility.rentalProductArrayList.get(position).getName());
        vh.priceTextView.setText(Utility.rentalProductArrayList.get(position).getRentFee()+"");

        return convertView;
    }

    static class ViewHolder {
        DynamicHeightImageView imgView;
        TextView titleTextView;
        TextView priceTextView;

    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
        // the width
    }






}
