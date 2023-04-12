# Lottery

基于Springboot，Dubbo 等开发的分布式抽奖系统

# 第一部分 领域开发

## 1. 环境 配置 规范



## 2. 搭建(DDD + RPC)架构

DDD（Domain-Driven Design 领域驱动设计）是由Eric Evans最先提出，目的是对软件所涉及到的领域进行建模，以应对系统规模过大时引起的软件复杂性的问题。整个过程大概是这样的，开发团队和领域专家一起通过 通用语言(Ubiquitous Language)去理解和消化领域知识，从领域知识中提取和划分为一个一个的子领域（核心子域，通用子域，支撑子域），并在子领域上建立模型，再重复以上步骤，这样周而复始，构建出一套符合当前领域的模型。

依靠领域驱动设计的设计思想，通过事件风暴建立领域模型，合理划分领域逻辑和物理边界，建立领域对象及服务矩阵和服务架构图，定义符合DDD分层架构思想的代码结构模型，保证业务模型与代码模型的一致性。通过上述设计思想、方法和过程，指导团队按照DDD设计思想完成微服务设计和开发。

- 拒绝泥球小单体、拒绝污染功能与服务、拒绝一加功能排期一个月
- 架构出高可用极易符合互联网高速迭代的应用服务
- 物料化、组装化、可编排的服务，提高人效

<img src="README.assets/image-20230314214310765-0102068.png" alt="image-20230314214310765" style="zoom: 33%;" />

- **应用层{application}**

  - 应用服务位于应用层。用来表述应用和用户行为，负责服务的组合、编排和转发，负责处理业务用例的执行顺序以及结果的拼装。

  - 应用层的服务包括应用服务和领域事件相关服务。

  - 应用服务可对微服务内的领域服务以及微服务外的应用服务进行组合和编排，或者对基础层如文件、缓存等数据直接操作形成应用服务，对外提供粗粒度的服务。

  - 领域事件服务包括两类：领域事件的发布和订阅。通过事件总线和消息队列实现异步数据传输，实现微服务之间的解耦。

- **领域层{domain}**

  - 领域服务位于领域层，为完成领域中跨实体或值对象的操作转换而封装的服务，领域服务以与实体和值对象相同的方式参与实施过程。

  - 领域服务对同一个实体的一个或多个方法进行组合和封装，或对多个不同实体的操作进行组合或编排，对外暴露成领域服务。领域服务封装了核心的业务逻辑。实体自身的行为在实体类内部实现，向上封装成领域服务暴露。

  - 为隐藏领域层的业务逻辑实现，所有领域方法和服务等均须通过领域服务对外暴露。

  - 为实现微服务内聚合之间的解耦，原则上禁止跨聚合的领域服务调用和跨聚合的数据相互关联。

- **基础层{infrastructure}**

  - 基础服务位于基础层。为各层提供资源服务（如数据库、缓存等），实现各层的解耦，降低外部资源变化对业务逻辑的影响。

  - 基础服务主要为仓储服务，通过依赖反转的方式为各层提供基础资源服务，领域服务和应用服务调用仓储服务接口，利用仓储实现持久化数据对象或直接访问基础资源。

- **接口层{interfaces}**
  - 接口服务位于用户接口层，用于处理用户发送的Restful请求和解析用户输入的配置文件等，并将信息传递给应用层。

**DDD是在MVC的基础上可以更加明确了房间的布局**

DDD结构它是一种充血模型结构，所有的服务实现都以领域为核心，应用层定义接口，领域层实现接口，领域层定义数据仓储，基础层实现数据仓储中关于DAO和Redis的操作，但同时几方又有互相的依赖。那么这样的结构再开发独立领域提供 http 接口时候，并不会有什么问题体现出来。但如果这个时候需要引入 RPC 框架，就会暴露问题了，因为使用 RPC 框架的时候，需要对外提供描述接口信息的 Jar 让外部调用方引入才可以通过反射调用到具体的方法提供者，那么这个时候，RPC 需要暴露出来，而 DDD 的系统结构又比较耦合，怎么进行模块化的分离就成了问题点。所以我们本章节在模块系统结构搭建的时候，也是以解决此项问题为核心进行处理的。

**DDD + RPC，模块分离系统搭建**

<img src="README.assets/image-20230314214553484-0102068.png" alt="image-20230314214553484" style="zoom:50%;" />

如果按照模块化拆分，那么会需要做一些处理，包括：

1. 应用层，不再给领域层定义接口，而是自行处理对领域层接口的包装。否则领域层既引入了应用层的Jar，应用层也引入了领域层的Jar，就会出现循环依赖的问题。
2. 基础层中的数据仓储的定义也需要从领域层剥离，否则也会出现循环依赖的问题。
3. RPC 层定义接口描述，包括：入参Req、出参Res、DTO对象，接口信息，这些内容定义出来的Jar给接口层使用，也给外部调用方使用。

<img src="README.assets/image-20230314214617625-0102068.png" alt="image-20230314214617625" style="zoom:50%;" />



## 3. 跑通广播模式RPC过程调用

### 一、创建抽奖活动表

抽奖活动的设计和开发过程中，涉及到的表信息包括：活动表、奖品表、策略表、规则表、用户参与表、中奖信息表等。

首先创建一个活动表，用于实现系统对数据库的CRUD操作，也就可以被RPC接口调用，在后续再进行优化。

**活动表(activity)**

