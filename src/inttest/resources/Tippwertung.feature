@cleanData @tippwertung
Feature: Tippwertung

  Scenario: Ergebnis zu bisherigem Stand addieren bei korrekter Tendenz Tipp
    Given ich habe 12 Punkte
    And ich tippe "3:1"
    When das Spiel mit Ergebnis "2:1" endet
    Then mein Punktestand wurde mit 13 berechnet

  Scenario: Ergebnis zu bisherigem Stand addieren bei korrekter Tendenz und Differenz Tipp
    Given ich habe 12 Punkte
    And ich tippe "3:2"
    When das Spiel mit Ergebnis "2:1" endet
    Then mein Punktestand wurde mit 14 berechnet

  Scenario: Ergebnis zu bisherigem Stand addieren bei korrektem Ergebnis Tipp
    Given ich habe 12 Punkte
    And ich tippe "2:1"
    When das Spiel mit Ergebnis "2:1" endet
    Then mein Punktestand wurde mit 15 berechnet

  Scenario Outline: Alle Wertungsmöglichkeiten
    Given Folgende Tipps sind für ein Spiel eingegangen:
      | tipper  | ergebnis |
      | Ronaldo | 0:1      |
      | Messi   | 1:1      |
    When das Spiel mit Ergebnis "<Ergebnis>" endet
    Then es wurden folgende Scores berechnet:
      | tipper  | score     |
      | Ronaldo | <Ronaldo> |
      | Messi   | <Messi>   |

    Examples:
      | Ergebnis | Ronaldo | Messi |
      | 0:1      | 3       | 0     |
      | 1:1      | 0       | 3     |
      | 7:1      | 0       | 0     |
      | 1:2      | 2       | 0     |
      | 0:2      | 1       | 0     |
      | 0:0      | 0       | 2     |
