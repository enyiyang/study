# study-best-practice

阿里云性能测试PTS的文档
https://help.aliyun.com/document_detail/29338.html#section-9q1-mug-j3t

https://zhuanlan.zhihu.com/p/491927737

https://baijiahao.baidu.com/s?id=1740860079075651792&wfr=spider&for=pc

## 启动命令

设置JVM 参数 

```

cd /Users/bage/bage/github/study/study-best-practice

jdk8 :
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation  -XX:+PrintHeapAtGC -XX:NumberOfGCLogFiles=100 -XX:GCLogFileSize=10M -Xloggc:my-gc-%t.gc.log -jar -Xms64m -Xmx256m target/study-best-practice-0.0.1-SNAPSHOT.jar

jdk17 :
java -jar -Xlog:gc:my-gc.log:time,level -Xms64m -Xmx256m target/study-best-practice-0.0.1-SNAPSHOT.jar


jdk17 :
java -jar -Xlog:gc:my-gc-0813.log:time,level target/study-best-practice-0.0.1-SNAPSHOT.jar

Remote :
java -jar -Xlog:gc:my-gc-0813.log:time,level target/study-best-practice-0.0.1-SNAPSHOT.jar
 --spring.config.location=file:///Users/bage/bage/config/application-remore-db.properties



```





## MySQL

查看负载 

```
show global status
```

查看查询 

```
show processlist;
```

配置查看 

```
 连接数
 SHOW GLOBAL VARIABLES WHERE Variable_name='max_connections';
 SHOW STATUS WHERE Variable_name like 'Threads_connect%';
 
 缓冲区 
 SHOW STATUS WHERE Variable_name like '%buffer%';
 
 
 Bin Log 
 SHOW STATUS WHERE Variable_name like '%binlog%';
 SHOW Variables like 'log_bin';


请问下，这个如何才能有权限呀？

```

调整

```
SET GLOBAL max_connections=10;

SET GLOBAL max_connections=500;

SET GLOBAL max_connections=500;

SET GLOBAL max_connections=500;

```

## WRK
wrk 压测
https://formulae.brew.sh/formula/wrk
https://github.com/wg/wrk

安装
```
brew install wrk
```

请求
This runs a benchmark for 30 seconds, using 12 threads, and keeping 400 HTTP connections open.
```
wrk -t10 -c50 -d90s http://localhost:8000/user/insert

wrk -t10 -c400 -d60s http://localhost:8000/user/insert

wrk -t10 -c100 -d60s http://localhost:8000/user/insert

wrk -t10 -c100 -d60s -T3s --latency http://localhost:8000/log/insert

wrk -t10 -c100 -d60s -T3s --latency http://localhost:8000/user/insert

wrk -t10 -c100 -d5s -T3s --latency http://localhost:8000/goods/buy

```

Output:
```
Running 30s test @ http://127.0.0.1:8080/index.html
12 threads and 400 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency   635.91us    0.89ms  12.92ms   93.69%
Req/Sec    56.20k     8.07k   62.00k    86.54%
22464657 requests in 30.00s, 17.76GB read
Requests/sec: 748868.53
Transfer/sec:    606.33MB
```

请求 实践
```
bage@bagedeMacBook-Pro ~ % wrk -t12 -c200 -d30s http://localhost:8000/user/insert

Running 30s test @ http://localhost:8000/user/insert
12 threads and 200 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency   827.00ms  256.50ms   1.99s    86.84%
Req/Sec    21.02     13.00    99.00     79.95%
6705 requests in 30.10s, 1.18MB read
Socket errors: connect 0, read 89, write 0, timeout 100
Requests/sec:    222.74
Transfer/sec:     40.25KB



bage@bagedeMacBook-Pro ~ % wrk -t12 -c400 -d30s http://localhost:8000/user/insert

Running 30s test @ http://localhost:8000/user/insert
12 threads and 400 connections
Thread Stats   Avg      Stdev     Max   +/- Stdev
Latency     1.45s   349.10ms   2.00s    72.86%
Req/Sec    22.62     13.43   100.00     59.04%
7287 requests in 30.07s, 1.29MB read
Socket errors: connect 0, read 1486, write 0, timeout 788
Requests/sec:    242.33
Transfer/sec:     43.79KB


bage@bagedeMacBook-Pro ~ % wrk -t100 -c400 -d30s http://localhost:8000/user/insert

Running 30s test @ http://localhost:8000/user/insert
  100 threads and 400 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.33s   396.91ms   2.00s    66.59%
    Req/Sec     2.44      3.56    29.00     86.15%
  2306 requests in 30.10s, 416.61KB read
  Socket errors: connect 0, read 933, write 0, timeout 1387
Requests/sec:     76.61
Transfer/sec:     13.84KB
```


