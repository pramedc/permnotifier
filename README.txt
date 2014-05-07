Steps to setup project

1.) You will need jdk7, maven, mysql-server, solr-server
2.) once the project is downloaded, go to <root> directory and run mvn eclipse:clean eclipse:eclipse, this will build your project with the correct eclipse settings
3.) import project into eclipse
4.) change db credential to the correct username/password. please don't check-in this file
5.) start solr server, go to solr root directory -> example, then run solr by typing java -jar start.jar
6.) import src/main/resources/initialdata/schema-mysql.sql into mysql, this is needed to keep track the batch jobs
6.) import initial data by running DataLoader.java from eclipse
7.) to start the app, goto project <root> then run mvn clean package jetty:run

