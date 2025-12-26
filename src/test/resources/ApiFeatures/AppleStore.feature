@AppleStore @API @Regression
Feature: Apple Store API - Product Orders and Customer Management
  As an Apple Store backend system
  I want to manage customer accounts, product orders, and support tickets
  So that customers can purchase products and receive support seamlessly

  Background:
    Given the Apple Store API is available at "https://gorest.co.in/public/v2"
    And I have valid Apple Store API credentials

  @Smoke @CustomerManagement
  Scenario: Create new Apple ID customer account
    Given I want to create a new Apple customer account
    When I submit a POST request to "/users" with customer data from "customer1"
    Then the response status code should be 201
    And the response should contain a unique customer ID
    And the customer email should match the test data
    And the account status should be "active"

  @Smoke @CustomerManagement @DataDriven
  Scenario: Create new Apple ID customer account with inline data
    Given I want to create a new Apple customer account
    When I submit a POST request to "/users" with the following customer details:
      | name        | email                     | gender | status |
      | Tim Cook Jr | tim.cook.test@apple.com   | male   | active |
    Then the response status code should be 201
    And the response should contain a unique customer ID
    And the customer email should be "tim.cook.test@apple.com"
    And the account status should be "active"

  @OrderManagement
  Scenario: Customer places order for iPhone 15 Pro Max
    Given a registered Apple customer exists with ID "12345"
    When the customer creates a new order with POST request to "/posts":
      | user_id | title                      | body                                                                  |
      | 12345   | Order: iPhone 15 Pro Max   | Product: iPhone 15 Pro Max 256GB Natural Titanium, Price: $1199.00    |
    Then the response status code should be 201
    And the response should contain an order ID
    And the order should be linked to customer ID "12345"
    And the order title should contain "iPhone 15 Pro Max"

  @OrderManagement
  Scenario Outline: Place multiple product orders from Apple Store
    Given a registered Apple customer with ID "<customer_id>"
    When the customer orders "<product>" with the following details:
      | title   | <order_title>   |
      | body    | <order_details> |
      | user_id | <customer_id>   |
    Then the order should be created successfully
    And the order confirmation should be sent

    Examples:
      | customer_id | product          | order_title              | order_details                                           |
      | 12345       | MacBook Pro      | Order: MacBook Pro 16"   | M3 Max chip, 64GB RAM, 2TB SSD, Space Black - $3999.00 |
      | 12345       | AirPods Pro      | Order: AirPods Pro 2     | USB-C, Active Noise Cancellation - $249.00              |
      | 12345       | Apple Watch      | Order: Apple Watch Ultra | Titanium, Ocean Band, GPS + Cellular - $799.00          |
      | 67890       | iPad Pro         | Order: iPad Pro 12.9"    | M2 chip, 512GB, Space Gray, Magic Keyboard - $1899.00   |

  @CustomerSupport
  Scenario: Customer adds support comment on existing order
    Given an existing order with ID "98765"
    And the order belongs to customer ID "12345"
    When the customer posts a support comment to "/comments":
      | post_id | name             | email                     | body                                              |
      | 98765   | Tim Cook Jr      | tim.cook.test@apple.com   | My iPhone hasn't arrived yet. Can I track it?     |
    Then the response status code should be 201
    And the comment should be linked to order ID "98765"
    And the support team should receive the notification

  @CustomerSupport @Priority
  Scenario: AppleCare warranty claim submission
    Given a customer with Apple ID "12345"
    And the customer owns a device with order ID "98765"
    When the customer submits a warranty claim comment:
      | post_id | 98765                                                           |
      | name    | Tim Cook Jr                                                     |
      | email   | tim.cook.test@apple.com                                         |
      | body    | Screen has dead pixels. Device under AppleCare+. Request repair |
    Then the warranty claim should be created
    And the claim status should be "pending"
    And a Genius Bar appointment should be available

  @TaskManagement
  Scenario: Apple Support creates internal task for order issue
    Given an Apple Support agent is logged in
    And there is a customer issue for order "98765"
    When the agent creates an internal task with POST to "/todos":
      | user_id | title                              | status  | due_on                    |
      | 12345   | Investigate delayed iPhone delivery | pending | 2025-12-31T23:59:59.000Z |
    Then the task should be created successfully
    And the task should be assigned to customer service team
    And the due date should be "2025-12-31"

  @OrderUpdate @Returns
  Scenario: Customer initiates return for MacBook Pro
    Given a customer with ID "12345" has an existing order "54321"
    And the order contains "MacBook Pro 16"
    When the customer updates the order status with PUT request to "/posts/54321":
      | user_id | 12345                                                   |
      | title   | Return Request: MacBook Pro 16"                         |
      | body    | Reason: Changed mind within 14 days. Original packaging |
    Then the response status code should be 200
    And the order status should be updated to "Return Requested"
    And a prepaid return label should be generated

  @DataValidation
  Scenario: Verify Apple customer profile completeness
    Given I retrieve customer details for Apple ID "12345" with GET request
    When the response is received
    Then the customer profile should contain:
      | field  | validation           |
      | id     | not null             |
      | name   | not empty            |
      | email  | matches Apple format |
      | gender | male or female       |
      | status | active or inactive   |

  @Inventory @ProductCatalog
  Scenario: Check available iPhone models in inventory
    When I send a GET request to "/posts" with query parameter "?page=1"
    Then the response should contain a list of products
    And each product should have:
      | title       |
      | description |
      | user_id     |
      | id          |
    And the response should be paginated

  @Security @Authentication
  Scenario: Unauthorized request without Apple API token
    Given I do not have a valid Bearer token
    When I attempt to POST a new order to "/posts"
    Then the response status code should be 401
    And the error message should indicate "Authentication failed"

  @NegativeTesting
  Scenario: Attempt to create customer with duplicate email
    Given a customer already exists with email "tim.cook.test@apple.com"
    When I try to create another customer with the same email
    Then the response status code should be 422
    And the error should indicate "Email has already been taken"

  @PerformanceTest
  Scenario: Bulk order processing during Apple Event launch
    Given the Apple Store is experiencing high traffic
    When 100 customers simultaneously place orders for "iPhone 15 Pro"
    Then all orders should be processed within 5 seconds
    And no orders should be lost
    And the API response time should be under 500ms per request

  @Integration
  Scenario: Complete customer journey - Registration to Purchase to Support
    # Step 1: Create Apple ID
    Given I create a new Apple customer account
    Then the customer account should be active

    # Step 2: Place order
    When the customer orders an "iPhone 15 Pro Max"
    Then the order should be confirmed

    # Step 3: Track order
    When the customer checks order status
    Then the order status should be "Processing"

    # Step 4: Contact support
    When the customer posts a support inquiry about delivery time
    Then the support ticket should be created

    # Step 5: Support creates task
    When Apple Support creates a task to investigate
    Then the task should be assigned and tracked