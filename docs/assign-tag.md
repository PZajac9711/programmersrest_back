# Assign Tag
    Method: POST
    Endpoint: /posts/{id}/tags

Add available tag from <a href="get-all-tags">Available Tags</a> and assign it to post.Only user with Admin authority can do this.

## Parameters
<b>URI Parameter</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| id | yes | no | defines post to which tag should be assign|

<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

<b>Request Body</b>
<br/>If any of these parameters are equal null, it will return 400 Bad Request.
```
{
    "name":"defines TAG name (NEED TO BE A NAME FROM LIST OF AVAILABLE TAGS)"
}
```

## Example 
<b>Request</p>
```
http://localhost:8080/posts/1/tags

Request Body:
{
    "name":"java"
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
    * Tag is already assigned to this post
    * Tag not exist in pool of available tags.
    
* Returns 401 when:
    * Token is invalid
    
    
* Returns 403 when:
    * User who are not posses ADMIN authority trying to assign task.
