# Create New Comment
    Method: Post
    Endpoint: /posts/{id}/comments

Add new comment if posts exists, otherwise it return PostNotFoundException.
Only users with valid token can reach this endpoint.
## Parameters
<b>URI Parameter</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| id | yes | no | defines post to which comment should be added
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
Endpoint: http://localhost:8080/posts/{1}/comments
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
    * Post which this id not exists
