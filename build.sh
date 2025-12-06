#!/bin/bash
mvn install

mvn clean compile
mvn exec:java