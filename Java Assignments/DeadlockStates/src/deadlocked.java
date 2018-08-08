
public class deadlocked {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		deadlocked test = new deadlocked();

		firstTest first = test.new firstTest();
		secondTest second = test.new secondTest();
		Runnable firstBlock = new Runnable() {
			public void run() {
				synchronized (first) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (second) {
						System.out.println("In block 1");
					}
				}
			}
		};
		Runnable secondBlock = new Runnable() {
			public void run() {
				synchronized (second) {
					synchronized (first) {
						System.out.println("In block 2");
					}
				}
			}
		};
		new Thread(firstBlock).start();
		new Thread(secondBlock).start();
	}

	private class firstTest {
		private int i = 10;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}
	}

	private class secondTest {
		private int i = 20;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}
	}
}
