# API Endpoints
| Method  | Endpoint                          | Description                                  |
|---------|-----------------------------------|----------------------------------------------|
| GET     | `/customer`                       | Get all customers                            |
| GET     | `/customer/search?query={query}`  | Search customers by query                    |
| POST    | `/customer`                       | Create a new customer                        |
| GET     | `/customer/{customerId}/orders`   | Get all orders for a specific customer       |
| GET     | `/products`                       | Get all products                             |
| GET     | `/products/{id}`                  | Get a specific product by ID                 |
| POST    | `/products`                       | Create a new product                         |
| GET     | `/order`                          | Get all orders                               |
| GET     | `/order/{id}`                     | Get a specific order by ID                   |
| POST    | `/order`                          | Create a new order                           |
| POST    | `/order/{orderId}/products/{productId}` | Add a product to an order             |
| DELETE  | `/order/{orderId}/products/{productId}` | Remove a product from an order        |

## Performance optimisation for GET endpoints

1. Can implement spring caching at service layer
2. Introduce pagination 
3. Can also introduce content compression.
    - This helps with reducing payload and therefore speeds up request/response time.
4. Can also implement asynchronous endpoints.

## Pipeline considerations
   - You need to have code static analysis tools to catch potential bugs
   - You need to embed software composition analysis tools, container and infrastructure security.
   - Basically install tools like SonarCube on the pipeline, code quality and automated testing capability