import java.util.*;
class Main{
	static class OOMObject{
	}

	public static void main(String[] args){
		List<OOMObject> list = new ArrayList<OOMObject>();		
		while(true){
			list.add(new OOMObject());
		}
	}
}
