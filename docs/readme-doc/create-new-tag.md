# Create New Tag
    Method: POST
    Endpoint: /tags

Create new Tag, name must contain only letters and be between 2-15 letters. Only user with ADMIN authority can do this.

## Parameters
<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

<b>Request Body</b>
<br/>If any of these parameters are equal null, it will return 400 Bad Request.
```
{
    "name": "defines name (NAME NEED TO BE UNIQUE!)"   
}
```

## Example 
<b>Request</p>
```
http://localhost:8080/tags

Request Body:
{
    "name":"JAVA"
}
```
<b>Response</b>
```
Status: 201 CREATED
```
## Exceptions
* Returns 400 when:
    * Token is not present
    * Token didn't starts with Bearer 
    * Tag already exist in database
    * Name didnt match to pattern

    
* Returns 401 when:
    * Token is invalid
    

* Returns 403 when:
    * User without ADMIN Authority try create new tag
