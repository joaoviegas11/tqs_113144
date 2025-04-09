FAzer para ligar o projeto: 

docker compose --build


Isto é para acder a bd via terminal
docker exec -it hw1-postgres-1 bash
psql -U postgres -d tqs_db


DROP TABLE IF EXISTS reservations CASCADE;
DROP TABLE IF EXISTS menu CASCADE;
DROP TABLE IF EXISTS refectory CASCADE;

sonarqube:
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
mvn clean verify sonar:sonar -D"sonar.projectKey=HW1" -D"sonar.projectName=HW1" -D"sonar.host.url=http://localhost:9000" -D"sonar.token=sqp_9f83282cf1ba3d24210d594057dbdcd7a17962de"