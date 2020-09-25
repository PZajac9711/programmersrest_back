# DELETE TAG
    Method: DELETE
    Endpoint: /tags/{name}

Delete Tag from database. Only user with ADMIN Authority can do this.

## Parameters
<b>URI Parameter</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| name | yes | no | defines Tag which should be deleted

<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

## Example 
```
Endpoint: DELETE localhost:8080/tags/java
Response: Status: 200 OK
```
## Exceptions
* Returns 400 when:
    * Token is not present
    * Token didn't starts with Bearer 
    * Tag not exists in database

    
* Returns 401 when:
    * Token is invalid
    

* Returns 403 when:
    * User without ADMIN Authority try delete tag
