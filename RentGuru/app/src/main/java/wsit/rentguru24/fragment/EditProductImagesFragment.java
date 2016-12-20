package wsit.rentguru24.fragment;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import wsit.rentguru24.R;
import wsit.rentguru24.activity.EditProductActivity;
import wsit.rentguru24.adapter.OtherImagesEditGridAdapter;
import wsit.rentguru24.asynctask.DeleteOtherImageAsynTask;
import wsit.rentguru24.asynctask.PostProductImageAsyncTask;
import wsit.rentguru24.asynctask.UpdateProductOtherImageAsynTask;
import wsit.rentguru24.asynctask.UpdateProductProfileImageAsynTask;
import wsit.rentguru24.model.MyRentalProduct;
import wsit.rentguru24.utility.ConnectivityManagerInfo;
import wsit.rentguru24.utility.ShowNotification;
import wsit.rentguru24.utility.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProductImagesFragment extends Fragment implements View.OnClickListener {
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;
    private ImageView productProfileImage;
    private OtherImagesEditGridAdapter otherImagesEditGridAdapter;
    private GridView otherImageGriView;
    private int imagePosition;
    private View view;
    private Button changeProfileImageButton,addNewImageButton;
    private static final int FILE_SELECT_CODE = 1;
    private int chooseImageFlag;
    private ArrayList<String> imagePathList;
    private ConnectivityManagerInfo connectivityManagerInfo;


    public EditProductImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      this.view = inflater.inflate(R.layout.fragment_edit_product_images, container, false);


        initialization(view);

        return this.view;
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
        System.out.println(EditProductActivity.myRentalProduct.getOtherImages().size()+" image size");
        otherImageGriView.setAdapter(otherImagesEditGridAdapter);

        System.out.println(EditProductActivity.myRentalProduct.getOtherImages().size()+" size");

        changeProfileImageButton =(Button)view.findViewById(R.id.change_product_profile_image);
        changeProfileImageButton.setOnClickListener(this);

        chooseImageFlag=-1;
        imagePathList=new ArrayList<>();
        connectivityManagerInfo=new ConnectivityManagerInfo(getActivity());

        addNewImageButton=(Button)view.findViewById(R.id.add_new_other_image);
        addNewImageButton.setOnClickListener(this);




    }

    public void deleteCallFromAdapter(int position){
        imagePosition=position;
        new DeleteOtherImageAsynTask(this).execute(String.valueOf(EditProductActivity.myRentalProduct.getId()),
                String.valueOf(EditProductActivity.myRentalProduct.getOtherImages()
                .get(position).getOriginal().getPath()));

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


    @Override
    public void onClick(View v) {
        if (v==changeProfileImageButton){
            showFileChooser();
            chooseImageFlag=1;

        }else if (v==addNewImageButton){

            if (EditProductActivity.myRentalProduct.getOtherImages().size()<=4) {
                showFileChooser();
                chooseImageFlag = 2;
            }else {
                ShowNotification.makeToast(getActivity(),"You have reached maximum Limit for uploading images");
            }
        }
    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(view.getContext(), "Please install a File Manager.",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("uri", "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    path = Utility.getPath(getContext(), uri);
                    imagePathList.clear();
                    imagePathList.add(path);

                    if (connectivityManagerInfo.isConnectedToInternet()) {


                        new PostProductImageAsyncTask(this, imagePathList).execute();
                    }




                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void completeImageUpload(String response){
        System.out.println(response);
        if (!response.equals("")) {
            if (connectivityManagerInfo.isConnectedToInternet()) {
                if (chooseImageFlag == 1) {
                    new UpdateProductProfileImageAsynTask(this, EditProductActivity.myRentalProduct.getId(), response).execute();
                }else if (chooseImageFlag==2){
                    new UpdateProductOtherImageAsynTask(this,EditProductActivity.myRentalProduct.getId(),Utility.temporaryArrayList).execute();
                }
            }

        }else {
            ShowNotification.showSnacksBarLong(getActivity(),otherImageGriView,"please Upload image less than 2 MB");
        }

    }

    public void updateProductComplete(MyRentalProduct myRentalProduct){
        if (myRentalProduct!=null){
            EditProductActivity.myRentalProduct=myRentalProduct;

            if (chooseImageFlag==1) {
                imageLoader.displayImage(Utility.picUrl + EditProductActivity.myRentalProduct.getProfileImage().getOriginal().getPath(),
                        this.productProfileImage, displayImageOptions);
            }else if (chooseImageFlag==2){

                System.out.println(EditProductActivity.myRentalProduct.getOtherImages().size()+" size");
                ShowNotification.showSnacksBarLong(getActivity(),view,"Image uploaded Successfully");
                EditProductActivity.myRentalProduct=myRentalProduct;
                otherImagesEditGridAdapter.setImages(EditProductActivity.myRentalProduct.getOtherImages());
                otherImagesEditGridAdapter.notifyDataSetChanged();
            }

        }else {
            ShowNotification.showSnacksBarLong(getActivity(),otherImageGriView,"Network Error");
        }
    }
}
