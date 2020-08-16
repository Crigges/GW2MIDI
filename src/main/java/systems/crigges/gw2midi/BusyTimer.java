package systems.crigges.gw2midi;

/**
 * @author Crigges
 * 
 *         This is a minimalistic approach to reach high precision non blocking
 *         timers in java with the best possible resolution and performance
 *         available on the given machine. This class tries to determine the
 *         sleep precision and fills the inaccuracy with a busy wait. Please
 *         notice that while functions accepts nanoseconds as input you will not
 *         get nanosecond precision. Also take into account that some parts the
 *         JVM does in the background, may take quite some time and lead to
 *         heavy inaccuracy. So take care of your memory management and run some
 *         warmup tests.
 *
 */

public class BusyTimer {
	private static long MIN_SLEEP = 3;
	private static int MIN_SLEEP_DEFAULT_ITERATIONS = 100;

	private long start;
	private long target;
	private Runnable task;

	static {
		autoSetMinSleep();
	}

	public static void doAfterNano(long nanoDelay, Runnable task) {
		new BusyTimer(nanoDelay, task);
	}

	public static void doAfter(long msDelay, Runnable task) {
		new BusyTimer(msDelay * 1000000, task);
	}
	
	public static void waitFor(long msDelay) {
		new BusyTimer(msDelay * 1000000);
	}
	
	//Blocking version
	public BusyTimer(long nanoDelay) {
		task = null;
		start = System.nanoTime();
		target = start + nanoDelay;
		handle();
	}

	public BusyTimer(long nanoDelay, Runnable task) {
		this.task = task;
		start = System.nanoTime();
		target = start + nanoDelay;
		Thread t = new Thread(() -> handle());
		t.setDaemon(true);
		t.start();
	}

	private void handle() {
		// We are not saving nano time in temp var here since it could increase
		// while executing
		if (target - System.nanoTime() > MIN_SLEEP * 1000000) {
			long sleepTime = (target - System.nanoTime()) / 1000000;
			if (sleepTime >= MIN_SLEEP) {
				try {
					if (sleepTime - MIN_SLEEP <= MIN_SLEEP) {
						Thread.sleep(MIN_SLEEP);
					} else {
						Thread.sleep(sleepTime - MIN_SLEEP);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		while (target - System.nanoTime() > 0) {
		}
		if(task != null) {
			task.run();
		}		
	}

	public static long checkMinSleep(int iterations) {
		long max = 0;
		for (int i = 0; i < iterations; i++) {
			long nanos = System.nanoTime();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nanos = System.nanoTime() - nanos;
			max = Math.max(max, nanos);
		}
		long sleep = (max + 500000) / 1000000;
		return sleep;
	}

	public static void setMinSleep(long millis) {
		MIN_SLEEP = millis;
	}

	public static void autoSetMinSleep() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				autoSetMinSleepBlocking();
			}
		});
		t.setDaemon(true);
		t.start();
	}

	public static void autoSetMinSleepBlocking() {
		long millis = checkMinSleep(MIN_SLEEP_DEFAULT_ITERATIONS);
		millis *= 1.5;
		MIN_SLEEP = millis;
		// warmup
		new BusyTimer(1000000, () -> {
			return;
		});
		new BusyTimer(1000000, () -> {
			return;
		});
		new BusyTimer(1000000, () -> {
			return;
		});
	}

	static long[] startTime = new long[100];
	static long[] delay = new long[100];

	public static void main(String[] args) throws InterruptedException {
		autoSetMinSleepBlocking();
		for (int i = 0; i < 100; i++) {
			startTime[i] = System.nanoTime();
			delay[i] = (long) (Math.random() * 5000000 + 1000000);
			final int index = i;
			new BusyTimer(delay[index], () -> System.out.println(System.nanoTime() - startTime[index] - delay[index]));
			Thread.sleep(10);
		}

		Thread.sleep(10000);
	}

}