```sql
CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '活动名称',
  `activity_desc` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '活动描述',
  `begin_date_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date_time` datetime DEFAULT NULL COMMENT '结束时间',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存',
  `take_count` int(11) DEFAULT NULL COMMENT '每人可参与次数',
  `state` tinyint(2) DEFAULT NULL COMMENT '活动状态：1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启',
  `creator` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_activity_id` (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='活动配置';

```

活动表：是一个用于配置抽奖活动的总表，用于存放活动信息，包括：ID、名称、描述、时间、库存、参与次数等。

### 二、POM 文件配置

按照现有工程的结构模块分层，包括：

- lottery-application，应用层，引用：`domain`
- lottery-common，通用包，引用：`无`
- lottery-domain，领域层，引用：`infrastructure`
- lottery-infrastructure，基础层，引用：`无`
- lottery-interfaces，接口层，引用：`application`、`rpc`
- lottery-rpc，RPC接口定义层，引用：`common`

**lottery-rpc配置**

```xml
<parent>
    <artifactId>Lottery</artifactId>
    <groupId>com.banana69.lottery</groupId>
    <version>1.0-SNAPSHOT</version>
</parent>
<modelVersion>4.0.0</modelVersion>
<artifactId>lottery-rpc</artifactId>

<packaging>jar</packaging>

<dependencies>
    <dependency>
        <groupId>cn.itedus.lottery</groupId>
        <artifactId>lottery-common</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>

<build>
    <finalName>lottery-rpc</finalName>
    <plugins>
        <!-- 编译plugin -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>${jdk.version}</source>
                <target>${jdk.version}</target>
                <compilerVersion>1.8</compilerVersion>
            </configuration>
        </plugin>
    </plugins>
</build>
```

**lottery-interfaces配置**

```xml
<artifactId>lottery-interfaces</artifactId>

<packaging>war</packaging>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    ...
</dependencies>

<build>
    <finalName>Lottery</finalName>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
            <includes>
                <include>**/**</include>
            </includes>
        </resource>
    </resources>
    <testResources>
        <testResource>
            <directory>src/test/resources</directory>
            <filtering>true</filtering>
            <includes>
                <include>**/**</include>
            </includes>
        </testResource>
    </testResources>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>8</source>
                <target>8</target>
            </configuration>
        </plugin>
    </plugins>
</build>

```

lottery-interfaces 是整个程序的出口，也是用于构建 War 包的工程模块，所以你会看到一个 `<packaging>war</packaging>` 的配置。

在 dependencies 会包含所有需要用到的 SpringBoot 配置，也会包括对其他各个模块的引入。

在 build 构建配置上还会看到一些关于测试包的处理，比如这里包括了资源的引入也可以包括构建时候跳过测试包的配置。

**配置广播模式Dubbo**

最早 RPC 的设计和使用都是依赖于注册中心，那就是需要把服务接口信息在程序启动的时候，推送到一个统一的注册中心，在其他需要调用 RPC 接口的服务上再通过注册中心的均衡算法来匹配可以连接的接口落到本地保存和更新。那么这样的标准的使用方式可以提供更大的连接数和更强的负载均衡作用，但目前我们这个以学习实践为目的的工程开发则需要尽可能减少学习成本，也就需要在开发阶段少一些引入一些额外的配置，那么目前使用广播模式就非常适合，以后也可以直接把 Dubbo 配置成注册中心模式。

```yml
# Dubbo 广播方式配置
dubbo:
  application:
    name: Lottery
    version: 1.0.0
  registry:
    address: N/A #multicast://224.5.6.7:1234
  protocol:
    name: dubbo
    port: 20880
  scan:
    base-packages: cn.itedus.lottery.rpc

```

广播模式的配置唯一区别在于注册地址，`registry.address = multicast://224.5.6.7:1234`，服务提供者和服务调用者都需要配置相同的📢广播地址。或者配置为 N/A 用于直连模式使用

application，配置应用名称和版本

protocol，配置的通信协议和端口

scan，相当于 Spring 中自动扫描包的地址，可以把此包下的所有 rpc 接口都注册到服务中

**搭建测试工程调用 RPC**

为了测试 RPC 接口的调用以及后续其他逻辑的验证，这里需要创建一个测试工程：Lottery-Test这个工程中用于引入 RPC 接口的配置和同样广播模式的调用。

![image-20230329162141922](README.assets/image-20230329162141922-0102068.png)

使用zookeeper作为注册中心，该项目中zookeeper使用docker搭建，版本为3.4.13

搭建dubbo-admin，在github下载，选择0.4.0版本

在 dubbo-admin中执行命令

```
mvn clean package -Dmaven.test.skip=true
```

执行完命令后切换到目录

```
dubbo-admin-develop/dubbo-admin-distribution/target>
```

执行：

```
java -jar ./dubbo-admin-0.4.0.jar
```

启动后端：

dubbo-admin-ui 目录下执行命令

```
npm run dev
```

在跑通RPC的过程中会遇到一些bug，dubbo无法注册，这里使用zookeeper作为注册中心，给消费者引入pom

```xml
<dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.7.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>2.7.1</version>
        </dependency>
        <!-- 引入zookeeper -->
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>2.12.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-recipes</artifactId>
            <version>2.12.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.14</version>
            <!--排除这个slf4j-log4j12-->
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

配置文件：

```yml
server:
  port: 8083

spring:
  datasource:
    username: root
    password: root
    url: jdbc:mysql://127.0.0.1:3306/lottery?useSSL=false&serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis-plus:
  mapper-locations: classpath:/mybatis/mapper/*.xml
#  config-location:  classpath:/mybatis/config/mybatis-config.xml

dubbo:
  application:
    name: Lottery
    version: 1.0.0
    parameters:
      unicast: false
  registry:
    address: zookeeper://127.0.0.1:2181
    timeout: 30000
    protocol: zookeeper
  protocol:
    name: dubbo
    port: 20881
  scan:
    base-packages: com.banana69.lottery.rpc
```

消费者配置文件：

```yml
server:
  port: 8081

# Dubbo 广播方式配置
dubbo:
  application:
    name: Lottery-Test
    version: 1.0.0
  registry:
    #注册中心地址
    address: zookeeper://127。0。0。1:2181
    timeout: 30000
  protocol:
    name: dubbo
    port: 20880

```

对接口进行测试：

```java
@SpringBootTest
@RunWith(SpringRunner.class)
class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Reference(interfaceClass = IActivityBooth.class,url="dubbo://127.0.0.1:20881")
    private IActivityBooth activityBooth;



    @Test
    public void test_rpc() {
        ActivityReq req = new ActivityReq();
        req.setActivityId(100002L);
        ActivityRes result = activityBooth.queryActivityById(req);
        logger.info("测试结果：{}", JSON.toJSONString(result));
    }


}
```

<img src="README.assets/image-20230329223614532-0102068.png" alt="image-20230329223614532" style="zoom:50%;" />



## 4. 抽奖活动策略表设计

### 4.1 需要建的表

一个满足业务需求的抽奖系统，需要提供抽奖活动配置、奖品概率配置、奖品梳理配置等内容，同时用户在抽奖后需要记录用户的抽奖数据，这就是一个抽奖活动系统的基本诉求。（后续可能会加上区块链的业务，与智能合约进行交互）

该项目提供的表包括

<img src="README.assets/image-20230329225815245.png" alt="image-20230329225815245" style="zoom: 33%;" />

- 活动配置，activity：提供活动的基本配置
- 策略配置，strategy：用于配置抽奖策略，概率、玩法、库存、奖品
- 策略明细，strategy_detail：抽奖策略的具体明细配置
- 奖品配置，award：用于配置具体可以得到的奖品
- 用户参与活动记录表，user_take_activity：每个用户参与活动都会记录下他的参与信息，时间、次数
- 用户活动参与次数表，user_take_activity_count：用于记录当前参与了多少次
- 用户策略计算结果表，user_strategy_export_001~004：最终策略结果的一个记录，也就是奖品中奖信息的内容



### 4.2 建立表结构

1⃣️：**lottery**

```sql
create database lottery;

-- auto-generated definition
create table activity
(
    id            bigint auto_increment comment '自增ID',
    activityId    bigint       null comment '活动ID',
    activityName  varchar(64)  not null comment '活动名称',
    activityDesc  varchar(128) null comment '活动描述',
    beginDateTime datetime     not null comment '开始时间',
    endDateTime   datetime     not null comment '结束时间',
    stockCount    int          not null comment '库存',
    takeCount     int          null comment '每人可参与次数',
    state         int          null comment '活动状态：编辑、提审、撤审、通过、运行、拒绝、关闭、开启',
    creator       varchar(64)  not null comment '创建人',
    createTime    datetime     not null comment '创建时间',
    updateTime    datetime     not null comment '修改时间',
    constraint activity_id_uindex
        unique (id)
)
    comment '活动配置';

alter table activity
    add primary key (id);

-- auto-generated definition
create table award
(
    id           bigint(11) auto_increment comment '自增ID'
        primary key,
    awardId      bigint                             null comment '奖品ID',
    awardType    int(4)                             null comment '奖品类型（文字描述、兑换码、优惠券、实物奖品暂无）',
    awardCount   int                                null comment '奖品数量',
    awardName    varchar(64)                        null comment '奖品名称',
    awardContent varchar(128)                       null comment '奖品内容「文字描述、Key、码」',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null comment 'updateTime'
)
    comment '奖品配置';

-- auto-generated definition
create table strategy
(
    id           bigint(11) auto_increment comment '自增ID'
        primary key,
    strategyId   bigint(11)   not null comment '策略ID',
    strategyDesc varchar(128) null comment '策略描述',
    strategyMode int(4)       null comment '策略方式「1:单项概率、2:总体概率」',
    grantType    int(4)       null comment '发放奖品方式「1:即时、2:定时[含活动结束]、3:人工」',
    grantDate    datetime     null comment '发放奖品时间',
    extInfo      varchar(128) null comment '扩展信息',
    createTime   datetime     null comment '创建时间',
    updateTime   datetime     null comment '修改时间',
    constraint strategy_strategyId_uindex
        unique (strategyId)
)
    comment '策略配置';

-- auto-generated definition
create table strategy_detail
(
    id         bigint(11) auto_increment comment '自增ID'
        primary key,
    strategyId bigint(11)    not null comment '策略ID',
    awardId    bigint(11)    null comment '奖品ID',
    awardCount int           null comment '奖品数量',
    awardRate  decimal(5, 2) null comment '中奖概率',
    createTime datetime      null comment '创建时间',
    updateTime datetime      null comment '修改时间'
)
    comment '策略明细';

```



2⃣️： **lottery_01.sql ~ lottery_02.sql**

```sql
create database lottery_01;

-- auto-generated definition
create table user_take_activity
(
    id           bigint    null,
    uId          tinytext  null,
    takeId       bigint    null,
    activityId   bigint    null,
    activityName tinytext  null,
    takeDate     timestamp null,
    takeCount    int       null,
    uuid         tinytext  null,
    createTime   timestamp null,
    updateTime   timestamp null
)
    comment '用户参与活动记录表';

-- auto-generated definition
create table user_take_activity_count
(
    id         bigint    null,
    uId        tinytext  null,
    activityId bigint    null,
    totalCount int       null,
    leftCount  int       null,
    createTime timestamp null,
    updateTime timestamp null
)
    comment '用户活动参与次数表';

-- auto-generated definition
create table user_strategy_export_001(id           bigint     null,uId          mediumtext null,activityId   bigint     null,orderId      bigint     null,strategyId   bigint     null,strategyType int        null,grantType    int        null,grantDate    timestamp  null,grantState   int        null,awardId      bigint     null,awardType    int        null,awardName    mediumtext null,awardContent mediumtext null,uuid         mediumtext null,createTime   timestamp  null,updateTime   timestamp  null) comment '用户策略计算结果表';
create table user_strategy_export_002(id           bigint     null,uId          mediumtext null,activityId   bigint     null,orderId      bigint     null,strategyId   bigint     null,strategyType int        null,grantType    int        null,grantDate    timestamp  null,grantState   int        null,awardId      bigint     null,awardType    int        null,awardName    mediumtext null,awardContent mediumtext null,uuid         mediumtext null,createTime   timestamp  null,updateTime   timestamp  null) comment '用户策略计算结果表';
create table user_strategy_export_003(id           bigint     null,uId          mediumtext null,activityId   bigint     null,orderId      bigint     null,strategyId   bigint     null,strategyType int        null,grantType    int        null,grantDate    timestamp  null,grantState   int        null,awardId      bigint     null,awardType    int        null,awardName    mediumtext null,awardContent mediumtext null,uuid         mediumtext null,createTime   timestamp  null,updateTime   timestamp  null) comment '用户策略计算结果表';
create table user_strategy_export_004(id           bigint     null,uId          mediumtext null,activityId   bigint     null,orderId      bigint     null,strategyId   bigint     null,strategyType int        null,grantType    int        null,grantDate    timestamp  null,grantState   int        null,awardId      bigint     null,awardType    int        null,awardName    mediumtext null,awardContent mediumtext null,uuid         mediumtext null,createTime   timestamp  null,updateTime   timestamp  null) comment '用户策略计算结果表';

```

<img src="README.assets/image-20230330125214107.png" alt=" " style="zoom:50%;" />



通常分库分表的几个常见方面； 1. 访问频率：对于高频访问的数据，可以将其存储在单独的数据库或表中，以提高读写性能。 2. 数据大小：对于大量的数据，可以将其拆分到多个表中，以减少单表的数据量，降低存储开销。 3. 数据类型：对于不同类型的数据，可以将其拆分到不同的数据库或表中，便于管理和查询。 4. 数据范围：对于不同范围的数据，可以将其拆分到不同的数据库或表中，便于数据的管理和查询。 分库分表的主要目的在于；数据分摊、提高QPS/TPS、分摊压力、提高可扩展性。比如；比如数据库的读写性能下降，或者单表数据量过大，这时候您就需要考虑进行分库分表操作了。通过拆分数据库，可以将单个数据库的压力分摊到多个数据库上，从而避免单个数据库的性能瓶颈，提高系统的性能和可扩展性。此外，分库分表还可以解决数据库存储容量的限制，提高数据库的存储能力。 另外在分库分表之后，数据的一致性会受到影响，数据库的管理和维护成本也会增加。因此，在考虑分库分表时，需要仔细权衡利弊，确定是否真的需要进行分库分表操作。也就是你的开发成本问题。因为有分库分表就会相应的引入 canal binlog同步、es、mq、xxl-job等分布式技术栈。

接下来我们会围绕这些库表一点点实现各个领域的功能，包括：抽奖策略领域、奖品发放领域、活动信息领域等



## 5. 抽奖策略领域模块开发

MVC与DDD的区别：

<img src="README.assets/image-20230330131252779.png" alt="image-20230330131252779" style="zoom:50%;" />

DDD优点：service包装各种领域的实现，Ax的对象只服务于Ax的仓储，没有其他业务领域使用这个对象，service也只专注于当前服务的实现。

**描述：在domain抽奖领域模块实现两种抽奖策略算法，包括：单项概率抽奖和整体概率抽奖，并提供统一的调用方式**

### 5.1 需求引出设计

**需求：**在一场营销抽奖活动玩法中，运营人员通常会配置不同形式的抽奖玩法。例如在转盘中配置12个奖品，每个奖品配置不同的中奖概率，当1个奖品被抽空了以后，那么再抽奖时，是剩余的奖品总概率均匀分配在11个奖品上，还是保持剩余11个奖品的中奖概率，如果抽到为空的奖品则表示未中奖。其实这两种方式在实际的运营过程中都会有所选取，主要是为了配合不同的玩法。

**设计**：在进行抽奖领域模块设计时，需要考虑到库表中要有对应的字段来区分当前运营选择的是什么样的抽奖策略。那么在开发实现上也会用到对应的`策略模式`的使用，两种抽奖算法可以算是不同的抽奖策略，最终提供统一的接口包装满足不同的抽奖功能调用。

<img src="README.assets/image-20230330130613130.png" alt="image-20230330130613130" style="zoom:50%;" />

- 库表设计上需要策略配置和策略明细，他们的关系是`1 v n`
- 另外为了让抽奖策略成为可以独立配置和使用的领域模块，在策略表用不引入活动ID信息的配置。因为在建设领域模块的时候，我们需要把让这部分的领域实现具有可独立运行的特性，不让它被业务逻辑污染，它只是一种无业务逻辑的通用共性的功能领域模块，在业务组合的过程中可以使用此功能领域提供的标准接口。
- 通过这样的设计实现，就可以满足于不同业务场景的灵活调用，例如：有些业务场景是需要你直接来进行抽奖反馈中奖信息发送给用户，但还有一些因为用户下单支付才满足抽奖条件的场景对应的奖品是需要延时到账的，避免用户在下单后又进行退单，这样造成了刷单的风险。`所以有时候你的设计是与业务场景息息相关的`

### 5.2 领域功能结构

抽奖系统工程采用DDD架构 + Module模块方式搭建，lottery-domain 是专门用于开发领域服务的模块，不限于目前的抽奖策略在此模块下实现还有以后需要实现的活动领域、规则引擎、用户服务等都需要在这个模块实现对应的领域功能。

![5-02](README.assets/5-02.png)

strategy 是第1个在 domain 下实现的抽奖策略领域，在领域功能开发的服务下主要含有model、repository、service三块区域，接下来分别介绍下在抽奖领域中这三块区域都做了哪些事情。

- model，用于提供vo、req、res 和 aggregates 聚合对象。
- repository，提供仓储服务，其实也就是对Mysql、Redis等数据的统一包装。
- service，是具体的业务领域逻辑实现层，在这个包下定义了algorithm抽奖算法实现和具体的抽奖策略包装 draw 层，对外提供抽奖接口 IDrawExec#doDrawExec

### 5.3 抽奖算法实现

两种抽奖算法描述，场景A20%、B30%、C50%

- **总体概率**：如果A奖品抽空后，B和C奖品的概率按照 `3:5` 均分，相当于B奖品中奖概率由 `0.3` 升为 `0.375`
- **单项概率**：如果A奖品抽空后，B和C保持目前中奖概率，用户抽奖扔有20%中为A，因A库存抽空则结果展示为未中奖。*为了运营成本，通常这种情况的使用的比较多*

#### 5.3.1 定义接口

`com.banana69.lottery.domain.strategy.service.algorithm.IDrawAlgorithm`

```java
public interface IDrawAlgorithm {

    /**
     * SecureRandom 生成随机数，索引到对应的奖品信息返回结果
     *
     * @param strategyId 策略ID
     * @param excludeAwardIds 排除掉已经不能作为抽奖的奖品ID，留给风控和空库存使用
     * @return 中奖结果
     */
    String randomDraw(Long strategyId, List<String> excludeAwardIds);
}
```

无论任何一种抽奖算法的使用，都以这个接口作为标准的抽奖接口进行抽奖。`strategyId` 是抽奖策略、`excludeAwardIds` 排除掉已经不能作为抽奖的奖品ID，留给风控和空库存使用

#### 5.3.2 总体概率算法

**算法描述**：分别把A、B、C对应的概率值转换成阶梯范围值，A=(0~0.2」、B=(0.2-0.5」、C=(0.5-1.0」，当使用随机数方法生成一个随机数后，与阶梯范围值进行循环比对找到对应的区域，匹配到中奖结果。

<img src="README.assets/image-20230330155401349.png" alt="image-20230330155401349" style="zoom: 33%;" />

如果使用常见的抽奖算法，设置搜索概率，给不同的奖品设置不同的概率，给它们对应的不同范围值，但在高并发的场景下，如果大家都抽到了同一个区间，性能曲线波动会比较频繁，时间范围就比较大。

**这里使用斐波那契算法**

![image-20230331225100475](README.assets/image-20230331225100475.png)

给不同的奖品设置一个概率码，不同的码对应不同的奖品，使用128个长度来存放这些不同奖品对应的数值，通过斐波那契算法将概率只转换为对应的索引值，在一些并发场景的下的抽奖效率会更高一些。

**为什么不使用HashMap：**可能存在hash碰撞，当产生碰撞后产生一个数据链，当超过8个时会转换成红黑树，斐波那契散列痛通过黄金分割点进行增量计算，散列效果更好，在最小空间下实现不碰撞。hashmap的负载因子决定哈希桶的大小，占用的空间也会更大。



```java
public class DefaultRateRandomDrawAlgorithm extends BaseAlgorithm {

    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {

        BigDecimal differenceDenominator = BigDecimal.ZERO;

        // 排除掉不在抽奖范围的奖品ID集合
        List<AwardRateInfo> differenceAwardRateList = new ArrayList<>();
        List<AwardRateInfo> awardRateIntervalValList = awardRateInfoMap.get(strategyId);
        for (AwardRateInfo awardRateInfo : awardRateIntervalValList) {
            String awardId = awardRateInfo.getAwardId();
            if (excludeAwardIds.contains(awardId)) {
                continue;
            }
            differenceAwardRateList.add(awardRateInfo);
            differenceDenominator = differenceDenominator.add(awardRateInfo.getAwardRate());
        }

        // 前置判断
        if (differenceAwardRateList.size() == 0) return "";
        if (differenceAwardRateList.size() == 1) return differenceAwardRateList.get(0).getAwardId();

        // 获取随机概率值
        SecureRandom secureRandom = new SecureRandom();
        int randomVal = secureRandom.nextInt(100) + 1;

        // 循环获取奖品
        String awardId = "";
        int cursorVal = 0;
        for (AwardRateInfo awardRateInfo : differenceAwardRateList) {
            int rateVal = awardRateInfo.getAwardRate().divide(differenceDenominator, 2, BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
            if (randomVal <= (cursorVal + rateVal)) {
                awardId = awardRateInfo.getAwardId();
                break;
            }
            cursorVal += rateVal;
        }

        // 返回中奖结果
        return awardId;
    }

}

```

- 首先要从总的中奖列表中排除掉那些被排除掉的奖品，这些奖品会涉及到概率的值重新计算。
- 如果排除后剩下的奖品列表小于等于1，则可以直接返回对应信息
- 接下来就使用随机数工具生产一个100内的随值与奖品列表中的值进行循环比对，算法时间复杂度O(n)



####  单项概率算法

**算法描述**：单项概率算法不涉及奖品概率重新计算的问题，那么也就是说我们分配好的概率结果是可以固定下来的。好，这里就有一个可以优化的算法，不需要在轮训匹配O(n)时间复杂度来处理中奖信息，而是可以根据概率值存放到HashMap或者自定义散列数组进行存放结果，这样就可以根据概率值直接定义中奖结果，时间复杂度由O(n)降低到O(1)。这样的设计在一般电商大促并发较高的情况下，达到优化接口响应时间的目的。

```java
@Component("singleRateRandomDrawAlgorithm")
public class singleRateRandomDrawAlgorithm extends BaseAlgorithm {
    @Override
    public String randomDraw(Long strategyId, List<String> excludeAwardIds) {
        // 获取策略对应的元组
        String[] rateTuple = super.rateTupleMap.get(strategyId);
        assert rateTuple != null;

        // 随机索引
        int randomVal = new SecureRandom().nextInt(100) + 1;
        int idx = super.hashIdx(randomVal);

        // 返回结果
        String awardId = rateTuple[idx];
        if(excludeAwardIds.contains(awardId)){
            return "未中奖";
        }

        return awardId;
    }
}
```

#### 测试结果

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class DrawAlgorithmTest {

    @Resource(name = "defaultRateRandomDrawAlgorithm")
    private IDrawAlgorithm randomDrawAlgorithm;

    @Resource(name = "singleRateRandomDrawAlgorithm")
    private IDrawAlgorithm singleRateRandomDrawAlgorithm;



    @Before
    public void init() {
        // 奖品信息
        List<AwardRateInfo> strategyList = new ArrayList<>();
        strategyList.add(new AwardRateInfo("一等奖：IMac", new BigDecimal("0.05")));
        strategyList.add(new AwardRateInfo("二等奖：iphone", new BigDecimal("0.15")));
        strategyList.add(new AwardRateInfo("三等奖：ipad", new BigDecimal("0.20")));
        strategyList.add(new AwardRateInfo("四等奖：AirPods", new BigDecimal("0.25")));
        strategyList.add(new AwardRateInfo("五等奖：充电宝", new BigDecimal("0.35")));

        // 初始数据
        randomDrawAlgorithm.initRateTuple(100001L, strategyList);

        singleRateRandomDrawAlgorithm.initRateTuple(100002L, strategyList);


    }

    @Test
    public void test_randomDrawAlgorithm(){
        List<String> excludes = new ArrayList<String>();
        excludes.add("二等奖: iPhone");
        excludes.add("四等奖: Airpods");

        for(int i = 0; i < 20; i++){
            System.out.println("中奖结果：" +
                    randomDrawAlgorithm.randomDraw(100001L, excludes));
        }
    }

    @Test
    public void test_singleRateRandomDrawAlgorithm(){
        List<String> excludes = new ArrayList<String>();
        excludes.add("二等奖: iPhone");
        excludes.add("四等奖: Airpods");
        excludes.add("一等奖：IMac");
        excludes.add("三等奖：ipad");
        excludes.add("五等奖：充电宝");

        for(int i = 0; i < 20; i++){
            System.out.println("中奖结果：" +
                    singleRateRandomDrawAlgorithm.randomDraw(100002L, excludes));
        }
    }

}
```

![image-20230402003708838](README.assets/image-20230402003708838.png)





## 6.模板模式处理抽奖流程

本节内容：基于模板设计模式，规范化抽奖执行流程。包括：提取抽象类、编排模板流程、定义抽象方法、执行抽奖策略、扣减中奖库存、包装返回结果等，并基于P3C标准完善本次开发涉及到的代码规范化处理。

<img src="README.assets/image-20230403134734996.png" alt="image-20230403134734996" style="zoom:50%;" />

将接口的职责，功能分离，不需要把所有的内容都放到抽象类中

### 6.1 开发日志

使用IDEA P3C插件`Alibaba Java Coding Guidelines`，统一标准化编码方式。

调整表`lottery.strategy_detail`，添加`awardSurplusCount`字段，用于记录扣减奖品库存使用数量。

**【重点】**使用`模板方法设计模式`优化类 `DrawExecImpl` 抽奖过程方法实现，主要以抽象类 `AbstractDrawBase` 编排定义流程，定义抽象方法由类 `DrawExecImpl` 做具体实现的方式进行处理。关于模板模式可以参考下：[重学 Java 设计模式：实战模版模式「模拟爬虫各类电商商品，生成营销推广海报场景」](https://mp.weixin.qq.com/s/3u1gCJBYLna8qwV9dUgpmA)



### 6.2 模版模式应用

本节目标在于把抽奖流程标准化，需要考虑的一条思路包括：

- 根据入参策略ID获取抽奖策略配置
- 校验和处理抽奖策略的数据初始化到内存
- 获取那些被排除掉的抽奖列表，这些奖品可能是已经奖品库存为空，或者因为风控策略不能给这个用户薅羊毛的奖品
- 执行抽奖算法
- 包装中奖结果

#### 6.2.1 工程结构

<img src="README.assets/image-20230403141443656.png" alt="image-20230403141443656" style="zoom:50%;" />

- 主要在于对领域模块`lottery-domain.strategy`中`draw`下的类处理
- **DrawConfig**：配置抽奖策略，SingleRateRandomDrawAlgorithm，EntireTyRateRandomDrawAlgorithm
- **DrawStrategySupport**：提供抽奖策略数据支持，便于查询策略配置、奖品信息。通过这样的方式隔离职责。
- **AbstractDrawBase**：抽象类定义模板方法流程，在抽象类的 `doDrawExec` 方法中，处理整个抽奖流程，并提供在流程中需要使用到的抽象方法，由 `DrawExecImpl` 服务逻辑中做具体实现。



#### 6.2.2 定义抽象抽奖过程，模版模式

```java
public abstract class AbstractDrawBase extends DrawStrategySupport implements IDrawExec {

    private Logger logger  = LoggerFactory.getLogger(AbstractDrawBase.class);

    @Override
    public DrawResult doDrawExec(DrawReq req) {
        // 1. 获取抽奖策略
        StrategyRich strategyRich = super.queryStrategyRich(req.getStrategyId());
        Strategy strategy = strategyRich.getStrategy();

        // 2. 校验抽奖策略是否已经初始化
        this.checkAndInitRateData(req.getStrategyId(), strategy.getStrategyMode(),strategyRich.getStrategyDetailList());

        // 3. 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等
        List<String> excludeAwardIds = this.queryExcludeAwardIds(req.getStrategyId());

        // 4. 执行抽奖算法
        String awardId = this.drawAlgorithm(req.getStrategyId(), drawAlgorithmGroup.get(strategy.getStrategyMode()), excludeAwardIds);

        // 5. 包装中奖结果
        return buildDrawResult(req.getUId(), req.getStrategyId(), awardId);
    }

    /**
     * 获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等，这类数据是含有业务逻辑的，所以需要由具体地实现方决定
     * @param strategyId 策略ID
     * @return 排除的奖品ID集合
     */
    protected abstract List<String> queryExcludeAwardIds(Long strategyId);

    /**
     * 执行抽奖算法
     * @param strategyId 策略ID
     * @param drawAlgorithm 抽奖算法模型
     * @param excludeAwardIds 排除的抽奖ID集合
     * @return 中奖奖品ID
     */
    protected abstract String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds);

    /**
     * 校验抽奖策略是否已经初始化到内存
     * @param strategyId 抽奖策略ID
     * @param strategyMode 筹集策略模式
     * @param strategyDetailList 抽奖策略详情
     */
    protected  void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList){

        // 非单项概率， 不必存入缓存
        if(!Constants.StrategyMode.SINGLE.getCode().equals(strategyMode)){
            return;
        }
        IDrawAlgorithm drawAlgorithm = drawAlgorithmGroup.get(strategyMode);
        // 已经初始化过的数据，不需要重复初始化
        if(drawAlgorithm.isExistRateTuple(strategyId)){
            return;
        }
        // 解析并初始化中奖概率数据的散列表
        List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for(StrategyDetail strategyDetail : strategyDetailList){
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }
        drawAlgorithm.initRateTuple(strategyId,awardRateInfoList);
    }

    /**
     * 包装抽奖结果
     * @param uId 用户ID
     * @param strategyId 策略ID
     * @param awardId 奖品ID，null 情况：并发抽奖情况下，库存临界值1 -> 0，会有用户中奖结果为 null
     * @return
     */
    private DrawResult buildDrawResult(String uId, Long strategyId, String awardId) {
        if(awardId== null){
            logger.info("执行策略抽奖完成【未中奖】，用户：{} 策略ID：{}", uId, strategyId);
            return new DrawResult(uId, strategyId, Constants.DrawState.FAIL.getCode());
        }
        Award award = super.queryAwardInfoByAwardId(awardId);
        DrawAwardInfo drawAwardInfo = new DrawAwardInfo(award.getAwardId(), award.getAwardName());
        logger.info("执行策略抽奖完成【已中奖】，用户：{} 策略ID：{} 奖品ID：{} 奖品名称：{}", uId, strategyId, awardId, award.getAwardName());
        return new DrawResult(uId, strategyId, Constants.DrawState.SUCCESS.getCode(), drawAwardInfo);
    }

}
```

- 抽象类 AbstractDrawBase 继承了 DrawStrategySupport 类包装的配置和数据查询功能，并实现接口 IDrawExec#doDrawExec 方法，对抽奖进行编排操作。
- 在 doDrawExec 方法中，主要定义了5个步骤：`获取抽奖策略`、`校验抽奖策略是否已经初始化到内存`、`获取不在抽奖范围内的列表，包括：奖品库存为空、风控策略、临时调整等`、`执行抽奖算法`、`包装中奖结果`，和2个抽象方法 `queryExcludeAwardIds`、`drawAlgorithm`。

#### 6.2.3 抽奖过程方法实现

```java
@Service("drawExec")
public class DrawExecImpl extends AbstractDrawBase {

    private Logger logger = LoggerFactory.getLogger(DrawExecImpl.class);

    @Resource
    private IStrategyRepository strategyRepository;


    @Override
    protected List<String> queryExcludeAwardIds(Long strategyId) {
        List<String> awardList = strategyRepository.queryNoStockStrategyAwardList(strategyId);
        logger.info("执行抽奖策略 strategyId：{}，无库存排除奖品列表ID集合 awardList：{}", strategyId, JSON.toJSONString(awardList));
        return awardList;

    }

    @Override
    protected String drawAlgorithm(Long strategyId, IDrawAlgorithm drawAlgorithm, List<String> excludeAwardIds) {
        // 执行抽奖
        String awardId = drawAlgorithm.randomDraw(strategyId, excludeAwardIds);

        // 判断抽奖结果
        if(null == awardId){
            return null;
        }

        // TODO: Redis
        /*
         * 扣减库存，暂时采用数据库行级锁的方式进行扣减库存，后续优化为 Redis 分布式锁扣减 decr/incr
         * 注意：通常数据库直接锁行记录的方式并不能支撑较大体量的并发，但此种方式需要了解，
         * 因为在分库分表下的正常数据流量下的个人数据记录中，是可以使用行级锁的，因为他只影响到自己的记录，不会影响到其他人
         */
        boolean isSuccess = strategyRepository.deductStock(strategyId, awardId);

        // 返回结果，库存扣减成功返回奖品ID，否则返回NULL 「在实际的业务场景中，如果中奖奖品库存为空，则会发送兜底奖品，比如各类券」
        return isSuccess ? awardId : null;

    }
}
```

- 抽象方法的具体实现类**DrawExecImpl**，分别实现了`queryExcludeAwardIds、drawAlgorithm`两个抽象方法，这两个方法可能随着实现方有不同的方式变化，不适合定义成通用的方法。
- queryExcludeAwardIds：排除奖品ID，可以包含无库存奖品，也可能是业务逻辑限定的风控策略排除奖品等，所以交给业务实现类做具体处理。
- drawAlgorithm：是算法抽奖的具体调用处理，因为这里还需要对策略库存进行处理，所以需要单独包装。*注意代码注释，扣减库存*

### 6.3 测试验证

#### 6.3.1 数据准备

![image-20230403202825441](README.assets/image-20230403202825441.png)

将ID：4 剩余库存设置为 0，在抽奖过程中 ID 4的奖品属于被排除的奖品，不会再抽到该奖品

#### 6.3.2 单元测试

```java
@Test
public void test_drawExec() {
    drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));
    drawExec.doDrawExec(new DrawReq("小佳佳", 10001L));
    drawExec.doDrawExec(new DrawReq("小蜗牛", 10001L));
    drawExec.doDrawExec(new DrawReq("八杯水", 10001L));
}
```

![image-20230403203101415](README.assets/image-20230403203101415.png)



## 7. 简单工厂搭建发奖领域

使用简单工厂，避免使用过多的`if-else`判断奖品的类型。

<img src="README.assets/image-20230411173550663.png" alt="image-20230411173550663" style="zoom:50%;" />

所有的方法交给工厂，工厂方法提供获取创建对象。奖品服务交给工厂处理，通过map数组类型替换掉`if-else`的过程。

### 7.1 调整数据库表字段名称

使用规范化的字段名称，如activityId` 调整为 `activity_id`，涉及改造的表包括：`activity`、`award`、`strategy`、`strategy_detail

```sql
-- create database lottery;

USE lottery;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '活动名称',
  `activity_desc` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '活动描述',
  `begin_date_time` datetime(3) DEFAULT NULL COMMENT '开始时间',
  `end_date_time` datetime(3) DEFAULT NULL COMMENT '结束时间',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存',
  `stock_surplus_count` int(11) DEFAULT NULL COMMENT '库存剩余',
  `take_count` int(11) DEFAULT NULL COMMENT '每人可参与次数',
  `strategy_id` bigint(11) DEFAULT NULL COMMENT '抽奖策略ID',
  `state` tinyint(2) DEFAULT NULL COMMENT '活动状态：1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启',
  `creator` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`),         
  UNIQUE KEY `unique_activity_id` (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='活动配置';

-- ----------------------------
-- Records of activity
-- ----------------------------
BEGIN;
INSERT INTO `activity` VALUES (1, 100001, '活动名', '测试活动', '2021-10-01 00:00:00', '2021-12-30 23:59:59', 100, 94, 10, 10001, 5, 'xiaofuge', '2021-08-08 20:14:50', '2021-08-08 20:14:50');
INSERT INTO `activity` VALUES (3, 100002, '活动名02', '测试活动', '2021-10-01 00:00:00', '2021-12-30 23:59:59', 100, 100, 10, 10001, 5, 'xiaofuge', '2021-10-05 15:49:21', '2021-10-05 15:49:21');
COMMIT;

-- ----------------------------
-- Table structure for award
-- ----------------------------
DROP TABLE IF EXISTS `award`;
CREATE TABLE `award` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `award_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '奖品ID',
  `award_type` tinyint(4) DEFAULT NULL COMMENT '奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）',
  `award_name` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '奖品名称',
  `award_content` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '奖品内容「文字描述、Key、码」',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_award_id` (`award_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='奖品配置';

-- ----------------------------
-- Records of award
-- ----------------------------
BEGIN;
INSERT INTO `award` VALUES (1, '1', 1, 'IMac', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `award` VALUES (2, '2', 1, 'iphone', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `award` VALUES (3, '3', 1, 'ipad', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `award` VALUES (4, '4', 1, 'AirPods', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `award` VALUES (5, '5', 1, 'Book', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
COMMIT;

-- ----------------------------
-- Table structure for rule_tree
-- ----------------------------
DROP TABLE IF EXISTS `rule_tree`;
CREATE TABLE `rule_tree` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tree_name` varchar(64) DEFAULT NULL COMMENT '规则树Id',
  `tree_desc` varchar(128) DEFAULT NULL COMMENT '规则树描述',
  `tree_root_node_id` bigint(20) DEFAULT NULL COMMENT '规则树根ID',
  `create_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(3) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2110081903 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rule_tree
-- ----------------------------
BEGIN;
INSERT INTO `rule_tree` VALUES (2110081902, '抽奖活动规则树', '用于决策不同用户可参与的活动', 1, '2021-10-08 15:38:05', '2021-10-08 15:38:05');
COMMIT;

-- ----------------------------
-- Table structure for rule_tree_node
-- ----------------------------
DROP TABLE IF EXISTS `rule_tree_node`;
CREATE TABLE `rule_tree_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tree_id` int(2) DEFAULT NULL COMMENT '规则树ID',
  `node_type` int(2) DEFAULT NULL COMMENT '节点类型；1子叶、2果实',
  `node_value` varchar(32) DEFAULT NULL COMMENT '节点值[nodeType=2]；果实值',
  `rule_key` varchar(16) DEFAULT NULL COMMENT '规则Key',
  `rule_desc` varchar(32) DEFAULT NULL COMMENT '规则描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rule_tree_node
-- ----------------------------
BEGIN;
INSERT INTO `rule_tree_node` VALUES (1, 2110081902, 1, NULL, 'userGender', '用户性别[男/女]');
INSERT INTO `rule_tree_node` VALUES (11, 2110081902, 1, NULL, 'userAge', '用户年龄');
INSERT INTO `rule_tree_node` VALUES (12, 2110081902, 1, NULL, 'userAge', '用户年龄');
INSERT INTO `rule_tree_node` VALUES (111, 2110081902, 2, '100001', NULL, NULL);
INSERT INTO `rule_tree_node` VALUES (112, 2110081902, 2, '100002', NULL, NULL);
INSERT INTO `rule_tree_node` VALUES (121, 2110081902, 2, '100003', NULL, NULL);
INSERT INTO `rule_tree_node` VALUES (122, 2110081902, 2, '100004', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for rule_tree_node_line
-- ----------------------------
DROP TABLE IF EXISTS `rule_tree_node_line`;
CREATE TABLE `rule_tree_node_line` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tree_id` bigint(20) DEFAULT NULL COMMENT '规则树ID',
  `node_id_from` bigint(20) DEFAULT NULL COMMENT '节点From',
  `node_id_to` bigint(20) DEFAULT NULL COMMENT '节点To',
  `rule_limit_type` int(2) DEFAULT NULL COMMENT '限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围];7:果实',
  `rule_limit_value` varchar(32) DEFAULT NULL COMMENT '限定值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rule_tree_node_line
-- ----------------------------
BEGIN;
INSERT INTO `rule_tree_node_line` VALUES (1, 2110081902, 1, 11, 1, 'man');
INSERT INTO `rule_tree_node_line` VALUES (2, 2110081902, 1, 12, 1, 'woman');
INSERT INTO `rule_tree_node_line` VALUES (3, 2110081902, 11, 111, 3, '25');
INSERT INTO `rule_tree_node_line` VALUES (4, 2110081902, 11, 112, 4, '25');
INSERT INTO `rule_tree_node_line` VALUES (5, 2110081902, 12, 121, 3, '25');
INSERT INTO `rule_tree_node_line` VALUES (6, 2110081902, 12, 122, 4, '25');
COMMIT;

-- ----------------------------
-- Table structure for strategy
-- ----------------------------
DROP TABLE IF EXISTS `strategy`;
CREATE TABLE `strategy` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` bigint(11) NOT NULL COMMENT '策略ID',
  `strategy_desc` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '策略描述',
  `strategy_mode` tinyint(2) DEFAULT NULL COMMENT '策略方式（1:单项概率、2:总体概率）',
  `grant_type` tinyint(2) DEFAULT NULL COMMENT '发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）',
  `grant_date` datetime(3) DEFAULT NULL COMMENT '发放奖品时间',
  `ext_info` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '扩展信息',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `strategy_strategyId_uindex` (`strategy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='策略配置';

-- ----------------------------
-- Records of strategy
-- ----------------------------
BEGIN;
INSERT INTO `strategy` VALUES (1, 10001, 'test', 2, 1, NULL, '', '2021-09-25 08:15:52', '2021-09-25 08:15:52');
COMMIT;

-- ----------------------------
-- Table structure for strategy_detail
-- ----------------------------
DROP TABLE IF EXISTS `strategy_detail`;
CREATE TABLE `strategy_detail` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` bigint(11) NOT NULL COMMENT '策略ID',
  `award_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '奖品ID',
  `award_name` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '奖品描述',
  `award_count` int(11) DEFAULT NULL COMMENT '奖品库存',
  `award_surplus_count` int(11) DEFAULT '0' COMMENT '奖品剩余库存',
  `award_rate` decimal(5,2) DEFAULT NULL COMMENT '中奖概率',
  `create_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='策略明细';

-- ----------------------------
-- Records of strategy_detail
-- ----------------------------
BEGIN;
INSERT INTO `strategy_detail` VALUES (1, 10001, '1', 'IMac', 10, 0, 0.05, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `strategy_detail` VALUES (2, 10001, '2', 'iphone', 20, 19, 0.15, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `strategy_detail` VALUES (3, 10001, '3', 'ipad', 50, 43, 0.20, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `strategy_detail` VALUES (4, 10001, '4', 'AirPods', 100, 70, 0.25, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `strategy_detail` VALUES (5, 10001, '5', 'Book', 500, 389, 0.35, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

```



### 7.2 发奖领域服务实现

截止到目前我们开发实现的都是关于 `domain` 领域层的建设，当各项核心的领域服务开发完成以后，则会在 `application` 层做服务编排流程处理的开发。例如：从用户参与抽奖活动、过滤规则、执行抽奖、存放结果、发送奖品等内容的链路处理。涉及的领域如下：

<img src="README.assets/image-20230411174755859.png" alt="image-20230411174755859" style="zoom:30%;" />

#### 7.2.1 工程结构

<img src="README.assets/image-20230411174826756.png" alt="image-20230411174826756" style="zoom:50%;" />

- 关于award发奖领域中主要的核心实现在于service中的两块功能逻辑实现，分别是goods 商品处理`、`factory 工厂
- goods：包装适配各类奖品的发放逻辑，在实际的业务中，需要调用优惠券，兑换码，物流发货等操作，这些内容经过封装后可以在自己的商品类下实现
- factory：工厂模式通过调用方提供的发奖类型，返回对应的发奖服务。通过这样由具体的子类决定返回结果，并且做相应的业务处理。从而不至于让领域层包装太多频繁变化的业务属性，如果核心功能域是在做业务逻辑封装，就会变得非常庞大且混乱。

#### 7.2.2 发奖适配策略

定义奖品配送接口：

```java
public interface IDistributionGoods {
    /**
     * 奖品配送接口，奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）
     *
     * @param req   物品信息
     * @return      配送结果
     */
    DistributionRes doDistribution(GoodsReq req);

}
```

抽奖，抽象出配送货物的接口，把各类奖品模拟成货物，配送代表着发货，包括虚拟奖品和实物奖品

**实现发送奖品：CouponGoods、DescGoods、PhysicalGoods、RedeemCodeGoods**

```java
@Component
public class CouponGoods extends DistributionBase implements IDistributionGoods {

    @Override
    public DistributionRes doDistribution(GoodsReq req) {

        // 模拟调用优惠券发放接口
        logger.info("模拟调用优惠券发放接口 uId：{} awardContent：{}", req.getuId(), req.getAwardContent());

        // 更新用户领奖结果
        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());

        return new DistributionRes(req.getuId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }
}
```

由于抽奖系统并没有与外部系统对接，所以在例如优惠券、兑换码、实物发货上只能通过模拟的方式展示。另外四种发奖方式基本类似。

#### 7.2.3 定义简单工厂

**工厂配置**

```java
public class GoodsConfig {

    /** 奖品发放策略组 */
    protected static Map<Integer, IDistributionGoods> goodsMap = new ConcurrentHashMap<>();

    @Resource
    private DescGoods descGoods;

    @Resource
    private RedeemCodeGoods redeemCodeGoods;

    @Resource
    private CouponGoods couponGoods;

    @Resource
    private PhysicalGoods physicalGoods;

    @PostConstruct
    public void init() {
        goodsMap.put(Constants.AwardType.DESC.getCode(), descGoods);
        goodsMap.put(Constants.AwardType.RedeemCodeGoods.getCode(), redeemCodeGoods);
        goodsMap.put(Constants.AwardType.CouponGoods.getCode(), couponGoods);
        goodsMap.put(Constants.AwardType.PhysicalGoods.getCode(), physicalGoods);
    }
}
```

把四种奖品的发奖，放到一个统一的配置文件类Map中，便于通过AwardType获取相应的对象，减少`if-else`的使用。

**工厂使用**

```java
@Service
public class DistributionGoodsFactory extends GoodsConfig {

    public IDistributionGoods getDistributionGoodsService(Integer awardType){
        return goodsMap.get(awardType);
    }

}
```

配送商品简单工厂，提供获取配送服务



### 7.3 测试

```java
 @Test
    public void test_award() {
        // 执行抽奖
        DrawResult drawResult = drawExec.doDrawExec(new DrawReq("测试用户",10001L));

        // 判断抽奖结果
        Integer drawState = drawResult.getDrawState();
        if(Constants.DrawState.FAIL.getCode().equals(drawState)){
            logger.info("未中奖 DrawAwardInfo is null");
            return;
        }

        // 封装发奖参数，orderId：2109313442431 为模拟ID，需要在用户参与领奖活动时生成
        DrawAwardInfo drawAwardInfo = drawResult.getDrawAwardInfo();
        GoodsReq goodsReq = new GoodsReq(drawResult.getUId(), "2109313442431", drawAwardInfo.getAwardId(), drawAwardInfo.getAwardName(), drawAwardInfo.getAwardContent());

        // 根据 awardType 从抽奖工厂中获取对应的发奖服务
        IDistributionGoods distributionGoodsService = distributionGoodsFactory.getDistributionGoodsService(drawAwardInfo.getAwardType());
        DistributionRes  distributionRes = distributionGoodsService.doDistribution(goodsReq);

        logger.info("测试结果： {}",JSON.toJSONString(distributionRes));
    }
```

<img src="README.assets/image-20230411222150140.png" alt="image-20230411222150140" style="zoom:50%;" />



## 8. 活动领域的配置与状态

活动领域部分功能的开发，包括：活动创建、活动状态变更。主要以 domain 领域层下添加 activity 为主，并在对应的 service 中添加 deploy(创建活动)、partake(领取活动，待开发)、stateflow(状态流转) 三个模块。以及调整仓储服务实现到基础层。

### 8.1 开发日志

- 按照 DDD 模型，调整包引用 lottery-infrastructure 引入 lottery-domain，调整后效果`领域层 domain` 定义仓储接口，`基础层 infrastructure` 实现仓储接口。
- 活动领域层需要提供的功能包括：活动创建、活动状态处理和用户领取活动操作，本章节先实现前两个需求，下个章节继续开发其他功能。
- 活动创建的操作主要会用到事务，因为活动系统提供给运营后台创建活动时，需要包括：活动信息、奖品信息、策略信息、策略明细以及其他额外扩展的内容，这些信息都需要在一个事务下进行落库。
- 活动状态的审核，【1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启】，这里我们会用到设计模式中的`状态模式`进行处理。
