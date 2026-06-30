import java.io.*;
import java.util.*;

public class Main{
    public static void main(String [] args){
        try{
            ArrayList<SeafoodOrder> customer = new ArrayList<>();
            FileReader fr = new FileReader("input.txt");
            BufferedReader br = new BufferedReader(fr);

            String data = null;

            while((data = br.readLine()) != null){
                ArrayList <String> seafoodNameArr = new ArrayList<>();
                ArrayList <Integer> quantityArr = new ArrayList<>();

                StringTokenizer st = new StringTokenizer(data,";");
                String name = st.nextToken();
                String phoneNum = st.nextToken();
                String address = st.nextToken();
                String deliveryType = st.nextToken();

                while(st.hasMoreTokens()){
                    String seafoodName = st.nextToken();
                    int seafoodQuantity = Integer.parseInt(st.nextToken());

                    seafoodNameArr.add(seafoodName);
                    quantityArr.add(seafoodQuantity);
                }

                SeafoodOrder cust = new SeafoodOrder(name, phoneNum, address, deliveryType, seafoodNameArr, quantityArr);
                customer.add(cust);
            }
            br.close();

            FileWriter fw = new FileWriter("output.txt");
            PrintWriter pw = new PrintWriter(fw);

            System.out.println("\nList Of Customer");
            for (int i = 0; i < customer.size(); i++) {
            System.out.println(customer.get(i));
            }

            pw.close();
        }catch(IOException e) {
            System.out.println("Error reading orders input file.");
        }catch(NoSuchElementException e) {
            System.out.println("Error: File format is incorrect or missing data fields.");
        } catch(NumberFormatException e) {
            System.out.println("Error: Could not parse quantity string into an integer.");
        }
    }
}