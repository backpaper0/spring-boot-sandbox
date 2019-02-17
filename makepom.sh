#!/bin/bash

echo \<?xml version=\"1.0\" encoding=\"UTF-8\"?\> > pom.xml
echo \<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\"\> >> pom.xml
echo \<modelVersion\>4.0.0\</modelVersion\> >> pom.xml
echo \<groupId\>com.example\</groupId\> >> pom.xml
echo \<artifactId\>spring-boot-sandbox\</artifactId\> >> pom.xml
echo \<version\>noversion\</version\> >> pom.xml
echo \<packaging\>pom\</packaging\> >> pom.xml
echo \<modules\> >> pom.xml

for i in `find . -type f|grep -v '^\./pom\.xml$'|grep 'pom\.xml$'|cut -c 3-|sort -u`; do echo \<module\>`dirname $i`\</module\>; done >> pom.xml

echo \</modules\> >> pom.xml
echo \</project\> >> pom.xml
