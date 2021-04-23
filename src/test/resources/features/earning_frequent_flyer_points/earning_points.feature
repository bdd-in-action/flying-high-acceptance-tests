Feature: Earning Points

  Frequent Flyers earn status points each time they fly.
  As they earn more points, their status level increases and they get more benefits.

  Rule: Members achieve new status levels when they earn sufficient points
    Scenario Outline: Earning status levels from points earned
      Given Stan is a new Frequent Flyer Member
      When he has earned between <Min Points> and <Max Points> points
      Then his status should become <Status Level>

      Examples:
        | Min Points | Max Points | Status Level |
        | 0          | 999        | STANDARD     |
        | 1000       | 1999       | BRONZE       |
        | 2000       | 4999       | SILVER       |
        | 5000       |            | GOLD         |

  Rule: Cities are located in one of four regions for the purposes of point calculations
    The four regions are:
      - Europe
      - Americas
      - Australia/New Zealand (ANZ)
      - Asia

    Scenario Outline: City regions
      When points are calculated for a flight to or from <City>
      Then the city should be considered to be part of the <Region> region
      Examples:
        | City      | Region  |
        | London    | Europe  |
        | Sydney    | ANZ     |
        | LA        | America |
        | San Paulo | America |
        | Seoul     | Asia    |
