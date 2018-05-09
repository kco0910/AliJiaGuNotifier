# AliJiaGuNotifier
使用阿里加固API开发的jenkins插件

mavan中引入的阿里加固jar是用本地的私有仓库，如果不使用mavan仓库引用，在工程开发期间的调试会报not find class 异常。
在打包成hpi后，可以使用pom.xml中引用工程jar的方式。可以不用私有mavan仓库了。
