# Change Post Status
    Method: PUT
    Endpoint: posts/{id}/status

Change Post status, if status is true while we calling this endpoint it will change it to false.It works in two ways. only user with ADMIN authority can reach this endpoint.

## Parameters
<b>URI Parameter</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| id | yes | no | defines post which should be updated|

<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

## Example 
<b>Request</p>
```
http://localhost:8080/posts/1/status
```
<b>Response</b>
```
Status: 200 OK
```
## Exceptions
* Returns 400 when:
    * Post not exists
    * Token is not present
    * Token didnt start with Bearer 


* Returns 403 when:
    * User who dont have ADMIN Authority try to change post status  
