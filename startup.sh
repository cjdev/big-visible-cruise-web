#!/bin/bash

python speedyCC.py  &
java -jar bvc.jar --port=4481 $@
