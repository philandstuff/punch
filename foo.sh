#!/bin/bash

PWD=`pwd`

mkdir catalogs
lein run > catalogs/vm.json


