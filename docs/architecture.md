# Architecture

The architecture should be as open for scaling as possible.
This means we try to archive an microservice architecture or something to be hosted and scaled native.

This will be some stuff in docker to be open to kubernetes.

## DB

The database will be mariadb. Maria should fix all needs. Including some json fields in the Database.
Later on we might need redis (which is not a real DB...) or similar stuff.

The DB will be managed by Django. This seems to be a good choice.

## Language

For prototyping we will use Python and Django. This enables a quick approach in getting things done.
Later on we might have services that can be wirtten and deployed in go as well. This makes a quick deployment possible, as we have a single binary. Also docker interaction would be a thing that can manage migrations via Django.

## Deployment

The deployment should scale. For development, testing and small scale we will use docker compose. This will be controlled via ansible.
On scale we will offer a helm chart.
