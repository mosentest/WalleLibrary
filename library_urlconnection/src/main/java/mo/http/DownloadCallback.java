package mo.http;

public interface DownloadCallback {

	public void onDownloaded();
	
	public void onProgress(float percent);
	
	public void onFailure(int code, String errMsg);
}
