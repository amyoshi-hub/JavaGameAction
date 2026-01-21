@echo off
javac -encoding UTF-8 Frame.java Gra.java Movement.java
if %errorlevel% equ 0 (
    echo [Success] Compilation complate.
)
pause