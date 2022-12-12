# Architecture

The architecture should be as open for scaling as possible.
This means we try to archive an microservice architecture or something to be hosted and scaled native.

This will be some stuff in docker to be open to kubernetes.

## APP

The app should be used on browsers, as well as mobiles. To get this a frontend/backend singulation will be in use.

### Web Frontend
The the web page will be written in REACT. This seem to be a good choice at the moment.

### Mobile

The mobile is a thing to be defined.

### Backend

The backend will be written in Django for MVP. This keeps a lot of smaller pitfalls away for the first phase.
For administration of the data in the app we will use the Django admin interface. This will work for the first entities. Later we may adopt the backend.

The api itself will only return json data. Also login stuff should be managed in jwt and rest. This is not the case for admnin! This will not be Rest for now. This sould not be a big limitation!


## DB

The database will be mariadb. Maria should fix all needs. Including some json fields in the Database.
Later on we might need redis (which is not a real DB...) or similar stuff.

The DB will be managed by Django. This seems to be a good choice.



## Deployment

The deployment should scale. For development, testing and small scale we will use docker compose. This will be controlled via ansible.
On scale we will offer a helm chart.
