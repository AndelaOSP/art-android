#!/bin/bash

set -ex
set -o pipefail

declare_env_variables() {

  # Declaring environment variables
  #
  # Some environment variables assigned externally are:
  # CIRCLE_TOKEN : This is the API token that is provided for the CircleCI user. Used for accessing artifacts
  # SLACK_CHANNEL_HOOK : This is the webhook for the Slack App where notifications will be sent from
  # DEPLOYMENT_CHANNEL : This is the channel on which the Slack notifications will be posted
  # QEMU_AUDIO_DRV : This will set the Android emulator used for integration tests to have no audio


  # Retrieving the urls for the CircleCI artifacts

  CIRCLE_ARTIFACTS_URL="$(curl https://circleci.com/api/v1.1/project/github/${CIRCLE_PROJECT_USERNAME}/${CIRCLE_PROJECT_REPONAME}/${CIRCLE_BUILD_NUM}/artifacts?circle-token=${CIRCLE_TOKEN} | grep -o 'https://[^"]*')"

  # Assigning slack messages based on the CircleCI job name

  if [ "$CIRCLE_JOB" == 'android_lint' ]; then
    MESSAGE_TEXT="Android Lint Phase Failed! :crying_cat_face:"

    # Sorting through the artifact urls to get only the android lint report

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo {\"type\": \"button\", \"text\": \"Android Lint Report\", \"url\": \"${CIRCLE_REPORT_ARTIFACTS}\"})"

  elif [ "$CIRCLE_JOB" == 'findbugs_lint' ]; then
    MESSAGE_TEXT="Findbugs Lint Phase Failed! :crying_cat_face:"

    # Sorting through the artifact urls to get only the findbugs lint report

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'findbugs\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo {\"type\": \"button\", \"text\": \"Findbugs Lint Report\", \"url\": \"${CIRCLE_REPORT_ARTIFACTS}\"})"

  elif [ "$CIRCLE_JOB" == 'pmd_lint' ]; then
    MESSAGE_TEXT="PMD Lint Phase Failed! :crying_cat_face:"

    # Sorting through the artifact urls to get only the PMD lint report

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo {\"type\": \"button\", \"text\": \"PMD Lint Report\", \"url\": \"${CIRCLE_REPORT_ARTIFACTS}\"})"

  elif [ "$CIRCLE_JOB" == 'checkstyle_lint' ]; then
    MESSAGE_TEXT="Checkstyle Lint Phase Failed! :crying_cat_face:"

    # Sorting through the artifact urls to get only the checkstyle lint report

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo {\"type\": \"button\", \"text\": \"Checkstyle Lint Report\", \"url\": \"${CIRCLE_REPORT_ARTIFACTS}\"})"

  elif [ "$CIRCLE_JOB" == 'test' ]; then
    MESSAGE_TEXT="Test Phase Failed! :scream:"

    # Sorting through the artifact urls to get only the unit test and integration test reports

    DEBUG_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'testDebugUnitTest\/index\.html')"
    RELEASE_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'testReleaseUnitTest\/index\.html')"
    JACOCO_DEBUG_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'jacocoTestDebugUnitTestReport\/html\/index\.html')"
    JACOCO_RELEASE_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'jacocoTestReleaseUnitTestReport\/html\/index\.html')"
    INTEGRATION_TEST_REPORT="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'AVD')"

    CIRCLE_ARTIFACTS_BUTTON="$(echo \
        "{\"type\": \"button\", \"text\": \"Unit Test Report (Debug)\", \"url\": \"${DEBUG_REPORT}\"}", \
        "{\"type\": \"button\", \"text\": \"Unit Test Report (Release)\", \"url\": \"${RELEASE_REPORT}\"}", \
        "{\"type\": \"button\", \"text\": \"Jacoco Test Report (Debug)\", \"url\": \"${JACOCO_DEBUG_REPORT}\"", \
        "{\"type\": \"button\", \"text\": \"Jacoco Test Report (Release)\", \"url\": \"${JACOCO_RELEASE_REPORT}\"}", \
        "{\"type\": \"button\", \"text\": \"Android Virtual Device (AVD) Test Report\", \"url\": \"${INTEGRATION_TEST_REPORT}\"}" \
    )"

  elif [ "$CIRCLE_JOB" == 'deploy_test_build' ]; then
    MESSAGE_TEXT="Test Build for Deployment Failed! :scream:"

  elif [ "$CIRCLE_JOB" == 'deploy_staging_build' ]; then
    MESSAGE_TEXT="Staging Build for Deployment Failed! :scream:"

  elif [ "$CIRCLE_JOB" == 'deploy_production_build' ]; then
    MESSAGE_TEXT="Production Build for Deployment Failed! :scream:"

  else
    MESSAGE_TEXT="Unknown job failed"
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
          \"color\": \"danger\",
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

main "$@"
