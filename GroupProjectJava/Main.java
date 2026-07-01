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
                            // =========================================================================
                            // IMPLEMENT ADDING NEW ORDER WITH ROBUST INPUT VALIDATION
                            // =========================================================================
                            String newName, newPhone, newAddress, newDelType;

                            // 1. Validate Customer Name (Cannot contain numbers)
                            // 1. Validate Customer Name (Must contain only letters and spaces)
                            while (true) {
                                System.out.print("Enter Customer Name: ");
                                newName = inputScanner.nextLine().trim();

                                if (newName.isEmpty()) {
                                    System.out.println("[Input Error] Name field cannot be left blank.");
                                    continue;
                                }

                                boolean validName = true;
                                for (int i = 0; i < newName.length(); i++) {
                                    char c = newName.charAt(i);
                                    if (!Character.isLetter(c) && c != ' ') {
                                        validName = false;
                                        break;
                                    }
                                }

                                if (!validName) {
                                    System.out.println("[Input Error] Name can only contain letters and spaces (no numbers or symbols).");
                                    continue;
                                }

                                break; // Valid name
                            }

                            // 2. Validate Phone Number (Malaysian format: digits and '-' only, starts with 0, 9-11 digits)
                            while (true) {
                                System.out.print("Enter Phone Number (e.g. 012-3456789): ");
                                newPhone = inputScanner.nextLine().trim();

                                if (newPhone.isEmpty()) {
                                    System.out.println("[Input Error] Phone number field cannot be left blank.");
                                    continue;
                                }

                                boolean validChars = true;
                                int digitCount = 0;
                                for (int i = 0; i < newPhone.length(); i++) {
                                    char c = newPhone.charAt(i);
                                    if (Character.isDigit(c)) {
                                        digitCount++;
                                    } else if (c != '-') {
                                        validChars = false;
                                        break;
                                    }
                                }

                                if (!validChars) {
                                    System.out.println("[Input Error] Phone number can only contain digits and '-'.");
                                    continue;
                                }

                                if (newPhone.charAt(0) != '0') {
                                    System.out.println("[Input Error] Malaysian phone numbers must start with 0.");
                                    continue;
                                }

                                if (digitCount < 9 || digitCount > 11) {
                                    System.out.println("[Input Error] Phone number must have 9 to 11 digits (Malaysian format).");
                                    continue;
                                }

                                break; // Valid phone number
                            }

                            // 3. Address (No validation required)
                            System.out.print("Enter Address: ");
                            newAddress = inputScanner.nextLine().trim();

                            // 4. Validate Delivery Type (Must be exactly Rush or Regular)
                            while (true) {
                                System.out.print("Enter Delivery Type (Rush/Regular): ");
                                newDelType = inputScanner.nextLine().trim();
                                if (newDelType.equalsIgnoreCase("Rush") || newDelType.equalsIgnoreCase("Regular")) {
                                    // Format correctly to match your file style capitalization
                                    newDelType = newDelType.substring(0, 1).toUpperCase() + newDelType.substring(1).toLowerCase();
                                    break;
                                }
                                System.out.println("[Validation Error] Type must match exactly 'Rush' or 'Regular'.");
                            }

                            ArrayList<String> freshItems = new ArrayList<>();
                            ArrayList<Integer> freshQtties = new ArrayList<>();
                            String continuous = "y";

                            // 5. Validate Seafood Items (must match system's priced items) and Quantities
                            while (continuous.equalsIgnoreCase("y")) {

                                String seafoodItem;
                                while (true) {
                                    System.out.print("Enter Seafood Item Name: ");
                                    seafoodItem = inputScanner.nextLine().trim();

                                    if (seafoodItem.isEmpty()) {
                                        System.out.println("[Input Error] Seafood item name cannot be empty.");
                                        continue;
                                    }

                                    boolean existsInSystem = false;
                                    String matchedName = seafoodItem;

                                    if (seafoodItem.equalsIgnoreCase("Tiger Shrimp")) {
                                        existsInSystem = true;
                                        matchedName = "Tiger Shrimp";
                                    } else if (seafoodItem.equalsIgnoreCase("White Shrimp")) {
                                        existsInSystem = true;
                                        matchedName = "White Shrimp";
                                    } else if (seafoodItem.equalsIgnoreCase("Royal Red Shrimp")) {
                                        existsInSystem = true;
                                        matchedName = "Royal Red Shrimp";
                                    } else if (seafoodItem.equalsIgnoreCase("Blue Pincer Crab")) {
                                        existsInSystem = true;
                                        matchedName = "Blue Pincer Crab";
                                    } else if (seafoodItem.equalsIgnoreCase("King Crab")) {
                                        existsInSystem = true;
                                        matchedName = "King Crab";
                                    }

                                    if (!existsInSystem) {
                                        System.out.println("[Input Error] '" + seafoodItem + "' is not a recognised seafood item.");
                                        System.out.println("Available items: Tiger Shrimp, White Shrimp, Royal Red Shrimp, Blue Pincer Crab, King Crab");
                                        continue;
                                    }

                                    seafoodItem = matchedName;
                                    break; // Valid item name
                                }

                                int qty = 0;
                                while (true) {
                                    System.out.print("Enter Quantity: ");
                                    // Prevent string/character entry crashes
                                    if (inputScanner.hasNextInt()) {
                                        qty = inputScanner.nextInt();
                                        inputScanner.nextLine(); // Clear scanner newline buffer
                                        if (qty > 0) {
                                            break; // Valid positive number entered successfully
                                        }
                                        System.out.println("[Validation Error] Quantity must be greater than 0.");
                                    } else {
                                        System.out.println("[Type Error] Invalid input format. Please enter a whole number integer.");
                                        inputScanner.nextLine(); // Clear invalid non-integer string data from buffer
                                    }
                                }

                                freshItems.add(seafoodItem);
                                freshQtties.add(qty);

                                // Validate loop exit command
                                while (true) {
                                    System.out.print("Add another item type to this cart? (y/n): ");
                                    continuous = inputScanner.nextLine().trim().toLowerCase();
                                    if (continuous.equals("y") || continuous.equals("n")) {
                                        break;
                                    }
                                    System.out.println("[Input Error] Please enter only 'y' for Yes or 'n' for No.");
                                }
                            }

                            // Instantiate object with verified inputs
                            SeafoodOrder newCust = new SeafoodOrder(newName, newPhone, newAddress, newDelType, freshItems, freshQtties);
                            customer.add(newCust);
                            System.out.println("[Success] Verified order successfully appended to system memory list.");


                            } else if (choice == 3) {
                                // 3. IMPLEMENT SEARCH & UPDATE SEAFOOD ORDER (ALL ATTRIBUTES)
                                if (customer.isEmpty()) {
                                    System.out.println("No entries inside memory tracking records to modify.");
                                    continue;
                                }

                                // --- SEARCH PHASE ---
                                System.out.print("Enter Customer Name to search: ");
                                String searchName = inputScanner.nextLine().trim();
                                int updateIdx = -1;

                                for (int i = 0; i < customer.size(); i++) {
                                    if (customer.get(i).getName().equalsIgnoreCase(searchName)) {
                                        updateIdx = i;
                                        break;
                                    }
                                }

                                if (updateIdx == -1) {
                                    System.out.println("[Search Error] No active order found under the name '" + searchName + "'.");
                                    continue;
                                }

                                SeafoodOrder target = customer.get(updateIdx);
                                System.out.println("\n[Match Found] Current order details:");
                                System.out.println(target);

                                // --- 1. UPDATE NAME (Optional, letters and spaces only) ---
                                while (true) {
                                    System.out.print("Enter New Name (Or press enter to keep '" + target.getName() + "'): ");
                                    String nextName = inputScanner.nextLine().trim();

                                    if (nextName.isEmpty()) {
                                        break; // Keep existing name
                                    }

                                    boolean validName = true;
                                    for (int i = 0; i < nextName.length(); i++) {
                                        char c = nextName.charAt(i);
                                        if (!Character.isLetter(c) && c != ' ') {
                                            validName = false;
                                            break;
                                        }
                                    }

                                    if (!validName) {
                                        System.out.println("[Input Error] Name can only contain letters and spaces (no numbers or symbols).");
                                        continue;
                                    }

                                    target.setName(nextName);
                                    break;
                                }

                                // --- 2. UPDATE PHONE NUMBER (Optional, Malaysian format) ---
                                while (true) {
                                    System.out.print("Enter New Phone Number (Or press enter to keep '" + target.getPhoneNum() + "'): ");
                                    String nextPhone = inputScanner.nextLine().trim();

                                    if (nextPhone.isEmpty()) {
                                        break; // Keep existing phone number
                                    }

                                    boolean validChars = true;
                                    int digitCount = 0;
                                    for (int i = 0; i < nextPhone.length(); i++) {
                                        char c = nextPhone.charAt(i);
                                        if (Character.isDigit(c)) {
                                            digitCount++;
                                        } else if (c != '-') {
                                            validChars = false;
                                            break;
                                        }
                                    }

                                    if (!validChars) {
                                        System.out.println("[Input Error] Phone number can only contain digits and '-'.");
                                        continue;
                                    }

                                    if (nextPhone.charAt(0) != '0') {
                                        System.out.println("[Input Error] Malaysian phone numbers must start with 0.");
                                        continue;
                                    }

                                    if (digitCount < 9 || digitCount > 11) {
                                        System.out.println("[Input Error] Phone number must have 9 to 11 digits (Malaysian format).");
                                        continue;
                                    }

                                    target.setPhoneNum(nextPhone);
                                    break;
                                }

                                // --- 3. UPDATE ADDRESS (Optional, no validation) ---
                                System.out.print("Enter New Address (Or press enter to keep '" + target.getAddress() + "'): ");
                                String nextAddress = inputScanner.nextLine().trim();
                                if (!nextAddress.isEmpty()) target.setAddress(nextAddress);

                                // --- 4. UPDATE DELIVERY TYPE (Optional, must be Rush/Regular) ---
                                while (true) {
                                    System.out.print("Enter New Delivery Type (Or press enter to keep '" + target.getDeliveryType() + "'): ");
                                    String nextDelType = inputScanner.nextLine().trim();
                                    if (nextDelType.isEmpty()) break;

                                    if (nextDelType.equalsIgnoreCase("Rush") || nextDelType.equalsIgnoreCase("Regular")) {
                                        nextDelType = nextDelType.substring(0, 1).toUpperCase() + nextDelType.substring(1).toLowerCase();
                                        target.setDeliveryType(nextDelType);
                                        break;
                                    }
                                    System.out.println("[Error] Selection must strictly match either 'Rush' or 'Regular'.");
                                }

                                // --- 5. UPDATE SEAFOOD ITEMS (Add / Remove / Update Quantity) ---
                                String editSeafood;
                                while (true) {
                                    System.out.print("Do you want to edit seafood items in this order? (y/n): ");
                                    editSeafood = inputScanner.nextLine().trim().toLowerCase();
                                    if (editSeafood.equals("y") || editSeafood.equals("n")) break;
                                    System.out.println("[Input Error] Please enter only 'y' for Yes or 'n' for No.");
                                }

                                while (editSeafood.equals("y")) {
                                    System.out.println("\n--- Current Seafood Items ---");
                                    if (target.getSeafoodName().isEmpty()) {
                                        System.out.println("No seafood items in this order.");
                                    } else {
                                        for (int i = 0; i < target.getSeafoodName().size(); i++) {
                                            System.out.println("[" + i + "] " + target.getSeafoodName().get(i) + " : " + target.getSeafoodQuantity().get(i));
                                        }
                                    }

                                    System.out.println("1. Add New Item");
                                    System.out.println("2. Update Quantity of Existing Item");
                                    System.out.println("3. Remove an Item");
                                    System.out.println("4. Done Editing Seafood Items");
                                    System.out.print("Enter choice (1-4): ");

                                    int seafoodChoice = -1;
                                    if (inputScanner.hasNextInt()) {
                                        seafoodChoice = inputScanner.nextInt();
                                        inputScanner.nextLine();
                                    } else {
                                        System.out.println("[Error] Please enter a valid number.");
                                        inputScanner.nextLine();
                                        continue;
                                    }

                                    if (seafoodChoice == 1) {
                                        // Add new item
                                        String seafoodItem;
                                        while (true) {
                                            System.out.print("Enter Seafood Item Name: ");
                                            seafoodItem = inputScanner.nextLine().trim();

                                            if (seafoodItem.isEmpty()) {
                                                System.out.println("[Input Error] Seafood item name cannot be empty.");
                                                continue;
                                            }

                                            boolean existsInSystem = false;
                                            String matchedName = seafoodItem;

                                            if (seafoodItem.equalsIgnoreCase("Tiger Shrimp")) {
                                                existsInSystem = true; matchedName = "Tiger Shrimp";
                                            } else if (seafoodItem.equalsIgnoreCase("White Shrimp")) {
                                                existsInSystem = true; matchedName = "White Shrimp";
                                            } else if (seafoodItem.equalsIgnoreCase("Royal Red Shrimp")) {
                                                existsInSystem = true; matchedName = "Royal Red Shrimp";
                                            } else if (seafoodItem.equalsIgnoreCase("Blue Pincer Crab")) {
                                                existsInSystem = true; matchedName = "Blue Pincer Crab";
                                            } else if (seafoodItem.equalsIgnoreCase("King Crab")) {
                                                existsInSystem = true; matchedName = "King Crab";
                                            }

                                            if (!existsInSystem) {
                                                System.out.println("[Input Error] '" + seafoodItem + "' is not a recognised seafood item.");
                                                System.out.println("Available items: Tiger Shrimp, White Shrimp, Royal Red Shrimp, Blue Pincer Crab, King Crab");
                                                continue;
                                            }

                                            seafoodItem = matchedName;
                                            break;
                                        }

                                        int qty = 0;
                                        while (true) {
                                            System.out.print("Enter Quantity: ");
                                            if (inputScanner.hasNextInt()) {
                                                qty = inputScanner.nextInt();
                                                inputScanner.nextLine();
                                                if (qty > 0) break;
                                                System.out.println("[Validation Error] Quantity must be greater than 0.");
                                            } else {
                                                System.out.println("[Type Error] Please enter a whole number integer.");
                                                inputScanner.nextLine();
                                            }
                                        }

                                        target.getSeafoodName().add(seafoodItem);
                                        target.getSeafoodQuantity().add(qty);
                                        System.out.println("[Success] Item added.");

                                    } else if (seafoodChoice == 2) {
                                        // Update quantity of existing item
                                        if (target.getSeafoodName().isEmpty()) {
                                            System.out.println("[Error] No items to update.");
                                            continue;
                                        }

                                        int itemIdx = -1;
                                        while (true) {
                                            System.out.print("Enter item index to update quantity (0 to " + (target.getSeafoodName().size() - 1) + "): ");
                                            if (inputScanner.hasNextInt()) {
                                                itemIdx = inputScanner.nextInt();
                                                inputScanner.nextLine();
                                                if (itemIdx >= 0 && itemIdx < target.getSeafoodName().size()) break;
                                                System.out.println("[Error] Index out of bounds.");
                                            } else {
                                                System.out.println("[Error] Please enter a valid numeric index.");
                                                inputScanner.nextLine();
                                            }
                                        }

                                        int newQty = 0;
                                        while (true) {
                                            System.out.print("Enter New Quantity: ");
                                            if (inputScanner.hasNextInt()) {
                                                newQty = inputScanner.nextInt();
                                                inputScanner.nextLine();
                                                if (newQty > 0) break;
                                                System.out.println("[Validation Error] Quantity must be greater than 0.");
                                            } else {
                                                System.out.println("[Type Error] Please enter a whole number integer.");
                                                inputScanner.nextLine();
                                            }
                                        }

                                        target.getSeafoodQuantity().set(itemIdx, newQty);
                                        System.out.println("[Success] Quantity updated.");

                                    } else if (seafoodChoice == 3) {
                                        // Remove an item
                                        if (target.getSeafoodName().isEmpty()) {
                                            System.out.println("[Error] No items to remove.");
                                            continue;
                                        }

                                        int itemIdx = -1;
                                        while (true) {
                                            System.out.print("Enter item index to remove (0 to " + (target.getSeafoodName().size() - 1) + "): ");
                                            if (inputScanner.hasNextInt()) {
                                                itemIdx = inputScanner.nextInt();
                                                inputScanner.nextLine();
                                                if (itemIdx >= 0 && itemIdx < target.getSeafoodName().size()) break;
                                                System.out.println("[Error] Index out of bounds.");
                                            } else {
                                                System.out.println("[Error] Please enter a valid numeric index.");
                                                inputScanner.nextLine();
                                            }
                                        }

                                        target.getSeafoodName().remove(itemIdx);
                                        target.getSeafoodQuantity().remove(itemIdx);
                                        System.out.println("[Success] Item removed.");

                                    } else if (seafoodChoice == 4) {
                                        break; // Exit seafood editing loop
                                    } else {
                                        System.out.println("[Error] Please enter a number between 1 and 4.");
                                    }
                                }

                                System.out.println("\n[Success] Order fully updated:");
                                System.out.println(target);



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

            FileWriter fw = new FileWriter("output.txt");
            PrintWriter pw = new PrintWriter(fw);

            // Get current date for the report
                java.time.LocalDate today = java.time.LocalDate.now();

                pw.println("===== DAILY SEAFOOD ORDER REPORT =====");
                pw.println("Generated on: " + today);
                pw.println();

                // --- Summary calculations ---
                int totalOrders = customer.size();
                double totalRevenue = 0;
                int rushCount = 0;
                int regularCount = 0;

                ArrayList<String> seafoodTypes = new ArrayList<>();
                ArrayList<Integer> seafoodTotals = new ArrayList<>();

                for (int i = 0; i < customer.size(); i++) {
                    SeafoodOrder c = customer.get(i);
                    totalRevenue += c.calcTotalPrice();

                    if (c.getDeliveryType().equalsIgnoreCase("Rush")) {
                        rushCount++;
                    } else if (c.getDeliveryType().equalsIgnoreCase("Regular")) {
                        regularCount++;
                    }

                    for (int j = 0; j < c.getSeafoodName().size(); j++) {
                        String name = c.getSeafoodName().get(j);
                        int qty = c.getSeafoodQuantity().get(j);

                        boolean found = false;
                        for (int k = 0; k < seafoodTypes.size(); k++) {
                            if (seafoodTypes.get(k).equalsIgnoreCase(name)) {
                                seafoodTotals.set(k, seafoodTotals.get(k) + qty);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            seafoodTypes.add(name);
                            seafoodTotals.add(qty);
                        }
                    }
                }

                pw.println("--- SUMMARY ---");
                pw.println("Total Orders: " + totalOrders);
                pw.println("Total Revenue: RM" + String.format("%.2f", totalRevenue));
                pw.println();

                pw.println("--- DELIVERY TYPE BREAKDOWN ---");
                pw.println("Rush Orders: " + rushCount);
                pw.println("Regular Orders: " + regularCount);
                pw.println();

                pw.println("--- SEAFOOD SALES SUMMARY ---");
                for (int i = 0; i < seafoodTypes.size(); i++) {
                    pw.println(seafoodTypes.get(i) + " : " + seafoodTotals.get(i) + " unit(s) sold");
                }
                pw.println();

                pw.println("--- DETAILED ORDER LIST ---");
                for (int i = 0; i < customer.size(); i++) {
                    pw.println("[" + i + "] " + customer.get(i));
                }

                pw.close();

                // Synchronize and dump memory collection updates back into input.txt structure file
                    PrintWriter dataFileWriter = new PrintWriter(new FileWriter("input.txt"));
                    for (int i = 0; i < customer.size(); i++) {
                        SeafoodOrder current = customer.get(i);
                        StringBuilder sb = new StringBuilder();
                        sb.append(current.getName()).append(";")
                        .append(current.getPhoneNum()).append(";")
                        .append(current.getAddress()).append(";")
                        .append(current.getDeliveryType());

                        // FIX: Used getter methods here instead of direct public access
                        for (int j = 0; j < current.getSeafoodName().size(); j++) {
                            sb.append(";").append(current.getSeafoodName().get(j))
                            .append(";").append(current.getSeafoodQuantity().get(j));
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