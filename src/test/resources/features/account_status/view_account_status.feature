Business Need: View account status

  Frequent Flyer members can view the points they have earned and their current status level
  in the My Accounts section of the site

  Rule: Frequent Flyer members can view their status in the My Accounts section
@current
    Example: Trevor has just joined the program
      Given Trevor has registered as a Frequent Flyer member
      When he logs on to the Frequent Flyer application
      Then his account status should have:
        | Point Balance | Status Level |
        | 0             | STANDARD     |
