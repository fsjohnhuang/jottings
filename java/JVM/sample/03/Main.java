class Main{
	static class Inner{
		final int[] nums = {1,2,3,4};
	}
	
	public static void main(String[] args){
		Inner i = new Main.Inner();
		System.out.println(i.nums[0]);
		i.nums[0] = 1111;
		System.out.println(i.nums[0]);
		final long x;
		x = 1000;
		x = 1001;
		System.out.println(x);
		try{
			System.in.read();
		}
		catch(Exception e){}
	}
}
