
# Stock Backend API 

This application serves as a backend server for the stock exchange app
[link to the app](https://stock-exchange-app-zeta.vercel.app/)

This server handles player accounts, company stock prices, and trading operations with persistent storage using MongoDB Atlas.

## Tech Stack 
- Spring Boot
- MongoDB Atlas

## Architecture 
React Frontend -> Spring Boot API -> MongoDB Atlas 


## API Endpoints 
### Player 
- `GET /player/{name}/name`
- `POST /player`

### Company 
- `GET /company`
- `PUT /company/{id}/price`

### Trading 
- `PUT /trade/{id}/buy`
- `PUT /trade/{id}/sell`

### Transactions
- `PATCH /{id}/addTransaction`
- `PATCH /{id}/deleteTransaction/{transactionId}`

## Example request 
`POST /player`

```json
{
    "name": "Shankar",
    "password": "123",
    "money": 300000,
    "stocksHeld": [
       { 
        "companyName": "Avionics",
        "numOfStocks": 0
        },
        { 
        "companyName": "Cybraxo Technologies",
        "numOfStocks": 0
        }
    ],
    "transactions": []
}
```

`PATCH /{id}/addTransaction`

```json 
{
 
    "trade": "BOUGHT",
    "company": "Nuvanta Financial Core",
    "sharesTraded": 435,
    "price": 27.98,
    "totalMoney": 531.67   
}
```

## Environment variables
```
MONGO_URI=mongodb+srv://shankarnarayan:<db_password>@cluster0.99ssduz.mongodb.net/?appName=Cluster0
```
(Do not commit real credentials to GitHub.)

## How to run locally 
### 1. Clone the repository
```bash
git clone https://github.com/Shankarc98/stock-backend`
```

### 2. Set Environment Variable 
`MONGO_URI=mongodb+srv://shankarnarayan:<db_password>@cluster0.99ssduz.mongodb.net/?appName=Cluster0`

### 3. Run the Application 
`mvn spring-boot:run`


## Deployment

Backend is deployed on Render 

## Base URL 
`https://stock-backend-k87i.onrender.com/`

## Author 
Shankar Narayan
