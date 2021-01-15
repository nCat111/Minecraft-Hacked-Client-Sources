package net.minecraft.src;

public class FileDownloadThread extends Thread
{
    private String urlString = null;
    private IFileDownloadListener listener = null;

    public FileDownloadThread(String urlString, IFileDownloadListener listener)
    {
        this.urlString = urlString;
        this.listener = listener;
    }

    @Override
	public void run()
    {
        try
        {
            byte[] e = HttpUtils.get(this.urlString);
            this.listener.fileDownloadFinished(this.urlString, e, (Throwable)null);
        }
        catch (Exception var2)
        {
            this.listener.fileDownloadFinished(this.urlString, (byte[])null, var2);
        }
    }

    public String getUrlString()
    {
        return this.urlString;
    }

    public IFileDownloadListener getListener()
    {
        return this.listener;
    }
}
