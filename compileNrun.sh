#!/bin/bash

# Clean and compile server code
cd connect4-server/
mvn clean
mvn compile
cd ..

# Clean and compile client code
cd connect4-client/
mvn clean
mvn compile
cd ..

# Run program
cd connect4-server/
mvn exec:java &
cd ..
cd connect4-client/
mvn exec:java &
mvn exec:java &
cd ..
exit

