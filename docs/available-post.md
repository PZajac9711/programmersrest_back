# Available Posts
    GET posts?page

Return list of posts, list can be empty if there's no post for specific page, otherwise return list between range 1-4 where 4 is a max posts on a single page.
Also it takes only post that have set field active to TRUE

## Parameters
<b>Query Parameter</b>

| Name | Required | Default | Description | 
| --- | --- | --- | --- |
| page | no | 0 | define witch page you wan't to get.Where value 0 represent newest posts, next value takes 4 next posts from database. All values lower than 0 are changed to 0 |


## Example 
<b>Request</p>
```
http://localhost:8080/posts?page
```
<b>Response</b>
```
{
        "id": 3,
        "imagePath": "path",
        "title": "title3",
        "date": "2020-09-02T00:00:00",
        "author": "admin",
        "shortDescription": "t1"
    },
    {
        "id": 2,
        "imagePath": "path",
        "title": "title2",
        "date": "2020-09-02T00:00:00",
        "author": "admin",
        "shortDescription": "t1"
    },
    {
        "id": 1,
        "imagePath": "path",
        "title": "title",
        "date": "2020-09-02T00:00:00",
        "author": "admin",
        "shortDescription": "t1"
    }
```
