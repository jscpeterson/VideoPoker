## Build Instructions
VideoPoker is not a finished product, so if you would like to directly run VideoPoker in its current build state you should use Android Studio or IntelliJ IDEA to open the source project from the Git repository and run it locally. To do this: 
1. Open IntelliJ or Android Studio. For these instructions I am assuming you are using IntelliJ IDEA. Click "Check out from Version Control".
2. Paste **git@github.com:jscpeterson/VideoPoker.git** into the dialog that appears and click "Clone". When asked if you want to open to the project, you may select Yes.
3. When the project loads, under "File" on the menubar go to "Project Structure".
4. Select SDKs.
5. Select "Android API 28 Platform" from the list and click OK. If it is not available, click the plus sign and click Android SDK, then locate your Android SDK directory.
6. On the project screen, under "Build" on the menubar, select "Build".
7. Under "Run" on the menubar, select "Run". If you do not have a local device it is recommended you run this program on an emulator for the Nexus API 28 or the Galaxy Nexus API 26. The application should install and autolaunch once the emulator has loaded or on your local device. 
