find . -name "*.class" -delete
javac -encoding UTF-8 -d . $(find . -name "*.java")

# 3. コンパイルが成功したら実行
if [ $? -eq 0 ]; then
    echo "[Success] Compilation complete."
    # メインクラスの場所に合わせて書き換えてください
    java mekou.GameEngine.Frame
else
    echo "[Error] Compilation failed."
fi