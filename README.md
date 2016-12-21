# Cadavres Exquis from "DockerCon 2016 edition" port to AspNetCore 1.1.0

## Setup (build and run AspNetCore app within docker)

```
docker-compose build
```

## Start

```
docker-compose up -d
```

## Alternate Setup (build AspNetCore in a dedicated conatiner then use an optimized container to run)

Please see `https://blogs.msdn.microsoft.com/stevelasker/2016/09/29/building-optimized-docker-images-with-asp-net-core/`

```
docker-compose -f docker-compose-build.yml up
docker-compose -f docker-compose-optimized.yml build
```

## Alternate Start

```
docker-compose -f docker-compose-optimized.yml up -d
```

Open a browser on `http://localhost`. Refresh. It should always be the same random sentence.

## Scale the backend

```
docker-compose scale words-api=20
```

Open a browser on `http://localhost:8000`. Refresh. It should be a new random sentence each time.

