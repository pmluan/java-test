package hcmus.edu.vn.main;

public class TestThread implements Runnable{

	@Override
	public void run() {
		System.out.println("thread is running...");
	}
	
	public static void main(String[] args) {
		TestThread runable = new TestThread();
        Thread t1 = new Thread(runable);
        t1.start();
	}
	
}
	