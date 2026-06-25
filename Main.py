print("Hello World")

print("Hello", "How are you?", sep="---")
x = ("apple", "banana", "cherry")
print(x)

# strNum = input("Enter a Number >> ")
# intNum = int(strNum)
# squareNum = intNum ** 2
# print(squareNum)

age = 55
text = "Ali is %d." % age
print(text)

myResult = 8%7
print("Result is -> %d" %myResult)

name = "John"
text = "My Name is " + name + " and I am %d."% age
print(text)

for x in range(6):
    print(x +1)

def myFunction():
    print("This is a fucntion without argument.")

def multiplication(vName1, vName2):
    return vName1 * vName2

vSent1 = input("Please Enter first Number >>")
vsent2 = input("please enter Second Number >>")

print(multiplication(float(vSent1), float(vsent2)))
myFunction()

myCollection = {}
myCollection[1] = "First To Die = Harith"
myCollection[2] = "2nd Chance"
myCollection[3] = "3rd Degree"

myCollectionCross = {
    "No.1" : "Along Came A spider",
    "No.2" : "Kiss The Girls",
    "No.3" : "Jack And Jill",
    "No.4" : "Cat And Mouse"
}

testScores = {
    "Mariam" : 78,
    "Faridah" : 87,
    "Rashid" : 65,
    "Farid" : 80
}

myCollection[4] = "4th of July"
myCollectionCross["No.5"] = "Pop Goes The Weasel"
testScores["Amran"] = 45

print (myCollectionCross)
print(myCollection)
print(testScores)