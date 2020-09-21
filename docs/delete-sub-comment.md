# Delete Comment
    DELETE comments

Delete sub-comment from database if exist,it can throw CommentNotFoundException in case if comment not exist or it not belong to this specific post.
Only users with active token can reach this endpoint.
If user that trying to delete sub-comment are not a original author it will return NoAuthException with status 403.
<br/>
<b>Note: ADMIN can delete all comments</b>

## Parameters
<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|
## Example 
<b>Request</p>
```
Endpoint: http://localhost:8080/posts/{1}/comments
Header:
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDYzNDIxLCJleHAiOjE1OTkwNjQzMjF9.ksZxd-Lte9IY37CtRgC5W_mFuugq-yeTcJpG2X7Ch40
```
<b>Response</b>
```
Status: 200 OK
```