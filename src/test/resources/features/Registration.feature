Feature: Registrácia - Úvodný formulár

   Scenario Outline: Validácia E-mailu - neplatné formáty
    Given používateľ otvorí registračnú stránku
    When zadá meno "Darren"
    And zadá neplatný e-mail "<email>"
    And klikne na tlačidlo Signup
    Then zobrazí sa chybová hláška Invalid email address

    Examples:

    | TestCaseId | email                  |
    | TC_001     | darrentest.com         |
    | TC_002     | darren@test            |
    | TC_003     | @test.com              |
    | TC_004     |                        |
    | TC_006     | a.@@b.com              |
    | TC_007     | darren test@a.com      |
    | TC_008     | aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@test.com|
    | TC_010     | abcd!@#%&*()@test.com  |