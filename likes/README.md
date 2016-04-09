```
$ http GET localhost:8080/likes
HTTP/1.1 200 OK
Content-Length: 31
Content-Type: application/json
Date: Sat, 09 Apr 2016 14:10:07 GMT

[
    {
        "Name": "foo"
    }, 
    {
        "Name": "bar"
    }
]

$ http PUT localhost:8080/likes  name=polka
HTTP/1.1 201 Created
Content-Length: 0
Content-Type: application/json
Date: Sat, 09 Apr 2016 14:10:14 GMT



$ http GET localhost:8080/likes
HTTP/1.1 200 OK
Content-Length: 48
Content-Type: application/json
Date: Sat, 09 Apr 2016 14:10:17 GMT

[
    {
        "Name": "foo"
    }, 
    {
        "Name": "bar"
    }, 
    {
        "Name": "polka"
    }
]
```
