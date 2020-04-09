# 乐优商城项目

##具体描述

---

##前端：HTML Css JavaScript thymeleaf Vue

---

##后端：springboot mybatis-plus springcloud maven git fastdfs

---

##中间件：Redis rabbitMq elasticsearch Nginx

---

##数据库： mysql

---
项目简介：此项目是仿乐优商城项目，且前后端分离分三部分，前端页面，后台管理页面，后端服务器，用户通过浏览器发送请求，被本地HOST配置的域名映射解析成具体IP地址，然后Nginx监听80端口，反向代理到具体的微服务，且gateway网关微服务作为微服务的入口，对不同的请求进行转发过滤。本项目分用户模块，订单模块，商品模块，搜索模块，短信模块，文件上传模块等模块，实现了用户无状态登录，注册，商品的增删改查，商品搜索，商品下单，商品也持久化，添加购物车，发送
短信，微信支付等功能。
---
关键技术： 1. 系统前后端分离，浏览器发送请求，通过Hosts文件解析，被Nginx配置的反向代理通信   2.搭建 gateway 网关中心对不同的URL进行转发到具体的微服务模块  3.利用Feign 来实现各个组件之间的调用，Hystrix进行项目的容错，Ribbon来实现负载均衡  4.在高并发上搭建 搜索微服务，把商品数据上传到ElasticSearch 上，实现读写分离 ，并用RabbitMq 保证数据一致性  5.对商品页做持久化，加快访问速度，把生成的静态页面存储到Nginx里面 5.利用JwtToken实现无状态登录，利用RSA对用户信息加密。 6.利用Redis存储用户注册短信，并设置失效时间

---
##前端商品服务
leyou-portal

部署方式 :npm install 下载依赖的jar包
启动： npm start
