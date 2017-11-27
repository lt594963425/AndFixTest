# AndFixTest
阿里的热修复Demo
热修复实现是基于阿里的一个开源项目AndFix
[https://github.com/alibaba/AndFix/](https://github.com/alibaba/AndFix/)



####第一步：接入项目

在项目app下build.gradle配置

	compile 'com.alipay.euler:andfix:0.4.0@aar'

####第二步：使用AndFix库进行加载补丁

使用方式

1. 初始化PatchManager，并且指定当前版本

		patchManager = new PatchManager(context);
		patchManager.init(appversion);//current version
	
	* app的版本号可以通过如下代码获取得到
			
			String appversion= getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

2. 加载补丁
		//这个方法越早执行越好，可以考虑放在Application的onCreate方法中
		patchManager.loadPatch();

3. 加载补丁文件

		//只要下载完补丁，一旦执行这个方法，补丁就会立马生效
		patchManager.addPatch(path);

####第三步：制作补丁

下载制作补丁的文件
　假如我们收到了用户上传的崩溃信息，我们改完需要修复的Bug，这个时候就会有一个新的的apk我们就叫它为new.apk，
原来的那个有Bug的apk你也有我们就叫它old.apk。这个时候我们就可以利用阿里github上面提供的工具生成一个xxxx.apatch包用于修复Bug。
https://github.com/alibaba/AndFix/tree/master/tools

提供之前存在bug的apk和修复后的apk
然后将2个apk放在工具目录下
在当前目录打开cmd

执行命令后会在工具目录下生成一个patch目录，里面的AndFixDemo-debug-after-86112f4778c67a620082b737ae673830.apatch则是我们生成的补丁文件

	usage: apkpatch -f <new> -t <old> -o <output> -k <keystore> -p <***> -a <alias> -e <***>

eg：

	F:\热修复\apkpatch-1.0.3>apkpatch.bat -f AndFixDemo-debug-after.apk -t AndFixDemo-debug-before.apk -o patch -k test.jks -p 123456 -a test -e 123456
	          apkpatch.bat -f new.apk -t old.apk -o output -k testapp.jks -p 123456 -a test -e 123456

![apkpatch_res](./apkpatch_res.png)

![hotFix_getPatch](./hotFix_getPatch.png)

-f : 没有Bug的新版本apk
-t : 有bug的旧版本apk
-o<output>  : 生成的补丁文件所放的文件夹
-k<keystore>: 签名打包密钥<打包所用的keystore>
-p<password>: 签名打包密钥密码（keystore的密码）
-a<alias>   : 签名密钥别名
-e<alias password> : 签名别名密码（这样一般和密钥密码一致）

####第四步：将补丁拖放到手机上

实际情况是从网络上下载补丁然后通过第二步将bug修复

####混淆
在对加入热修复情况下做混淆的时候，在proguard-rules文件里配置

		-keep class * extends java.lang.annotation.Annotation
		-keepclasseswithmembernames class * {
		    native <methods>;
		}

		
