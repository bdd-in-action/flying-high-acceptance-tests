Feature: Earning More Points

  Frequent Flyers earn status points each time they fly.
  As they earn more points, their status level increases and they get more benefits.
  For example:

  @journey-scenario
  @current
  Example: Stan goes from Standard to Silver after he makes several more trips
    Given Stan has logged onto the Frequent Flyer application as a new member
    When he books the following flights:
      | From   | To          | Travel Class | Trip Type | Notes           |
      | London | New York    | Business     | Return    |                 |
      | London | Los Angeles | Business     | Return    | Gets to BRONZE  |
      | Sydney | Hong Kong   | Economy      | Single    | Earns 25% extra |
    Then his booking history should contain:
      | Departure   | Destination | Points Earned |
      | London      | New York    | 250           |
      | New York    | London      | 250           |
      | London      | Los Angeles | 250           |
      | Los Angeles | London      | 250           |
      | Sydney      | Hong Kong   | 125           |
    And his account status should become:
      | Point Balance | Status Level |
      | 1125          | BRONZE       |
