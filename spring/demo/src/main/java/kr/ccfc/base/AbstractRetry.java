package kr.ccfc.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 重试方法基类
 *
 * @author taojiacheng
 */
@Slf4j
public abstract class AbstractRetry<T> {


    private static final int DEFAULT_RETRY_TIME = 1;
    private int retryTime = DEFAULT_RETRY_TIME;
    private int sleepTime = 0;

    public int getSleepTime() {
        return sleepTime;
    }

    public AbstractRetry<T> setSleepTime(int sleepTime) {
        if (sleepTime < 0) {
            throw new IllegalArgumentException("다시 시도 회수가 0보다 많아야합니다");
        }
        this.sleepTime = sleepTime;
        return this;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public AbstractRetry<T> setRetryTime(int retryTime) {
        if (retryTime <= 0) {
            throw new IllegalArgumentException("다시 시도 회수가 0보다 많아야합니다");
        }
        this.retryTime = retryTime;
        return this;
    }

    /**
     * 重试操作
     */
    protected abstract T retry() throws Exception;

    public T execute() throws InterruptedException {
        for (int i = 0; i < retryTime; i++) {
            try {
                return retry();
            } catch (Exception e) {
                log.error("이상발생，다시 시도합니다" + e.getMessage(), e);
                Thread.sleep(sleepTime);
            }
        }
        return null;
    }


    /**
     * 异步重试
     */
    public T asyncExecute(ExecutorService executorService) throws ExecutionException, InterruptedException {
        if (executorService == null) {
            throw new IllegalArgumentException("프로세서 지정하십시오");
        }
        Future<T> submit = executorService.submit(this::execute);
        return submit.get();
    }

}
