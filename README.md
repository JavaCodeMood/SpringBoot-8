# :cn: `Spring Boot 多modules工程实战开发` :cn: <br>

### 1 : * `SpringBoot` 多模块工程配置清单 <br>
  * `dao` 项目为基类，主要是配置`数据库连接池、mybatis xml、log、redis、MQ`等相关。<br>
  * `dao` 作用是让`web项目、MQ项目`只编写业务层代码。<br>
  * `model` 项目编写`model`和`dto、vo`等对象。<br>
  * `web`和`MQ`：`web`提供外部接口调用，`MQ`启动后属于消费状态。<br>
  * `producer`后续写在`web`工程下，`web`下产生队列，`mq`端收到则消费。<br>
  
### 2 : * 不足之处还请帮忙指出： :cn: `yugenhai108@gmail.com` :cn:
