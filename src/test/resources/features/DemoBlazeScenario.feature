Feature: Purchase Function

Scenario: Login into App
Given User is on Launch Page
When User navigates to Login Page
Then Should display Home Page

Scenario: Add items to the cart
When User Add an item to cart
 |category | items|
 |Phones | Samsung galaxy s6|
 |Laptops | Sony vaio i5| 
Then Items must be added to the cart

Scenario: Delete an items in the cart
Given User is in the Cart Page
When List of items should be available in the cart
Then Delete an item from the cart

Scenario: Place Order of the items
When User Place ordering 
Then Item must be Placed