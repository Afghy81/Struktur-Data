@echo off
REM Simple Windows Batch Script for Clean Compile & Run
REM Usage: compile.bat [clean|compile|run]

setlocal enabledelayedexpansion

if "%1"=="" goto RUN_ALL

if /i "%1"=="clean" goto CLEAN_ONLY
if /i "%1"=="compile" goto COMPILE_ONLY
if /i "%1"=="run" goto RUN_ALL

echo Unknown option: %1
echo Usage: compile.bat [clean^|compile^|run]
goto END

:CLEAN_ONLY
echo [DEBUG] Membersihkan bin folder...
if exist bin rmdir /s /q bin
mkdir bin
echo [OK] Folder bin dibersihkan
goto END

:COMPILE_ONLY
echo [DEBUG] Mengkompilasi project...
if not exist bin mkdir bin
javac -d bin -encoding UTF-8 src\Main.java src\App.java
if errorlevel 1 (
    echo [ERROR] Kompilasi gagal!
    exit /b 1
)
echo [OK] Kompilasi berhasil
goto END

:RUN_ALL
echo [DEBUG] Clean + Compile + Run...
if exist bin rmdir /s /q bin
mkdir bin
echo [DEBUG] Folder bin dibersihkan
echo.
javac -d bin -encoding UTF-8 src\Main.java src\App.java
if errorlevel 1 (
    echo [ERROR] Kompilasi gagal!
    exit /b 1
)
echo [OK] Kompilasi berhasil
echo.
echo [DEBUG] Menjalankan aplikasi...
echo.
java -cp bin Main
goto END

:END
endlocal
