# living

## ORCA 使用的组件有

- Pulsar 消息中间件
- MySQL 关系型数据库
- MongoDB 非关系型数据库
- Redis 缓存
- Nacos Dubbo注册中心

## living 项目分层

```markdown

living
- deploy 开发部署
- doc SQL与文档
- living-beans                  对象层
- living-common                 通用层
- living-console          [APP] 主播端与助理端
- living-core-dao               DAO服务层
- living-core-api               核心服务API
- living-core-provider    [APP] 核心服务
- living-dubbo-adapter          Dubbo适配层
- living-seq-api                序列API
- living-seq-provider     [APP] 序列服务
- living-sharing          [APP] 观看端
- living-admin            [APP] 管理端
- living-short            [APP] 短链接
- living-task             [APP] 定时任务
- living-web-adapter            WebApi适配层

```

## 为什么禁止使用 BeanUtils

- 显示进行 属性赋值，不做隐性复制

## 关于网关 不考虑使用 SCG 和 ZUUL 等这一套

- KONG
- APISIX

## 为什么不使用 MyBatisPlus

- 现在裸表并没有建立索引, 未知后续查询的可控，需要统计后续构建合理的 索引。
- 需要关注明细 SQL、更好优化处理。不屏蔽 隐式SQL

## 为什么不使用JPA

- 一个应用里面多个 ORM 带来的维护复杂问题


## 规范类
### Bean层
```markdown
- bo      [业务对象]
- entity  [bean]
- ro      [请求对象]
- vo      [响应对象]
- event   [事件 OR 消息]
- convert [各个之间DTO层的转换]
```

### 禁用类
```markdown
- 禁止用 JPA 
- 禁止用 Mybatis Plus
- 禁止用 BeanUtils.copy...
- 禁止用 Date
- 禁止生成  Mybatis XML, 哪怕生成请保证  XML 裸干净【是纯粹用到的 SQL】
- 禁止 Vo 出现在  XML  内
- ...
```
### 明确类
```markdown
- 明确用 LocalDateTime 替代 Date
- 明确用 Builder 链式替代 setXXX
- 明确用 Convert 进行多层DTO转换 
- 明确 Dubbo API 层使用 Service 结尾
- 明确 Dubbo API  实现使用  Provider 结尾
- 明确 Dubbo 应用层内 实现逻辑层使用 Repo 结尾[不要啊使用 Service 结尾]
- 明确 List 之间的转化优先使用 CommonConvert 统一处理
- 明确 代码内校验类使用  OrcaAssert 处理【抛出异常体系】
- 至于 Hibernate validator 校验(@NotNull等这类的) 后续将统一处理 
- 推荐返回 Optional<T> 不推荐返回 null  
- ...
```

### API 规范类
```markdown
- 明确 优先 Restful 风格
- 禁止 API 上出现驼峰、无法明确表达请使用 多路径('/') 或者 ('-')
- 禁止 API 路径上出现 list、insert、save、update、del(delete) 等等
- 确实需要使用 请求体 (DELETE 方法)无法满足、改用 POST, 路径出现 remove
- 功能解构
- ...
```

[//]: # (docker system prune)

[//]: # (docker system prune -a)

[//]: # (docker image prune)