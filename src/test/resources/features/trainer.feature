Feature: Start new game
  As a user,
  I want to start a new game by pressing a button,
  In order to practice lingo.

  Scenario: Start a new game
    When I click on the "Start Game" button
    Then I should see a textfield where I can put word in
    And I should see a tabel with 5 rows that contains 5,6 or 7 letter words
    And In the first row there should be a random word with the first letter known


    Scenario Outline: start a new round
      Given I am playing a game
      And the round was won
      And The last word had "<previous length>" letters
      When I start a new round
      Then the word to guess has "<next length>" letters

      Examples:
      | previous length | next length |
      | 5               |6            |
      |6                |7            |
      |7                |5            |

      #Failure path
      Given I am playing a game
      And the round was lost
      Then I cannot start a new round


Scenario: Guessing a word
  Given a "<word>" to guess


  Scenario Outline: Guessing a word
    Given a "<word>" to guess
    When I  attempt to "<guess>" the word
    Then I expect to get "<feedback>" with "<shown hint>"


  Examples:
  | word | shown hint               | guess       | feedback                                             |
  | BAARD| 'B', '.', '.', '.', '.'  | BERGEN      | INVALID, INVALID, INVALID, INVALID, INVALID, INVALID |
  | BAARD| 'B', '.', '.', '.', '.'  | BONJE       | CORRECT, ABSENT, ABSENT, ABSENT ,ABSENT              |
  | BAARD| 'B', '.', '.', '.', '.'  | BARST       | CORRECT, CORRECT, PRESENT, ABSENT, ABSENT            |
  | BAARD| 'B', 'A', '.', '.', '.'  | DRAAD       | ABSENT, PRESENT, CORRECT, PRESENT, CORRECT           |
  | BAARD| 'B', 'A', 'A', '.', 'D'  |BAARD        | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT          |


