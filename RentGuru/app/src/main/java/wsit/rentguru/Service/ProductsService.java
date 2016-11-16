package wsit.rentguru.Service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import wsit.rentguru.model.BannerImage;
import wsit.rentguru.model.MyRentalProduct;
import wsit.rentguru.model.RentRequest;
import wsit.rentguru.model.RentalProduct;
import wsit.rentguru.model.ResponseStat;
import wsit.rentguru.utility.Utility;

/**
 * Created by workspaceinfotech on 8/10/16.
 */
public class ProductsService extends ApiManager {


    private ResponseStat responseStat;

    public ProductsService() {
        this.responseStat = new ResponseStat();

    }


    public ArrayList<RentalProduct> getProductCategoryWise(int categoryId, int limit, int offset) {
        ArrayList<RentalProduct> rentalProductArrayList = new ArrayList<>();
        this.responseStat = new ResponseStat();
        this.setController("/product/get-product-by-category?categoryId=" + categoryId + "&limit=" + limit + "&offset=" + offset + "");

        String resp = this.getData("GET");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {

                RentalProduct[] rentalProducts = gson.fromJson(jsonObject.get("responseData"), RentalProduct[].class);
                Collections.addAll(rentalProductArrayList, rentalProducts);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return rentalProductArrayList;
    }

    public ArrayList<RentalProduct> getProductList() {
        ArrayList<RentalProduct> rentalProductArrayList = new ArrayList<RentalProduct>();
        this.responseStat = new ResponseStat();
        this.setController("product/get-product?limit=6&offset=" + Utility.offset);
        String resp = this.getData("GET");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                Utility.offset = Utility.offset + 1;
                RentalProduct[] rentalProducts = gson.fromJson(jsonObject.get("responseData"), RentalProduct[].class);
                Utility.productCount = rentalProducts.length;
                Utility.indicator = false;
                Log.d("product count:", String.valueOf(Utility.productCount));
                for (RentalProduct rentalProduct : rentalProducts) {
                    rentalProductArrayList.add(rentalProduct);
                }

            } else {

                Utility.indicator = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rentalProductArrayList;
    }


    public ResponseStat rentProduct(RentRequest requestRentNow) {

        this.responseStat = new ResponseStat();
        this.setParams("startDate", requestRentNow.getStartDate());
        this.setParams("endsDate", requestRentNow.getEndDate());
        this.setParams("remark", requestRentNow.getRemark());
        this.setController("auth/rent/make-request/" + requestRentNow.getRentalProduct().getId());
        String resp = this.getData("POST");
        Log.d("resp", resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                RentRequest rentRequest = new RentRequest();
                rentRequest = gson.fromJson(jsonObject.get("responseData"), rentRequest.getClass());
                Utility.requestedItemId = rentRequest.getId();
            } else {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return responseStat;

    }

    public boolean getBannerImages() {

        this.responseStat = new ResponseStat();
        this.setController("banner-image/get-all");
        String resp = this.getData("GET");
        System.out.println(resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                BannerImage[] bannerImages = gson.fromJson(jsonObject.get("responseData"), BannerImage[].class);
                Collections.addAll(Utility.bannerImages, bannerImages);

                return true;
            } else {

                return false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;

    }

    public ArrayList<RentalProduct> getSearchProductList(String queryString) {
        ArrayList<RentalProduct> rentalProductArrayList = new ArrayList<>();
        this.responseStat = new ResponseStat();


        String controller = "search/rental-product?" + queryString;
        System.out.println(controller);
        this.setController(controller);
        String resp = this.getData("GET");
        System.out.println(resp);
        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {

                RentalProduct[] rentalProducts = gson.fromJson(jsonObject.get("responseData"), RentalProduct[].class);
                Collections.addAll(rentalProductArrayList, rentalProducts);

            } else {
                rentalProductArrayList = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rentalProductArrayList;

    }

    public ArrayList<RentRequest> getRequestedProductsList(int offset) {
        ArrayList<RentRequest> rentalProductArrayList = new ArrayList<RentRequest>();
        this.responseStat = new ResponseStat();
        this.setParams("limit", "6");
        this.setParams("offset", String.valueOf(offset));
        this.setController("auth/rent/get-my-pending-rent-request");
        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                Utility.offset = Utility.offset + 1;
                RentRequest[] rentalProducts = gson.fromJson(jsonObject.get("responseData"), RentRequest[].class);
                Utility.productCount = rentalProducts.length;
                Utility.indicator = false;
                Log.d("product count:", String.valueOf(Utility.productCount));
                for (RentRequest rentRequest : rentalProducts) {

                    rentalProductArrayList.add(rentRequest);
                }

            } else {

                Utility.indicator = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rentalProductArrayList;

    }


    public ArrayList<RentRequest> getRequestedApprovedProductList(int offset) {

        ArrayList<RentRequest> rentRequestArrayList = new ArrayList<RentRequest>();
        this.responseStat = new ResponseStat();
        this.setParams("limit", "6");
        this.setParams("offset", String.valueOf(offset));
        this.setController("auth/rent/get-my-approved-rent-request");
        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                RentRequest[] rentRequests = gson.fromJson(jsonObject.get("responseData"), RentRequest[].class);

                Log.d("product count:", String.valueOf(Utility.productCount));

                for (RentRequest rentRequest : rentRequests) {
                    if (rentRequest.getApprove() == true && rentRequest.getDisapprove() == false)
                        rentRequestArrayList.add(rentRequest);
                }

            } else {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rentRequestArrayList;


    }


    public ArrayList<RentRequest> getRequestedDisapprovedProductList(int offset) {

        ArrayList<RentRequest> rentRequestArrayList = new ArrayList<RentRequest>();
        this.responseStat = new ResponseStat();
        this.setParams("limit", "6");
        this.setParams("offset", String.valueOf(offset));
        this.setController("auth/rent/get-my-disapproved-rent-request");
        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                RentRequest[] rentRequests = gson.fromJson(jsonObject.get("responseData"), RentRequest[].class);

                Log.d("product count:", String.valueOf(Utility.productCount));

                for (RentRequest rentRequest : rentRequests) {
                    if (rentRequest.getApprove() == false && rentRequest.getDisapprove() == true)
                        rentRequestArrayList.add(rentRequest);
                }

            } else {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rentRequestArrayList;


    }


    public ArrayList<MyRentalProduct> getUploadedProductList(int offset) {

        ArrayList<MyRentalProduct> rentalProductArrayList = new ArrayList<MyRentalProduct>();
        this.responseStat = new ResponseStat();
        this.setParams("limit", "6");
        this.setParams("offset", String.valueOf(offset));
        this.setController("auth/product/get-my-rental-product");
        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                MyRentalProduct[] rentalProducts = gson.fromJson(jsonObject.get("responseData"), MyRentalProduct[].class);

                Log.d("product count:", String.valueOf(Utility.productCount));

                for (MyRentalProduct rentalProduct : rentalProducts) {
                    rentalProductArrayList.add(rentalProduct);
                }

            } else {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rentalProductArrayList;


    }

    public ArrayList<RentRequest> getListForApproval(int offset) {

        ArrayList<RentRequest> rentRequestArrayList = new ArrayList<RentRequest>();
        this.responseStat = new ResponseStat();
        this.setParams("limit", "6");
        this.setParams("offset", String.valueOf(offset));
        this.setController("auth/rent/get-my-pending-product-rent-request");
        String resp = this.getData("POST");
        Log.d("pending", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                RentRequest[] rentRequests = gson.fromJson(jsonObject.get("responseData"), RentRequest[].class);

                Log.d("product count:", String.valueOf(Utility.productCount));

                for (RentRequest rentRequest : rentRequests) {
                    if (rentRequest.getApprove() == false && rentRequest.getDisapprove() == false)
                        rentRequestArrayList.add(rentRequest);
                }

            } else {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rentRequestArrayList;


    }


    public ArrayList<RentRequest> getApprovedProductList(int offset) {

        ArrayList<RentRequest> rentRequestArrayList = new ArrayList<RentRequest>();
        this.responseStat = new ResponseStat();
        this.setParams("limit", "6");
        this.setParams("offset", String.valueOf(offset));
        this.setController("auth/rent/get-my-approved-product-rent-request");
        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                RentRequest[] rentRequests = gson.fromJson(jsonObject.get("responseData"), RentRequest[].class);

                Log.d("product count:", String.valueOf(Utility.productCount));

                for (RentRequest rentRequest : rentRequests) {
                    if (rentRequest.getApprove() == true && rentRequest.getDisapprove() == false)
                        rentRequestArrayList.add(rentRequest);
                }

            } else {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rentRequestArrayList;


    }


    public ArrayList<RentRequest> getDisapprovedProductList(int offset) {

        ArrayList<RentRequest> rentRequestArrayList = new ArrayList<RentRequest>();
        this.responseStat = new ResponseStat();
        this.setParams("limit", "6");
        this.setParams("offset", String.valueOf(offset));
        this.setController("auth/rent/get-my-disapproved-product-rent-request");
        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                RentRequest[] rentRequests = gson.fromJson(jsonObject.get("responseData"), RentRequest[].class);

                Log.d("product count:", String.valueOf(Utility.productCount));

                for (RentRequest rentRequest : rentRequests) {
                    if (rentRequest.getApprove() == false && rentRequest.getDisapprove() == true)
                        rentRequestArrayList.add(rentRequest);
                }

            } else {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rentRequestArrayList;


    }


    public ResponseStat getConfirmation(int id) {

        this.responseStat = new ResponseStat();


        this.setController("auth/rent/approve-request/" + id);


        String resp = this.getData("GET");
        Log.d("resp", resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


        } catch (Exception e) {
            e.printStackTrace();

        }


        return responseStat;
    }


    public boolean getRequestCancalation(int id) {
        boolean response = false;

        this.responseStat = new ResponseStat();


        this.setController("auth/rent/cancel-request/" + id);


        String resp = this.getData("GET");
        Log.d("resp", resp);

        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


            if (this.responseStat.isStatus()) {
                response = true;

            } else {

                response = false;

            }
        } catch (Exception e) {
            e.printStackTrace();

        }


        return response;
    }


    public ResponseStat getPaypalPaymentResponse(String transactionId, int rentRequestId) {
        this.responseStat = new ResponseStat();

        this.setParams("paymentId", String.valueOf(transactionId));
        this.setController("auth/rent-payment/verify-payment/" + rentRequestId);
        String resp = this.getData("POST");
        Log.d("resp", resp);


        try {
            JsonObject jsonObject = new JsonParser().parse(resp).getAsJsonObject();
            Gson gson = new Gson();

            this.responseStat = gson.fromJson(jsonObject.get("responseStat"), responseStat.getClass());


        } catch (Exception e) {
            e.printStackTrace();
        }


        return responseStat;
    }


}
