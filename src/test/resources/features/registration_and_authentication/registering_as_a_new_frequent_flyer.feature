Business Need: Registering as a new Frequent Flyer

  New Frequent Flyer members need to register in order to book a flight.

  Rule: Customers must register to be able to use the Frequent Flyer members area
    Example: Trevor registers as a Frequent Flyer member
      Given Trevor does not have a Frequent Flyer account
      When Trevor registers as a Frequent Flyer member
      Then he should be able to log on to the Frequent Flyer application

  Rule: Duplicate usernames are not allowed
    Rule: Duplicate accounts with the same email address are not allowed

    Example: Someone tries to register with an email that is already used
    Trevor is an existing Frequent Flyer member.
    His wife Candy does not have a Frequent Flyer account

      Given Trevor has registered as a Frequent Flyer member
      When Candy tries to register with the same email
      Then she should be notified that "Email exists, please try another name"

  Rule: The unique username should be a valid email address

    @current
    Scenario Outline: Only correctly structured emails should be accepted
      Given Candy does not have a Frequent Flyer account
      When Candy tries to register with an email of "<email>"
      Then she should be told "Not a valid email format"
      Scenarios:
        | email        |
        | not-an-email |
        | notemail.com |
#        | candy@#.com  |

    Scenario: Email addresses need to be well formed
      Given Candy does not have a Frequent Flyer account
      When Candy wants to register a new Frequent Flyer account
      Then the following emails should not be considered valid:
        | Email        | Message                  | Reason Rejected   |
        |              | Please enter your email  | Cannot be empty   |
        | not-an-email | Not a valid email format | Missing @ section |
        | wrong.com    | Not a valid email format | Missing @         |
        | wrong@       | Not a valid email format | Missing domain    |
#        | wrong@#.com  | Not a valid email format | Invalid characters |

  Rule: New members need to complete all the mandatory fields and approve the terms & conditions
    Scenario: Candy fails to enter to enter a mandatory field
      Given Candy does not have a Frequent Flyer account
      When Candy wants to register a new Frequent Flyer account
      Then the following information should be mandatory to register:
        | Field     | Error Message If Missing     |
        | email     | Please enter your email      |
        | password  | Please enter your password   |
        | firstName | Please enter your first name |
        | lastName  | Please enter your last name  |
        | address   | Please enter your address    |
        | country   | Please enter a valid country |

    Example: Candy forgets to agree to the Terms and Conditions
      Given Candy does not have a Frequent Flyer account
      When Candy tries to register without approving the terms and conditions
      Then she should be told "Please confirm the terms and conditions to continue"


  Rule: New members need to complete all the mandatory fields and approve the terms & conditions
    Scenario: Candy fails to enter to enter a mandatory field
      Given Candy does not have a Frequent Flyer account
      When Candy wants to register a new Frequent Flyer account
      Then the following information should be mandatory to register:
        | email     |
        | password  |
        | firstName |
        | lastName  |
        | address   |
        | country   |

    Example: Candy forgets to agree to the Terms and Conditions
      Given Candy does not have a Frequent Flyer account
      When Candy tries to register without approving the terms and conditions
      Then she should be told "Please confirm the terms and conditions to continue"


#  Rule: A valid password contains at least 8 characters, a digit and a punctuation mark
#    Background:
#      Given Fred does not have a Frequent Flyer account
#
#    Scenario: A valid password
#      When Fred tries to register using a password of "Alphab3tSoup!"
#      Then then his password should be considered valid
#
#    Example: The password should be at least 8 characters
#      When Fred tries to register using a password of "Sh0rt!"
#      Then then his password should be rejected
#
#    Example: The password should contain at least 1 digit
#      When Fred tries to register using a password of "Alphabet!"
#      Then then his password should be rejected
#
#    Example: The password should contain at least 1 punctuation mark
#      When Fred tries to register using a password of "Alphab3t"
#      Then then his password should be rejected
#
#    Example: The password should contain at least 1 punctuation mark
#      When Fred tries to register using an invalid password
#      Then then his password should be rejected
#      And he should be presented with an error message describing the password rules
#
#  Rule: A valid password contains at least 8 characters, a digit and a punctuation mark
#    Scenario Outline:
#      Given Fred does not have a Frequent Flyer account
#      When Fred tries to register using a password of "<password>"
#      Then then his password should be considered <valid or invalid>
#      Scenarios:
#        | password      | valid or invalid | reason                            |
#        | Alphab3tSoup! | valid            | A valid password                  |
#        | Sh0rt!        | invalid          | Should be at least 8 characters   |
#        | Alphabet!     | invalid          | Should contain a digit            |
#        | Alphab3t      | invalid          | Should contain a punctuation mark |
#
#    Example: Users should be shown an appropriate error message
#      Given Fred does not have a Frequent Flyer account
#      When Fred tries to register using an invalid password
#      Then then his password should be rejected
#      And he should be presented with an error message describing the password rules
