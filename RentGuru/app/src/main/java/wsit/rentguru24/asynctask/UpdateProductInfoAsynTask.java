package wsit.rentguru24.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import wsit.rentguru24.Service.MyProductService;
import wsit.rentguru24.fragment.EditProductInfoFragment;
import wsit.rentguru24.model.MyRentalProduct;

/**
 * Created by Tomal on 11/14/2016.
 */

public class UpdateProductInfoAsynTask extends AsyncTask<String,String,MyRentalProduct> {
    private EditProductInfoFragment editProductInfoFragment;
    private ProgressDialog progressDialog;
    private String name,description,currentValue,rentFee,rentTypeId,availableFrom,availableTill;

    private int productId;

    private int[]categoryIds;
    private String formattedAddress,zip,city,lat,lng,stateId;

    public UpdateProductInfoAsynTask(EditProductInfoFragment editProductInfoFragment,int productId,int[]categoryIds,
                                     String name,String description,String currentValue,String rentFee,
                                     String rentTypeId,String availableFrom,String availableTill,String formattedAddress,
                                     String zip,String city,String lat,String lng,String stateId){
        this.editProductInfoFragment=editProductInfoFragment;
        this.productId=productId;
        this.categoryIds=categoryIds;
        this.name=name;
        this.description=description;
        this.currentValue=currentValue;
        this.rentFee=rentFee;
        this.rentTypeId=rentTypeId;
        this.availableFrom=availableFrom;
        this.availableTill=availableTill;
        this.formattedAddress=formattedAddress;
        this.zip=zip;
        this.city=city;
        this.lat=lat;
        this.lng=lng;
        this.stateId=stateId;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(editProductInfoFragment.getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Updating Product Info...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    @Override
    protected MyRentalProduct doInBackground(String... params) {

        return new MyProductService().updateProductInfo(productId,name,description,currentValue,rentFee,rentTypeId,availableFrom,
                availableTill,formattedAddress,zip,city,lat,lng,categoryIds,stateId);
    }

    @Override
    protected void onPostExecute(MyRentalProduct myRentalProduct) {
        super.onPostExecute(myRentalProduct);
        progressDialog.dismiss();
        editProductInfoFragment.completeProductUpdate(myRentalProduct);

    }

}
