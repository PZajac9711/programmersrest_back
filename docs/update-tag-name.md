# Update Tag Name
    Method: PUT
    Endpoint: /tags/{name}

Update name for a specific Tag. Only Admin can do this.

## Parameters
<b>URI Parameter</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| name | yes | no | defines which tag should be update

<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

<b>Request Body</b>
<br/>If any of these parameters are equal null, it will return 400 Bad Request.
```
{
    "name": "defines new name (NAME NEED TO BE UNIQUE!)"   
}
```

## Example 
<b>Request</p>
```
http://localhost:8080/tags/Algorithms

Request Body:
{
    "name":"Python"
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
    * Tag not exists in database
    * Name didnt match to pattern

    
* Returns 401 when:
    * Token is invalid
    

* Returns 403 when:
    * User without ADMIN Authority try update tag
