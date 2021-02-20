#!/bin/bash

REPOSITORY=/home/ec2-user/app
GITPROJECT_NAME=Jup-Jup-Server
PROJECT_NAME=jupjup

cd $REPOSITORY/$GITPROJECT_NAME/

echo "> Git Pull"
git pull

echo "target 폴더 삭제"
mvn clean

echo "> 프로젝트 compile 시작"
mvn compile

echo "> 프로젝트 package 시작"
mvn package

echo "> Build 파일 복사"
cp $REPOSITORY/$GITPROJECT_NAME/target/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl jupjup | grep java | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
   echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
   echo "> kill -15 $CURRENT_PID"
   kill -15 $CURRENT_PID
   sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls $REPOSITORY/ | grep 'jupjup' | tail -n 1)

echo "> JAR Name: $JAR_NAME"
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &