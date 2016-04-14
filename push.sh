#!/bin/bash

set -e

docker build -t dockerdemos/lab-web web
docker push dockerdemos/lab-web

docker build -t dockerdemos/lab-words-dispatcher words-dispatcher
docker push dockerdemos/lab-words-dispatcher

docker build -t dockerdemos/lab-words-java words-java
docker push dockerdemos/lab-words-java
