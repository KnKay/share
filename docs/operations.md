# Operations

This chapter will describe the deployment. This will be the most detailed. We should help to easy install and use the stuff.

## Docker

All services will get a docker on release. This is giving us the chance to go crazy in deployments.

## Install

### Testing and first installs

For a real simple setup a docker compose is in the root folder. It will build and run a simple web based stack.
Our test setup is using sqlite. This may not be suitable for production.

Code will be mapped into the compose via volume mount.

### Small production

There is a operations folder. It holds a docker compose file that is hosting an mariaDB.

### Production

For production a helm chart will be supplied.
