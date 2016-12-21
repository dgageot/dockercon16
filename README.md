# Cadavres Exquis from "DockerCon 2016 edition" port to AspNetCore 1.1.0

## Setup (build AspNetCore app within docker)

```
docker-compose build
```

Then skip the next step

## Alternate Setup (build AspNetCore app outside docker)

...
dotnet restore .\words-aspnet\project.json
dotnet publish .\words-aspnet\project.json
docker-compose -f docker-compose.nobuild.yml build
docker build -f Dockerfile.run -t dockercon16-aspnetcore-backend .
...

## Start

```
docker-compose up -d
```

Open a browser on `http://localhost:8000`. Refresh. It should always be the same random sentence.

## Scale the backend

```
docker-compose scale words-api=20
```

Open a browser on `http://localhost:8000`. Refresh. It should be a new random sentence each time.


