echo off

rmdir /S /Q ipch 

IF NOT EXIST .\Debug GOTO NODEBUG:
mkdir .\Debug\saveme
move .\Debug\*.exe .\Debug\saveme
move .\Debug\*.dll .\Debug\saveme
move .\Debug\*.fx .\Debug\saveme
del /Q .\Debug\*.* 
move .\Debug\saveme\* .\Debug
rmdir /Q /S .\Debug\saveme
:NODEBUG

rmdir /Q /S .\Project\Debug

IF NOT EXIST .\Release GOTO NORELEASE:
mkdir .\Release\saveme
move .\Release\*.exe .\Release\saveme
move .\Release\*.dll .\Release\saveme
move .\Release\*.fx .\Release\saveme
del /Q .\Release\*.* 
move .\Release\saveme\* .\Release
rmdir .\Release\saveme
:NORELEASE

rmdir /Q /S .\Project\Release

del /Q *.sdf


