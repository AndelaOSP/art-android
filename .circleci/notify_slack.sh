#!/bin/bash

set -eo pipefail

declare_env_variables() {

  # Declaring environment variables
  #
  # Some environment variables assigned externally are:
  # CIRCLE_TOKEN : This is the API token that is provided for the CircleCI user. Used for accessing artifacts
  # SLACK_CHANNEL_HOOK : This is the webhook for the Slack App where notifications will be sent from
  # DEPLOYMENT_CHANNEL : This is the channel on which the Slack notifications will be posted

  # Retrieving the urls for the CircleCI artifacts

  CIRCLE_ARTIFACTS_URL="$(curl https://circleci.com/api/v1.1/project/github/${CIRCLE_PROJECT_USERNAME}/${CIRCLE_PROJECT_REPONAME}/${CIRCLE_BUILD_NUM}/artifacts?circle-token=${CIRCLE_TOKEN} | grep -o 'https://[^"]*')"

  # Assigning slack messages based on the CircleCI job name

  if [ "$CIRCLE_JOB" == 'test' ]; then
    MESSAGE_TEXT="Test Phase Passed! :smiley:"

    # Sorting through the artifact urls to get only the unit test reports
    DEBUG_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' | grep 'test[A-Za-z0-9]*Debug[A-Za-z0-9]*\/index\.html')"
    RELEASE_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' | grep 'test[A-Za-z0-9]*Release[A-Za-z0-9]*\/index\.html')"
    JACOCO_DEBUG_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' | grep 'jacoco[A-Za-z0-9]*Debug[A-Za-z0-9]*\/html\/index\.html')"
    JACOCO_RELEASE_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' | grep 'jacoco[A-Za-z0-9]*Release[A-Za-z0-9]*\/html\/index\.html')"

    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Unit Test Report (Debug)\", \"url\": \"${DEBUG_REPORT}\"}", \
        "{\"type\": \"button\", \"text\": \"Unit Test Report (Release)\", \"url\": \"${RELEASE_REPORT}\"}", \
        "{\"type\": \"button\", \"text\": \"Jacoco Test Report (Debug)\", \"url\": \"${JACOCO_DEBUG_REPORT}\"}", \
        "{\"type\": \"button\", \"text\": \"Jacoco Test Report (Release)\", \"url\": \"${JACOCO_RELEASE_REPORT}\"}", \
    )"

  elif [ "$CIRCLE_JOB" == 'instrumented_test' ];  then
    MESSAGE_TEXT="The Instrumented Tests Passed! :smiley:"

    # Sorting through the artifact urls to get only the instrumentation test reports
    INSTRUMENTATION_RESULTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'instrumentation.results')"
    LOGCAT_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'logcat')"
    TEST_RESULT_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'test_result_1.xml')"
    TEST_RESULT_VIDEO="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'video.mp4')"

    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Instrumentation Test Report \", \"url\": \"${INSTRUMENTATION_RESULTS}\"}", \
        "{\"type\": \"button\", \"text\": \"Logcat Test Report \", \"url\": \"${LOGCAT_REPORT}\"}", \
        "{\"type\": \"button\", \"text\": \"Test Result Report \", \"url\": \"${TEST_RESULT_REPORT}\"}", \
        "{\"type\": \"button\", \"text\": \"Test Result Video \", \"url\": \"${TEST_RESULT_VIDEO}\"}", \
    )"
    
  elif [ "$CIRCLE_JOB" == 'deploy_test_build' ]; then
    # Sorting through the artifact urls to get only the apk files
    CIRCLE_APK_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.apk')"

    CIRCLE_DEBUG_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'debug[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_RELEASE_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'release[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Debug APK\", \"url\": \"${CIRCLE_DEBUG_ARTIFACT}\"}", \
        "{\"type\": \"button\", \"text\": \"Release APK\", \"url\": \"${CIRCLE_RELEASE_ARTIFACT}\"}" \
    )"

    MESSAGE_TEXT="Deploy Test Build Succeeded :rocket:"


  elif [ "$CIRCLE_JOB" == 'deploy_staging_build' ]; then
    CIRCLE_APK_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.apk')"

    CIRCLE_DEBUG_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'debug[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_RELEASE_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'release[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Debug APK\", \"url\": \"${CIRCLE_DEBUG_ARTIFACT}\"}", \
        "{\"type\": \"button\", \"text\": \"Release APK\", \"url\": \"${CIRCLE_RELEASE_ARTIFACT}\"}" \
    )"

    MESSAGE_TEXT="Deploy Staging Build Succeeded :rocket:"


  elif [ "$CIRCLE_JOB" == 'deploy_production_build' ]; then
    CIRCLE_APK_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.apk')"

    CIRCLE_DEBUG_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'debug[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_RELEASE_ARTIFACT="$(echo $CIRCLE_APK_ARTIFACTS | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'release[a-z0-9.-]*[a-z0-9.-]*.apk$')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Debug APK\", \"url\": \"${CIRCLE_DEBUG_ARTIFACT}\"}", \
        "{\"type\": \"button\", \"text\": \"Release APK\", \"url\": \"${CIRCLE_RELEASE_ARTIFACT}\"}" \
    )"

    MESSAGE_TEXT="Deploy Production Build Succeeded :rocket:"


  else
    MESSAGE_TEXT="Unknown Task"
    CIRCLE_ARTIFACTS_MESSAGE="No artifacts for unknown job"
  fi

  # Some template for the Slack message

  COMMIT_LINK="https://github.com/${CIRCLE_PROJECT_USERNAME}/${CIRCLE_PROJECT_REPONAME}/commit/${CIRCLE_SHA1}"
  IMG_TAG="$(git rev-parse --short HEAD)"
  CIRCLE_WORKFLOW_URL="https://circleci.com/workflow-run/${CIRCLE_WORKFLOW_ID}"
  SLACK_TEXT_TITLE="CircleCI Build #$CIRCLE_BUILD_NUM"
  SLACK_DEPLOYMENT_TEXT="Executed Git Commit <$COMMIT_LINK|${IMG_TAG}>: ${MESSAGE_TEXT}"
}

send_notification() {

  # Sending the Slack notification

  curl -X POST --data-urlencode \
  "payload={
      \"channel\": \"${DEPLOYMENT_CHANNEL}\", 
      \"username\": \"DeployNotification\", 
      \"attachments\": [{
          \"fallback\": \"CircleCI build notification and generated files\",
          \"color\": \"good\",
          \"author_name\": \"Branch: $CIRCLE_BRANCH by ${CIRCLE_USERNAME}\",
          \"author_link\": \"https://github.com/AndelaOSP/art-android/tree/${CIRCLE_BRANCH}\",
          \"title\": \"${SLACK_TEXT_TITLE}\",
          \"title_link\": \"$CIRCLE_WORKFLOW_URL\",
          \"text\": \"${SLACK_DEPLOYMENT_TEXT}\",
          \"actions\": [${CIRCLE_ARTIFACTS_BUTTON}]
      }]
  }" \
  "${SLACK_CHANNEL_HOOK}"
}

main() {
  echo "Declaring environment variables"
  declare_env_variables

  echo "Sending notification"
  send_notification

}

main
