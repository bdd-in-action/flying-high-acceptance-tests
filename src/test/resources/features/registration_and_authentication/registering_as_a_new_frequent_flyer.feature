@current
Business Need: Registering as a new Frequent Flyer

  New Frequent Flyer members need to register to book a flight.

  Rule: Customers must register to be able to use the Frequent Flyer members area
    Example: Trevor registers as a Frequent Flyer member
      Given Trevor does not have a Frequent Flyer account
      When Trevor registers as a Frequent Flyer member
      Then he should be able to log on to the Frequent Flyer application

  Rule: Frequent Flyer members can view their status in the My Accounts section
    Example: Trevor views his Frequent Flyer account status
      Given Trevor has registered as a Frequent Flyer member
      When he logs on to the Frequent Flyer application
      Then he should have a Frequent Flyer account with:
        | Status Level | STANDARD |
        | Points       | 0        |

  Rule: The unique username should be a valid email address
    Scenario Outline: Only correctly structured emails should be accepted
      Given Candy does not have a Frequent Flyer account
      When Candy tries to register with an email of "<email>"
      Then she should be told "Not a valid email format"
      Examples:
        | email        |
        | not-an-email |
        | notemail.com |
        | candy@#.com  |

    Scenario: Email addresses need to be well formed
      Given Candy does not have a Frequent Flyer account
      When Candy wants to register a new Frequent Flyer account
      Then the following emails should not be considered valid:
        | Email        | Message                  | Reason Rejected    |
        | not-an-email | Not a valid email format | Missing @ section  |
        | wrong.com    | Not a valid email format | Missing @          |
        | wrong@       | Not a valid email format | Mission domain     |
        | wrong@#.com  | Not a valid email format | Invalid characters |
        |              | Please enter your email  | Cannot be empty    |

  Rule: Duplicate usernames are not allowed
    Example: Someone tries to register with an email that is already used
    Trevor is an existing Frequent Flyer member.
    His wife Candy does not have a Frequent Flyer account
      Given Trevor has registered as a Frequent Flyer member
      When Candy tries to register with the same email
      Then she should be notified that "Email exists, please try another name"

  Rule: New members need to complete all the mandatory fields and approve the terms & conditions
    Scenario: Mandatory fields for registration
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

    Scenario: Customers must agree to the terms and conditions before registering
      Given Candy does not have a Frequent Flyer account
      When Candy tries to register without approving the terms and conditions
      Then she should be told "Please confirm the terms and conditions to continue"

