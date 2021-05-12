#!/bin/bash

function upload_codecov_report {
  # upload_codecov_report <dir> <flag>
  cd "$1" || return 126
  wget --no-check-certificate https://codecov.io/bash -O codecov.sh
  pwd
  bash codecov.sh -cF "$2"
}

function run_go {
  cd util/golang/ || return 126
  export GO111MODULE=on
  go mod tidy
  go test -race -coverprofile=coverage.txt -covermode=atomic ./service/... || return 126
  cd ../../
  upload_codecov_report golang go
}

function run_python {
  #env
  export PYTHONPATH=$PYTHONPATH:`pwd`/python
  echo $PYTHONPATH 
  # install
  cd util/python || return 126
  pip install coverage
  pip install alibabacloud-tea

  coverage run --source="./alibabacloud_tea_util" -m pytest tests/test_* || return 126
  cd ../../
  upload_codecov_report python python
}

function contains {
  local value=$2
  for i in $1
  do
    if [ "$i" == "$value" ]; then
      echo "y"
      return 0
    fi
  done
  echo "n"
  return 1
}

lang=$1

if [ "$lang" == "go" ]
then
  echo "run golang"
  run_go
elif [ "$lang" == "python" ] 
then
  echo "run python"
  run_python
fi

exit $?