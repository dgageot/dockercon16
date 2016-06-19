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

