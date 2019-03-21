#!/bin/bash

echo "Directory: $(pwd)"
echo "Maven information:"
echo "$(mvn -version)"

echo "Build, execute tests and install in local m2 repo"
./scripts/build.sh clean install -T$(nproc)
