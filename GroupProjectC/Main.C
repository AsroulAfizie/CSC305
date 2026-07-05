#include <stdio.h>
#include <cstring>
#include <stdlib.h>
#include <string.h>
//STARTING

typedef struct{
    char seafoodName[100];
    int quantity;
}Seafood;

typedef struct{
    char name[100];
    char phoneNum[100];
    char address[100];
    char deliveryType[100];
    Seafood items[100];
    int itemCount;
}Delivery;

int inputFile(char *line, Delivery *cust){
    char *token;

    token = strtok(line, ",");
    strcpy(cust->name, token);

    token = strtok(NULL, ",");
    strcpy(cust->phoneNum, token);

    token = strtok(NULL, ",");
    strcpy(cust->address, token);

    token = strtok(NULL, ",");
    strcpy(cust->deliveryType, token);

    cust->itemCount = 0;
    while((token = strtok(NULL, ",")) != NULL){
        strcpy(cust->items[cust->itemCount].seafoodName, token);

        token = strtok(NULL, ",");
        if(!token) break;
        cust->items[cust->itemCount].quantity = atoi(token);

        cust->itemCount++;
    }

    return 1;
}

double getDeliveryFee(const Delivery *cust){
    double deliveryFee = 0;
    char deliveryType [16];
    strcpy(deliveryType,cust->deliveryType);

    if(strcmp(deliveryType, "Rush") == 0){
        return 10;
    }else{
        return 5;
    }
    return 0;
}



double getPrice(const char *seafoodName){
    if(strcmp(seafoodName, "Tiger Shrimp") == 0){
        return 200.00;
    }else if(strcmp(seafoodName, "White Shrimp") == 0){
        return 125.00;
    }else if(strcmp(seafoodName, "King Crab") == 0){
        return 990.00;
    }else if(strcmp(seafoodName, "Royal Red Shrimp") == 0){
        return 175.00;
    }else if(strcmp(seafoodName, "Blue Pincer Crab") == 0){
        return 150.00;
    }else{
        return 0;
    }
}

double calculatePrice(const Delivery *cust){
    double totalPrice = 0;
    
    for(int i = 0; i < cust->itemCount; i++){
        double price = getPrice(cust->items[i].seafoodName);
        totalPrice += price * cust->items[i].quantity;
    }

    totalPrice += getDeliveryFee(cust);

    return totalPrice;
}

void printOrder(const Delivery *cust){
    printf("Customer: %s\n", cust->name);
    printf("Phone Number : %s\n", cust->phoneNum);
    printf("Address : %s\n", cust->address);
    printf("Delivery Type : %s\n", cust->deliveryType);

    for(int i = 0; i < cust->itemCount;i++){
        printf("- %s \t -%d\n", cust->items[i].seafoodName,cust->items[i].quantity);
    }
    printf("RM%.2f\n", calculatePrice(cust));
    printf("\n");
}
void saveToFile(Delivery customer[], int count) {
    FILE *fp = fopen("input.txt", "w");
    if (!fp) return;
    for (int i = 0; i < count; i++) {
        fprintf(fp, "%s,%s,%s,%s", customer[i].name, customer[i].phoneNum, customer[i].address, customer[i].deliveryType);
        for (int j = 0; j < customer[i].itemCount; j++) {
            fprintf(fp, ",%s,%d", customer[i].items[j].seafoodName, customer[i].items[j].quantity);
        }
        fprintf(fp, "\n");
    }
    fclose(fp);
}

// =========================================================================
// 3) BASIC CRUD OPERATIONS (Rubric 3a, 3b, 3c)
// =========================================================================

