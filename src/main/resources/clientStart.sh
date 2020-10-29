#!/bin/bash

PROJECT_HOME="/home/deploy/moaservice/ctrlc"
PROJECT_LIB=$PROJECT_HOME/lib;

CLASSPATH=.;
for i in ${PROJECT_LIB}/*.jar ; do
      CLASSPATH=${CLASSPATH}:${i}
  done
  CLASSPATH=$CLASSPATH:$PROJECT_CONF;

  LD_LIBRARY_PATH="$LD_LIBRARY_PATH:/usr/local/lib"
  export CLASSPATH LD_LIBRARY_PATH


exec java -cp .:conf/*:lib/*  -Xmx7024m -Xms2048m -verbose:gc -Xloggc:/home/deploy/moaservice/ctrlc/gc.log -XX:CMSInitiatingOccupancyFraction=70  -XX:MaxTenuringThreshold=15  -XX:MaxPermSize=128M -XX:SurvivorRatio=3  -XX:NewRatio=1 -XX:+CMSScavengeBeforeRemark -XX:+CMSParallelRemarkEnabled   -XX:+PrintGCDetails  -XX:+UseConcMarkSweepGC ctrlc.InsertClient >>/home/deploy/moaservice/ctrlc/stdout.log 2>&1