# QQBot

--- 
使用QQ官方api,
java-sdk使用`kloping/qqpd-bot-java`
> 请注意:本项目仍然在***早期开发***中,如有bug,欢迎填写`issue`或者发起`Pull request`.

<details> <summary>目录</summary>

- [命令注册](#命令注册)
- [配置文件](#配置文件)
- [如何使用](#如何使用)

</details>

## 命令注册

1,命令

```java

@Author("qfys521")//类注解,作者qfys521
public class Example {
    @Usage("/hello")
    @Command("/hello")//命令注解,/hello命令
    public void hello(MessageEvent<?, ?> event) {
        event.send("Hello World");
    }
}
```

2,注册

```java
//BotApplication.java

private static void regCmd() {
    RegisterCommand.registerCommand(new Class[]{
            Interactor.class,//Interactor.java命令
            CoreInteractors.class,//CoreInteractor.java命令
            Example.class//示例命令
    });
}
```

## 配置文件

配置文件有3个类,分别为`CoreConfigApplication` , `DataConfigApplication` 和 `OtherDataConfigApplication`
> 在使用时,可以使用`DataConfigApplication`.

```java

@Data //lombok annotation
class CountData {
    int count;
}
```

```java
@Command("/count")
@Usage("/count")
public vote count(MessageEvent<?, ?> event) {
    ConfigApplication configApplication = new DataConfigApplication(new CountData(), "count.json");// path: ./data/data/path.to.you.class.CountData
    CountData countData = (CountData) configApplication.getDataOrFail();
    event.send("count is " + countData.getCount);
    configApplication.saveOrFail();

    //File file = configApplication.getFile;
}
```

## 如何使用

1,构建

```shell
# Linux
chmod 777 -R ./
./gradlew shadowJar
# Build in ./build/libs/qqbot-${version}-all.jar
```

2,运行

```shell
java -jar qqbot-${version}-all.jar
# ${version} 填入你的版本
```