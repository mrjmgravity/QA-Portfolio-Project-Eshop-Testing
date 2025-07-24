Feature: Registrácia - Úvodný formulár

   Scenario: TC_001 - E-mail neobsahuje znak @
    Given používateľ otvorí registračnú stránku
    When zadá meno "Darren"
    And zadá neplatný e-mail "darrentest.com"
    And klikne na tlačidlo Signup
    Then zobrazí sa chybová hláška Invalid email address

    Scenario: TC_002 - E-mail neobsahuje doménu
     Given používateľ otvorí registračnú stránku
    When zadá meno "Darren"
    And zadá neplatný e-mail "darren@test"
    And klikne na tlačidlo Signup
    Then zobrazí sa chybová hláška Invalid email address