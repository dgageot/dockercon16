#!/bin/bash

set -e

pushd web
docker build -t dgageot/lab-web .
docker push dgageot/lab-web
popd

pushd words-dispatcher/
docker build -t dgageot/lab-words-dispatcher .
docker push dgageot/lab-words-dispatcher
popd

pushd words-java
docker build -t dgageot/lab-words-java .
docker push dgageot/lab-words-java
popd
