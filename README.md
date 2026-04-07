# Java Shopping Cart

JavaFX shopping cart application with MariaDB-backed localization strings.

## Clone and Build

```bash
git clone <your-repo-url>
cd java_shopping_cart
mvn clean install
```

## Database and Localization Setup

The UI strings are loaded from the table `localization_strings` in the database
`shopping_cart_localization`.

### 1. Start MariaDB

Make sure your MariaDB server is running locally on port `3306`.

### 2. Create schema and tables

Run the schema script:

- run src/main/java/org/example/java_shopping_cart/db/setup/database.sql

This creates:

- `shopping_cart_localization` database
- `cart_records`
- `cart_items`
- `localization_strings`

### 3. populate the localization_string table

- run src/main/java/org/example/java_shopping_cart/db/setup/localization_string.sql

Languages included by default:

- `en` (English)
- `fi` (Finnish)
- `ja` (Japanese)
- `sv` (Swedish)
- `ar` (Arabic)

### 4. Configure DB connection credentials

Update db credentials in:

`src/main/java/org/example/java_shopping_cart/db/DataBaseConnection.java`

set your database url, user and password

## Run the App

```bash
mvn javafx:run
```

## Run Tests

```bash
mvn test
```

## Notes

- If DB connection fails, the app falls back to built-in English strings.
- language keys in the DB should match ISO language codes (example: `en`, `fi`, `ja`, `ar`, `sv`).
