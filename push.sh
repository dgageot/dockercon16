#!/bin/bash

set -e

folders="web words-dispatcher words-java"

for folder in $folders; do
  docker build -t dockerdemos/lab-$folder $folder
  docker push dockerdemos/lab-$folder
done
