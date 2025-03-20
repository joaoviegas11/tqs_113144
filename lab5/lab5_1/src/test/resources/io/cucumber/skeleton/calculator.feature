Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
    When I add 4 and 5
    Then the result is 9

  Scenario: Substraction
    When I substract 7 to 2 
    Then the result is 5

  Scenario: Multiply
    When I multiply 71 to 2 
    Then the result is 142



  Scenario: Error by division by zero
    When I divide 45 by 0
    Then Error division by zero

  Scenario Outline: Several divides
    When I divide <aa> by <bb>
    Then the result is <cc>

  Examples: Single digits2
    | aa | bb | cc  |
    | 110 | 2 | 55  |
    | 16 | 2 | 8 |

  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>

  Examples: Single digits
    | a | b | c  |
    | 11 | 2 | 13  |
    | 3 | 7 | 10 |