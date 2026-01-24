@echo off
setlocal
echo ======================================================
echo  MEKOU ENGINE - Verbose Build Pipeline
echo ======================================================

:: 1. 作業ディレクトリのクリーンアップ
set WORK_DIR=%~dp0build_work
set OUT_DIR=%~dp0dist

if exist "%WORK_DIR%" rmdir /s /q "%WORK_DIR%"
if exist "%OUT_DIR%" rmdir /s /q "%OUT_DIR%"
mkdir "%WORK_DIR%"

:: 2. JAR作成
echo [Step 1] Creating JAR...
(echo Main-Class: mekou.GameEngine.Frame & echo.) > manifest.mf
"C:\Program Files\Java\jdk-17\bin\jar.exe" cvfm "%WORK_DIR%\Game.jar" manifest.mf -C bin .

:: 3. jpackage実況ログ開始
echo [Step 2] Starting jpackage with Verbose logging...
echo ------------------------------------------------------
"C:\Program Files\Java\jdk-17\bin\jpackage.exe" ^
  --name "MEKOU_ACTION" ^
  --input "%WORK_DIR%" ^
  --main-jar Game.jar ^
  --main-class mekou.GameEngine.Frame ^
  --java-options "-Dfile.encoding=UTF-8" ^
  --type app-image ^
  --dest "%OUT_DIR%" ^
  --icon "mekou_logo.ico" ^
  --verbose
echo ------------------------------------------------------

:: 4. リソースのコピー
echo [Step 3] Copying Resources...
:: Gamesフォルダのコピー
xcopy /E /I /Y "Games" "%OUT_DIR%\MEKOU_ACTION\Games"

:: mekou\img フォルダのコピー (mekouフォルダごとコピーする)
xcopy /E /I /Y "src\mekou\img" "%OUT_DIR%\MEKOU_ACTION\mekou\img"
:: もし bin の中に最新画像があるならこちら
:: xcopy /E /I /Y "bin\mekou\img" "%OUT_DIR%\MEKOU_ACTION\mekou\img"

echo.
echo ======================================================
echo  Build Complete! ログを確認してください。
echo ======================================================
pause