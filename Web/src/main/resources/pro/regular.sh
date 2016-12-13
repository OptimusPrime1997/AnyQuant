#!/bin/bash

for i in *.txt
do
    sed -i '1,5d' $i
done

echo "process end"
