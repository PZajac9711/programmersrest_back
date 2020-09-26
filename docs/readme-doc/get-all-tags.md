# Get All Available Tags
    Method: GET
    Endpoint: /tags

Get a list of all available Tags (can return empty list).

## Example 
<b>Request</p>
```
http://localhost:8080/tags
```
<b>Response</b>
```
[
    {
        "id": 2,
        "name": "SPRING"
    },
    {
        "id": 3,
        "name": "HIBERNATE"
    },
    {
        "id": 4,
        "name": "TRAVELING"
    },
    {
        "id": 5,
        "name": "PYTHON"
    }
]
status: 200 OK
```