## Tomcat 

线程配置 
https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.server

https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.server.server.tomcat.threads.max


Mac 启动 https://cwiki.apache.org/confluence/display/TOMCAT/Building+Tomcat+on+MacOS

```

cd /Users/bage/bage/software/apache-tomcat-10.1.10/

chmod 755 ./bin/*.sh

./bin/startup.sh
```



## Spring
https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties

## Jmeter
吞吐量上不去？

## Druid 

基本配置和使用 

https://github.com/alibaba/druid

https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter



查看监控 

http://localhost:8000/druid/index.html



http://localhost:8000/druid/sql.html



## Redis

Docker 安装启动

https://github.com/bage2014/study/blob/master/study-docker/README.md

```
docker run --network bage-net -p 6379:6379 --name bage-redis -d redis --requirepass "bage"
```

Spring 集成 Redis

https://spring.io/guides/gs/messaging-redis/





## metrics

### 基本使用
启动 prometheus 挂在 网络bage-net 下  

```
docker run --network bage-net -d --name bage-prometheus -p 9090:9090 -v /Users/bage/bage/docker-conf/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
```

启动 kabana

```
docker run --network bage-net -d --name=bage-grafana -p 3000:3000 grafana/grafana

```

本地指标
http://localhost:8000/actuator/prometheus

Promethoes
http://localhost:9090

查看指标
http://localhost:9090/targets

Grafana
http://localhost:3000/

admin/admin 登陆 

配置数据源 Data Sources
http://bage-prometheus:9090

画图
sum(rate(bage_user_request_count_total[1m]))

sum_over_time(bage_user_request_count_total[1m])





### JVM 模板 

https://grafana.com/grafana/dashboards/4701-jvm-micrometer/



导入

dashboard -> import ;

拷贝上面 https://grafana.com/grafana/dashboards/4701-jvm-micrometer/

链接对应的 ID 或者 json



## Docker 
CPU 使用情况 

```
docker stats --no-stream

docker stats

```

## JVM 
打开自带的 Jconsole

### 堆内存

选择对应的JAVA 进程 

    java -jar -Xms64m -Xmx256m target/study-best-practice-0.0.1-SNAPSHOT.jar




### 线程栈大小？？

配置 减少，用不到1M ？



## deploy 

参考链接 https://www.baeldung.com/deployable-fat-jar-spring-boot
```
打包 
mvn clean package 

cd /Users/bage/bage/github/study/study-best-practice


启动 
-Xms【memory size】 初试堆大小 
-Xmx 【memory max】 最大堆大小
java -jar  -Xms64m -Xmx256m target/study-best-practice-0.0.1-SNAPSHOT.jar

java -jar target/study-best-practice-0.0.1-SNAPSHOT.jar

java -jar target/study-best-practice-0.0.1-SNAPSHOT.jar --spring.config.location=file:///Users/bage/bage/config/application-remore-db.properties

```



拷贝到远程

```
scp -r ./target/study-best-practice-0.0.1-SNAPSHOT.jar bage@124.221.97.97:/home/bage

java -jar study-best-practice-0.0.1-SNAPSHOT.jar


```



## 生成 dump

