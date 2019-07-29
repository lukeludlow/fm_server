
# family map server

## web api operations

### /user/register
create a new user account and randomly generate ancestor data 

### /user/login
log the user in and return and authtoken

### /fill/[username]/{generations}
randomly generate ancestor data for the specified user. 
`{generations}` specifies how many generations back to create (default is 4).

### /person/[personId]
get person data

### /person
get all family members of the current user 

### /event/[eventId]
get event data

### /event
get all events for all family members of the current user

### /load
load the given user, people, and event data into the database. used for testing purposes.

### /clear
clear the database. used for testing purposes.