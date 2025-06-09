package test.com.dmeta;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class EfficientTimers {

	
	
	
	    // 타이머 태스크를 저장할 DelayQueue
	    private final DelayQueue<TimerTask> queue = new DelayQueue<>();
	    // 실행 중인 타이머 수
	    private final AtomicInteger activeTimers = new AtomicInteger(0);
	    // 작업자 스레드
	    private final Thread worker;
	    // 실행 상태
	    private volatile boolean running = true;
	    public EfficientTimers() {
	        // 단일 작업자 스레드 생성
	        worker = new Thread(() -> {
	            while (running) {
	                try {
	                    // 큐에서 다음 만료될 작업 가져오기
	                    TimerTask task = queue.take();
	                    if (task != null) {
	                        // 태스크 실행
	                        task.executeTask();
	                        // 반복 타이머인 경우 다시 큐에 추가
	                        if (task.isRepeating()) {
	                            task.scheduleNextExecution();
	                            queue.put(task);
	                        } else {
	                            activeTimers.decrementAndGet();
	                        }
	                    }
	                } catch (InterruptedException e) {
	                    // 인터럽트 처리
	                    if (!running) {
	                        break;
	                    }
	                } catch (Exception e) {
	                    System.err.println("타이머 작업 실행 중 오류: " + e.getMessage());
	                }
	            }
	            
	        });
	        worker.setDaemon(true);
	        worker.start();
	    }
	    /**
	     * 한 번만 실행되는 타이머 생성
	     *
	     * @param delayMs 지연 시간(밀리초)
	     * @param action  실행할 작업
	     * @return 타이머 ID
	     */
	    public int scheduleOnce(long delayMs, Consumer<Integer> action) {
	        int timerId = activeTimers.incrementAndGet();
	        TimerTask task = new TimerTask(timerId, delayMs, 0, action);
	        queue.put(task);
	        return timerId;
	    }
	    /**
	     * 반복 실행되는 타이머 생성
	     *
	     * @param delayMs      초기 지연 시간(밀리초)
	     * @param intervalMs   반복 간격(밀리초)
	     * @param action       실행할 작업
	     * @return 타이머 ID
	     */
	    public int scheduleRepeating(long delayMs, long intervalMs, Consumer<Integer> action) {
	        int timerId = activeTimers.incrementAndGet();
	        TimerTask task = new TimerTask(timerId, delayMs, intervalMs, action);
	        queue.put(task);
	        return timerId;
	    }
	    /**
	     * 타이머 취소
	     *
	     * @param timerId 취소할 타이머 ID
	     * @return 성공 여부
	     */
	    public boolean cancelTimer(int timerId) {
	        // 큐에서 타이머 찾아 제거
	        boolean removed = queue.removeIf(task -> task.getId() == timerId);
	        if (removed) {
	            activeTimers.decrementAndGet();
	        }
	        return removed;
	    }
	    /**
	     * 현재 활성 타이머 수 반환
	     */
	    public int getActiveTimerCount() {
	        return activeTimers.get();
	    }
	    /**
	     * 모든 리소스 정리
	     */
	    public void shutdown() {
	        running = false;
	        worker.interrupt();
	        queue.clear();
	    }
	    /**
	     * 타이머 작업 클래스
	     */
	    private static class TimerTask implements Delayed {
	        private final int id;
	        private final long intervalMs;
	        private final Consumer<Integer> action;
	        private long nextExecutionTime;
	        public TimerTask(int id, long delayMs, long intervalMs, Consumer<Integer> action) {
	            this.id = id;
	            this.intervalMs = intervalMs;
	            this.action = action;
	            scheduleNextExecution(delayMs);
	        }
	        public int getId() {
	            return id;
	        }
	        public boolean isRepeating() {
	            return intervalMs > 0;
	        }
	        public void scheduleNextExecution() {
	            scheduleNextExecution(intervalMs);
	        }
	        private void scheduleNextExecution(long delay) {
	            this.nextExecutionTime = System.currentTimeMillis() + delay;
	        }
	        public void executeTask() {
	            try {
	                action.accept(id);
	            } catch (Exception e) {
	                System.err.println("타이머 #" + id + " 실행 중 예외 발생: " + e.getMessage());
	            }
	        }
	        @Override
	        public long getDelay(TimeUnit unit) {
	            long diff = nextExecutionTime - System.currentTimeMillis();
	            return unit.convert(diff, TimeUnit.MILLISECONDS);
	        }
	        @Override
	        public int compareTo(Delayed other) {
	            if (other == this) {
	                return 0;
	            }
	            long diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
	            return Long.compare(diff, 0);
	        }
	    }
	    // 사용 예제
	    public static void main(String[] args) throws Exception {
	        EfficientTimers timers = new EfficientTimers();
	        // 3초 후 한 번 실행되는 타이머
	        timers.scheduleOnce(3000, id ->
	            System.out.println("단발성 타이머 #" + id + " 실행됨"));
	        // 1초 후 시작해서 2초마다 실행되는 타이머
	        int repeatingId = timers.scheduleRepeating(1000, 2000, id ->
	            System.out.println("반복 타이머 #" + id + " 실행됨: " + System.currentTimeMillis()));
	        // 여러 타이머를 한번에 만드는 예제
	        for (int i = 0; i < 100; i++) {
	            final int delay = 5000 + (i * 50); // 각 타이머마다 약간씩 다른 지연 시간
	            timers.scheduleOnce(delay, id ->
	                System.out.println("대량 타이머 #" + id + " 실행됨"));
	        }
	        System.out.println("활성 타이머 수: " + timers.getActiveTimerCount());
	        // 10초 후 반복 타이머 취소
	        Thread.sleep(10000);
	        timers.cancelTimer(repeatingId);
	        System.out.println("타이머 #" + repeatingId + " 취소됨");
	        // 20초 후 정리
	        Thread.sleep(10000);
	        System.out.println("남은 타이머 수: " + timers.getActiveTimerCount());
	        timers.shutdown();
	    }
	
}
