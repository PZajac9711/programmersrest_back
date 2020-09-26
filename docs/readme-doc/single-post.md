# Single Post
    Method: GET
    Endpoint: posts/{id} 

Return specific post if exist, otherwise return status code 400 with message "Post not found"

## Parameters
<b>URI Parameter</b>

| Name | Required | Type | Description | 
| --- | --- | --- | --- |
| id | yes | long | defines which post should be returned from the database |


## Example 
<b>Request</p>
```
http://localhost:8080/posts/1
```
<b>Response</b>
```
{
    "post": {
        "id": 1,
        "title": "title",
        "shortDescription": "t1",
        "fullDescription": "t2",
        "createDate": "2020-09-25T00:00:00",
        "author": "admin",
        "lastModified": null,
        "imaginePath": "path",
        "active": true,
        "commentList": [
            {
                "id": 1,
                "postId": 1,
                "author": "admin",
                "description": "desc",
                "createDate": "2020-09-25T00:00:00",
                "score": 0,
                "subCommentList": [
                    {
                        "id": 1,
                        "commentId": 1,
                        "author": "admin",
                        "createDate": "2020-09-25T00:00:00",
                        "description": "asd"
                    }
                ]
            }
        ]
    },
    "tagDetailsList": [
        {
            "id": 1,
            "name": "HELLO"
        },
        {
            "id": 2,
            "name": "SPRING"
        },
        {
            "id": 3,
            "name": "HIBERNATE"
        }
    ]
}
```
