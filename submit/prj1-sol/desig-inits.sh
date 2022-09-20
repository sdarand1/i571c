#!/bin/sh

inputs=$(sed '/^$/q')

#sets dir to directory containing this script
dir=`dirname $0`

#use $dir/ as prefix to run any programs in this dir
#so that this script can be run from any directory.

java -cp $dir:./bin/:./src/lib/json-20200518.jar SyntaxAnalyzer.SyntaxAnalyzer $inputs;
