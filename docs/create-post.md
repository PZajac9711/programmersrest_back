# Create Post
    POST posts

Add new Post to database, only user with ADMIN authority can reach this endpoint.

## Parameters
<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| Authorization | yes| no | Header should contain authorization token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

<b>Request Body</b>
<br/>If any of these parameters are equal null, it will return 400 Bad Request.
```
{
    "title": "defines title (Title is UNIQUE!)",
    "shortDescription": "defines short description wich should encourage user to checking this post",
    "fullDescription": "contest of the article including html tags",
    "imaginePath": "url to some image"
}
```
## Example 
<b>Request</p>
```
Endpoint: http://localhost:8080/posts
Body:
{
    "title": "Hire me",
    "shortDescription": "some short text",
    "fullDescription": "main text",
    "imaginePath": "sad pepe"
}
Header:
Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDYzNDIxLCJleHAiOjE1OTkwNjQzMjF9.ksZxd-Lte9IY37CtRgC5W_mFuugq-yeTcJpG2X7Ch40
```
<b>Response</b>
```
Status: 201 created
```
