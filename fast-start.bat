rem start "EurekaServiceBuild" /wait cmd.exe /v /c "cd .\eureka-service & mvn clean package"

rem start "ConfigServiceBuild" /wait cmd.exe /v /c "cd .\config-service & mvn clean package"

rem start "GatewayServiceBuild" /wait cmd.exe /v /c "cd .\gateway-service & mvn clean package"

start "EurekaService" cmd.exe /v /c "java -jar .\eureka-service\target\EurekaService-0.0.1-SNAPSHOT.jar"

start "ConfigService" cmd.exe /v /c "java -jar .\config-service\target\ConfigService-0.0.1-SNAPSHOT.jar"

start "GatewayService" cmd.exe /v /c "java -jar .\gateway-service\target\GatewayService-0.0.1-SNAPSHOT.jar"