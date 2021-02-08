Feature: Start new game
  As a user,
  I want to start a new game by pressing a button,
  In order to practice lingo.

  Scenario: a new game is started
    When I click on the "Start Game" button
    Then I should see a textfield where I can put word in
    And I should see a tabel with 5 rows that contains 5,6 or 7 cells
    And In the first row there should be a random word with 1 letter red
    And 1 letter yellow




