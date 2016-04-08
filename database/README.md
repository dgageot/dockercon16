## Database

TODO
 [ ] Write a Dockerfile which initialize the database.



## With Mongo

```
 docker run --name db -p -d mongo
 docker run --name db-ui --link db:mongo -p 8081:8081 -d mongo-express
```


## interact with the mongo shell by hand

```
docker run -it --link db:mongo --rm mongo sh -c 'exec mongo "$MONGO_PORT_27017_TCP_ADDR:$MONGO_PORT_27017_TCP_PORT/devoxx"'
```
