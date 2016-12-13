'''
# Created on 
# id:stock 
# start:start time
# end:end time
# exchange:exchange place
# @author: 1
'''
import urllib2
import json
# str='http://121.41.106.89:8010/api/stock/sh600000/?start=2014-10-10&end=2015-10-10&fields=open+high+low+close+adj_price+volume+turnover'
    # str='http://121.41.106.89:8010/api/stock/sh600000/?start=2014-10-10&end=2015-10-10&fields=pe+pb'# error
    # str='http://121.41.106.89:8010/api/benchmark/all'
#     str='http://121.41.106.89:8010/api/benchmark/hs300?start=2015-01-01&end=2015-01-30&fields=open+high+low+close+adj_price+volume+turnover'
def print_lol(collection):
    for i in collection:
        if(isinstance(i, list)):
            print_lol(i)
        else:
            print(i)
    return
def getDataById(id, start, end, exchange):
    url = 'http://121.41.106.89:8010/api/stock/'  # the url to visit
    head = {'X-Auth-Code': '44895fc229fefbd07cc009a787d554c5'}  # my opencode , you can replace it with yours
    st = '/?start='
    en = '&end='
    fd = '&fields=open+high+low+close+adj_price+volume+turnover'
    checkurl = url + exchange + id + st + start + en + end + fd
    print(checkurl)
    try:
        request = urllib2.Request(checkurl, headers=head)  # remember to add the head
        print(type(request))
        response = urllib2.urlopen(request)  # open the url
        print(type(response))
        data = [i for i in response]
        info=data[0].decode("GB2312");
        jsonData=json.loads(info,encoding="GB2312")
        print(type(jsonData))
#         print(type(data))
#         print_lol(data)
        print(data[0])
        temp=jsonData['data']
        print(jsonData['data'])
        print(type(temp))
        print(temp['trading_info'])
        stockList=temp['trading_info']
        print_lol(stockList)
#         dict={"123":"23","ag":"543"}
#         print(dict)
    except urllib2.URLError as e:
        print(e.code)
        print(e.reason)
    return
getDataById('600000', '2015-01-01', '2015-01-30', 'sh')
