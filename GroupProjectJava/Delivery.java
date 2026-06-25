public class Delivery extends SeafoodOrder {
    private String name;
    private String phoneNum;
    private String address;
    private String deliveryType;
    private final double DELIVERYFEE = 5.00;

    public Delivery(String n, String phone, String a, String d){
        name = n;
        phoneNum = phone;
        address = a;
        deliveryType = d;
    }

    
}
