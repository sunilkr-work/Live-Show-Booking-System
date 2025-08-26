@echo off
echo Compiling Live Show Booking System...
javac -cp src\main\java -d target\classes src\main\java\com\LiveShowBookingSystem\constant\*.java src\main\java\com\LiveShowBookingSystem\pojo\*.java src\main\java\com\LiveShowBookingSystem\model\*.java src\main\java\com\LiveShowBookingSystem\utils\*.java src\main\java\com\LiveShowBookingSystem\repository\*.java src\main\java\com\LiveShowBookingSystem\service\*.java src\main\java\com\LiveShowBookingSystem\controller\*.java src\main\java\com\LiveShowBookingSystem\*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo Starting Live Show Booking System...
echo.
java -cp target\classes com.LiveShowBookingSystem.LiveShowBookingSystemApplication
pause