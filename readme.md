# ProgrammersRest's

## Overview
ProgrammersRest is a project that allow you to sending and receiving information <br/>
between Business logic and Client and manage your blog data in a easy way. All request need to have valid JSON body, response is returned in JSON format.
<br/>Project provides functionality like:
* authenticate user
* create/update/delete posts
* create/update/delete comments
* Pageable 

And more.

## Tutorial
Below is a video show's basic communication with the API with usage of postman<br/>

## Questions
All questions send to patzaj9711@gmail.com

## HTTP requests
All API requests are made by sending a request using one of the following methods, depending on the action being taken:

* POST Create a resource
* PUT Update a resource
* GET Get a resource or list of resources
* DELETE Delete a resource

For PUT and POST requests the body of your request may include a JSON payload.

## HTTP status
Every response will be returned with one of the following HTTP status codes:

* <code>200</code> - <code>OK</code> The request was successful 
* <code>201</code> - <code>CREATED</code> The request was successful and the new resource is effectively created
* <code>400</code> - <code>Bad Request</code> There was a problem with the request (for example. wrong payload)
* <code>401</code> - <code>Unauthorized</code> The supplied API credentials are invalid
* <code>403</code> - <code>Forbidden</code> The credentials provided do not have permission to access the requested resource
* <code>404</code> - <code>Not Found</code> An attempt was made to access a resource that does not exist in the API
* <code>405</code> - <code>Method not allowed</code> The resource being accessed doesn't support the method specified   
* <code>500</code> - <code>Server Error</code> An error on the server occurred


## Authentication
Access to some endpoints is granted by providing username and password.
<br/>In case successfully authentication are generated two JWT authentication and refresh
```
Endpoint: POST http://localhost:8080/authenticate
Request:
* curl --header "Content-Type: application/json" --request POST --data "{\"username\":\"admin\",\"password\":\"admin\"}" http://localhost:8080/authenticate
Response: 
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0MzAyLCJleHAiOjE1OTkwNTUyMDJ9.C_EeiJdA-8dhVtmoIubBr67uzjfo5Kn5sxFwqjrQemA",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0MzAyLCJleHAiOjE1OTkwNTYxMDJ9.TUgz2i0PsLgDVLka2EzK9Sx-BB9EZd_ldd5-vIfMLt4"
```
After successfully token is required to authenticate user privilege and refreshToken is used to generate new authentication credentials without passing username and password
<br/>
Refresh token example:
```
Endpoint: GET http://localhost:8080/authenticate
Request:
* curl --header "refresh: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0MzAyLCJleHAiOjE1OTkwNTYxMDJ9.TUgz2i0PsLgDVLka2EzK9Sx-BB9EZd_ldd5-vIfMLt4" --request GET http://localhost:8080/authenticate
Response:
    "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0NTkzLCJleHAiOjE1OTkwNTU0OTN9.nk-SNAx5ImC7PK5latGENuuAU2X1ggih_vmcrPMXzIk",
    "refreshToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0NTkzLCJleHAiOjE1OTkwNTYzOTN9._xOKNSjt9x2pE5yiIeczLTP9b9P_Kh2w8n-2UtZsK3c"
```
Token expired after 15 minutes, while refresh token expired after 30 min. <br/>
You can use refreshToken only once after refreshing be sure you grab the new refreshToken.

## Resources
### <b>Post</b>
* <code>GET</code> <a href="/docs/available-post.md">Available Posts</a>
* <code>GET</code> <a href="/docs/single-post.md">Single Post</a>
* <code>POST</code> <a href="/docs/create-post.md">Create Post</a>
* <code>PUT</code> <a href="/docs/update-post.md">Update Post</a>
* <code>PUT</code> <a href="/docs/change-status.md">Change Post Status</a>
* <code>DELETE</code> <a href="/docs/delete-post.md">Delete Post</a>

### <b>Authentication</b>
* <code>GET</code> &nbsp;<a href="/docs/refresh.md">Refresh</a>
* <code>POST</code> <a href="/docs/authenticate.md">Authenticate</a>

### Comment
* <code>POST</code> <a href="/docs/create-new-comment.md">Create New Comment </a>
* <code>POST</code> <a href="/docs/create-new-sub-comment.md">Create New Sub Comment </a>
* <code>DELETE</code> <a href="/docs/delete-comment.md">Delete Comment</a>
* <code>DELETE</code> <a href="/docs/delete-sub-comment.md">Delete Sub Comment</a>
* <code>PUT</code> <a href="/docs/update-comment.md">Update Comment</a>
* <code>PUT</code> <a href="/docs/update-sub-comment.md">Update Sub Comment</a>

### User
* <code>POST</code> <a href="/docs/create-new-user.md">Create User</a>
