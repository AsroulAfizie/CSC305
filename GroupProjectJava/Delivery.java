public abstract class Delivery {
    private String name;
    private String phoneNum;
    private String address;
    private String deliveryType;

    public Delivery(){
       name = null;
        phoneNum = null;
        address = null;
        deliveryType = null; 
    }

    public Delivery(String n, String p, String a, String d){
        name = n;
        phoneNum = p;
        address = a;
        deliveryType = d;
    }

    public double deliveryFee(){
        double deliveryFee = 0;

        if(deliveryType.equalsIgnoreCase("Rush")){
            deliveryFee = 10;
        }
        else{
            deliveryFee = 5;
        }
        return deliveryFee;
    }

    public abstract double calcTotalPrice();

    public String toString(){
        return "Name : " + name + "\nPhone Number : " + phoneNum + "\nAddress : " + address + "\nDeliveryType : " + deliveryType + "\n";
    }
}
