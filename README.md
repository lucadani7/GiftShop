# Gift Shop

This repository contains a Java-based solution for the "Gift Shop" puzzle. The challenge involves identifying "invalid" product IDs within specific numerical ranges based on pattern repetition rules.

## 📝 Problem Description

One of the gift shop computers has been filled with invalid product IDs. Our task is to calculate the sum of all invalid IDs found in the given ranges.

The problem is divided into two parts:

* **Part 1:** An ID is invalid if it is made of a sequence of digits repeated exactly twice (e.g., `123123`, `11`, `6464`).
* **Part 2:** An ID is invalid if it is made of a sequence of digits repeated **at least twice** (e.g., `123123123`, `12121212`, `1111111`).

## 🚀 Features

* **Dual Solution:** Solves both Part 1 and Part 2 in a single pass.
* **Performance Optimized:** Uses `Predicate<String>` to avoid redundant string conversions.
* **Robust Input Handling:**
    * Reads from `input.txt`.
    * Falls back to default example data if the file is missing or empty.
    * Handles reversed ranges (e.g., `12-10` is treated as `10-12`).
    * Skips malformed data without crashing.
* **Unit Tested:** Comprehensive test suite using JUnit 5.

## 🛠️ Technologies Used

* **Java 21**
* **Maven** (Build Tool)
* **JUnit 5** (Testing Framework)

## 📦 How to Run

### Prerequisites
* Java 21 or higher installed.
* Maven installed.

### Execution
To run the main application:
```bash
mvn compile exec:java -Dexec.mainClass="com.lucadani.GiftShop"
```
### Running tests
Run the tests with:
```bash
mvn test
```
