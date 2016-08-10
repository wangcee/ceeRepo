# Maven - Nexus


## Nexus安装

### 安装 maven 和 jdk

```shell
[root@javaserver ~]# java -version
java version "1.8.0_20"
Java(TM) SE Runtime Environment (build 1.8.0_20-b26)
Java HotSpot(TM) 64-Bit Server VM (build 25.20-b23, mixed mode)
[root@javaserver ~]# 
[root@javaserver ~]# mvn -version
Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-11T00:41:47+08:00)
Maven home: /home/sdk/apache-maven-3.3.9
Java version: 1.8.0_20, vendor: Oracle Corporation
Java home: /usr/java/jdk1.8.0_20/jre
Default locale: zh_CN, platform encoding: UTF-8
OS name: "linux", version: "2.6.32-431.el6.x86_64", arch: "amd64", family: "unix"
[root@javaserver ~]#
```

### Download Nexus

1. [www.sonatype.org/nexus](http://www.sonatype.org/nexus/downloads/)
1. NEXUS OSS (Open Source Software) 免费的开源版本
1. NEXUS PROFESSIONAL 收费的专业版本 FREE TRIAL

### 搭建Nexus

- /opt/nexus
- 解压:  `tar -zvxf nexus-3.0.1-01-unix.tar.gz`
- 解压后会自动生成一个nexus工作目录`sonatype-work`, 
- nexus下载的jar包会存放在`sonatype-work/nexus/storage`中 --indexer
- nexus的索引会存放在`sonatype-work/nexus/indexer`

配置环境变量
```shell
vi /etc/profile
NEXUS_HOME=/opt/nexus/nexus-2.13.0-01
export PATH=$PATH:$NEXUS_HOME/bin
source /etc/profile
```

```shell
[root@javaserver bin]# /opt/nexus/nexus-2.13.0-01/bin/nexus --help
WARNING: ************************************************************
WARNING: Detected execution as "root" user.  This is NOT recommended!
WARNING: ************************************************************
Usage: /opt/nexus/nexus-2.13.0-01/bin/nexus {start|stop|run|run-redirect|status|restart|force-reload}

[root@javaserver bin]# /opt/nexus/nexus-2.13.0-01/bin/nexus status
WARNING: ************************************************************
WARNING: Detected execution as "root" user.  This is NOT recommended!
WARNING: ************************************************************
nexus is stopped.
[root@javaserver bin]# 

useradd nexus
passwd nexus     --nexus
chown -R nexus:nexus /opt/nexus

su - nexus
[nexus@javaserver ~]$ nexus start
#//启动nexus
Starting Nexus OSS...
Started Nexus OSS.
[nexus@javaserver ~]$ nexus status
#//查看状态
Nexus OSS is running (26447).
[nexus@javaserver ~]$
```

## Nexus配置

- [Nexus Web控制台](http://javaserver:8081/nexus )
- 用户名 admin
- 密码 admin123



### 8081端口配置

在`$NEXUS_HOME/conf/nexus.properties`文件中

```
	# Jetty section
	application-port=8081
	application-host=0.0.0.0
	nexus-webapp=${bundleBasedir}/nexus
	nexus-webapp-context-path=/nexus
```

## 四种仓库

**Nexus有四种仓库**

### (1) 代理仓库 	

是对远程仓库的一个代理.最典型的是Maven官方中央仓库、JBoss仓库等. 如果构建的Maven项目本地仓库没有依赖包,就会去这个代理站点下载,如果代理站点也没有此依赖包,就去远程中央仓库下载依赖,代理站点下载成功后再下载至本机. Nexus默认自带了如下代理仓库： 

1. Apache Snapshots 			包含了来自于Apache软件基金会的快照版本.http://people.apache.org/repo/m2-snapshot-repository 
1. Codehaus Snapshots 		包含了来自于Codehaus Maven仓库的快照版本. http://snapshots.repository.codehaus.org/ 
1. Central Maven Repository 	是中央Maven仓库(发布版本). http://repo1.maven.org/maven2/ 

### (2) 宿主仓库 	

是由Nexus托管的仓库.主要用于部署无法从公共仓库获取的构件(如oracle的JDBC驱动, 商业软件jar包)以及自己或第三方的项目构件. Nexus自带了如下宿主仓库：

1. 3rd Party 	用来存储在公共Maven仓库中找不到的第三方依赖.如：组织使用的,商业的,私有的类库如Oracle JDBC驱动
1. Releases 	是组织部署管理内部发布版本的宿主仓库.存放自己项目中发布的构建, 通常是Release版本的, 比如我们自己做了一个FTP Server的项目, 生成的构件为ftpserver.war, 就可以把这个构建发布到Nexus的Releases仓库. 
1. Snapshots 	是组织部署管理内部开发版本的宿主仓库.这个仓库非常有用, 目的是让我们可以发布那些非release版本, 非稳定版本, 比如在trunk下开发一个项目,在正式release之前你可能需要临时发布一个版本给你的同伴使用, 因为你的同伴正在依赖你的模块开发,这时就可以发布Snapshot版本到这个仓库, 你的同伴就可以通过简单的命令来获取和使用这个临时版本.

### (3)虚拟仓库 	

作为Maven 1的适配器存在.Nexus自带了一个central-m1虚拟仓库.

### (4)仓库组	
	
组是Nexus一个强大特性,Nexus通过仓库组的概念统一管理多个仓库,它允许在一个单独的URL中组合多个仓库. 可以建立多个proxy代理仓库,hosted本地仓库, 如果没有仓库组,需要引用这些仓库的时候需要一一加入到setting.xml里, 有了仓库组,  只需要做一次引用就可以了,把需要的仓库加入到仓库组即可.

如果需要可以创建仓库组,在Repositories菜单处选add-->`Repository Group`即可.
Nexus自带组：

1. public Repositories	包含三个宿主仓库：`3rd Party`, `Releases`, 和`Snapshots`, 还有`中央Maven仓库`.

## Nexus 使用 

### 开启远程索引下载

新搭建的neuxs环境只是一个空的仓库,需要手动和远程中心库进行同步,nexus默认关闭远程索引下载功能,需要开启远程索引下载. 

点击左侧菜单`Repositories`,将Apache Snapshots,Codehaus Snapshots,Maven Central三个repository的`Download Remote Indexes`修改为`true`. 然后在这三个仓库上分别右键,选择`Repari Index`, 这样Nexus就会去下载远程的*索引*文件. 

### 建立宿主仓库

新建公司的内部仓库,步骤为Repositories –> Add –> Hosted Repository, 在页面的下半部分输入框中填入Repository ID和Repository Name即可,
 比如分别填入test 和 test repostiory,另外把Deployment Policy设置为`Allow Redeploy`, 点击save就创建完成了.

### 增加新的Repository

添加步骤： 点击Add->Proxy Repository->填写Repository ID, Repository Name, 以及Remote Storage Location 其他的默认即可.  

```
  <groupId>com.oracle.jdbc</groupId>
  <artifactId>jdbc</artifactId>
  <version>1.0</version>
```

### 添加自己的jar包 

在repository列表中有一个`3rd party`, 也就是第三方jar包,点击会看到一个`Artifact Upload`选项卡,点击后,填写相应的信息.  
GAV Definition 一般选择 GAV Parameters. 然后添加Group: Artifact: Version: Package.     示例    juel:juel-impl:2.2.1:jar  
然后选择要上传的jar包,保存即可.

## 客户端使用Nexus

### (1)第一种方式：配置工程的pom.xml

每增加repository一个都需要添加,会增加配置的复杂度,并且增加了配置文件的冗余,所以不推荐使用.

```xml
<repositories>
  <repository>
    <id>nexus</id>
    <name>nexus Repository</name>
    <url>http://192.168.0.222:8081/nexus/content/repositories/central/</url>
  </repository>
</repositories>
```

这种方式,Maven只会从nexus中的central中央仓库下载,而我们希望开发的releases、snapshots仓库都能下载,因此也需要相应添加：

```xml
  <repository>
    <id>nexusreleases</id>
    <name>nexus Repository</name>
    <url>http://192.168.0.222:8081/nexus/content/repositories/releases/</url>
  </repository>
  <repository>
    <id>nexussnapshots</id>
    <name>nexus Repository</name>
    <url>http://192.168.0.222:8081/nexus/content/repositories/snapshots/</url>
  </repository>
 ```

### (2)第二种方式：为解决第一种方式的问题,在nexus中提供了另外一种方式.

 仓库：Public Repositories,类型：`group Repository`, Path：http://nexusIP:8081/nexus/content/groups/public/
在项目的pom.xml中只需要将url地址更改成它的地址即可,用了这个仓库就相当于用了Releases、Snapshots、3rd party 、Central这几个仓库：

```xml
<repositories>
  <repository>
    <id>nexus</id>
    <name>nexus Repository</name>
    <url>http://192.168.0.222:8081/nexus/content/groups/public/</url>
  </repository>
</repositories>
```

这种方式,也是只针对当前项目,每一个项目都需要配置,不太方便.

### (3)第三种方式：配置settings profile,分为两种：

1. 定义在~/.m2/settings.xml中的用户特定 settings profile
2. 定义在${M2_HOME}/conf/settings.xml中的全局 settings profile

```xml
<profiles>
  <profile>
      <id>nexusRepository</id>
      <repositories>
	   <repository>
	      <id>nexus</id>
	      <name>nexus Repository</name>
	      <url>http://192.168.0.222:8081/nexus/content/groups/public/</url>
	      <releases><enabled>true</enabled></releases> <!-- 默认就是true -->
	      <snapshots><enabled>true</enabled></snapshots> <!-- 默认是false,需手动设置为true -->
	  </repository>
     </repositories>
  </profile>   
</profiles>
```

```xml
<!-- 必须激活profile才能生效 -->
<activeProfiles>
    <activeProfile>nexusRepository</activeProfile>
</activeProfiles>
```


第三种方式在nexus服务器停止时,maven会从maven的中央仓库mvn repository进行下载,这是因为 Maven项目首先会去nexus中去找,
当它发现nexus服务停止时它就会去找Maven的仓库.Maven安装包中lib的maven-model-builder-X.X.X.jar中的pom.xml配置如下：

```xml
<repositories>
    <repository>
      <id>central</id>
      <name>Central Repository</name>
      <url>https://repo.maven.apache.org/maven2</url>
      <layout>default</layout>
      <snapshots><enabled>false</enabled></snapshots>
    </repository>
  </repositories>
```

问题是当我们发现Nexus服务停止了不能下载, 而又不允许去Maven中下载, 这就需要第四种方式.

### (4)第四种方式：配置Nexus镜像

```xml
<mirrors>
    <mirror>
      <id>mirrorNexusId</id>
      <mirrorOf>*</mirrorOf> <!-- *代表所有仓库镜像 ,不管什么仓库都会去找URL地址下载 -->
      <name>nexus Mirror.</name>
      <url>http://192.168.0.222:8081/nexus/content/groups/public/</url>
    </mirror>
</mirrors>
```

```xml
<!-- 这里的仓库配置,是Maven中的,其snapshots是false,通过这种方式将其激活,就可以访问中央仓库中的snapshots -->
<profiles>
  <profile>
      <id>nexusRepository</id>
      <repositories>
	   <repository>
	     <id>central</id>
	     <name>Central Repository</name>
	     <url>https://repo.maven.apache.org/maven2</url>
	     <layout>default</layout>
	     <snapshots><enabled>true</enabled></snapshots>
	   </repository>
	 </repositories>
    </profile>   
</profiles>
<activeProfiles>
    <activeProfile>nexusRepository</activeProfile> <!-- 这里必须激活profile 才能生效 -->
</activeProfiles>
```





## 发布自己的jar到Nexus Repository

### 项目pom文件中配置releases和snapshots版本发布的具体repository

```xml
<distributionManagement>
	<repository>
		<id>releases</id>
		<name>Nexus Release Repository</name>
		<url>http://192.168.0.222:8081/nexus/content/repositories/releases/</url>
	</repository>
	<snapshotRepository>
		<id>snapshots</id>
		<name>Nexus Snapshot Repository</name>
		<url>http://192.168.0.222:8081/nexus/content/repositories/snapshots/</url>
	</snapshotRepository>
</distributionManagement>
```

Nexus仓库对于匿名用户是只读权限,为了能够部署构件,需要在Maven的conf/settings.xml配置账号密码. **注意server的id与repository的id必须对应**

```xml
<servers>
	<server>
		<id>releases</id>
		<username>admin</username>
		<password>admin123</password>
	</server>
	<server>
		<id>snapshots</id>
		<username>admin</username>
		<password>admin123</password>
	</server>
</servers>
```

### 项目中执行mvn deploy

`clean deploy`

发布成功后,可以在repositories中的releases或snapshots中看到所发布的项目.


注：最好将Nexus配置成系统服务,并设置成开机自动启动.

> java -cp D:\eclipseWorkSpace\maven\target\maven-0.0.1-SNAPSHOT.jar t.App
