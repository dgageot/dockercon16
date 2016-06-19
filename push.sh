#!/bin/bash

set -e

docker-compose build
docker-compose push
docker-compose pull mongo

docker save -o images.tar dockerdemos/lab-web dockerdemos/lab-words-dispatcher dockerdemos/lab-words-java mongo:3.2.4
