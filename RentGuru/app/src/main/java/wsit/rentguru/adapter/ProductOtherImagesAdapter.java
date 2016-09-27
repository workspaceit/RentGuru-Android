package wsit.rentguru.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.model.Picture;
import wsit.rentguru.utility.Utility;

/**
 * Created by workspaceinfotech on 8/11/16.
 */
public class ProductOtherImagesAdapter extends RecyclerView.Adapter<ProductOtherImagesAdapter.ViewHolder> {


    private ArrayList<Picture> mDataset;
    private ImageLoader imageLoader;



    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView mImageView;
        public ViewHolder(View v) {
            super(v);
            this.mImageView = (ImageView) v.findViewById(R.id.other_image);
        }


    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductOtherImagesAdapter(ArrayList<Picture> myDataset,Context context) {

        mDataset = myDataset;
        this.imageLoader= ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductOtherImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_other_images, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.setLayoutParams(new RecyclerView.LayoutParams(300, 200));
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.mImageView.setText(mDataset[position]);
        System.out.println("ImageUrl: " + Utility.picUrl+mDataset.get(position).getOriginal().getPath());
        this.imageLoader.displayImage(Utility.picUrl+ mDataset.get(position).getOriginal().getPath(),holder.mImageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }







}
