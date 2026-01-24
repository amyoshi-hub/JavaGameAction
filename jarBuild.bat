@echo off
setlocal
echo [Step 1] Creating manifest.mf...
(
echo Main-Class: mekou.GameEngine.Frame
echo.
) > manifest.mf

echo [Step 2] Building Game.jar...
"C:\Program Files\Java\jdk-17\bin\jar.exe" cvfm Game.jar manifest.mf -C bin .

echo [Step 3] Done!
pause