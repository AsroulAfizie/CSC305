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


int main(void) {
    FILE *fp = fopen("input.txt", "r");
    if(!fp){
        perror("Error Opening Files");
        return 1;
    }

    char line[1024];

    int valid = 1;
    while(fgets(line, sizeof(line), fp)){
        Delivery cust;

        if(inputFile(line, &cust)){
            printOrder(&cust);
        }else{
            printf("Incomplete!");
        }

    }
    fclose(fp);
    return 0;
}
