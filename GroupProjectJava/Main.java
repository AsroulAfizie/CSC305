import java.util.*;

public class Main{
    public static void main(String [] args){
        System.out.println("Hello World");

        System.out.print("Enter A nigga name : ");
        Scanner scan = new Scanner(System.in);

        int num = scan.nextInt();

        System.out.println("Your Number " + num);

        scan.close();
    }
}