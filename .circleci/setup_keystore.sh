#!/bin/bash

set -ex
set -o pipefail

export KEYSTORE_DIR=$HOME"/repo/keystores"
export KEYSTORE_PROPERTIES=$HOME"/repo/keystores/keystore.properties"
export STORE_FILE_LOCATION=$HOME"/.secrets/art-android.jks"

# Environment variables assigned externally are:
# KEY_ALIAS: This value was set when generating the key in Android Studio
# KEY_PASSWORD: This value was set when generating the key in Android Studio
# KEY_STORE_URI: This is the URL to download the keystore file from online storage
# STORE_FILE: The path to the keystore file on the build server. Use /home/circleci/repo/art-android.jks for CircleCI
# STORE_PASSWORD: This value was set when generating the key in Android Studio

# Create the keystore.properties file and populate it with the values in the environment variables
function copyEnvVarsToProperties {

    echo "Keystore Properties should exist at $KEYSTORE_PROPERTIES"

    if [ ! -d "$KEYSTORE_DIR" ]
    then
        echo "Keystore directory does not exist. Making the directory now..."
        mkdir $KEYSTORE_DIR
    fi

    if [ ! -f "$KEYSTORE_PROPERTIES" ]
    then
        echo "${KEYSTORE_PROPERTIES} does not exist. Creating the file..."

        touch ${KEYSTORE_PROPERTIES}
    else
        echo "${KEYSTORE_PROPERTIES} detected. Overwriting values..."

        echo "" > ${KEYSTORE_PROPERTIES}
    fi

    echo "Copying keystore properties..."

    echo "keyAlias=$KEY_ALIAS" >> ${KEYSTORE_PROPERTIES}
    echo "keyPassword=$KEY_PASSWORD" >> ${KEYSTORE_PROPERTIES}
    echo "storeFile=$STORE_FILE" >> ${KEYSTORE_PROPERTIES}
    echo "storePassword=$STORE_PASSWORD" >> ${KEYSTORE_PROPERTIES}

}

main() {
  
  copyEnvVarsToProperties

}

main