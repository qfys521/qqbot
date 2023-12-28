# QQBot 

--- 
使用QQ官方api,框架使用`kloping/qqpd-bot-java`
> 请注意:本项目仍然在***早期开发***中,如有bug,欢迎填写`issue`或者发起`Pull request`.

## 命令注册

1,命令
```java
@Author("qfys521")//类注解,作者qfys521
public class Example{
    @Command("/hello")//命令注解,/hello命令
    public void hello(MessageEvent<?,?> event){
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

## 如何使用
1,构建
```shell
chmod 777 -R ./
./gradlew shadowJar
# Build in ./build/libs/qqbot-${version}-all.jar
```
2,运行
```shell
java -jar qqbot-${version}-all.jar
# ${version} 填入你的版本
```