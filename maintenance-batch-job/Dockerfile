FROM java:8
RUN apt-get update -y
RUN apt-get install -y vim
ADD target/spring-batch-maintenance-1.3.2.RELEASE.jar /opt/
RUN mkdir /opt/config
RUN echo "# Add override spring properties here" > /opt/config/application.properties
RUN bash -c 'touch /opt/spring-batch-maintenance-1.3.2.RELEASE.jar'
#ENTRYPOINT ["java","-Dmaintenance.file.directory=file:/opt/data","-jar","/opt/spring-batch-maintenance-1.3.2.RELEASE.jar"]