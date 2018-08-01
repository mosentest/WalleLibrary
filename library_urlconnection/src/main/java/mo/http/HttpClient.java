package mo.http;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpClient {

	static String TAG = HttpClient.class.getSimpleName();
	
	//最大同时请求数
	private int maxRequests = 64;
	//每个IP最多请求数
	private int maxRequestsPerHost = 5;
	// 设置连接主机超时时间
	private int connectTimeout = 60 * 1000;
    //设置从主机读取数据超时
	private int readTimeout = 60 * 1000;
	  
	  
	private ExecutorService executorService;
	
	private final Deque<HttpTask> readyAsyncCalls = new ArrayDeque<>();

	  /** Running asynchronous calls. Includes canceled calls that haven't finished yet. */
	private final Deque<HttpTask> runningAsyncCalls = new ArrayDeque<>();

	//异步
	public void download(Request request, String path, DownloadCallback callback){
		//LogUtil.log(TAG,  "download  url=" + request.url);
		HttpDownloader download = new HttpDownloader(this, request, path, callback);
		if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(download) < maxRequestsPerHost) {
			runningAsyncCalls.add(download);
			executorService().execute(download);
		} else {
			readyAsyncCalls.add(download);
		}
	}
	
	
	
	public synchronized ExecutorService executorService() {
	    if (executorService == null) {
	      executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
	          new SynchronousQueue<Runnable>(), new ClientThreadFactory());
	    }
	    return executorService;
	}
	
	private class ClientThreadFactory implements ThreadFactory {    
        private AtomicInteger count = new AtomicInteger(0);    
        @Override    
        public Thread newThread(Runnable r) {    
            Thread t = new Thread(r);    
            String threadName = "mo_httpclient" + count.addAndGet(1);
            //System.out.println(threadName);
            t.setName(threadName);    
            return t;    
        }    
    }    
	
	private int runningCallsForHost(HttpTask call) {
		int result = 0;
	    for (HttpTask c : runningAsyncCalls) {
	      if (c.host().equals(call.host())) result++;
	    }
	    return result;
	}
	
	public void callFinished(HttpTask call){
		synchronized (this) {
		      if (!runningAsyncCalls.remove(call)) throw new AssertionError("Call wasn't in-flight!");
		      promoteCalls();
//		      runningCallsCount = runningCallsCount();
//		      idleCallback = this.idleCallback;
		    }
	}
	
	private void promoteCalls() {
		if (runningAsyncCalls.size() >= maxRequests) return; // Already running max capacity.
		if (readyAsyncCalls.isEmpty()) return; // No ready calls to promote.
		
		for (Iterator<HttpTask> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
			HttpTask call = i.next();
		
			if (runningCallsForHost(call) < maxRequestsPerHost) {
			    i.remove();
			    runningAsyncCalls.add(call);
			    executorService().execute(call);
			}
		
			if (runningAsyncCalls.size() >= maxRequests) return; // Reached max capacity.
		}
	}
	
	public int getConnectTimeout(){
		return connectTimeout;
	}
	
	public int getReadTimeout(){
		return readTimeout;
	}
	
}
