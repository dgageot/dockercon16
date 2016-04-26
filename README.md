# Cadavres Exquis "Swarm edition"

## Warm-up

If you plan on attending this workshop, you need:

* A laptop + power cord
* Install docker 1.11:
  * Install Toolbox for [windows](https://github.com/docker/toolbox/releases/download/v1.11.0/DockerToolbox-1.11.0.exe) or for [Mac](https://github.com/docker/toolbox/releases/download/v1.11.0/DockerToolbox-1.11.0.pkg)
  * If you are on linux, and only on linux, grab [docker-compose v1.7](https://github.com/docker/compose/releases/download/1.7.0/docker-compose-Linux-x86_64) and put it in your path somewhere.
  * If you haven't done it already, register for the Docker for Desktop Beta at https://beta.docker.com, and give us your hub account name during the sessioni. We can probably do something for you.
* Test that your docker installation works fine:
  * `docker version` should show version `1.11` on both Client and Server side.
  * Run `docker run hello-world` and check you see the welcome message.
  * Run `docker run -p 8080:80 nginx:1.8.1-alpine` and open your browser to the IP given by `docker-machine ip`, on port 8080. You should see nginx's default page.
* As network availability is often flaky at conferences, pull the base images we are going to use during the lab:
  * `docker pull dockerdemos/lab-web`
  * `docker pull dockerdemos/lab-words-dispatcher`
  * `docker pull dockerdemos/lab-words-java`
  * `docker pull mongo-express:0.30.43`
  * `docker pull mongo:3.2.4`
* Get the source of the lab by `git clone git@github.com:CodeStory/lab-docker.git`

## 1 - Look Ma', micro-services on my laptop

Our first version of the application is composed of four micro-services:

  - A `web` service that uses `nginx` running on port `80` to serve an HTML5/Js
    application written in angularJs.
  - A `words-java` service that runs a `java` web server on a random port. This
    server connects to the database and exposes a Rest Api to the `web`.
  - A `db` service that runs a `mongoDb` database on a random port.
  - A `db-ui` service that runs a web UI on port `8081` to edit the content of
    the database

## Let's run the application

1. Point Docker CLI to the Docker daemon:

  - If you have `Docker for Mac` or `Docker for Windows`, there's nothing to be
    done. Run `docker info` to check that everything is up and running.

  - If you have `Docker Toolbox`, either open the `Quick Start` terminal or run
    `docker-machine env` to show the command you have to **run** to point to the
    Docker daemon running on the VirtualBox VM. On OSX, it's typically:

    ```
    eval $(docker-machine env default)
    ```

2. Configure *Docker Compose* to use the first configuration file:

  ```
  cd lab-docker
  cp docker-compose-v1.yml docker-compose.yml
  ```

3. Build and start the application:

  ```
  docker-compose up -d
  ```

4. Take a look at the logs, to see if there's any error:

  ```
  docker-compose logs
  ```

5. List the running containers:

  ```
  docker-compose ps

    Name                       Command               State              Ports
  ---------------------------------------------------------------------------------------------
  labdocker_db-ui_1        tini -- node app                 Up      0.0.0.0:8081->8081/tcp
  labdocker_db_1           /entrypoint.sh mongod            Up      27017/tcp
  labdocker_web_1          nginx -g daemon off;             Up      443/tcp, 0.0.0.0:80->80/tcp
  labdocker_words-java_1   java -DPROD_MODE=true -Xmx ...   Up      8080/tcp, 8888/tcp
  ```

## Let's use the application

Find the IP and port to the web UI and open a browser to that url:

  ```
  docker-compose port web 80
  ```
If you get `0.0.0.0` as an answer on the command above, don't use it and use the output of `docker-machine ip default` instead as the ip address.

Each time the page is refreshed, you will see a sentence composed of 5 random
words: a noun, an adjective, a verb, a noun and an adjective. That's a "Cadavre
Exquis"! You did it!

## How does it work?

The angularJs application served by the `nginx` based `web` service sends 5
http `GET` queries to the `nginx` that proxies the `words-java` REST service.

On each query, the `words-java` loads all the words from the database and chooses
a random one.

The `db-ui` web UI can be used to configure the list of words in the database.
Use this command to find the url for the UI:

```

**Careful**, all words added to the database at this stage will be lost for the
next stages.

# 2 - Run the application with a dispatcher

We are going to change the micro-service based architecture of our application
without changing its code. That's neat!

Our idea is to introduce an additional micro-service between the `web` and the
`java` rest api. This new component is called the `words-dispatcher`. It's a Go
based web server that will later help dispatch word queries to multiple `words-java`
backends.

Thanks to a new [compose file](docker-compose-v2.yml), we are able to insert
the `words-dispatcher` service between the `web` service and the `words-java`
service without touching our application code.

## How is that possible?

The `web` expectation is that a `words` host exists on the network and that
it will be able to connect to it via port `8080`.

Thanks to the `links` configuration, we are able to connect the `web` service
to the `words-java` service using the `words` DNS name.

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

1. Stop the application currently running:

  ```
  cd lab-docker
  docker-compose stop
  docker-compose rm -f
  ```

2. Configure *Docker Compose* to use the second configuration file:

  ```
  cp docker-compose-v2.yml docker-compose.yml
  ```

3. Build and start the application:

  ```
  docker-compose up -d
  docker-compose logs
  ```

As a user, you should see no difference compared to the original application.
That's the whole point!

# 3 - Run the application on a shared Swarm

We are going to the Cloud! Your containers will be send to a shared Swarm
composed of multiple nodes. We have already setup the Swarm for you before the talk.
You just need to point your Docker CLI to the Swarm rather than to your local
Docker daemon.
This is done through environment variables. And because our Swarm has TLS enabled,
you need a copy of our certificates. We'll pass along a couple of USB keys with
the certificates on them. Then follow the instructions below:

1. Stop the application currently running:

  ```
  cd lab-docker
  docker-compose stop
  docker-compose rm -f
  ```

2. Copy the provided `certificates` from the USB key.

3. Point your docker client to the proper machine:

If you are on the Google cloud swarm cluster
  ```
  export DOCKER_TLS_VERIFY="1"
  export DOCKER_HOST="tcp://xxx.xxx.xxx.xxx:xxxx"
  export DOCKER_CERT_PATH="full_path_to_certificates"
  ```

if you are running in your local swarm cluster in virtualbox
  ```
  eval $(docker-machine env master)
  ```

4. Confirm that `docker info` shows multiple nodes.

5. Configure *Docker Compose* to use the third configuration file:

  ```
  mv lab-docker team-XX
  cd team-XX
  cp docker-compose-v3.yml docker-compose.yml
  ```

Renaming the folder you are working in is **VERY** important. It makes it possible
to scope the whole project to your team name.

5. Build and start the application:

  ```
  docker-compose up -d
  docker-compose logs
  ```

The same application that ran on you machine now runs in the Cloud on a shared Swarm.

## How is that possible?

If you compare [docker-compose-v2.yml](docker-compose-v2.yml) and [docker-compose-v3.yml](docker-compose-v3.yml)
you'll see that all the links have been removed and all the services now use a
private network instead. This network is created by *Docker Compose*. Its name
is `private`, prefixed by the name of your project (ie your team name). It's a network
available to your containers only.

Thanks to this private network, multiple similar applications can coexist on the same Swarm.

Also this overlay network and docker's DNS make it possible for the containers to find each other by their service names.

## What to explore in this step

1. You can check that consul is correctly running, by checking the numbers of nodes it runs
  * Find the Consul server ip by running : `docker-machine ip kv`
  * Then points your browser to `http://{consul-server-ip}/:8500/ui/#/dc1/kv/docker/nodes/`

2. You can try to scale the numbers of `words-java` nodes and see how the dispatcher react.
  * Add 4 more `words-java` node by issuing `docker-compose scale words-java=4`.
  * You have now 5 words-java containers. Check their numbers with `docker-compose ps`
  * Point your browser to the web container on the swarm cluster, don't forget the container can run anywhere on the swarm cluster. You'll find the correct ip/port by issuing `docker-compose port web 80`. You'll likely see the differents words coming from differents `word-java` ip.
  * Start `docker-compose logs -f` and refresh your browser, pay attention to the logs from the dispatcher.
  * Connect to one of the containers `docker exec -ti labdocker_words_1 sh` and do a `nslookup words-java` (change `labdocker` with your team prefix) see that there is 5 values for the entry.

# 4 - Connect to the other nodes

The goal of this last step is to make all the applications communicate transparently.
Every `words-dispatcher` will connect to the `words-java` deployed by all the teams.

1. Stop the application currently running:

  ```
  cd team-XX
  docker-compose stop
  docker-compose rm -f
  ```

2. Configure *Docker Compose* to use the fourth configuration file:

  ```
  cd team-XX
  cp docker-compose-v4.yml docker-compose.yml
  ```

3. Build and start the application:

  ```
  docker-compose up -d
  docker-compose logs
  ```

We changed the services so that some of them are now part of multiple networks.
A `private` network, project scoped, automatically created and a shared
pre-existing `lab-net` network.

All the services with the same name or alias on a shared network
will be reachable on the same DNS name. A client can get all the IPs for
the DNS name and start load balancing between the nodes. Nothing complicated
to setup!

That's exactly what the `words-dispatcher` does. To bypass the DNS cache, it
searches for all the IPs for the `works-java` services and uses a random one
each time. This effectively load balances queries among all the teams.

Try adding words of your own and play with those "Cadavres Exquis", Swarm style!

# Docker Features demonstrated

Last Devoxx & Mix-IT in 2015 was with docker 1.6. Since then, a lot of features
were added. Here's the short list of those we demonstrated with this lab.

* Multi-host Networking - *Docker 1.9*
* New compose file - *Docker 1.10*
* Use links in networks - *Docker 1.10*
* Network-wide container aliases - *Docker 1.10*
* DNS discovery - *Docker 1.11*
* Build in docker-compose up - *Docker-Compose 1.7*

# About 'Cadavres Exquis'

Cadavres Exquis is a French word game, you'll find more on
[wikipedia page](https://fr.wikipedia.org/wiki/Cadavre_exquis_(jeu)) (in French)

# How did we create the swarm cluster ?

The swarm cluster has been created on Google Cloud with the [init_swarm_google.sh](init_swarm_google.sh) script. Take a look to what we do there but you'll need an account and this may cost you money.
You can also try it on your own laptop by running the [init_swarm_virtualbox.sh](init_swarm_virtualbox.sh), you'll need virtualbox and `docker-machine`
