# Change Password
    Method: PUT
    Endpoint: /users/change-password

Change password, user is defined by Token, token is validate and details are set to security context.

## Parameters
<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

<b>Request Body</b>
<br/>If any of these parameters are equal null, it will return 400 Bad Request.
```
{
    "newPassword":"old password",
    "oldPassword":"new password"
}
```

## Example 
<b>Request</p>
```
http://localhost:8080/users/change-password

Request Body:
{
    "newPassword":"JAVA",
    "oldPassword":"admin"
}
```
<b>Response</b>
```
Status: 200 OK
```
## Exceptions
* Returns 400 when:
    * Token is not present
    * Token didn't starts with Bearer 
    * Password is incorrect
    * newPassword or oldPassword are null
    
* Returns 401 when:
    * Token is invalid
    
