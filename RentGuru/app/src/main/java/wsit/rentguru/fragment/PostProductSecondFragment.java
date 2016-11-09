package wsit.rentguru.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import wsit.rentguru.R;
import wsit.rentguru.activity.PostProductActivity;
import wsit.rentguru.adapter.GridviewAdapter;
import wsit.rentguru.asynctask.PostProductImageAsyncTask;
import wsit.rentguru.model.ImageItem;
import wsit.rentguru.model.PostProduct;
import wsit.rentguru.utility.ConnectivityManagerInfo;
import wsit.rentguru.utility.ShowNotification;
import wsit.rentguru.utility.Utility;


public class PostProductSecondFragment extends Fragment implements View.OnClickListener {

    private GridView gridView;
    private GridviewAdapter gridAdapter;
    private View view;
    private static final int FILE_SELECT_CODE = 0;
    private Button addImage,next;
    private ConnectivityManagerInfo connectivityManagerInfo;
    private ArrayList<ImageItem> imageItems;
    private LinearLayout imageField;
    private boolean maxLimit;
    private ArrayList<String> imagePathList;
    private PostProduct postProduct;
    private EditText productDescription;

    private void initiate(View view)
    {
        this.connectivityManagerInfo = new ConnectivityManagerInfo(getContext());
        imageItems = new ArrayList<ImageItem>();
        this.addImage = (Button)view.findViewById(R.id.addImage);
        this.addImage.setOnClickListener(this);
        gridView = (GridView) view.findViewById(R.id.gridView);

        this.imagePathList = new ArrayList<String>();

        this.imageField = (LinearLayout)view.findViewById(R.id.empty_image_field);
        this.next = (Button)view.findViewById(R.id.tab2_next);
        this.next.setOnClickListener(this);

        this.productDescription = (EditText)view.findViewById(R.id.product_description);
        this.postProduct = new PostProduct();


    }

    public PostProductSecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_second, container, false);

        initiate(view);

        return view;
    }


    private void getData(Bitmap bitmap) {

        ImageItem imageItem = new ImageItem(bitmap);
        imageItems.add(imageItem);

        this.imageField.setVisibility(View.GONE);
        this.gridView.setVisibility(View.VISIBLE);

        if(imageItems.size()== 5)
        {
            maxLimit = true;

        }
        else
        {
            int value = 5-imageItems.size();
            String text = "You can upload "+String.valueOf(value)+" more";
            maxLimit = false;
            Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {

        if(v == addImage)
        {
            if(maxLimit == false)
            showFileChooser();
            else
            {
                String text = "You reached your limit";
                Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == next)
        {

            if(this.productDescription.getText().length() == 0) {

                Toast.makeText(getContext(),"Product Description is required",Toast.LENGTH_SHORT).show();
            }
            else if(imagePathList.size() == 0)
            {
                Toast.makeText(getContext(),"Please Upload a product Image",Toast.LENGTH_SHORT).show();

            }
            else if (imagePathList.size()>0)
            {
                PostProductActivity.postProduct.setDescription(this.productDescription.getText().toString());
                if (connectivityManagerInfo.isConnectedToInternet()) {

                        new PostProductImageAsyncTask(this, imagePathList).execute();

                } else {
                    Toast.makeText(view.getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }



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
                    Log.d("path", "File Path: " + path);
                    // Get the file instance
                    //File file = new File(path);
                    // Initiate the upload

                    imagePathList.add(path);

                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    options.inSampleSize = 1;
                    options.inJustDecodeBounds = false;
                    options.inTempStorage = new byte[16 * 1024];

                    Bitmap bmp = BitmapFactory.decodeFile(path,options);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bmp, 100, 100, false);

                    int bitmapByteCount= BitmapCompat.getAllocationByteCount(resizedBitmap);
                    System.out.println("resized image: " + bitmapByteCount);

                    getData(resizedBitmap);

                    gridAdapter = new GridviewAdapter(view.getContext(), R.layout.post_product_grid_item, this.imageItems);
                    gridView.setAdapter(gridAdapter);


                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }





    public void nextTab(String response)
    {
        if (Utility.temporaryArrayList.size()==0){
            ShowNotification.showToast(getActivity(),"Your Image Didn't uploaded. Please try again");
            return;
        }
        PostProductActivity.viewPager.setCurrentItem(2);

    }


}