// Option 2: Add data (Rubric 3a)
void handleAddData(Delivery customer[], int *customerCount) {
    char buffer[256];
    if (*customerCount >= MAX_CUSTOMERS) return;

    Delivery newCust;
    newCust.itemCount = 0;

    while (1) {
        printf("Enter Customer Name: ");
        fgets(newCust.name, sizeof(newCust.name), stdin);
        newCust.name[strcspn(newCust.name, "\n")] = 0;
        trimString(newCust.name);
        if (strlen(newCust.name) > 0) break;
    }

    while (1) {
        printf("Enter Phone Number: ");
        fgets(newCust.phoneNum, sizeof(newCust.phoneNum), stdin);
        newCust.phoneNum[strcspn(newCust.phoneNum, "\n")] = 0;
        trimString(newCust.phoneNum);
        if (strlen(newCust.phoneNum) > 0) break;
    }

    while (1) {
        printf("Enter Address: ");
        fgets(newCust.address, sizeof(newCust.address), stdin);
        newCust.address[strcspn(newCust.address, "\n")] = 0;
        trimString(newCust.address);
        if (strlen(newCust.address) > 0) break;
    }

    while (1) {
        printf("Enter Delivery Type (Rush/Regular): ");
        fgets(newCust.deliveryType, sizeof(newCust.deliveryType), stdin);
        newCust.deliveryType[strcspn(newCust.deliveryType, "\n")] = 0;
        trimString(newCust.deliveryType);
        if (strcasecmp(newCust.deliveryType, "Rush") == 0 || strcasecmp(newCust.deliveryType, "Regular") == 0) {
            if (strcasecmp(newCust.deliveryType, "Rush") == 0) strcpy(newCust.deliveryType, "Rush");
            else strcpy(newCust.deliveryType, "Regular");
            break;
        }
    }

    char another = 'y';
    while (tolower(another) == 'y' && newCust.itemCount < MAX_ITEMS) {
        char seafoodInput[100];
        while (1) {
            printf("Enter Seafood Item Name: ");
            fgets(seafoodInput, sizeof(seafoodInput), stdin);
            seafoodInput[strcspn(seafoodInput, "\n")] = 0;
            trimString(seafoodInput);

            if (isValidSeafood(seafoodInput)) {
                if (strcasecmp(seafoodInput, "Tiger Shrimp") == 0) strcpy(seafoodInput, "Tiger Shrimp");
                else if (strcasecmp(seafoodInput, "White Shrimp") == 0) strcpy(seafoodInput, "White Shrimp");
                else if (strcasecmp(seafoodInput, "King Crab") == 0) strcpy(seafoodInput, "King Crab");
                else if (strcasecmp(seafoodInput, "Royal Red Shrimp") == 0) strcpy(seafoodInput, "Royal Red Shrimp");
                else if (strcasecmp(seafoodInput, "Blue Pincer Crab") == 0) strcpy(seafoodInput, "Blue Pincer Crab");
                
                strcpy(newCust.items[newCust.itemCount].seafoodName, seafoodInput);
                break;
            }
            printf("[Error] Item does not exist.\n");
        }

        while (1) {
            printf("Enter Quantity: ");
            fgets(buffer, sizeof(buffer), stdin);
            int qtyInt;
            if (sscanf(buffer, "%d", &qtyInt) == 1 && qtyInt > 0) {
                newCust.items[newCust.itemCount].quantity = qtyInt;
                break;
            }
        }

        newCust.itemCount++;
        printf("Add another item to this cart? (y/n): ");
        fgets(buffer, sizeof(buffer), stdin);
        another = buffer[0];
    }

    customer[*customerCount] = newCust;
    (*customerCount)++;
    printf("[Success] Order added successfully.\n");
}

