# spring boot web 脚手架
本项目主要为快速启动一个springboot开发框架，可用于快速启动一个项目，不用每次重复配置日志、ORM、异常等配置。
## 项目结构
```
|--fast-web  
    |-- src  
        |-- main 代码和配置文件  
            |-- java 代码  
                |-- com.lv.fast  
                    |-- common 公共对象包
                        |-- annotation 自定义注解包  
                        |-- constant 常量对象包  
                        |-- entity 实体包  
                        |-- enums 枚举包
                        |-- log 业务日志包  
                        |-- util 工具类包  
                        |-- valid 校验相关对象包  
                    |-- config 配置对象包  
                    |-- exception 异常包  
                    |-- model 业务模块
                        |-- test 测试模块/提供参考
            |-- resource 配置文件  
                |-- mapper mybatis/mybatis-plus dao xml文件  
                |-- static 静态资源文件  
                |-- application.yml Springboot上下文配置文件  
                |-- application-dev.yml Springboot环境文件  
                |-- log4j2-spring.xml 日志配置文件  
        |-- test 单元测试  
    |-- .gitignore  
    |-- pom.xml  
    |-- Readme.md
```
## 功能列表
- 基础框架：SpringBoot，[官网](https://spring.io/projects/spring-boot)
- 日志：集成log4j2日志框架,并持久化到日志文件，详情查看src/main/resources/log4j2-spring.xml
  - 保存目录：logs/
  - 持久化机制：保留15日内的日志，每日一次归档，单文件最大1024M，超过单文件最大大小触发归档
- ORM：集成Mybatis-Plus，[官网](https://mp.baomidou.com/)
  - 分页：配置为Mysql分页方式，详情查看com.lv.fast.config.MybatisPlusConfig
  - mapperScan：配置为com.*.*.dao，详情查看com.lv.fast.config.MybatisPlusConfig
  - 日志：dev环境，配置打印SQL日志，详情查看src/main/resources/application-dev.yml
  - 包别名：配置为com.**.DO，详情查看src/main/resources/application.yml
  - 主键策略：配置为使用数据库自增，详情查看src/main/resources/application.yml
  - 其他配置：开启驼峰命名映射，关闭缓存，允许value为null，关闭Mybatis-plus banner打印，详情查看src/main/resources/application.yml
- 数据库：集成Mysql，具体配置参考src/main/resources/application-dev.yml
  - 驱动：mysql-connector-java（Springboot管理依赖）
  - 连接池： hikari
- 接口管理：集成knife4j，[官网](https://doc.xiaominfo.com/)
  - 权限：开启访问认证
  - 访问路径：http://{ip}:{port}/{context-path}/doc.html
- 参数校验：集成validation
- 应用管理监控：集成actuator，配置开放health端点，basePath为info，详情查看src/main/resources/application.yml
## 技术桟
  - 基础框架：SpringBoot
  - ORM：Mybatis-plus
  - 日志：Sl4j Log4j2
  - 数据库：Mysql
  - 应用监控：Actuator
  - 接口管理：Knife4j
  - 工具包：Guava Lombok Hutool
## 自定义扩展功能
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
  - 记录业务日志
    - 使用注解com.lv.fast.common.log.LogRecord，参考使用：com.lv.fast.model.test.service.TestService.convert
    - 实现获取操作者接口com.lv.fast.common.log.OperatorService，参考：com.lv.fast.model.test.service.TestOperatorServiceImpl.getOperator
    - 实现日志存储接口com.lv.fast.common.log.LogRecordService，默认实现：com.lv.fast.common.log.LogRecordConfig.logRecordService
    - 设置线程上下文变量（暂时不支持使用线程池）com.lv.fast.common.log.LogRecordContext，
      注意如果需要在同一个线程设置多个环境变量需要使用com.lv.fast.common.log.LogRecordContext#put(Map<String,Object>)方法
## 业务功能参考