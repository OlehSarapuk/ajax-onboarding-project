#!/bin/bash

./gradlew build
docker build -t ajax-onboarding-project .
docker-compose up
