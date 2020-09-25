# Update Sub Comment
    Method: PUT
    Endpoint: /posts/{id}/comments/{comment-id}/sub-comments/{sub-id}

Update sub-comment if exist,can throw CommentNotFoundException in case if comment not exist or it not belong to this specific post.
Only users with active token can reach this endpoint.
If user that trying to update comment are not a original author it will return NoAuthException with status 403.
<br/>

## Parameters
<b>URI Parameter</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| id | yes | no | defines post
| comment-id | yes | no | defines comment
| sub-id | yes | no | defines sub comment which should be updated

<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

<b>Request Body</b>
<br/>If any of these parameters are equal null, it will return 400 Bad Request.
```
{
    "contents": "defines the content of comment",
}
```
## Example 
<b>Request</p>
```
Endpoint: http://localhost:8080/posts/{1}/comments/{1}/sub-comments/{1}
Body:
{
    "contents": "Hire me"
}
Header:
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDYzNDIxLCJleHAiOjE1OTkwNjQzMjF9.ksZxd-Lte9IY37CtRgC5W_mFuugq-yeTcJpG2X7Ch40
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
    * Comment with this id not exists
    * Sub comment with this id not exists
    
   
* Returns 403 when:
    * User who are not a owner trying to update comment(Admin can't update other comments).


* Returns 401 when:
     * Token is invalid
