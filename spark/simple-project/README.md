SETUP
* Versions
  * https://github.com/big-data-europe/docker-spark
  
* URLS
  * localhost:8080 for spark ui master
    http://localhost:8081 worker 1
    http://localhost:8082 worker 2
  
* STEPS
  * brew install adoptopenjdk8* jdk 1.8
  * /usr/libexec/java_home -V
  * jdk 1.8
  * /Users/paul/Dev/Apps/spark-3.1.1-bin-hadoop3.2/bin/spark-submit --version

/Users/paul/Dev/Apps/spark-3.1.1-bin-hadoop3.2/bin/spark-submit \
--class "SimpleApp" \
--master "local[4]" \
target/simple-project-1.0.jar

/Users/paul/Dev/Apps/spark-3.1.1-bin-hadoop3.2/bin/spark-submit \
--class "SimpleApp" \
--master "spark://localhost:7077" \
target/simple-project-1.0.jar

docker exec -ti spark-master bash

TODO:
can't access file in spark.
maybe try with hdfs? https://github.com/big-data-europe/demo-spark-sensor-data

try using the java template to submit work


