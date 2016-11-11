package wsit.rentguru.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import wsit.rentguru.R;
import wsit.rentguru.activity.EditProductActivity;
import wsit.rentguru.activity.EditProfileActivity;
import wsit.rentguru.adapter.OtherImagesEditGridAdapter;
import wsit.rentguru.asynctask.DeleteOtherImageAsynTask;
import wsit.rentguru.utility.ShowNotification;
import wsit.rentguru.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProductImagesFragment extends Fragment {
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;
    private ImageView productProfileImage;
    private OtherImagesEditGridAdapter otherImagesEditGridAdapter;
    private GridView otherImageGriView;
    private int imagePosition;


    public EditProductImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_product_images, container, false);


        initialization(view);

        return view;
    }

    private void initialization(View view) {
        this.imageLoader = ImageLoader.getInstance();

        this.displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        productProfileImage=(ImageView)view.findViewById(R.id.product_profile_image);

        imageLoader.displayImage(Utility.picUrl+ EditProductActivity.myRentalProduct.getProfileImage().getOriginal().getPath(),
                productProfileImage,displayImageOptions);
        otherImageGriView=(GridView)view.findViewById(R.id.grid_view);

        otherImagesEditGridAdapter=new OtherImagesEditGridAdapter(this,EditProductActivity.myRentalProduct.getOtherImages());
        otherImageGriView.setAdapter(otherImagesEditGridAdapter);

        System.out.println(EditProductActivity.myRentalProduct.getOtherImages().size()+" size");





    }

    public void deleteCallFromAdapter(int position){
        imagePosition=position;
        new DeleteOtherImageAsynTask(this).execute(String.valueOf(EditProductActivity.myRentalProduct.getId()),
                String.valueOf(EditProductActivity.myRentalProduct.getOtherImages()
                .get(position).getOriginal()));

    }

    public void confirmDetele(boolean flag){
        if (flag){
            ShowNotification.showSnacksBarLong(getActivity(),otherImageGriView,"Image Deleted Successfully");
            EditProductActivity.myRentalProduct.getOtherImages().remove(imagePosition);
            otherImagesEditGridAdapter.setImages(EditProductActivity.myRentalProduct.getOtherImages());
            otherImagesEditGridAdapter.notifyDataSetChanged();

        }else {
            ShowNotification.showSnacksBarLong(getActivity(),otherImageGriView,"Something went wrong");
        }
    }


}
