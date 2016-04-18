# Cadavres Exquis "Swarm edition"

Our first version of the application is composed of four micro-services:

  - A `web` service that uses `nginx` running on port `80` to serve an HTML5/Js
    application written in angularJs.
  - A `words-java` service that runs a `java` web server on a random port. This
    server connects to the database and exposes a Rest Api to the `web`.
  - A `db` service that runs a `mongoDb` database on a random port.
  - A `db-ui` service that runs a web UI on port `8081` to edit the content of
    the database

## Let's run the application

1. Point Docker CLI to the Docker daemon

  - If you have `Docker for Mac` or `Docker for Windows`, there's nothing to be
    done. Run `docker info` to check that everything is up and running.

  - If you have `Docker Toolbox`, either open the `Quick Start` terminal or run
    `docker-machine env` to show the command you have to **run** to point to the
    Docker Daemon running on Virtualbox.

2. Configure *Docker Compose* to use the first configuration file:

  ```
  cd lab-docker
  cp docker-compose-v1.yml docker-compose.yml
  ```

3. Build and start the application:

  ```
  docker-compose up --build -d
  ```

4. Take a look at the logs, to see if there's any error:

  ```
  docker-compose logs
  ```

## Let's use the application

Find the IP and port to the web UI and open a browser to that url:

  ```
  docker-compose port web 80
  ```

Each time the page is refreshed, you will see a sentence composed of 5 random
words: a noun, an adjective, a verb, a noun and an adjective.

## How does it work?

The angularJs application served by the `nginx` based `web` service sends 5
GET queries to the `nginx` that proxies the `words-java` rest service.

On each query, the `words-java` loads of the words from the database and chooses
a random one.

The `db-ui` web UI can be used to configure the list of words in the database.
Use this command to find its url:

```
docker-compose port db-ui 8081
```

# Run the application with a dispatcher

We are going to change the micro-service based architecture of our application
without changing its code.

Our idea is to introduce an additional micro-service between the `web` and the
`java` rest api. This new component is called the `words-dispatcher`. It's a go
based web server that will later help dispatch word queries to multiple `words-java`
backends.

Thanks to a new (compose file)[docker-compose-v2.yml], we are able to insert
the `words-dispatcher` service between the `web` service and the `words-java`
service without touching our application code.

## How is that possible?

The `web` expectation is that a `words` host exists on the network and that
it will be able to connect to it via port `8080`.

Thanks to the `links` configuration, we are able to connect the `web` service
to the `words-java` service using the `words` dns name.

Also, thanks to the `expose` configuration, the `words-java` service will be
reachable on port `8080` by the other services. Because the `8080` port is not
exposed to the host but only the the other containers, we could have multiple
services running on the port coexist on the same host.

This last point is very important because now we want to insert a new service
between the `web` and the `words-java`. This service runs on the same `8080` port.

All we have to do is:

- Change the `links` configuration of the `web` service so that when it connects
  to `words` host, it reaches `words-dispatcher` instead of `words-java`.
- Set the `links` configuration of the `words-dispatcher` service to connect it
  to `words-java`.
- Both `words-dispatcher` and `words-java` will expose their `8080` port to the
  other containers but it's not an issue because as it is not published to the
  host, they can co-exist on the same host.

## Let's use the application

1. Configure *Docker Compose* to use the first configuration file:

  ```
  cd lab-docker
  cp docker-compose-v2.yml docker-compose.yml
  ```

2. Build and start the application:

  ```
  docker-compose up --build -d
  docker-compose logs
  ```

As a user, you should see no difference compared to the original application.

# Run the application on a shared swarm


# Warm-up

Before attending this workshop in a conference, as network availibility is often complexe, please check you have the following :
* A laptop + cord
* Have docker 1.11 installed through toolbox or docker desktop beta
  * [Toolbox for windows](https://github.com/docker/toolbox/releases/download/v1.11.0/DockerToolbox-1.11.0.exe) 
  * [Toolbox for Mac](https://github.com/docker/toolbox/releases/download/v1.11.0/DockerToolbox-1.11.0.pkg) 
  * If you haven't before, register for the beta at https://beta.docker.com
* Test your docker installation works fine
  * `docker run hello-world` and check you see the welcome message
  * `docker run -p 8080:8080 nginx` and open your browser to your machine on port 8080 and check you see nginx message.
* Warm up your local docker install with the following images :
  * `docker pull dockerdemos/lab-web`
  * `docker pull dockerdemos/lab-words-dispatcher`
  * `docker pull dockerdemos/lab-words-java`
  * `docker pull mongo-express:0.30.43`
  * `docker pull mongo:3.2.4`

# Docker Features demonstrated
Last Devoxx & Mix-IT in 2015 was with docker 1.6

* Multi-host Networking - Docker 1.9
* New compose file - Docker 1.10
* Use links in networks - Docker 1.10
* Network-wide container aliases - Docker 1.10
* DNS discovery - Docker 1.11
* Build in docker-compose up - Docker-Compose 1.7



