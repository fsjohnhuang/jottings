import java.net.*;
class Main{
	public static void main(String[] args){
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
		for(URL url : urls){
			System.out.println(url.toExternalForm());
		}
	}
}
