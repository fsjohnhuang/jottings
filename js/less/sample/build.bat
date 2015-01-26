@echo off
cls
goto :%1

:bin
echo Building......
::remove subitems of bin
rd /S /Q bin
::copy html files
xcopy /y src\*.html bin\
::compile less to css
cmd /C lessc --include-path=lib/less --relative-urls --source-map --source-map-map-inline --source-map-rootpath=../../src/less/main.less src/less/main.less bin/style/main.css
echo Building is over!
goto :over

:debug
echo Building......
::remove subitems of bin
rd /S /Q bin
::copy html files
xcopy /y src\*.html bin\
::compile less to css
cmd /C lessc --include-path=lib/less --relative-urls --source-map --source-map-rootpath=../../src/less/ --modify-var="env=debug" src/less/main.less bin/style/main.css
echo Building is over!
goto :over

:dist
echo Deploying......
::remove subitems of dist
rd /S /Q dist
::copy lib
xcopy /y lib\less\img dist\lib\less\img\
::copy html files
xcopy /y src\*.html dist\app\
::compile less to css
cmd /C lessc --include-path=lib/less --clean-css="advanced" --relative-urls src/less/main.less dist/app/style/main.css
echo Deploying is over!

:over