https://www.baeldung.com/java-heap-dump-capture



选择对应的JAVA 进程 

```
jps

jmap -dump:file=javaDump.hprof,format=b {pid}

example：
jmap -dump:file=bestp.hprof,format=b 41337

bage@bagedeMacBook-Pro ~ % jps
95028 Jps
90631 study-best-practice-0.0.1-SNAPSHOT.jar
90806 ApacheJMeter.jar

bage@bagedeMacBook-Pro ~ % jmap -dump:file=0707.hprof,format=b 90631   
Dumping heap to /Users/bage/0707.hprof ...
Heap dump file created [120639811 bytes in 0.287 secs]


```



## Arthas 

A里爸爸工具使用 
热点图 线上服务的 CPU 火焰图。



## SafePoint 

本地请求地点 

```
http://localhost:8000/gc/safe/point/start

```



## 瓶颈分析

https://www.jianshu.com/p/62cf2690e6eb

**业务指标**：如吞吐量(QPS、TPS)、响应时间(RT)、并发数、业务成功率等

**资源指标**：如CPU、内存、Disk I/O、Network I/O等资源的消耗情况



- 我终于知道高工是如快速分析线上程序性能瓶颈了（CPU篇）

https://zhuanlan.zhihu.com/p/613430726

- 性能测试：CPU/内存/IO性能瓶颈分析常用工具

https://www.jianshu.com/p/e1deb8b6984d

- 性能测试中服务器关键性能指标浅析

https://www.jianshu.com/p/62cf2690e6eb

- 初探调优1：系统压测，瓶颈定位及调优方案

https://blog.51cto.com/u_14006572/3153832

- 性能之巅：定位和优化程序 CPU、内存、IO 瓶颈

https://my.oschina.net/u/4526289/blog/4783751

- 性能测试中如何定位性能瓶颈？

https://www.zhihu.com/question/29269160

- 性能测试如何定位分析性能瓶颈？ 

https://blog.51cto.com/u_12087147/6225614

- 压力测试瓶颈分析

https://zhuanlan.zhihu.com/p/486542009



### 分析过程

#### 硬件环境

应用程序运行的机器环境，CPU、内存、磁盘IO

**CPU**

- 使用率 

**内存**

**Disk I/O**

**Network I/O**

#### 应用程序

通过增加资源也就是扩容可以提高系统总体的TPS，但是这种方法，往往会掩盖掉程序本身存在的设计和编码缺陷，还是需要从代码上进行分析，例如缓存设计和命中率，数据库连接设计，数据结构和算法，资源合理释放。

DB



#### 中间件环境

例如高并发下数据库mysql性能调优，通常有建索引，慢SQL分析，SQL语句优化，Buffer Pool调整等措施；Tomcat的JVM内存启动参数调优等。





## 问题排查 

- G1GC慢的排查过程

https://zhuanlan.zhihu.com/p/165285835

- 有钱人的烦恼——服务器CPU和JVM GC卡顿问题排查和解决

https://www.jianshu.com/p/890d44df0ba4

- jvm Linux服务器出现异常和卡顿排查思路和步骤

http://www.kuazhi.com/post/442548.html

- 一次YoungGC过慢排查

https://www.jianshu.com/p/4afabaa2b390

- 系统运行缓慢，CPU 100%，以及Full GC次数过多问题的排查思路

https://maimai.cn/article/detail?fid=1746642867&efid=IScZAP8KVukoQ0ZANO9G7A

- 系统运行缓慢，CPU 100%，以及 Full GC 次数过多问题的排查思路

https://my.oschina.net/zhangxufeng/blog/3017521

- 一次线上遇到磁盘 IO 瓶颈的问题处理

https://my.oschina.net/u/4417528/blog/3943828



## GC日志

日志输出配置参数

https://www.oracle.com/java/technologies/javase/vmoptions-jsp.html

JDK doc文档 

https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.htm

日志查看解析

https://dzone.com/articles/understanding-g1-gc-log-format

