# coding=utf-8
# 这是一个调用api的python程序
# 只是最简单的调用了api说明里的主页
# 其他以此类推吧
# 用到网络的话，python比java好用
# 你们可以尝试一下
#     open: 开盘价
#     high: 最高价
#     low: 最低价
#     close: 收盘价
#     adj_price: 后复权价
#     volume: 成交量
#     turnover: 换手率
#     pe: 市盈率
#     pb: 市净率
'''
Created on 2016��2��28��

@author: run
'''
# packages needed
import json
import urllib2
# api/stocks/?year=2014&exchange=sh
url = 'http://121.41.106.89:8010/'  # the url to visit
head = {'X-Auth-Code': '44895fc229fefbd07cc009a787d554c5'}  # my opencode , you can replace it with yours

# codes to get json data
# handle exceptions
try:
    request = urllib2.Request(url, headers=head)  # remember to add the head
    response = urllib2.urlopen(request)  # open the url

    data = [i for i in response]
    jsonData = json.loads(data[0])  # use the data get to build a json object
    print(data)
    print(jsonData['status'])
    # visit the 'status' attribute of the data get,the information we get
    # is "{"status": "ok", "data": "Welcome AnyQuant API, Visit Doc For Usage"}"
except urllib2.URLError as e:
    print
    e.code
    print
    e.reason
