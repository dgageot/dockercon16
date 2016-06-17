#!/bin/bash

set -e

export GOOGLE_PROJECT="code-story-blog"
export GOOGLE_ZONE="europe-west1-d"
export GOOGLE_DISK_SIZE="1000"
export GOOGLE_MACHINE_TYPE="custom-2-8192"

docker-machine create -d google --google-address ip-kv kv

docker $(docker-machine config kv) run \
    -d -p "8500:8500" \
    --name="consul" --restart "always" -h "consul" \
    progrium/consul -server -bootstrap

docker-machine create -d google \
    --google-address ip-master \
    --swarm --swarm-master \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth0:2376" \
    master

for i in {1..4}; do
  name=$(printf "node-%0.2d" $i)
  ip=$(printf "ip-node%0.2d" $i)

  docker-machine -D create -d google --swarm \
      --google-address $ip \
      --swarm-discovery="consul://$(docker-machine ip kv):8500" \
      --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
      --engine-opt="cluster-advertise=eth0:2376" \
      $name
done

docker $(docker-machine config --swarm master) network create --driver overlay lab-net
