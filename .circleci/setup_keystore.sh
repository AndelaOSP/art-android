#!/bin/bash

set -ex
set -o pipefail

export KEYSTORE_DIR=$HOME"/repo/keystores"
export KEYSTORE_PROPERTIES=$HOME"/repo/keystores/keystore.properties"
export STORE_FILE_LOCATION=$HOME"/repo/art-android.jks"

# Environment variables assigned externally are:
# KEY_ALIAS: This value was set when generating the key in Android Studio
# KEY_PASSWORD: This value was set when generating the key in Android Studio
# KEY_STORE_URI: This is the URL to download the keystore file from online storage
# STORE_FILE: The path to the keystore file on the build server. Use /home/circleci/repo/art-android.jks for CircleCI
# STORE_PASSWORD: This value was set when generating the key in Android Studio

# Create the keystore.properties file and populate it with the values in the environment variables
function copyEnvVarsToProperties {

    echo "Keystore Properties should exist at $KEYSTORE_PROPERTIES"

    if [ ! -f "$KEYSTORE_PROPERTIES" ]
    then
        echo "${KEYSTORE_PROPERTIES} does not exist...Creating file"

        if [ ! -d "$KEYSTORE_DIR" ]
        then
            mkdir $KEYSTORE_DIR
        fi

        touch ${KEYSTORE_PROPERTIES}

        echo "keyAlias=$KEY_ALIAS" >> ${KEYSTORE_PROPERTIES}
        echo "keyPassword=$KEY_PASSWORD" >> ${KEYSTORE_PROPERTIES}
        echo "storeFile=$STORE_FILE" >> ${KEYSTORE_PROPERTIES}
        echo "storePassword=$STORE_PASSWORD" >> ${KEYSTORE_PROPERTIES}
    fi

}

# download key store file from remote location
# keystore URI will be the location uri for the *.jks file for signing the application
function downloadKeyStoreFile {
    # use curl to download a keystore from $KEYSTORE_URI, if set,
    # to the path/filename set in $KEYSTORE.
    echo "Looking for $STORE_FILE_LOCATION ..."

    if [ ! -f ${STORE_FILE_LOCATION} ] ; then
        echo "Downloading keystore file"

        curl -L -o ${STORE_FILE} ${KEY_STORE_URI}
    else
            echo "Keystore uri not set, .APK artifact will not be signed."
    fi
}

main() {
  
  copyEnvVarsToProperties
  downloadKeyStoreFile

}

main "$@"