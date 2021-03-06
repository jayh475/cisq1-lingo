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


      Scenario: Cannot start a round if still guessing
        Given I am playing a game
        And I am still guessing a word
        Then I cannot start a new round


        Scenario: Cannot start a round if eliminated
          Given I am playing a game
          And I have been eliminated
          Then I cannot start a new round

          Scenario: Cannot guess word if round not started
            Given I am playing a game
            And the round was won
            Then I cannot guess the word


  Scenario Outline: Guessing a word
    Given a "<word>" to guess
    When I  attempt to "<guess>" the word
    Then I expect to get "<feedback>" with "<shown hint>"


  Examples:
  | word | shown hint               | guess       | feedback                                             |
  | BAARD| 'B', '.', '.', '.', '.'  | BERGEN      | Throws Exception|
  | BAARD| 'B', '.', '.', '.', '.'  | BONJE       | CORRECT, ABSENT, ABSENT, ABSENT ,ABSENT              |
  | BAARD| 'B', '.', '.', '.', '.'  | BARST       | CORRECT, CORRECT, PRESENT, ABSENT, ABSENT            |
  | BAARD| 'B', 'A', '.', '.', '.'  | DRAAD       | ABSENT, PRESENT, CORRECT, PRESENT, CORRECT           |
  | BAARD| 'B', 'A', 'A', '.', 'D'  | BAARD       | CORRECT, CORRECT, CORRECT, CORRECT, CORRECT          |


Scenario: Player is eliminated after 5 incorrect guesses
  Given I am playing a game
  And the word to guess is "school"
  When I guess "towers"
  And I guess "towers"
  And I guess "towers"
  And I guess "towers"
  And I guess "towers"
  Then I should be eliminated


  Scenario Outline: Score increases based on number of attempts
    Given I am playing a game
    And the score is "<current score>"
    And the word to guess is "<school>"
    Examples:
      |  |


