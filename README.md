Store has to support following methods: 
 
1. Register new user. 
Respond with an appropriate HTTP codes (200 for ok, 409 for existing user)
Your app must not store password as plain text, use some good approach to identify user.\
**Request path:** `/auth/register` \
**Example request:** 
`{
    email: ”admin@gmail.com”,
    password: ”admin”
}`
 
2. Login into system.
Respond with JSON containing sessionId.
*(optional) Think about preventing an intruder from bruteforcing. \
**Request path:** `/auth/login`\
**Example request:** 
`{
    email: ”admin@gmail.com”,
    password: ”admin”
}`

3. *(optional) Reset password.
 
4. Get all products in store.
Respond with JSON list of items you have.\
**Request path:** `/store/getProducts`\
**Example response:**
`{
    "products": [
        {
            "id": 1,
            "title": "Product1",
            "price": 20.1,
            "quantity": 0
        }
    ]
} `

5. Add item to cart. 
Allow adding only one position at a time. If you don’t have this quantity in store - respond with an error. The information has to be session-scoped: once session expires - user will get new empty cart.
**Request path:** `cart/addProduct`\
**Example request:** \
`{
    id: ”363”,
    quantity: ”2”
}
`
6. Display your cart content.
Respond with list of product names with their quantities added. Calculate subtotal. Assign an ordinal to each cart item. \
**Request path:** `/cart/getProducts`\
**Example response:**
`{
    "total": 100,
    "products": [
        {
            "quantity": 1,
            "subtotal": 100,
            "title": "Product3",
            "ordinal": 1
        }
    ]
}`

7. Remove an item from user’s cart.\
**Request path:** `/cart/removeProduct`

8. Modify cart item. Example request: {“id”:2, quantity: 3} - user should be able to modify number of some items in his cart.\
**Request path:** `/cart/addProduct `
 
9. Checkout: verify your prices in cart, ensure you still have desired amount of goods. If all is good - send a user confirmation about successful order. \
 **Request path:** `/cart/checkout`
 
10. *(optional) Cancel order: return all products from order back to available status. 
 
11. *(optional) Get user’s order list. Should contain order id, date, total, status.