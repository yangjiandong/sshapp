1. cd to the dir of this file present

2. install kaptcha into local maven repository
mvn install:install-file -DgroupId=com.google.code -DartifactId=kaptcha -Dversion=2.3 -Dpackaging=jar -Dfile=kaptcha-2.3.jar

3. install zapcat into local maven repository
mvn install:install-file -DgroupId=zapcat -DartifactId=zapcat -Dversion=1.2 -Dpackaging=jar -Dfile=zapcat-1.2.jar



