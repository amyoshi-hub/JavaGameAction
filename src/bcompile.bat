@echo off
javac -encoding UTF-8 -d . mekou/*.java mekou/UtilObjects/*.java mekou/interfaces/*.java
if %errorlevel% equ 0 (
    echo [Success] Compilation complate.
    java mekou.Frame
)
pause