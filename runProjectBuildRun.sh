#!/usr/bin/env bash

mvn clean package

export LANG=en_US.UTF-8

JAR_FILE=$(ls target/*.jar | head -n 1)

if [[ -f "$JAR_FILE" ]]; then
  echo "Запуск $JAR_FILE"
  java -Dfile.encoding=UTF-8 -jar "$JAR_FILE"
else
  echo "JAR-файл не найден."
  exit 1
fi
read -n 1