#!/bin/bash

set -e

docker-machine create -d google kv

docker $(docker-machine config kv) run -d -p "8500:8500" --name="consul" --restart "always" -h "consul" progrium/consul -server -bootstrap

docker-machine create -d google --swarm --swarm-master \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth0:2376" \
    master

docker-machine create -d google --swarm \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth0:2376" \
    node-01

docker-machine create -d google --swarm \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth0:2376" \
    node-02

docker-machine create -d google --swarm \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth0:2376" \
    node-03

docker-machine create -d google --swarm \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth0:2376" \
    node-04

docker-machine create -d google --swarm \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth0:2376" \
    node-05

eval $(docker-machine env --swarm master)
docker network create --driver overlay lab-net
