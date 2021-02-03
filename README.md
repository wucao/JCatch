## 什么是JCatch
当程序发生异常(Exception)，处理方式一般是通过日志文件记录下来，这种方式很容易被忽略，而且查询起来比较麻烦。

JCatch提供了一种方案，当程序发生异常时，通过JCatch平台接口提交到JCatch平台，由JCatch平台统一管理所有异常，这样可以方便查询，并且JCatch平台提供异常监控功能，当应用程序发生异常时会以邮件方式提醒相关人员。

应用列表:
![应用列表](http://7xi3wi.com1.z0.glb.clouddn.com/image/jpg/WechatIMG14861.jpeg)

应用配置:
![应用配置](http://7xi3wi.com1.z0.glb.clouddn.com/image/jpg/WechatIMG14869.jpeg)

异常列表:
![异常列表](http://7xi3wi.com1.z0.glb.clouddn.com/image/jpg/WechatIMG14863.jpeg)

Java异常详情:
![Java异常](http://7xi3wi.com1.z0.glb.clouddn.com/image/jpg/WechatIMG164.jpeg)

PHP异常:
![PHP异常](http://7xi3wi.com1.z0.glb.clouddn.com/image/jpg/WechatIMG14870.jpeg)

邮件通知:
![邮件通知](http://7xi3wi.com1.z0.glb.clouddn.com/WechatIMG163.jpg)


## 运行/部署JCatch
### MySQL数据库
使用MySQL数据库执行doc/sql.sql中的SQL脚本创建表

修改`src/main/resources/MyBatis.xml`文件中的数据库连接

### 配置登录管理员账户
默认管理员账户： 用户名 admin  密码 xxg

管理员登录基于Spring Security实现，可以配置src/main/resources/springSecurity.xml文件

### 支持邮件订阅功能
邮件订阅功能会在应用发生异常时，每小时发送一封邮件给应用订阅者，邮件内容为这个小时内的异常信息，配置src/main/resources/messageService.xml支持邮件订阅

### 打包
`mvn package`命令生成war包，可部署在Tomcat、Jetty等服务器中

### 运行MyBatis Generator生成代码
如果二次开发需要修改表结构, 修改`src/main/resources/generatorConfig.xml`文件中的数据库连接, `com.xxg.jcatch.mbg`包下代码是通过MyBatis Generator自动生成的，可以运行`mvn mybatis-generator:generate`命令生成代码


## API
### 提交一条Exception
URL: http://[your baseUrl]/api/submitExceptionJson?appId=[your appId]

请求类型: POST

请求Body:
```
{
    "fileName": "Main.java",
    "methodName": "main",
    "className": "com.xxg.jcatch.Main",
    "stackTrace": "java.lang.ArithmeticException: / by zero\n\tat com.xxg.jcatch.Main.main(Main.java:16)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.lang.reflect.Method.invoke(Method.java:498)\n\tat com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)\n",
    "message": "/ by zero",
    "lineNumber": 16,
    "exceptionName": "java.lang.ArithmeticException"
}
```

响应Body:
```
{
    "success": true
}
```


## 客户端接入
### Java
JCatch Java客户端 传送门： https://github.com/wucao/jcatch-java-client

### PHP
#### 通用
```
<?php

function submitToJCatch($e) {

    $json = json_encode($e);

    $ch = curl_init('http://<your baseUrl>/api/submitExceptionJson?appId=<your appId>');
    curl_setopt_array($ch, array(
        CURLOPT_POST => TRUE,
        CURLOPT_RETURNTRANSFER => TRUE,
        CURLOPT_HTTPHEADER => array(
            'Content-Type: application/json'
        ),
        CURLOPT_POSTFIELDS => $json,
        CURLOPT_CONNECTTIMEOUT => 2,
        CURLOPT_TIMEOUT => 4,
    ));
    curl_exec($ch);
}
function error_handler($errno, $errstr, $errfile, $errline) {
    submitToJCatch(array(
        'message' => $errstr,
        'fileName' => $errfile,
        'lineNumber' => $errline,
    ));
}
function exception_handler($exception) {
    submitToJCatch(array(
        "fileName" => $exception->getFile(),
        "lineNumber" => $exception->getLine(),
        "stackTrace" => $exception->getTraceAsString(),
        "message" => $exception->getMessage(),
        "exceptionName" => get_class($exception)
    ));
}
set_error_handler("error_handler");
set_exception_handler("exception_handler");
```
PHP的错误和异常会通过set_error_handler、set_exception_handler指定的函数来处理，一般PHP框架都会有自己的一套错误处理机制，如果使用PHP框架建议通过框架本身的错误处理机制来提交错误到JCatch，下面有各个框架接入JCatch的方法。
#### Laravel
修改App/Exceptions/Handler.php文件:
```
public function report(Exception $exception)
{
    $appId     = '<your appId>';
    $submitUrl = 'http://<your baseUrl>/api/submitExceptionJson?appId=' . $appId;
    $data      = json_encode([
        "fileName"      => $exception->getFile(),
        "lineNumber"    => $exception->getLine(),
        "stackTrace"    => $exception->getTraceAsString(),
        "message"       => $exception->getMessage(),
        "exceptionName" => get_class($exception)
    ]);

    $ch = curl_init($submitUrl);
    curl_setopt_array($ch, array(
        CURLOPT_POST           => TRUE,
        CURLOPT_RETURNTRANSFER => TRUE,
        CURLOPT_HTTPHEADER     => array(
            'Content-Type: application/json'
        ),
        CURLOPT_POSTFIELDS     => $data,
        CURLOPT_CONNECTTIMEOUT => 2,
        CURLOPT_TIMEOUT        => 4,
    ));
    curl_exec($ch);

    return parent::report($exception);
}
```
#### CodeIgniter 3.x
在application/core目录下新建文件MY_Exceptions.php，代码如下：
```
<?php

class MY_Exceptions extends CI_Exceptions
{
    public function log_exception($severity, $message, $filepath, $line)
    {
        parent::log_exception($severity, $message, $filepath, $line);

        $data = array(
            "message" => $message,
            "fileName" => $filepath,
            "lineNumber" => $line
        );

        $trace = debug_backtrace();
        if (count($trace) > 1 && $trace[1]["function"] === "_exception_handler") {
            $exception = $trace[1]["args"][0];
            $data["stackTrace"] = $exception->getTraceAsString();
            $data["exceptionName"] = get_class($exception);
        }

        $json = json_encode($data);

        $ch = curl_init('http://<your baseUrl>/api/submitExceptionJson?appId=<your appId>');
        curl_setopt_array($ch, array(
            CURLOPT_POST => TRUE,
            CURLOPT_RETURNTRANSFER => TRUE,
            CURLOPT_HTTPHEADER => array(
                'Content-Type: application/json'
            ),
            CURLOPT_POSTFIELDS => $json,
            CURLOPT_CONNECTTIMEOUT => 2,
            CURLOPT_TIMEOUT => 4,
        ));
        curl_exec($ch);
    }
}
```
当发生错误或异常时，CI框架会自动调用以上代码，将异常信息提交到JCatch API。
#### Yii 1.1
在protected/components目录下新建文件ErrorHandler.php，代码如下：
```
<?php

class ErrorHandler extends CErrorHandler
{

    public $jcatchBaseUrl;
    public $jcatchAppId;
    public $jcatchSecretKey;

    protected function handleException($exception)
    {
        parent::handleException($exception);
        $this->submitToJCatch(array(
            "fileName" => $exception->getFile(),
            "lineNumber" => $exception->getLine(),
            "stackTrace" => $exception->getTraceAsString(),
            "message" => $exception->getMessage(),
            "exceptionName" => get_class($exception)
        ));

    }

    protected function handleError($event)
    {
        parent::handleError($event);
        $this->submitToJCatch(array(
            'message' => $event->message,
            'fileName' => $event->file,
            'lineNumber' => $event->line,
        ));
    }

    private function submitToJCatch($e) {

        $json = json_encode($e);

        $ch = curl_init($this->jcatchBaseUrl.'/api/submitExceptionJson?appId='.$this->jcatchAppId);
        curl_setopt_array($ch, array(
            CURLOPT_POST => TRUE,
            CURLOPT_RETURNTRANSFER => TRUE,
            CURLOPT_HTTPHEADER => array(
                'Content-Type: application/json'
            ),
            CURLOPT_POSTFIELDS => $json,
            CURLOPT_CONNECTTIMEOUT => 2,
            CURLOPT_TIMEOUT => 4,
        ));
        curl_exec($ch);
    }
}
```
修改所使用的配置文件，例如protected/config/main.php：
```
'errorHandler'=>array(
    'errorAction'=>'site/error',
    'class'=>'ErrorHandler',
    'jcatchBaseUrl'=>'<your baseUrl>',
    'jcatchAppId'=>'<your appId>',
    'jcatchSecretKey'=>'<your secretKey>',
),
```
当发生错误或异常时，Yii框架会自动调用ErrorHandler来处理异常，将异常信息提交到JCatch API。
