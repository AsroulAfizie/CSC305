#include <stdio.h>
#include <cstring>
#include <stdlib.h>
#include <string.h>
//STARTING
//========INITIALIZATION
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


//========READ FILE AND INSERT INSERT STRUCT
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

int loadOrders(const char *filename, Delivery orders[], int *count){
    FILE *fp = fopen(filename, "r");
    if(!fp){
        perror("Error Opening Files");
        return 0;
    }

    char line[1024];
    *count = 0;

    while(fgets(line, sizeof(line), fp)){
        if(inputFile(line, &orders[*count])){
            (*count)++;
        }
    }

    fclose(fp);
    return 1;
}


//========CALCULATION

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


//========DISPLAY AND PRINTING
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

void printAllOrder(const Delivery cust[], int count){
    for(int i = 0; i < count; i++){
        printf("[%d]\n", i+1);
        printOrder(cust);
    }
}

//========ADD DATA

void addData(Delivery orders[], int *count){
    Delivery newOrder;
    printf("Enter Name : ");
    scanf(" %[^\n]", newOrder.name);
    printf("Enter Phone Number : ");
    scanf(" %[^\n]", newOrder.phoneNum);
    printf("Enter Address : ");
    scanf(" %[^\n]", newOrder.address);
    printf("Enter Delivery Type (Rush/Regular) : ");
    scanf(" %[^\n]", newOrder.deliveryType);

    newOrder.itemCount = 0;
    printf("How many Seafood Items? : ");
    int n;
    scanf("%d", &n);

    for(int i = 0; i < n; i++){
        printf("Seafood Name %d : ", i + 1);
        scanf(" %[^\n]", newOrder.items[i].seafoodName);
        printf("Quantity %d : ", i+1);
        scanf(" %d", &newOrder.items[i].quantity);
        newOrder.itemCount++;
    }

    orders[*count] = newOrder;
    (*count)++;

    printf("\nOrder added Successfully \n\n");
}

//========SEARCH AND UPDATE

int findbyName(const Delivery cust[], int count, const char *name){
    for(int i = 0; i < count; i++){
        if(strcmp(cust[i].name, name) == 0){
            return i; // Return the Index of the Searched Name.
        }
    }
    return -1; //Name Not Found.
}

void searchAndUpdate(Delivery cust[], int count){
    char name[100];
    printf("Enter Name to Search : ");
    scanf(" %[^n]", name);

    int index = findbyName(cust, count, name);
    if(index == -1){
        printf("Customer not Found");
        return;
    } 

    printf("\nCustomer Found!");
    printOrder(&cust[index]);

    printf("Update Delivery Type [Rush/Regular] : ");
    scanf(" %[^\n]", cust[index].deliveryType);

    printf("Update Phone Number : ");
    scanf(" %[^\n]", cust[index].phoneNum);

    printf("Order Updated! \n\n");
    printOrder(&cust[index]);
}

int main(void) {
    Delivery cust[100];
    int count = 0;

    loadOrders("input.txt", cust, &count);

    int choice;
    do{
        printf("1. Show All Orders\n");
        printf("2. Insert Data. \n");
        printf("3. Search and Update Data. \n");
        printf("4. Delete Data. \n");
        printf("5. Exit \n");
        printf("Choice : ");
        scanf(" %d", &choice);
        printf("\n");

        switch(choice){
            case 1 :
                printAllOrder(cust, count);
                break;
            case 2 :
                addData(cust, &count);
                break;
            case 3 :
                searchAndUpdate(cust, count);
                break;
            case 4: 
            
            case 5 : 
                printf("Fuck Off"); 
                break;
            default:
                printf("Invalid Choice.");
        }
    }while(choice != 5);
    return 0;
}