https://blog.tier1app.com/2016/04/06/gc-logging-user-sys-real-which-time-to-use/

基本解析

https://zhuanlan.zhihu.com/p/267388951

各种类型 

https://blog.csdn.net/yunxing323/article/details/108304534

### 日志信息

- GC类型

这里会告诉我们产生的是**YGC** （YGC 代表新生代的GC只会收集Eden区和Survivor ）、 还是**Full GC**（Full GC是针对于整个堆进行搜集）

- GC产生的原因

这里一般都会告诉我们是因为什么原因导致产生GC，一般通过这里可以分析出具体是因为哪个区域空间不够了导致的GC。

- 回收的情况

这里主要体现出回收的成果，通过数据告诉我们 回收之前的区域对象占用空间大小、回收之后区域对象占用空间的大小 、当前区域的空间大小、回收使用的时长。



https://zhuanlan.zhihu.com/p/267388951

![](https://pic1.zhimg.com/v2-5f7d61a15fa505a84fb3459a23988210_r.jpg)



个人汇总文章

https://learn.lianglianglee.com/%e4%b8%93%e6%a0%8f/JVM%20%e6%a0%b8%e5%bf%83%e6%8a%80%e6%9c%af%2032%20%e8%ae%b2%ef%bc%88%e5%ae%8c%ef%bc%89/17%20GC%20%e6%97%a5%e5%bf%97%e8%a7%a3%e8%af%bb%e4%b8%8e%e5%88%86%e6%9e%90%ef%bc%88%e5%9f%ba%e7%a1%80%e9%85%8d%e7%bd%ae%ef%bc%89.md



https://www.cnblogs.com/felixzh/p/11526306.html

```java
Real is wall clock time – time from start to finish of the call. This is all elapsed time including time slices used by other processes and time the process spends blocked (for example if it is waiting for I/O to complete).
User is the amount of CPU time spent in user-mode code (outside the kernel) within the process. This is only actual CPU time used in executing the process. Other processes and time the process spends blocked do not count towards this figure.

Sys is the amount of CPU time spent in the kernel within the process. This means executing CPU time spent in system calls within the kernel, as opposed to library code, which is still running in user-space. Like ‘user’, this is only CPU time used by the process.

User+Sys will tell you how much actual CPU time your process used. Note that this is across all CPUs, so if the process has multiple threads it could potentially exceed the wall clock time reported by Real.
  
```

配置GC日志写出 

```
-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation  -XX:+PrintHeapAtGC -XX:NumberOfGCLogFiles=100 -XX:GCLogFileSize=10M -Xloggc:/opt/ard-user-gc-%t.gc.log

```

解释说明 

```text
 -Xloggc:/opt/app/ard-user/ard-user-gc-%t.log   设置日志目录和日志名称
 -XX:+UseGCLogFileRotation           开启滚动生成日志
 -XX:NumberOfGCLogFiles=5            滚动GC日志文件数，默认0，不滚动
 -XX:GCLogFileSize=20M               GC文件滚动大小，需开启UseGCLogFileRotation
 -XX:+PrintGCDetails                 开启记录GC日志详细信息（包括GC类型、各个操作使用的时间）,并且在程序运行结束打印出JVM的内存占用情况
 -XX:+ PrintGCDateStamps             记录系统的GC时间           
 -XX:+PrintGCCause                   产生GC的原因(默认开启)
```





GC 分析工具 

https://dgrt.cn/a/2439307.html?action=onClick



## MAT 使用

官方网址 https://help.eclipse.org/latest/index.jsp?topic=/org.eclipse.mat.ui.help/welcome.html

https://zhuanlan.zhihu.com/p/585668729

http://androooid.github.io/public/lightsky/mat_usage/mat_usage.html



## 分析过程 

实践参考 https://help.aliyun.com/document_detail/91580.html?spm=a2c4g.29342.0.0

排查过程 https://www.zhihu.com/question/29269160/answer/2649417643

线上问题排查  https://developer.aliyun.com/article/778128


