# ART Android Application
[![CircleCI](https://circleci.com/gh/AndelaOSP/art-android.svg?style=svg&circle-token=b1250aceec287752949d11b859d661b0499d1fd7)](https://circleci.com/gh/AndelaOSP/art-android)
[![codecov.io](https://codecov.io/gh/AndelaOSP/art-android/branch/develop/graph/badge.svg)](https://codecov.io/gh/AndelaOSP/art-android/)
[![Build status](https://build.appcenter.ms/v0.1/apps/d4500178-11b2-401f-83b8-e30fea93bb06/branches/ch-setup-microsoft-app-centre-%23162710280/badge)](https://appcenter.ms)


The Andela Resource Tracker Android app.

## Project Setup

This project is built with Gradle, the [Android Gradle plugin](http://tools.android.com/tech-docs/new-build-system/user-guide). Follow the steps below to setup the project locally.

1. Clone [ART Android](https://github.com/AndelaOSP/art-android) inside your working folder.
2. Start Android Studio
3. Select "Open Project" and select the generated root Project folder
4. Request the current dev ops team or current team lead for a viable ` google-services.json ` file. 
5. Add the ` google-services.json ` to the following directories:
- ` app/src/mock `
- ` app/src/debug `
- ` app/src/prod `
- ` app/src/release `
6. You may be prompted with "Unlinked gradle project" -> Select "Import gradle project" and select 
the option to use the gradle wrapper
7. You may also be prompted to change to the appropriate SDK folder for your local machine
8. Once the project has compiled -> run the project!

## Usable accounts
ART has two types of users: Security users and Regular users. Security users will use the application to track andela provided devices while regular users will only have access to their profile, listing their andela devices. As a developer on ART, you will need your andela email and a separate gmail account. *(Consult with dev ops or the team lead on the addition of your emails to the database)*. This will allow you to replicate application behavior during development. However, since a google authentication api is used, you will need a SHA-1 key to login.

## SHA1 Key
You will require your device's SHA-1 key to be able to use the google API. 

### Getting your device key
#### Using the terminal
Run the following command: `keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android. `

#### Using android studio
- Open the project in android studio.
- Click on Gradle Menu on the right side and expand it.
- Click on android and then run signing report. More info.
**Note:** Once you generate your SHA-1 key, copy it and send it to the current dev ops team or the current team lead. This will allow you to log into the application with your andela email and security user email.

## Trouble logging in?
If you get an authentication error ` Google account not selected `
#### First
- Check if you are in the correct build variant flavor.

![Build flavor](https://image.ibb.co/ir1Z6V/Screenshot-2018-10-24-at-17-51-16.png)


#### Next
- Find the pinned post in ` art-android ` slack channel that contains ` Super Secret Stuff ` and download it to your ` Downloads ` folder.
- Move it to your secrets folder by running the following command while in the base directory of the app: ` mkdir -p ~/.secrets && mv ~/Downloads/art-android.jks ~/.secrets/art-android.jks `
- Next you'll need to export the following variables and you're good to go:
  ```
  export KEY_ALIAS=art-android-key
  export KEY_PASSWORD=art-android
  export STORE_PASSWORD=art-android
  ```

#### Other fixes
Confirm your SHA-1 key has been added to the database.
