# Create New Sub Comment
    Method: POST
    Endpoint: /posts/{post-id}/comments/{comment-id}/sub-comments
    
Add new sub comment if comment exist and belong to specific post, otherwise it return PostNotFoundException or CommentNotFoundException.
Only users with valid token can reach this endpoint.

## Parameters
<b>URI Parameter</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| post-id | yes | no | defines post
| comment-id | yes | no | defines comment to which sub comment should be add

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
Endpoint: http://localhost:8080/posts/{1}/comments/{1}/sub-comments
Body:
{
    "contents": "Hire me"
}
Header:
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDYzNDIxLCJleHAiOjE1OTkwNjQzMjF9.ksZxd-Lte9IY37CtRgC5W_mFuugq-yeTcJpG2X7Ch40
```
<b>Response</b>
```
Status: 201 created
```
## Exceptions
* Returns 401 when Token expired or is invalid


* Returns 400 when:
    * Token is not present
    * Token didn't start with Bearer 
    * Post or comment which this id not exists
