# Create User
    POST users

Create a new user with USER role.Login and email need to be unique.

## Parameters
<b>Request Body</b>
<br/>If any of these parameters are equal null, it will return 400 Bad Request.
```
{
    "username":"defines username",
    "password":"defines password",
    "email":"defines email"
}
```
## Example 
<b>Request</p>
```
Endpoint: http://localhost:8080/users
Body:
{
    "username":"hello",
    "password":"admin",
    "email":"jacek@wp.pl"
}
```
<b>Response</b>
Status: 201 CREATED
