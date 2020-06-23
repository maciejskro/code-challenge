#!/bin/bash

PROJECTPATH=$(pwd)
cd $PROJECTPATH

$PROJECTPATH/gradlew :bootRun
