# 📚 **Book Store Project**

## 📝 Description 
The "Online Book Store" web application is developed using Spring Boot to create RESTful APIs that facilitate 
interaction between users (USER) and administrators (ADMIN) with the bookstore's database. 
The project demonstrates the application of modern authentication technologies and data management, 
simulating the core functionalities of e-commerce
in the book industry.

## Project Overview
The Book Store API offers the following functionalities:
* Roles: Defines two user roles: ADMIN and USER.
* ADMIN Book Management: Includes adding, removing, editing book information, and categorizing books.
* USER Shopping Cart: Allows users to select multiple books with different quantities before ordering.
* ADMIN Order Management: Enables saving and status tracking of orders.

## 📋 Technology Stack

### Core
|         |                Description                |
|:-------:|:-----------------------------------------:|
| Java 17 | Core programming language of the backend  | 
|  Maven  |     Project management and build tool     |

### Spring
|                        |                       Description                        |
|:----------------------:|:--------------------------------------------------------:|
|   Spring Boot 3.1.5    |     Architecture framework for building applications     |
|    Spring Data JPA     | Simplifies database access operations using JPA and ORM  |
|  Spring Boot Security  |  Provides authentication and authorization capabilities  |
| Spring Boot Validation |    Ready-to-use collection of data constraints/checks    |

### Database & Migrations
|              |                   Description                   |
|:------------:|:-----------------------------------------------:|
| MySQL 8.0.33 |           Database management system            |
|  Liquibase   | 	Tool for database creation and version control |

### Testing 
|                |             Description             |
|:--------------:|:-----------------------------------:|
|    JUnit 5     |       Unit testing framework        |
| Testcontainers | Containerized testing environments  |
|    Mockito     | Mocking frameworks for unit testing |

### Additions tools & libraries
|                |                Description                |
|:--------------:|:-----------------------------------------:|
|     Lombok     |   Library for Java code simplification    |
|   MapStruct    |       Tool for simple data mapping        |
|      JWT       |          Authorization standard           |
|     Docker     |           Containerization tool           |
| Docker Compose | Orchestrates multi-container applications |
|    Swagger     |     Tools to create API documentation     |


## 📌 Endpoints

Some endpoints require a [role] for access, use JWT token (Bearer) or Basic authentication.

### UserController: Handles registration and login requests.
* POST: /api/auth/registration - register new user.
* POST: /api/auth/login - login user and receive JWT token.

### BookController: Handles requests for book operations (Authorization is required).
* GET: /api/books - Receive all books with optional pagination.
* GET: /api/books/{id} - Search a specific book by ID.
* GET: /api/search - Fiter books by symbols contained in title or author name with optional pagination.
* POST: /api/books - Create new book. [Admin]
* PUT: /api/books/{id} - Update book data. [Admin]
* DELETE: /api/books/{id} - Soft delete book. [Admin]


### CategoryController: Handles requests for category operations and getting all books by category(Authorization is required).

* GET: /api/categories - Receive all categories.
* GET: /api/categories/{id} - Receive a specific category by its ID.
* GET: /api/categories/{id}/books - Receive all books of category by a category ID.
* POST: /api/categories - Create a new category. [Admin]
* PUT: /api/categories/{id} - Update book data. [Admin]
* DELETE: /api/categories/{id} - Soft delete category. [Admin]

### ShoppingCartController: Handles requests for shopping cart operations (Authorization is required).
* GET: /api/cart - Receive all items from a shopping cart.
* POST: /api/cart - Add an item to a shopping cart.
* PUT: /api/cart/cart-items/{cartItemId} - Update quantity of a specific item in shopping cart.
* DELETE: /api/cart/cart-items/{cartItemId} - Delete items from a shopping cart.

### OrderController: Handles requests for order operations.
* GET: /api/orders - Receive all user orders.
* GET: /api/orders/{order-id}/items - Receive all items from a specific order.
* GET: /api/orders/{order-id}/items/{item-id} - Receive a specific item from a specific order.
* POST: /api/orders - Create an order.
* PATCH: /api/orders/{id} - Updating an order status. [Admin] Allowed order status values are: COMPLETED, DELIVERED, PENDING
