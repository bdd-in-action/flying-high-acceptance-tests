Business Need: Searching for flights

  Frequent Flyer members can look for flights to different destinations to help plan their journeys

  Background:
    Given Amy is a registered Frequency Flyer member
    And she has logged on with a valid username and password

  Rule: Travellers must provide at least departure, destination and travel class
    Example:
      When Amy tries to searche for flights with the following crtieria
        | From   | To          | Travel Class | Trip Type |
        | Sydney | Hong Kong   | Economy      | Single    |
      Then the


#
#
#  Rule: Members can see the history of their flights
#    Example: Trevor views his flight history for a single flight
#      When Trevor books the following flights:
#        | From   | To          | Travel Class | Trip Type |
#        | Sydney | Hong Kong   | Economy      | Single    |
#      Then his booking history should contain:
#        | Departure   | Destination | Points Earned |
#        | Sydney      | Hong Kong   | 100           |
#
#    Example: Trevor views his flight history for a return flight
#      When Trevor books the following flights:
#        | From   | To          | Travel Class | Trip Type |
#        | London | New York    | Business     | Return    |
#      Then his booking history should contain:
#        | Departure   | Destination | Points Earned |
#        | London      | New York    | 250           |
#        | New York    | London      | 250           |
