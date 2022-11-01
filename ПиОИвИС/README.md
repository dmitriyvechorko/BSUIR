Описание первой лабораторной работы

На вход пакетному файлу приходит относительный путь к папке и адрес сайта (как параметры пакетного файла). Если такой папки нет, то писать “Данной папки нет” и завершить выполнение программы. Если в папке есть файлы с раcширением *.exe, то выполнить проверку связи с сайтом, результаты записать в файл log.txt. Если таких файлов нет, то создать файл, содержащий число активных в ОС процессов


bat

@echo off
if not exist %1 (
   exit
)
cd %1
if exist *.exe (
   echo > log.txt 
   ping %2 -n 3 > log.txt
   more +1 "log.txt" > "1.txt"
   more  "1.txt" > "log.txt"
   del /q 1.txt
) else (
   echo > Process.txt
   tasklist /nh > %1/process.txt
   more +1 "process.txt" > "1.txt"
   more  "1.txt" > "process.txt"
   del /q 1.txt 
)
set /a counter=0 
for /f %%a in (process.txt) do set /a counter+=1
echo %counter% > process.txt
cls
pause

bash

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

