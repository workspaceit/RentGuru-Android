package wsit.rentguru24.model;

/**
 * Created by workspaceinfotech on 9/26/16.
 */
public class RentPayment {

    private long id;
    private AppCredential appCredential;
    private RentRequest rentRequest;
    private RentInf rentInf;
    private double rentFee;
    private double refundAmount;
    private double totalAmount;
    private double transactionFee;
    private String currency;
    private String paypalPayerId;
    private String paypalPayId;
    private String paypalSaleId;
    private String authorizationId;
    private String paypalPaymentDate;
    private String createdDate;



    public RentPayment()
    {
        this.id = 0;
        this.appCredential = new AppCredential();
        this.refundAmount = 0.0;
        this.rentRequest = new RentRequest();
        this.rentInf = new RentInf();
        this.rentFee = 0.0;
        this.totalAmount = 0.0;
        this.transactionFee = 0.0;
        this.currency = "";
        this.paypalPayerId = "";
        this.paypalPayId = "";
        this.paypalSaleId = "";
        this.authorizationId = "";
        this.paypalPaymentDate = "";
        this.createdDate = "";

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppCredential getAppCredential() {
        return appCredential;
    }

    public void setAppCredential(AppCredential appCredential) {
        this.appCredential = appCredential;
    }

    public RentRequest getRentRequest() {
        return rentRequest;
    }

    public void setRentRequest(RentRequest rentRequest) {
        this.rentRequest = rentRequest;
    }

    public RentInf getRentInf() {
        return rentInf;
    }

    public void setRentInf(RentInf rentInf) {
        this.rentInf = rentInf;
    }

    public double getRentFee() {
        return rentFee;
    }

    public void setRentFee(double rentFee) {
        this.rentFee = rentFee;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaypalPayerId() {
        return paypalPayerId;
    }

    public void setPaypalPayerId(String paypalPayerId) {
        this.paypalPayerId = paypalPayerId;
    }

    public String getPaypalPayId() {
        return paypalPayId;
    }

    public void setPaypalPayId(String paypalPayId) {
        this.paypalPayId = paypalPayId;
    }

    public String getPaypalSaleId() {
        return paypalSaleId;
    }

    public void setPaypalSaleId(String paypalSaleId) {
        this.paypalSaleId = paypalSaleId;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getPaypalPaymentDate() {
        return paypalPaymentDate;
    }

    public void setPaypalPaymentDate(String paypalPaymentDate) {
        this.paypalPaymentDate = paypalPaymentDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
