#!/bin/bash
if [ JAVA_HOME = "" ] ; then
    export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
fi

rm -r target
mkdir target
wget -q https://download.eclipse.org/jakartaee/authentication/3.0/jakarta-authentication-tck-3.0.1.zip -O target/jakarta-authentication-tck-3.0.1.zip
unzip -q target/jakarta-authentication-tck-3.0.1.zip -d target
sed -i "/<id>custom<\/id>/r payara-profile.xml" target/authentication-tck-3.0.1/tck/pom.xml
mvn clean install -Pcustom -f target/authentication-tck-3.0.1/tck/pom.xml