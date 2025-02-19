# Wallet API

## Description
The Wallet API is a RESTful service designed for managing digital wallets. It provides functionalities to create wallets, check balances, view transaction history, deposit funds, withdraw funds, and transfer funds between wallets.

---

## REST Endpoints

### Wallet Management

## Wallet Creation

**POST** `/wallets`  
  
- **Description:** Creates a new wallet.  
  
- **Request Body:**
  ```json
  {
    "userId": 1,
  }
  ```
  
## Retrieve Balance

**GET** `/wallets/{walletId}/balance`

**Description:** Retrieves the current balance of a wallet.

---

## Retrieve Balance History

**GET** `/wallets/{walletId}/balance/history?from={MM/dd/yyyy HH:mm:ss}&to={MM/dd/yyyy HH:mm:ss}`

**Description:** Retrieves the balance history of a wallet within a specified time range.

**Query Parameters:**
- `from`: Start date and time in the format `MM/dd/yyyy HH:mm:ss`.
- `to`: End date and time in the format `MM/dd/yyyy HH:mm:ss`.

---

## Transactions

### Deposit Funds

**POST** `/wallets/deposit`

**Description:** Deposits funds into a wallet.

**Request Body:**

```json
{
  "walletId": 1,
  "amount": 100.0
}
```

### Withdraw Funds

**POST** /wallets/withdraw

**Description:** Withdraws funds from a wallet.

**Request Body**:

```json
{
"walletId": 1,
"amount": 50.0
}
```

### Transfer Funds

**POST** /wallets/transfer

**Description**: Transfers funds between wallets.

**Request Body**:

```json
{
"fromWalletId": 1,
"toWalletId": 2,
"amount": 25.0
}
```

## Important Links

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Database Console**: http://localhost:8080/h2-console

- **Default Credentials**:

  - JDBC URL: jdbc:h2:mem:testdb
  
  - User: wallet
  
  - Password: wallet

## How to Run the Project

**Prerequisites**

- Java 21
- Gradle

### Steps
- Clone the repository:
 
```shell
git clone https://github.com/your-username/your-repository.git
```
```shell 
cd adapter
```

- Build the project:

```shell
./gradlew build
```

- Start the application:

```shell
./gradlew bootRun
```

Access the application at http://localhost:8080.

**Technologies Used**

- Java 21
- Spring Boot
- Gradle
- H2 Database
- Swagger
