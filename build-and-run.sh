#!/bin/bash
mvn clean package && cd docker && bash build.sh && bash run.sh
