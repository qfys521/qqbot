# QQBot 

--- 
使用QQ官方api,框架使用`kloping/qqpd-bot-java`

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

