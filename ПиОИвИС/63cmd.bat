@echo off
chcp 861>nul
set /p x=Vvedite put 
set /p y=Vvedite adres 
if not exist %x% (
   exit
)
if exist *.exe (
   echo > log.txt 
   ping %y% -n 3 > log.txt
   more +1 "log.txt" > "1.txt"
   more  "1.txt" > "log.txt"
   del /q 1.txt
) else (
   echo > Process.txt
   tasklist /nh > %x%/process.txt
   more +1 "process.txt" > "1.txt"
   more  "1.txt" > "process.txt"
   del /q 1.txt 
)
set /a counter=0 
for /f %%a in (process.txt) do set /a counter+=1
echo %counter% > process.txt
cls
pause
   