// Option 3 & 4: Search, Update, and Delete operations (Rubric 3b, 3c)
void handleSearchUpdate(Delivery customer[], int customerCount) {
    char buffer[256];
    if (customerCount == 0) return;

    int subChoice = 0;
    printf("\n1. Search Order\n2. Update Order\nEnter choice (1-2): ");
    fgets(buffer, sizeof(buffer), stdin);
    sscanf(buffer, "%d", &subChoice);

    if (subChoice == 1) {
        char searchKey[100];
        printf("Enter Customer Name to search: ");
        fgets(searchKey, sizeof(searchKey), stdin);
        searchKey[strcspn(searchKey, "\n")] = 0;
        trimString(searchKey);

        for (int i = 0; i < customerCount; i++) {
            if (strcasecmp(customer[i].name, searchKey) == 0) {
                printOrder(&customer[i], i);
            }
        }
    } else if (subChoice == 2) {
        int targetIdx;
        printf("Enter index to update (0 to %d): ", customerCount - 1);
        fgets(buffer, sizeof(buffer), stdin);
        if (sscanf(buffer, "%d", &targetIdx) != 1 || targetIdx < 0 || targetIdx >= customerCount) return;

        printf("Enter New Address (Or press enter to keep current): ");
        char nextAddress[100];
        fgets(nextAddress, sizeof(nextAddress), stdin);
        nextAddress[strcspn(nextAddress, "\n")] = 0;
        trimString(nextAddress);
        if (strlen(nextAddress) > 0) strcpy(customer[targetIdx].address, nextAddress);

        while (1) {
            printf("Enter New Delivery Type (Or press enter to keep current): ");
            char nextDelType[100];
            fgets(nextDelType, sizeof(nextDelType), stdin);
            nextDelType[strcspn(nextDelType, "\n")] = 0;
            trimString(nextDelType);
            
            if (strlen(nextDelType) == 0) break; 
            if (strcasecmp(nextDelType, "Rush") == 0 || strcasecmp(nextDelType, "Regular") == 0) {
                if (strcasecmp(nextDelType, "Rush") == 0) strcpy(customer[targetIdx].deliveryType, "Rush");
                else strcpy(customer[targetIdx].deliveryType, "Regular");
                break;
            }
        }
        printf("[Success] Order updated.\n");
    }
}

void handleDeleteData(Delivery customer[], int *customerCount) {
    char buffer[256];
    if (*customerCount == 0) return;

    int deleteIdx;
    printf("Enter index to delete (0 to %d): ", (*customerCount) - 1);
    fgets(buffer, sizeof(buffer), stdin);
    
    if (sscanf(buffer, "%d", &deleteIdx) != 1 || deleteIdx < 0 || deleteIdx >= *customerCount) return;

    for (int i = deleteIdx; i < (*customerCount) - 1; i++) {
        customer[i] = customer[i + 1];
    }
    (*customerCount)--;
    printf("[Success] Order deleted.\n");
}

int main(void) {
    Delivery customer[MAX_CUSTOMERS];
    int customerCount = 0;

    FILE *fp = fopen("input.txt", "r");
    if (fp) {
        char line[1024];
        while (fgets(line, sizeof(line), fp) && customerCount < MAX_CUSTOMERS) {
            line[strcspn(line, "\r\n")] = 0;
            if (strlen(line) == 0) continue;

            Delivery tempCust;
            if (inputFile(line, &tempCust)) {
                customer[customerCount] = tempCust;
                customerCount++;
            }
        }
        fclose(fp);
    }

    int choice = 0;
    char menuBuffer[256];

    while (choice != 5) {
        printf("\n===== SEAFOOD ORDER MANAGEMENT SYSTEM =====\n");
        printf("1. Display All Customer Orders (Read)\n");
        printf("2. Add New Customer Order (Create)\n");
        printf("3. Search/Update Customer Order (Update)\n");
        printf("4. Delete Customer Order (Delete)\n");
        printf("5. Save Changes and Exit\n");
        printf("Enter option (1-5): ");
        
        if (!fgets(menuBuffer, sizeof(menuBuffer), stdin)) break;
        if (sscanf(menuBuffer, "%d", &choice) != 1) continue;

        switch (choice) {
            case 1:
                printf("\n--- Current Registered Orders ---\n");
                if (customerCount == 0) {
                    printf("No records found.\n");
                } else {
                    for (int i = 0; i < customerCount; i++) {
                        printOrder(&customer[i], i);
                    }
                }
                break;
            case 2:
                handleAddData(customer, &customerCount);
                break;
            case 3:
                handleSearchUpdate(customer, customerCount);
                break;
            case 4:
                handleDeleteData(customer, &customerCount);
                break;
            case 5:
                saveToFile(customer, customerCount);
                printf("\nChanges persisted into input.txt. Goodbye!\n");
                break;
        }
    }
    return 0;
}
