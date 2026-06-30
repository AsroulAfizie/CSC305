import java.io.*;
import java.util.*;

public class Main{
    public static void main(String [] args){
        try{
            ArrayList<SeafoodOrder> customer = new ArrayList<>();
            System.out.println("Working dir: " + System.getProperty("user.dir"));
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
            
            Scanner inputScanner = new Scanner(System.in);
            int choice = 0;

            while (choice != 5) {
                System.out.println("\n===== SEAFOOD ORDER MANAGEMENT MENU =====");
                System.out.println("1. Display All Customer Orders (Read)");
                System.out.println("2. Add New Customer Order (Create)");
                System.out.println("3. Update Existing Customer Order (Update)");
                System.out.println("4. Delete Customer Order (Delete)");
                System.out.println("5. Save Changes and Exit");
                System.out.print("Enter choice (1-5): ");

                // Validation: Prevent character/string mismatch crashes
                if (!inputScanner.hasNextInt()) {
                    System.out.println("[Error] Please enter a valid menu number between 1 and 5.");
                    inputScanner.nextLine(); 
                    continue;
                }
                choice = inputScanner.nextInt();
                inputScanner.nextLine(); // Clear scanner newline buffer

                if (choice < 1 || choice > 5) {
                    System.out.println("[Error] Menu choice must be strictly from 1 to 5.");
                    continue;
                }

                if (choice == 1) {
                    // 1. DISPLAY CURRENT ORDERS
                    System.out.println("\n--- Current Active Orders ---");
                    if (customer.isEmpty()) {
                        System.out.println("No records found.");
                    } else {
                        for (int i = 0; i < customer.size(); i++) {
                            System.out.println("[" + i + "] " + customer.get(i));
                        }
                    }

                } else if (choice == 2) {
                    // 2. IMPLEMENT ADDING NEW ORDER WITH INPUT VALIDATION
                    String newName, newPhone, newAddress, newDelType;

                    while (true) {
                        System.out.print("Enter Customer Name: ");
                        newName = inputScanner.nextLine().trim();
                        if (!newName.isEmpty()) break;
                        System.out.println("[Error] Name field cannot be left blank.");
                    }

                    while (true) {
                        System.out.print("Enter Phone Number: ");
                        newPhone = inputScanner.nextLine().trim();
                        if (!newPhone.isEmpty()) break;
                        System.out.println("[Error] Phone number field cannot be left blank.");
                    }

                    while (true) {
                        System.out.print("Enter Address: ");
                        newAddress = inputScanner.nextLine().trim();
                        if (!newAddress.isEmpty()) break;
                        System.out.println("[Error] Address field cannot be left blank.");
                    }

                    while (true) {
                        System.out.print("Enter Delivery Type (Rush/Regular): ");
                        newDelType = inputScanner.nextLine().trim();
                        if (newDelType.equalsIgnoreCase("Rush") || newDelType.equalsIgnoreCase("Regular")) break;
                        System.out.println("[Error] Type must match exactly 'Rush' or 'Regular'.");
                    }

                    ArrayList<String> freshItems = new ArrayList<>();
                    ArrayList<Integer> freshQtties = new ArrayList<>();
                    String continuous = "y";

                    while (continuous.equalsIgnoreCase("y")) {
                        System.out.print("Enter Seafood Item Name: ");
                        String seafoodItem = inputScanner.nextLine().trim();

                        int qty = 0;
                        while (true) {
                            System.out.print("Enter Quantity: ");
                            if (inputScanner.hasNextInt()) {
                                qty = inputScanner.nextInt();
                                inputScanner.nextLine(); // Clear buffer
                                if (qty > 0) break;
                                System.out.println("[Error] Quantity must be greater than 0.");
                            } else {
                                System.out.println("[Error] Invalid character input. Enter a whole number.");
                                inputScanner.nextLine(); 
                            }
                        }

                        if (!seafoodItem.isEmpty()) {
                            freshItems.add(seafoodItem);
                            freshQtties.add(qty);
                        }

                        System.out.print("Add another item type to this cart? (y/n): ");
                        continuous = inputScanner.nextLine().trim();
                    }

                    SeafoodOrder newCust = new SeafoodOrder(newName, newPhone, newAddress, newDelType, freshItems, freshQtties);
                    customer.add(newCust);
                    System.out.println("[Success] Order added to dynamic memory lists.");

                } else if (choice == 3) {
                    // 3. IMPLEMENT UPDATING SEAFOOD ORDER WITH VALIDATION
                    if (customer.isEmpty()) {
                        System.out.println("No entries inside memory tracking records to modify.");
                        continue;
                    }

                    int updateIdx = -1;
                    while (true) {
                        System.out.print("Enter index to update (0 to " + (customer.size() - 1) + "): ");
                        if (inputScanner.hasNextInt()) {
                            updateIdx = inputScanner.nextInt();
                            inputScanner.nextLine(); // Clear buffer
                            if (updateIdx >= 0 && updateIdx < customer.size()) break;
                            System.out.println("[Error] Index value is out of system boundaries.");
                        } else {
                            System.out.println("[Error] Please enter a valid numeric integer index.");
                            inputScanner.nextLine();
                        }
                    }

                    SeafoodOrder target = customer.get(updateIdx);
                    System.out.println("Modifying order parameters for customer: " + target.getName());

                    System.out.print("Enter New Address (Or press enter to keep '" + target.getAddress() + "'): ");
                    String nextAddress = inputScanner.nextLine().trim();
                    if (!nextAddress.isEmpty()) target.setAddress(nextAddress);

                    while (true) {
                        System.out.print("Enter New Delivery Type (Or press enter to keep '" + target.getDeliveryType() + "'): ");
                        String nextDelType = inputScanner.nextLine().trim();
                        if (nextDelType.isEmpty()) break;
                        if (nextDelType.equalsIgnoreCase("Rush") || nextDelType.equalsIgnoreCase("Regular")) {
                            target.setDeliveryType(nextDelType);
                            break;
                        }
                        System.out.println("[Error] Selection must strictly match either 'Rush' or 'Regular'.");
                    }
                    System.out.println("[Success] System parameters modified successfully.");

                } else if (choice == 4) {
                    // 4. IMPLEMENT DELETING SEAFOOD ORDER WITH VALIDATION
                    if (customer.isEmpty()) {
                        System.out.println("No records available to delete.");
                        continue;
                    }

                    int removeIdx = -1;
                    while (true) {
                        System.out.print("Enter index to delete (0 to " + (customer.size() - 1) + "): ");
                        if (inputScanner.hasNextInt()) {
                            removeIdx = inputScanner.nextInt();
                            inputScanner.nextLine(); // Clear buffer
                            if (removeIdx >= 0 && removeIdx < customer.size()) break;
                            System.out.println("[Error] Index out of bounds.");
                        } else {
                            System.out.println("[Error] Please enter a valid numeric integer index.");
                            inputScanner.nextLine();
                        }
                    }

                    customer.remove(removeIdx);
                    System.out.println("[Success] Order record completely wiped from internal runtime memory storage structures.");
                }
            }
            inputScanner.close();
            // =========================================================================

            FileWriter fw = new FileWriter("GroupProjectJava/output.txt");
            PrintWriter pw = new PrintWriter(fw);

            // Synchronize and dump memory collection updates back into input.txt structure file
            PrintWriter dataFileWriter = new PrintWriter(new FileWriter("GroupProjectJava/input.txt"));
            for (int i = 0; i < customer.size(); i++) {
                SeafoodOrder current = customer.get(i);
                StringBuilder sb = new StringBuilder();
                sb.append(current.getName()).append(";")
                  .append(current.getPhoneNum()).append(";")
                  .append(current.getAddress()).append(";")
                  .append(current.getDeliveryType());

                for (int j = 0; j < current.seafoodName.size(); j++) {
                    sb.append(";").append(current.seafoodName.get(j))
                      .append(";").append(current.seafoodQuantity.get(j));
                }
                dataFileWriter.println(sb.toString());
                pw.println(current); // Keeping original output file copy mechanism
            }
            dataFileWriter.close();

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