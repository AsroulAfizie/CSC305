import java.util.*;

public class Main{
    public static void main(String [] args){
        System.out.println("Hello World");

        System.out.print("Enter A number : ");
        Scanner scan = new Scanner(System.in);

        int num = scan.nextInt();

        System.out.println("Your Number " + num);

        scan.close();
    }
}