@txn
Feature: User Dao
  This feature verifies the functionality of the UserDao

  Scenario: One User in DB
    Given new User with name "Herbert" and password "password" is saved to DB
    When I search for User with ID 1 returned name is "Herbert"
    Then total number of DB entries is 1
