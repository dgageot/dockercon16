#!/bin/bash

set -e

pushd web
docker build -t dgageot/lab-web .
docker push dgageot/lab-web
popd

pushd dispatcher/
docker build -t dgageot/lab-dispatcher .
docker push dgageot/lab-dispatcher
popd

pushd words
docker build -t dgageot/lab-words .
docker push dgageot/lab-words
popd
