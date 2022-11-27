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

