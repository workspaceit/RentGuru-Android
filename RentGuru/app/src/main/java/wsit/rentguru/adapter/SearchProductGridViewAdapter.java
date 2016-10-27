package wsit.rentguru.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    public SearchProductGridViewAdapter(Activity context){
        this.context=context;
        this.layoutInflater=context.getLayoutInflater();
        this.imageLoader= ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(context));

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
                viewHolder.productImage);
        viewHolder.productName.setText(SearchActivity.rentalSearchProducts.get(position).getName());
        viewHolder.priceView.setText(SearchActivity.rentalSearchProducts.get(position).getRentFee()+"");

        return convertView;
    }




    private class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;


    }
}
