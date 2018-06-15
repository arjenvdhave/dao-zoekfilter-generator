#!/bin/bash
echo Building
mvn -q clean compile assembly:single
