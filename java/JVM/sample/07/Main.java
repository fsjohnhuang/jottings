import java.net.*;
import java.io.*;

class Main{
	public static void main(String[] args) throws MalformedURLException, IOException{
		//File file = new File("d:\\log1.txt");
		//URL url = file.toURL();
		URL url = new URL("file:/d:/log1.txt");
		System.out.println(url.toString());
		InputStream is = url.openStream();
		InputStreamReader reader = new InputStreamReader(is);
		int buf = 0;
		while(buf != -1){
			buf = reader.read();
			System.out.print((char)buf);
		}
	}
}
