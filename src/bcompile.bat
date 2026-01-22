@echo off
del /s /q *.class


dir /s /b *.java > sources.txt
javac -encoding UTF-8 -d . @sources.txt
if %errorlevel% equ 0 (
    echo [Success] Compilation complate.
    java mekou.GameEngine.Frame
)
pause