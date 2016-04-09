#!/bin/bash

set -e

docker build -t dgageot/lab-web web
docker push dgageot/lab-web

docker build -t dgageot/lab-words-dispatcher words-dispatcher
docker push dgageot/lab-words-dispatcher

docker build -t dgageot/lab-words-java words-java
docker push dgageot/lab-words-java
