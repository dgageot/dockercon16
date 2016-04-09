#!/bin/bash

set -e

docker-machine create -d virtualbox kv

docker $(docker-machine config kv) run -d -p "8500:8500" --name="consul" -h "consul" progrium/consul -server -bootstrap

docker-machine create -d virtualbox --swarm --swarm-master \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth1:2376" \
    master

docker-machine create -d virtualbox --swarm \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth1:2376" \
    node-01

docker-machine create -d virtualbox --swarm \
    --swarm-discovery="consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-store=consul://$(docker-machine ip kv):8500" \
    --engine-opt="cluster-advertise=eth1:2376" \
    node-02
