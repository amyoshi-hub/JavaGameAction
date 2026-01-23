#!/bin/bash

# --- 設定項目 ---
MAIN_CLASS="mekou.GameEngine.Frame"
SRC_DIR="src"
BIN_DIR="bin"
IMG_SRC="src/mekou/img"
IMG_DEST="bin/mekou/img"

# 1. 掃除とディレクトリ準備
echo "[Step 1] Cleaning up..."
mkdir -p $BIN_DIR
find $BIN_DIR -name "*.class" -delete

# 2. リソース（画像）の同期
# srcにある画像をbinにコピー。更新があったファイルだけコピーする -u が便利。
echo "[Step 2] Copying resources..."
mkdir -p $IMG_DEST
cp -ru $IMG_SRC/* $IMG_DEST/

# 3. コンパイル
echo "[Step 3] Compiling..."
javac -encoding UTF-8 -d $BIN_DIR $(find $SRC_DIR -name "*.java")

# 4. 実行
if [ $? -eq 0 ]; then
    echo "[Success] Compilation complete."
    echo "[Step 4] Launching Game..."
    # -cp bin で bin フォルダをルートとして認識させる
    java -cp $BIN_DIR $MAIN_CLASS
else
    echo "[Error] Compilation failed. Please check the logs."
fi