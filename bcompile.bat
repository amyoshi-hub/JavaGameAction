@echo off
setlocal enabledelayedexpansion

:: --- 設定項目 ---
set MAIN_CLASS=mekou.GameEngine.Frame
set SRC_DIR=src
set BIN_DIR=bin
set IMG_SRC=src\mekou\img
set IMG_DEST=bin\mekou\img

echo [Step 1] Cleaning up...
if not exist %BIN_DIR% mkdir %BIN_DIR%
:: 古いクラスファイルを削除
del /s /q %BIN_DIR%\*.class >nul 2>&1

echo [Step 2] Copying resources...
:: /D (更新分のみ) /E (空のフォルダ含む) /Y (上書き確認なし) /I (ディレクトリとして扱う)
xcopy "%IMG_SRC%" "%IMG_DEST%" /D /E /Y /I >nul

echo [Step 3] Compiling...
:: Javaファイルのリストを作成
dir /s /b %SRC_DIR%\*.java > sources.txt

javac -encoding UTF-8 -d %BIN_DIR% @sources.txt

if %errorlevel% equ 0 (
    echo [Success] Compilation complete.
    echo [Step 4] Launching Game...
    :: クラスパスに bin を指定して実行
    java -Dfile.encoding=UTF-8 -cp %BIN_DIR% %MAIN_CLASS%
) else (
    echo [Error] Compilation failed.
)

:: 一時ファイルの削除
if exist sources.txt del sources.txt
pause