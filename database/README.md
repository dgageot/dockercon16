## Database

TODO
 [ ] Write a Dockerfile which initialize the database.



# Run with compose

```
docker-compose up
docker run -it --net labdocker_default --link labdocker_db_1:mongo --rm mongo sh  -c 'exec mongo "db:27017/mongo"'
```



# Everything by hand

```
 docker run --name db -p -d mongo
 docker run --name db-ui --link db:mongo -p 8081:8081 -d mongo-express
 docker run -it --link db:mongo --rm mongo sh -c 'exec mongo "$MONGO_PORT_27017_TCP_ADDR:$MONGO_PORT_27017_TCP_PORT/devoxx"'
```
