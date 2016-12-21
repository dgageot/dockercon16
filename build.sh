#!bin/bash
set -e
dotnet restore words-aspnet/project.json
rm -rf $(pwd)/publish/words-aspnet
dotnet publish words-aspnet/project.json -c release -o $(pwd)/publish/words-aspnet