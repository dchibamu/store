# API Endpoints

| Method | Endpoint    | Description                |
|--------|-------------|----------------------------|
| GET    | `/order`    | Get all orders             |
| POST   | `/order`    | Create a new order         |
| GET    | `/customer` | Get all customers          |
| POST   | `/customer` | Create a new customer      |

## Performance optimisation for GET endpoints

1. Can implement spring caching at service layer
2. Introduce pagination 
3. Can also introduce content compression.
    - This helps with reducing payload and therefore speeds up request/response time.
4. Can also implement asynchronous endpoints.