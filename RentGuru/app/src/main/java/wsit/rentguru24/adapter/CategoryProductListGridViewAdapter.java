package wsit.rentguru24.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import wsit.rentguru24.R;
import wsit.rentguru24.activity.CategoryProductListViewActivity;
import wsit.rentguru24.utility.Utility;

/**
 * Created by Tomal on 11/8/2016.
 */

public class CategoryProductListGridViewAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Activity activity;
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;

    public CategoryProductListGridViewAdapter(Activity activity){
        this.activity=activity;
        this.layoutInflater=activity.getLayoutInflater();
        this.imageLoader= ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
        displayImageOptions=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public int getCount() {
        return CategoryProductListViewActivity.categoryRentalProdutcs.size();
    }

    @Override
    public Object getItem(int position) {
        return CategoryProductListViewActivity.categoryRentalProdutcs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return CategoryProductListViewActivity.categoryRentalProdutcs.get(position).getId();
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


        this.imageLoader.displayImage(Utility.picUrl+CategoryProductListViewActivity.categoryRentalProdutcs.get(position).getProfileImage().getOriginal().getPath(),
                viewHolder.productImage,displayImageOptions);
        String upperString = CategoryProductListViewActivity.categoryRentalProdutcs.get(position).getName().substring(0,1).toUpperCase() +CategoryProductListViewActivity.categoryRentalProdutcs.get(position).getName().substring(1);
        viewHolder.productName.setText(upperString);
        viewHolder.priceView.setText(Utility.CURRENCY+" "+CategoryProductListViewActivity.categoryRentalProdutcs.get(position).getRentFee());

        return convertView;
    }


    private class ViewHolder {

        public ImageView productImage;
        public TextView productName;
        public TextView priceView;


    }
}
