# Run the database with compose

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


# Docker news

+ Multi-host Networking - Docker 1.9
+ Persistent Storage - Docker 1.9
+ New compose file - Docker 1.10
+ Use links in networks - Docker 1.10
+ Network-wide container aliases - Docker 1.10
+ Reschedule containers when a node fails - Swarm 1.1
+ Interlock
+ two networks (back / front)
+ Set up development environments much faster: At the same time as specifying a build directory such as build: ./code, you can also specify an image such as image: myusername/webapp. This means that you can use either docker-compose build to build the image, or make it faster by using docker-compose pull to pull it from a registry. You can thus use pre-built images to get a development environment running faster, instead of waiting for images to build locally.
+ Build arguments: You can now pass arguments to builds from your Compose file.
+ SOCKS PROXY https://github.com/docker/docker/pull/18373


