package wsit.rentguru.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.twitter.sdk.android.core.internal.TwitterApiConstants;

import wsit.rentguru.R;
import wsit.rentguru.activity.SearchActivity;
import wsit.rentguru.utility.Utility;

/**
 * Created by Tomal on 10/27/2016.
 */

public class SearchProductGridViewAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Activity context;
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;

    public SearchProductGridViewAdapter(Activity context){
        this.context=context;
        this.layoutInflater=context.getLayoutInflater();
        this.imageLoader= ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        displayImageOptions=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();


    }

    @Override
    public int getCount() {
        return SearchActivity.rentalSearchProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return SearchActivity.rentalSearchProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return SearchActivity.rentalSearchProducts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_view_single_item,null);
            viewHolder = new ViewHolder();

            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.search_product_image_gv);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.search_title_gv);
            viewHolder.priceView = (TextView) convertView.findViewById(R.id.search_price_gv);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        this.imageLoader.displayImage(Utility.picUrl+SearchActivity.rentalSearchProducts.get(position).getProfileImage().getOriginal().getPath(),
                viewHolder.productImage,displayImageOptions);
        String upperString = SearchActivity.rentalSearchProducts.get(position).getName().substring(0,1).toUpperCase() + SearchActivity.rentalSearchProducts.get(position).getName().substring(1);
        viewHolder.productName.setText(upperString);
        viewHolder.priceView.setText(Utility.CURRENCY+" "+SearchActivity.rentalSearchProducts.get(position).getRentFee()+"/"+
        SearchActivity.rentalSearchProducts.get(position).getRentType().getName());

        return convertView;
    }




    private class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;


    }
}
