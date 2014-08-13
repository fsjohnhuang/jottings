## 使用URLClassLoader加载外部jar
被加载的jar下的test.Test类

	package test;
	public Test{
		public void echo(String msg){
			System.out.println(msg);
		}
	}

主程序

	// 注意：URL必须是jar包的绝对路径
	URLClassLoader clsLoader = new URLClassLoader(new URL[]{ new URL("file:/d:/extJar/test.jar") });
	Class<?> testCls = clsLoader.loadClass("test.Test");
	testCls.getMethod("echo", String.class).invoke(testCls.newInstance(), "HelloWorld");
