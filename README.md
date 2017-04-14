# LC 后台 Java实现微信&支付宝支付

使用LC后台实现微信&支付宝的APP支付功能(其他端支付功能待添加)

在LC应用->云引擎->设置中添加自定义变量\
微信变量:
变量名:appid //微信支付appid\
变量名:mch_id //微信支付mch_id(商户号)\
变量名:notify_url //微信支付通知URL\
变量名:trade_type //微信支付类型目前仅支持APP 

支付宝变量
变量名:APPID //支付宝支付appid\
变量名:RSA2_PRIVATE //支付宝支付私钥 RSA2\
变量名:RSA_PRIVATE //支付宝支付私钥 RSA

## 本地运行
首先确认本机已经安装 [LeanCloud 命令行工具](https://www.leancloud.cn/docs/leanengine_cli.html)，然后执行下列指令：

```
$ git clone https://github.com/leancloud/java-war-getting-started.git
$ cd java-war-getting-started
```

安装依赖：

```
mvn package
```

登录账户：
```
lean login
```

关联项目：
```
lean checkout
```
根据列表提示，输入数字，依次按回车确认，以关联到你的应用。


启动项目：

```
lean up
```

应用即可启动运行：[localhost:3000](http://localhost:3000)

## 部署到 LeanEngine

部署到预备环境（若无预备环境则直接部署到生产环境）：
```
lean deploy
```

将预备环境的代码发布到生产环境：
```
lean publish
```

## 相关文档

* [云引擎服务总览](https://leancloud.cn/docs/leanengine_overview.html)
* [网站托管开发指南](https://leancloud.cn/docs/leanengine_webhosting_guide-java.html)
* [云函数开发指南](https://leancloud.cn/docs/leanengine_cloudfunction_guide-java.html)
* [数据存储开发指南](https://leancloud.cn/docs/leanstorage_guide-java.html)
* [命令行工具使用详解](https://leancloud.cn/docs/leanengine_cli.html)
