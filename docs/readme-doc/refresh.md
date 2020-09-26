# REFRESH
    Method: GET
    Endpoint: /authenticate

Get new valid authentication token with the help of refresh Token.
Refresh token can be used only once, after request refresh token will be replace, so be sure that you grab the new token.

## Parameters
<b>Headers</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| refresh | yes| no | Header should contain refresh token generated previous by server, it need to start with Bearer , dont forget about space after Bearer|

## Example 
```
Endpoint: GET http://localhost:8080/authenticate
Request:
* curl --header "refresh: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0MzAyLCJleHAiOjE1OTkwNTYxMDJ9.TUgz2i0PsLgDVLka2EzK9Sx-BB9EZd_ldd5-vIfMLt4" --request GET http://localhost:8080/authenticate
Response:
    "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0NTkzLCJleHAiOjE1OTkwNTU0OTN9.nk-SNAx5ImC7PK5latGENuuAU2X1ggih_vmcrPMXzIk",
    "refreshToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0eSI6IkFETUlOIiwiaWF0IjoxNTk5MDU0NTkzLCJleHAiOjE1OTkwNTYzOTN9._xOKNSjt9x2pE5yiIeczLTP9b9P_Kh2w8n-2UtZsK3c"
```
##Exceptions
* Returns 400 when:
    * Token is not present
    * Token didnt start's with Bearer 
    * Token is not present in database
    * Token is invalid or been already used
    * Token expired or been modified
