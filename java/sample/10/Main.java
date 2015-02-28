public class Main{
	volatile int i = 0;
	public void setI(int i){
		this.i = i;
	}
	public int getI(){
		return this.i;
	}

	public static void main(String[] args){
		final Main main = new Main();

		Thread a = new Thread(){
			@Override
			public void run(){
				for (int i = 0; i < 100; i++){
					main.setI(i);		
					try{
						Thread.currentThread().sleep(1);
					}
					catch(InterruptedException e){}
				}
			}
		};
		Thread b = new Thread(){
			@Override
			public void run(){
				for (int i = 0; i < 100; i++){
					System.out.println(main.getI());
					try{
						Thread.currentThread().sleep(1);
					}
					catch(InterruptedException e){}
				}
			}
		};
		a.start();
		b.start();
		try{
			a.join();
			b.join();
		}
		catch(Exception e){}
	}
}
