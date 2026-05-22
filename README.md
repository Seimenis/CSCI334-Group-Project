# UOW Parking Lot System
The UOW Parking Lot System is a Smart Lot, equipped with parking space sensors, availability forecasting and a Traffic Prediction Model.

## Running the application

Startup docker instance
```
docker compose up -d
docker ps
docker logs kafka
docker logs zookeeper
```

Now startup microservice using the run button in vscode

Make sure to operate terminal commands using Git Bash.

# Accounts Microservice

## Environment variables

For demo purposes, default values for environment variables have been provided.

For production, make sure to set:

JWT_SECRET
JWT_EXPIRATION

ADMIN_EMAIL
ADMIN_PASSWORD
ADMIN_USERNAME

KAFKA_SERVER


After logging into your account you will receive a JWT Token, 
this token is necessary security measure for any API call using an Authorization header.
It can be set in the terminal like so:

```
export TOKEN="<your-token>"
```


## Commands

### User Commands

Registering a user

```
curl -X POST http://localhost:8080/accounts/register \
-H "Content-Type: application/json" \
-d '{
  "username": "Tom",
  "email": "tom@ross.com",
  "password": "something1"
}'
```

Logging in as a user

```
curl -X POST http://localhost:8080/accounts/login \
-H "Content-Type: application/json" \
-d '{
  "email": "tom@ross.com",
  "password": "something1"
}'
```

Logging in as admin

```
curl -X POST http://localhost:8080/accounts/login \
-H "Content-Type: application/json" \
-d '{
  "email": "admin@example.com",
  "password": "changemepls123!"
}'
```

Viewing account details (except for password)

```
curl -G "http://localhost:8080/accounts" -H "Authorization: Bearer ${TOKEN}"
```

Update account details (will need to login and export token again afterwards)

```
curl -X PATCH "http://localhost:8080/accounts" \
-H "Authorization: Bearer ${TOKEN}" \
-H "Content-Type: application/json" \
-d '{
  "username": "Michael Fazbender",
  "email": "fazbend@gmail.com",
  "password": "yodellingman2500"
}'
```

### Staff Commands

This will enable you to use the following commands

Querying accounts for analytics using different syntax

```
curl -G "http://localhost:8080/staff/accounts" \
  -H "Authorization: Bearer ${TOKEN}"


curl -G "http://localhost:8080/staff/accounts" \
  -H "Authorization: Bearer ${TOKEN}" \
  -d "enabled=true" \
  -d "role=STAFF" \
  -d "startDate=2025-01-01" \
  -d "endDate=2026-12-31"

curl -G "http://localhost:8080/staff/accounts" -H "Authorization: Bearer ${TOKEN}" -d "enabled=false&role=USER"
```

### Admin Commands

Registering staff and admin accounts

```
curl -X POST http://localhost:8080/admin/accounts/staff \
-H "Content-Type: application/json " \
-H "Authorization: Bearer ${TOKEN}" \
-d '{
  "username": "Billy Jean",
  "email": "thekidisnotmyown@gmail.com",
  "password": "moviestar25"
}'

curl -X POST http://localhost:8080/admin/accounts/admin \
-H "Content-Type: application/json" \
-H "Authorization: Bearer ${TOKEN}" \
-d '{
  "username": "Bob rossy",
  "email": "whateveweqafwar@gmail.com",
  "password": "mirfed256"
}'
```

Querying all and specific users with admin privliges

```
curl -G "http://localhost:8080/admin/accounts" -H "Authorization: Bearer ${TOKEN}"
curl -G "http://localhost:8080/admin/accounts/89" -H "Authorization: Bearer ${TOKEN}"
```

Enabling and disabling accounts

```
curl -X PATCH "http://localhost:8080/admin/accounts/72/enable" -H "Authorization: Bearer ${TOKEN}"
curl -X PATCH "http://localhost:8080/admin/accounts/21/disable" -H "Authorization: Bearer ${TOKEN}"
```

Deleting accounts

```
curl -X DELETE "http://localhost:8080/admin/accounts/89" -H "Authorization: Bearer ${TOKEN}"
```

# Spotter Microservice

The Spotter service provides UOW parking space detection, seeded parking data, and a repeatable sensor simulation feed for frontend and analytics work.

It runs on port `8085` and loads its datasets from inside the service:

```
project/spotter/src/main/resources/data/uow-parking-spaces.csv
project/spotter/src/main/resources/data/uow-spotter-feed.csv
```

The service publishes JSON Kafka events when spaces are created or updated:

```
spotter.created
spotter.updated
```

For frontend-only development without Kafka running, start Spotter with `SPOTTER_KAFKA_ENABLED=false`.

## Spotter commands

Run only the Spotter service:

```
cd project
mvn -pl spotter spring-boot:run
```

Use these endpoints from the frontend:

```
curl http://localhost:8085/api/spotter/health
curl http://localhost:8085/api/spotter/spaces
curl http://localhost:8085/api/spotter/lots
curl http://localhost:8085/api/spotter/zones
curl http://localhost:8085/api/spotter/summary
curl http://localhost:8085/api/spotter/events
```

Filter spaces by lot, zone, occupancy, or disability permit requirement:

```
curl "http://localhost:8085/api/spotter/spaces?lotName=P1-North&zone=A&occupied=false"
```

Advance the simulation by one sensor event:

```
curl -X POST http://localhost:8085/api/spotter/simulation/next
```

Run several simulation events at once:

```
curl -X POST http://localhost:8085/api/spotter/simulation/run \
-H "Content-Type: application/json" \
-d '{
  "eventCount": 5,
  "publishEvents": true
}'
```

Record a manual sensor reading:

```
curl -X POST http://localhost:8085/api/spotter/sensors/UOW-P1-A-001/detect \
-H "Content-Type: application/json" \
-d '{
  "occupied": true,
  "confidence": 0.98,
  "source": "frontend-demo"
}'
```

Reset the in-memory database back to the CSV dataset and restart the simulation feed:

```
curl -X POST http://localhost:8085/api/spotter/simulation/reset
```
  
