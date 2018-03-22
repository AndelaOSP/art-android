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
    JOB_NAME="Android Lint Phase Passed! :smirk_cat:"

    # Sorting through the artifact urls to get only the android lint reports

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.html')"
    CIRCLE_ARTIFACTS_BUTTON="$(echo {\"type\": \"button\", \"text\": \"Android Lint Report\", \"url\": \"${CIRCLE_REPORT_ARTIFACTS}\"})"

  elif [ "$CIRCLE_JOB" == 'findbugs_lint' ]; then
    JOB_NAME="Findbugs Lint Phase Passed! :smirk_cat:"

    # Sorting through the artifact urls to get only the findbugs lint reports

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep 'findbugs\.html')"
    CIRCLE_ARTIFACTS_MESSAGE="Get the report <${CIRCLE_REPORT_ARTIFACTS}|here>"

  elif [ "$CIRCLE_JOB" == 'pmd_lint' ]; then
    JOB_NAME="PMD Lint Phase Passed! :smirk_cat:"

    # Sorting through the artifact urls to get only the PMD lint reports

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.html')"
    CIRCLE_ARTIFACTS_MESSAGE="Get the report <${CIRCLE_REPORT_ARTIFACTS}|here>"

  elif [ "$CIRCLE_JOB" == 'checkstyle_lint' ]; then
    JOB_NAME="Checkstyle Lint Phase Passed! :smirk_cat:"

    # Sorting through the artifact urls to get only the findbugs lint reports

    CIRCLE_REPORT_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.html')"
    CIRCLE_ARTIFACTS_MESSAGE="Get the report <${CIRCLE_REPORT_ARTIFACTS}|here>"

  elif [ "$CIRCLE_JOB" == 'test' ]; then
    JOB_NAME="Test Phase Passed! :smiley:"

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
    CIRCLE_ARTIFACTS_MESSAGE="Get the test reports here:
    \n Unit Test Reports <${DEBUG_REPORT}|Debug> | <${RELEASE_REPORT}|Release>
    \n Jacoco Unit Test Reports <${JACOCO_DEBUG_REPORT}|Debug> | <${JACOCO_RELEASE_REPORT}|Release>
    \n <${INTEGRATION_TEST_REPORT}|Android Virtual Device (AVD) Test Report>"

  elif [ "$CIRCLE_JOB" == 'deploy_test_build' ]; then
    JOB_NAME="Deploy Test Build Succeeded :rocket:"

    # Sorting through the artifact urls to get only the apk files

    CIRCLE_BUILD_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.apk')"
    CIRCLE_ARTIFACTS_MESSAGE="Download the build files here: \n ${CIRCLE_BUILD_ARTIFACTS}"

  elif [ "$CIRCLE_JOB" == 'deploy_staging_build' ]; then
    JOB_NAME="Deploy Staging Build Succeeded :rocket:"
    CIRCLE_BUILD_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.apk')"
    CIRCLE_ARTIFACTS_MESSAGE="Download the build files here: \n ${CIRCLE_BUILD_ARTIFACTS}"

  elif [ "$CIRCLE_JOB" == 'deploy_production_build' ]; then
    JOB_NAME="Deploy Production Build Succeeded :rocket:"
    CIRCLE_BUILD_ARTIFACTS="$(echo $CIRCLE_ARTIFACTS_URL | sed -E -e 's/[[:blank:]]+/\
/g' |  grep '\.apk')"
    CIRCLE_ARTIFACTS_MESSAGE="Download the build files here: \n ${CIRCLE_BUILD_ARTIFACTS}"

  else
    JOB_NAME="Unknown Task"
    CIRCLE_ARTIFACTS_MESSAGE="No artifacts for unknown job"
  fi

  # Some template for the Slack message

  COMMIT_LINK="https://github.com/${CIRCLE_PROJECT_USERNAME}/${CIRCLE_PROJECT_REPONAME}/commit/${CIRCLE_SHA1}"
  IMG_TAG="$(git rev-parse --short HEAD)"
  CIRCLE_WORKFLOW_URL="https://circleci.com/workflow-run/${CIRCLE_WORKFLOW_ID}"
  SLACK_TEXT_TITLE="CircleCI Build #$CIRCLE_BUILD_NUM"
  SLACK_DEPLOYMENT_TEXT="Executed Git Commit <$COMMIT_LINK|${IMG_TAG}>: ${JOB_NAME} \n ${CIRCLE_ARTIFACTS_MESSAGE}"

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
          \"pretext\": \"Art Android Builds\",
          \"author_name\": \"Branch: $CIRCLE_BRANCH by ${CIRCLE_USERNAME}\",
          \"author_link\": \"https://github.com/AndelaOSP/art-android/tree/${CIRCLE_BRANCH}\",
          \"title\": \"${SLACK_TEXT_TITLE}\",
          \"title_link\": \"$CIRCLE_WORKFLOW_URL\"
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
