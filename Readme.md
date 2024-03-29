# spring boot web 脚手架
本项目主要为快速启动一个springboot web开发框架，避免每次重复配置日志、ORM、异常等配置。
同时也封装一些常用功能，目标是达到开箱即用，做一个能用、好用的Java Web脚手架。
## 项目结构
```
|--fast-web  
    |-- src  
        |-- main 代码和配置文件  
            |-- java 代码  
                |-- com.lv.fast  
                    |-- common 公共包
                        |-- annotation 注解包  
                        |-- aop 切面包  
                        |-- constant 常量包  
                        |-- entity 实体包  
                        |-- enums 枚举包
                        |-- log 业务日志包  
                        |-- util 工具类包  
                        |-- valid 参数校验包  
                    |-- config 配置对象包  
                    |-- exception 异常包  
                    |-- module 业务模块
                        |-- test 测试模块(可删除,提供业务代码编写参考)
                    |-- redis 缓存组件Redis相关功能代码 (如不需要可删除,同时删除pom.xml的redis相关依赖)
            |-- resource 配置文件  
                |-- mapper mybatis/mybatis-plus dao xml文件  
                |-- static 静态资源文件  
                |-- application.yml Springboot上下文配置文件  
                |-- application-dev.yml Springboot环境文件  
                |-- log4j2-spring.xml 日志配置文件  
        |-- test 单元测试  
    |-- .gitignore  
    |-- pom.xml
    |-- LICENSE 授权文件
    |-- TODO.md 功能规划  
    |-- Readme.md
    |-- Dockerfile 构建docker镜像
```
## 技术桟
- JDK：***17+***
- 基础框架：SpringBoot
- ORM：Mybatis-plus
- 日志：Sl4j Log4j2
- 数据库：Mysql
- 缓存：Redis
- 应用监控：Actuator
- 容器：Docker
- 接口管理：Spring doc && knife4j
- 工具包：Guava Lombok Hutool
## 基础功能
- 基础框架：SpringBoot，[官网](https://spring.io/projects/spring-boot)
- 日志：集成log4j2日志框架,并持久化到日志文件，详情查看src/main/resources/log4j2-spring.xml
  - 保存目录：logs/
- ORM：Mybatis-Plus，[官网](https://mp.baomidou.com/)
  - 分页：配置为Mysql分页方式，详情查看com.lv.fast.config.MybatisPlusConfig
  - mapperScan：配置为com.*.*.dao，详情查看com.lv.fast.config.MybatisPlusConfig
  - 日志：dev环境，配置打印SQL日志，详情查看src/main/resources/application-dev.yml
  - 包别名：配置为com.**.DO，详情查看src/main/resources/application.yml
  - 主键策略：配置为使用数据库自增，详情查看src/main/resources/application.yml
  - 其他配置：开启驼峰命名映射，关闭缓存，允许value为null，关闭Mybatis-plus banner打印，详情查看src/main/resources/application.yml
- 数据库：集成Mysql，具体配置参考src/main/resources/application-dev.yml
  - 驱动：mysql-connector-j（Springboot管理依赖）
  - 连接池： hikari
- 接口管理：集成[Spring doc](https://springdoc.org/#properties) 和 [knife4j](https://doc.xiaominfo.com/)
  - 访问路径：http://{ip}:{port}/{context-path}/{knife4j.path}，默认值http://127.0.0.1:8080/web/doc.html
- 安全：
  - xss(mica-xss)过滤，[官网](https://gitee.com/596392912/mica/tree/master/mica-xss) ，配置参考src/main/resources/application.yml
  - sql注入，***目前依赖开发者自行避免,比如使用Mybatis的占位符功能，因为我没有找到比较好的解决方案，如果有好的解决方案请联系我/或提交pr***
  - 跨域使用cors解决方案,配置参考com.lv.fast.config.WebMvcConfig#addCorsMappings
- 参数校验：集成validation
- 应用监控：集成actuator，配置开放health端点，basePath为info，详情查看src/main/resources/application.yml，默认访问路径：http://{ip}:{port}/{context-path}/{basePath}/{endPoint}
## 扩展功能
  - 参数校验
    - 校验参数是否在枚举限制内，注解com.lv.fast.annotation.EnumCheck
    - 校验手机号格式，注解com.lv.fast.annotation.Phone
    - validation新增策略，com.lv.fast.valid.group.AddStrategy
    - validation修改策略，com.lv.fast.valid.group.UpdateStrategy
  - 全局对象
    - 统一包含标识码的接口com.lv.fast.common.Code
    - 统一包描述信息的接口com.lv.fast.common.Describe
    - 请求统一响应对象com.lv.fast.model.RestResult
    - 全局请求响应码常量类com.lv.fast.constant.RestResultCodeConstant
    - 全局请求响应描述常量类com.lv.fast.constant.RestResultDescribeConstant
    - 全局请求响应枚举类com.lv.fast.enums.RestResultEnum
  - 异常
    - 自定义异常com.lv.fast.exception.MyException
    - 全局异常捕获com.lv.fast.exception.GlobalExceptionHandler
  - 配置
    - Mybatis-plus配置com.lv.fast.config.MybatisPlusConfig
    - Swagger配置com.lv.fast.config.SwaggerConfig
    - WebMvc配置com.lv.fast.config.WebMvcConfig
  - 分页
    - 分页基础对象com.lv.fast.DTO.PageQuery
    - 分页工具类com.lv.fast.util.PageUtil
  - 记录请求日志
    - 默认开启记录controller接口请求日志,实现类com.lv.fast.common.aop.RequestLogAop
    - 记录内容：请求参数、执行时间、响应结果、是否执行成功、请求URI路径、客户端ip
    - 持久化：默认使用名为RequestLog的logger输出日志，默认单独输出到logs/request*.log文件中
  - 记录业务日志
    - 实现参考美团技术团队的文章，奈何没有找到源码，所以自己实现了一个简单版本，[原文](https://mp.weixin.qq.com/s/JC51S_bI02npm4CE5NEEow)
    - 使用注解com.lv.fast.common.log.LogRecord，参考使用：TestService.convert
    - 实现获取操作者接口com.lv.fast.common.log.OperatorService，参考：TestOperatorServiceImpl.getOperator
    - 实现日志存储接口com.lv.fast.common.log.LogRecordService，默认实现：com.lv.fast.common.log.LogRecordConfig.logRecordService(输出到info级别的日志文件))
    - 设置线程上下文变量（暂时不支持使用线程池）com.lv.fast.common.log.LogRecordContext，
      ***注意因为使用aop实现记录业务日志，如果嵌套使用需要满足spring aop嵌套使用规则，避免aop失效导致未记录日志，[spring aop官方文档](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop)***
  - Redis自定义注解
    - com.lv.fast.redis.RedisHashCache 使用Redis Hash数据结构进行缓存,使用案例：com.lv.fast.module.test.service.TestService.testRedisCache
    - com.lv.fast.redis.RedisEvict 清除Redis缓存,使用案例：com.lv.fast.module.test.service.TestService.testRedisEvict
    - com.lv.fast.redis.RedisBatchEvict 批量清除Redis缓存,使用案例：com.lv.fast.module.test.service.TestService.testRedisBatchEvict
## 业务编码参考
com.lv.fast.module包是用于写业务代码，规划为以模块为单位划分包，com.lv.fast.module.test为测试模块
提供参考参考，mvc模型开发方式，可以测试记录请求日志和业务日志以及参数校验和swagger以及自定义Redis注解等功能。不需要可以自行删除。