# Change Post Status
    Method: DELETE
    Endpoint: /posts/{id}

Permanently delete post from database. only user with ADMIN authority can reach this endpoint.

## Parameters
<b>URI Parameter</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| id | yes | no | defines post which should be deleted|

<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

## Example 
<b>Request</p>
```
http://localhost:8080/posts/1
```
<b>Response</b>
```
Status: 200 OK
```
##Exceptions
* Returns 400 when:
    * Token is not present
    * Token didnt start's with Bearer 
    * Post not exists
    
   
* Returns 403 when:
    * User who are not a Admin trying to delete post.


* Returns 401 when:
     * Token is invalid
