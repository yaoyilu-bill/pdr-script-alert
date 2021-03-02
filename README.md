Pandora2.0告警操作使用“执行脚本”的方式实现三方平台通知

### Pandora2.0中“脚本参数”配置

脚本参数key=interface
value为以下json串

{
  "ip": "100.100.69.143",
  "port": "8081",
  "method": "get",
  "path": "event/getData",
  "pdrParams": [
    {
      "content": "eventDetails"
    }
  ],
  "otherParams": [
    {
      "source": "Test"
    },
    {
      "group": "aaa"
    }
  ]
}

参数说明：ip,port,method,path,pdrParams,otherParams 为固定配置，其对应值根据需求填写相应的值。
method : get/post	(大小写均可)
pdrParams下的参数：key为自己接口中的key, pdrParams的value值为Pandora2.0告警产生的对象属性。
otherParams 下的参数：	自定义参数

例：http://100.100.69.143:8081/event/getData?content=%3E+1.+%E6%9D%A1%E4%BB%B61%2C+%E7%BA%A7%E5%88%AB0%3A+%E5%AD%97%E6%AE%B5%E3%80%90count%E3%80%91%E5%BC%82%E5%B8%B8%2C+%E5%BD%93%E5%89%8D%E5%80%BC%E3%80%9075.0%E3%80%91%E5%A4%A7%E4%BA%8E%E7%AD%89%E4%BA%8E+10.0%E3%80%82%0A&source=Test&group=aaa

### Pandora2.0 告警产生EventInfo
  	 eventDisplayId	 	事件序列id,用以展示用的
     eventName			事件名称
     eventDescription 	事件描述
     alertService		告警服务， 废弃
     alertLevel			告警级别
     eventTime			事件产生时间
     alertSourceType		告警来源
     alertName			告警名称
     alertDescription	告警描述
     alertInterval		告警频率
     alertConditionCnt	告警条件数
     alertExecuteCount	告警执行次数
     eventDetails		事件详情
     eventId				事件id，唯一标识
     operationType		操作类型
     operateNote	 		操作备注
     phoenixHost			pandora服务host
     userName			告警创建者的用户名
     realUserName		真实用户名
     params				web页面配置的自定义参数
     additionContent		附加内容

### 整体配置
操作方式：执行脚本
脚本名称：/xxx/pdr-script-alert.jar
脚本参数：
	key = interface 
	value = json串


### 打包方式
export可执行jar


