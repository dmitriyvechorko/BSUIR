#!/bin/bash
read -p "Vvedite put " x
read -p "Vvedite adres " y
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
