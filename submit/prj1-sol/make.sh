#!/bin/sh

#sets dir to directory containing this script
dir=`dirname $0`

#use $dir to access programs in this directory
#so that this script can be run from any directory.

javac -d bin -cp $dir:./src/:./src/lib/json-20200518.jar src/SyntaxAnalyzer/*.java;

echo "Build Done."
