cd /entrypoint.d/
javac InitDb.java
java InitDb

java -Duser.timezone=Asia/Shanghai -jar  /entrypoint.d/learn-service-1.0.0-SNAPSHOT.jar