Команды необходимо вводить в директории проекта:

1)  gradlew clean - clean target folder
2)  gradlew build - create target folder and .jar file
3)  gradlew test -  test run
    
4)  //run on the test profile
    java -jar build/libs/app-1.0.jar 
    //run on the production profile
    java -jar -Dspring.profiles.active=prod build/libs/app-1.0.jar

5)  https://localhost:9003 - url

/*---CUSTOM PORT IN COMMAND LINE-----------------------*/
/*---${number} - any number for example 8080-----------*/    
    java -jar -Dserver.port=${number} build/libs/app-1.0.jar
    
