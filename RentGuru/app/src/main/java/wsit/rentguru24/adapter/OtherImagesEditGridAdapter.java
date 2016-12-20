package wsit.rentguru24.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import wsit.rentguru24.R;
import wsit.rentguru24.activity.EditProductActivity;
import wsit.rentguru24.fragment.EditProductImagesFragment;
import wsit.rentguru24.model.Picture;
import wsit.rentguru24.utility.Utility;

/**
 * Created by Tomal on 11/11/2016.
 */

public class OtherImagesEditGridAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private EditProductImagesFragment context;
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;
    private ArrayList<Picture>pictures;


    public OtherImagesEditGridAdapter(EditProductImagesFragment editProductImagesFragment,ArrayList<Picture> pictures){
        this.context=editProductImagesFragment;
        layoutInflater=context.getActivity().getLayoutInflater();
        this.pictures=new ArrayList<>();
        this.pictures.addAll(pictures);
        this.imageLoader=ImageLoader.getInstance();
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(context.getActivity()));
        displayImageOptions=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

    }

    public void setImages(ArrayList<Picture> pictures){
        this.pictures.clear();
        this.pictures.addAll(pictures);

    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public Object getItem(int position) {
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.other_image_grid_single_item,null);
            viewHolder = new ViewHolder();

            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.image_view);
            viewHolder.deleteButton = (Button) convertView.findViewById(R.id.delete_button);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        this.imageLoader.displayImage(Utility.picUrl+pictures.get(position).getOriginal().getPath(),viewHolder.productImage,
                displayImageOptions);

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(pictures.get(position).getOriginal().getPath()+" "+EditProductActivity.myRentalProduct.getId());
                context.deleteCallFromAdapter(position);
            }
        });


        return convertView;
}


    private class ViewHolder {

        public ImageView productImage;
        public Button deleteButton;



    }
}
