start "AuthService" cmd.exe /v /c "java -jar .\auth-service\target\authservice-0.0.1-SNAPSHOT.jar"

start "RoomService" cmd.exe /v /c "java -jar .\room-service\target\roomservice-0.0.1-SNAPSHOT.jar"

start "ReservationService" cmd.exe /v /c "java -jar .\reservation-service\target\reservationservice-0.0.1-SNAPSHOT.jar"

start "UserService" cmd.exe /v /c "java -jar .\user-service\target\userservice-0.0.1-SNAPSHOT.jar"