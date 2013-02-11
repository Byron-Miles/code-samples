#!/bin/sh

# Ensure there are two command line parameters
if [ $# -ne 2 ]
then
	echo "Usage: $0 filename date(yyyy-mm-dd)"
	exit
fi

#Save the two command line parameters
FILE=$1
DATE=$2

# Check that the file exists
if [ ! -f $FILE ] 
then
	echo "File Not Found: $FILE"
	exit 
fi

# Get the modification date of the file
set `stat -c %y $FILE`
MOD=$1

#Check the modification date against the parameter date
if [ $MOD = $DATE ]
then  #Print Ok!
	echo "Ok!"
else  #Write the number of files in pwd to file 'numfiles'
	ls -l | grep -e ^- | wc -l > numfiles
fi
