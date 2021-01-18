# DistributedTracing

This project helps to find the latency for a given trace, number of traces between two nodes and minimum latency in a distributed system. 

# Prerequisites

* You must be able to work with [GitHub](https://help.github.com/articles/set-up-git) repositories.
* Clone repository.

        git clone https://github.com/Pratibha24/DistributedTracing.git
        
* Install the latest version of [Java](preferably Java 1.8).
* You may need to set `JAVA_HOME`.
* Install Maven and set `MAVEN_HOME`.

# Technicals

In this project, we are having 10 different use cases to find traces of Instana's Distributed System. In order to supply input, we are using a text file (default is present inside project). The core logic is written in /DistributedTracing/src/main/java/com/instana/Main.java. Resource file is maintained under 
/DistributedTracing/src/main/resources/test.txt file. Last but not the least, unit tests are written under /DistributedTracing/src/test/java/com/instana/MainTest.java.

# Compile and Execute

In order to compile and build the project:
	
	1. navigate to folder where project is cloned ie. inside DistributedTracing folder
	2. execute the command
			 mvn compile exec:java -Dexec.mainClass="com.instana.Main"

As a result we will get "Build Success" result with expected output.

# Steps To Execute the Test

In order to execute the test, we are using '''maven-surefire-plugin'''. It gives us flexibility to execute Unit Tests present inside the project. To run the tests, run the following command:

		mvn test

It will display output as :

		Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.065 s - in com.instana.MainTest
		
As test 1-5 and 8-9 are common, we have clubbed them in one unit test. So we will get result as total Test run : 5. But internally it executes all 10 test cases.

Note: JUnit executes the test cases in Random Order.
