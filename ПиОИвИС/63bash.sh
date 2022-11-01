#!/bin/bash
x=$1
y=$2
if [ ! -d $x ]; then
  exit
fi
if [ -f *.exe ]; then
  ping $y > log.txt
else
  echo > Process.txt
  ps aux | wc -l > Process.txt
 fi
$SHELL