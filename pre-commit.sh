#!/bin/sh
#
# To skip running tests, run the --no-verify argument.
#       i.e   git commit --no-verify
#
## Run unit tests with gradle wrapper.
./gradlew test --daemon

# Store the last exit code in a variable.
testResult=$?

# Perform checks
if [ $testResult -ne 0 ]
then
    echo "Tests failed to run, fix them to proceed with the commit"
    exit 1
fi

## Run checkstyle to check the code quality.
./gradlew check --daemon

# Store the last exit code in a variable.
checkResult=$?

# Perform checks
if [ $checkResult -ne 0 ]
then
    echo "Code violations were found, fix them to proceed with the commit"
    exit 1
fi

# You can commit
exit 0
