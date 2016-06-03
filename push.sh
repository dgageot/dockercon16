#!/bin/bash

set -e

folders="web words-dispatcher words-java"

for folder in $folders; do
  docker build -t dockerdemos/lab-$folder $folder
  docker push dockerdemos/lab-$folder
done

docker save -o images.tar dockerdemos/lab-web dockerdemos/lab-words-dispatcher dockerdemos/lab-words-java mongo-express:0.30.43 mongo:3.2.4