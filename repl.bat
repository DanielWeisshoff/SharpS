@echo off
javac src/Shell.java -cp src -d out
java -cp out Shell repl