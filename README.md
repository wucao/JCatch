## 客户端接入
### Java
JCatch Java客户端 传送门: https://github.com/wucao/jcatch-java-client

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
PHP的错误和异常会通过set_error_handler、set_exception_handler指定的函数来处理, 一般PHP框架都会有自己的一套错误处理机制, 如果使用PHP框架建议通过框架本身的错误处理机制来提交错误到JCatch, 下面有各个框架接入JCatch的方法.
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
在application/core目录下新建文件MY_Exceptions.php, 代码如下:
```
<?php

class MY_Exceptions extends CI_Exceptions
{

	public function log_exception($severity, $message, $filepath, $line)
	{
		parent::log_exception($severity, $message, $filepath, $line);

		$json = json_encode(array(
			"message" => $message,
			"fileName" => $filepath,
			"lineNumber" => $line
		));

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
当发生错误或异常时, CI框架会自动调用以上代码, 将异常信息提交到JCatch API。
#### Yii 1.1
在protected/components目录下新建文件ErrorHandler.php, 代码如下:
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
修改所使用的配置文件, 例如protected/config/main.php:
```
'errorHandler'=>array(
    'errorAction'=>'site/error',
    'class'=>'ErrorHandler',
    'jcatchBaseUrl'=>'<your baseUrl>',
    'jcatchAppId'=>'<your appId>',
    'jcatchSecretKey'=>'<your secretKey>',
),
```
当发生错误或异常时, Yii框架会自动调用ErrorHandler来处理异常, 将异常信息提交到JCatch API。