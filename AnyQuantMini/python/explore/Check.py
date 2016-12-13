#coding=utf-8
'''
Created on 2016年03月08日
@author: 1
'''
#!/bin/env python
import time
import sys

argCount = len(sys.argv)
print('before sleep')
time.sleep(5);
print('after sleep')
for str in sys.argv:
    print(str) 