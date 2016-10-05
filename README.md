# Cadavres Exquis "DockerCon 2016 edition"

## Setup

```
docker-compose build
docker-compose pull
```

## Start

```
docker-compose up -d
```

Open a browser on `http://localhost`. Refresh. It should always be the same random sentence.

## Scale the backend

```
docker-compose scale words-java=20
```

Open a browser on `http://localhost`. Refresh. It should be a new random sentence each time.


# Docker 1.12 - Swarm Mode style

* Rename image name in `push.sh` and `docker-compose.yml` from `dockerdemos/lab-web` to `dockerdemos/jl-lab-web`
* `./push.sh`
* `docker-compose bundle`
* `docker swarm init`
* `docker deploy dockercon16`
* `docker service update --publish-rm 80 dockercon16_web`
* `docker service update --publish-add 80:80 dockercon16_web`
* `docker service scale dockercon16_words-java=10`
