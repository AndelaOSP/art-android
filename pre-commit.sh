#!/bin/sh
#
# To skip running tests, run the --no-verify argument.
#       i.e   git commit --no-verify
#
## Run unit tests with gradle wrapper.
./gradlew test --daemon

# Store the last exit code in a variable.
RESULT=$?

# Perform checks
if [ $RESULT -ne 0 ]
then
    echo "Tests failed to run, fix them to proceed with the commit"
    exit 1
fi

# You can commit
exit 0
