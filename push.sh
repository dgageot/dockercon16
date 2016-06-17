#!/bin/bash

set -e

cp docker-compose-v1.yml docker-compose.yml

docker-compose build
docker-compose push
docker-compose pull db

docker save -o images.tar dockerdemos/lab-web dockerdemos/lab-words-dispatcher dockerdemos/lab-words-java mongo:3.2.4