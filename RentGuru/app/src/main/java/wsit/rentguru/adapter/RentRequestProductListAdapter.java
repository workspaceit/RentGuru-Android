package wsit.rentguru.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import wsit.rentguru.R;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.utility.Utility;

/**
 * Created by workspaceinfotech on 8/24/16.
 */
public class RentRequestProductListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RentRequest> rentRequestArrayList;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;


    public RentRequestProductListAdapter(Context context, ArrayList<RentRequest> rentRequestArrayList)
    {
        this.context = context;
        this.rentRequestArrayList=new ArrayList<>();
        this.rentRequestArrayList.addAll(rentRequestArrayList);
        this.inflater = LayoutInflater.from(this.context);

        this.imageLoader= ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(context.getApplicationContext()));

        displayImageOptions=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

    }

    public void setArray(ArrayList<RentRequest> rentRequests){
        rentRequestArrayList.clear();
        rentRequestArrayList.addAll(rentRequests);

    }

    public void reserArray(){
        this.rentRequestArrayList.clear();
    }


    private static class ViewHolder {

        ImageView productImage;
        Button edit;
        TextView productName;
        TextView categoryName;
        TextView timeFrame;

    }

    @Override
    public int getCount() {

        return this.rentRequestArrayList.size();

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

        ViewHolder viewHolder = null;

        if(convertView == null) {

            convertView = inflater.inflate(R.layout.uploadedlist_row_item,null);

            viewHolder = new ViewHolder();
            viewHolder.productImage = (ImageView)convertView.findViewById(R.id.product_image);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.product_title);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.product_category);
            viewHolder.timeFrame = (TextView) convertView.findViewById(R.id.product_time_frame);
            viewHolder.edit = (Button) convertView.findViewById(R.id.edit_button);

            convertView.setTag(viewHolder);


        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();

        }

        System.out.println("imageurl: " + rentRequestArrayList.get(position).getRentalProduct().getProfileImage().getOriginal().getPath());
        this.imageLoader.displayImage(Utility.picUrl+rentRequestArrayList.get(position).getRentalProduct().getProfileImage().getOriginal().getPath(), viewHolder.productImage,displayImageOptions);
        viewHolder.productName.setText(rentRequestArrayList.get(position).getRentalProduct().getName());
        viewHolder.categoryName.setText(rentRequestArrayList.get(position).getRentalProduct().getProductCategories().get(0).getCategory().getName());
        viewHolder.timeFrame.setText(getDate(rentRequestArrayList.get(position).getRentalProduct().getAvailableFrom())+" - "+ getDate(rentRequestArrayList.get(position).getRentalProduct().getAvailableTill()));

        return convertView;
    }


    private String getDate(String timeStamp)
    {
        String date = "";
        long time = Long.valueOf(timeStamp).longValue();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        date = DateFormat.format("MMM dd yyyy", cal).toString();

        return date;
    }
}