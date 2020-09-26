# Authenticate
    Method: POST
    Endpoint: /authenticate

Generate two Tokens, one for authenticate user and another of refreshing authentication.
Authentication token expired after 15 min, after this time you can refresh token in next 15 min using refresh token
see <a href=refresh.md>Refresh</a>

## Parameters
<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

<b>Request Body</b>
<br/>If any of these parameters are equal null, it will return 400 Bad Request.
```
{
    "username": "defines username",
    "password": "defines user password",
}
```
## Example 
```
Endpoint: POST http://localhost:8080/authenticate
Request:
* curl --header "Content-Type: application/json" --request POST --data "{\"username\":\"admin\",\"password\":\"admin\"}" http://localhost:8080/authenticate
Response: 
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0MzAyLCJleHAiOjE1OTkwNTUyMDJ9.C_EeiJdA-8dhVtmoIubBr67uzjfo5Kn5sxFwqjrQemA",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0MzAyLCJleHAiOjE1OTkwNTYxMDJ9.TUgz2i0PsLgDVLka2EzK9Sx-BB9EZd_ldd5-vIfMLt4"
```
