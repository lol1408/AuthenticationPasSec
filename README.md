Команды необходимо вводить в директории проекта:

1)  gradlew clean - clean target folder
2)  gradlew build - create target folder and .jar file
3)  gradlew test -  test run
    //run in test profile
4)  java -jar build/libs/app-1.0.jar 
    //run in production profile
    java -jar -Dspring.profiles.active=prod build/libs/app-1.0.jar
    
5)  https://localhost:9003 - url
