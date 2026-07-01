import java.util.ArrayList;

public class SeafoodOrder extends Delivery {
    private ArrayList<String> seafoodName = new ArrayList<>();
    private ArrayList<Integer> seafoodQuantity = new ArrayList<>();
    
    public SeafoodOrder(){
        super();
        seafoodName = null;
        seafoodQuantity = null;
    }

    public SeafoodOrder(String n, String phone, String a, String d, ArrayList<String> sN, ArrayList<Integer> qtty){
        super(n, phone, a, d);
        seafoodName = new ArrayList<>(sN);
        seafoodQuantity= new ArrayList<>(qtty);
    }

    public ArrayList<String> getSeafoodName() {
        return seafoodName;
    }

    public void setSeafoodName(ArrayList<String> seafoodName) {
        this.seafoodName = seafoodName;
    }

    public ArrayList<Integer> getSeafoodQuantity() {
        return seafoodQuantity;
    }

    public void setSeafoodQuantity(ArrayList<Integer> seafoodQuantity) {
        this.seafoodQuantity = seafoodQuantity;
    }
    
    @Override
    public double calcTotalPrice(){
        double totalPrice = 0;
        for(int i = 0; i < seafoodName.size(); i++){
            String seafood = seafoodName.get(i);
            int qtty = seafoodQuantity.get(i);

            if(seafood.equalsIgnoreCase("Tiger Shrimp")){
                totalPrice += qtty * 200;
            }
            else if(seafood.equalsIgnoreCase("White Shrimp")){
                totalPrice += qtty * 125;
            }
            else if(seafood.equalsIgnoreCase("Royal Red Shrimp")){
                totalPrice += qtty * 175;
            }
            else if(seafood.equalsIgnoreCase("Blue Pincer Crab")){
                totalPrice += qtty * 150;
            }
            else if(seafood.equalsIgnoreCase("King Crab")){
                totalPrice += qtty * 990;
            }
        }

        totalPrice += super.deliveryFee();
        return totalPrice;
    }

    @Override
    public String toString(){
        StringBuilder data = new StringBuilder();

        for(int i = 0; i < seafoodName.size(); i++){
            data.append(seafoodName.get(i)).append(" : ").append(seafoodQuantity.get(i)).append("\n");
        }

        return super.toString() + data + "Total Price: RM" + String.format("%.2f", calcTotalPrice());
    }

}
