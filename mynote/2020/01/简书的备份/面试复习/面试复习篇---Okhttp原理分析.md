okhttp是支持http1.0，http1.1，http2.0，spdy协议

#okhttp发起请求
反编译okhttp-3.4.1.jar版本代码看到http2和http1
```
   public HttpStream newStream(OkHttpClient client, boolean doExtensiveHealthChecks) {
        int connectTimeout = client.connectTimeoutMillis();
        int readTimeout = client.readTimeoutMillis();
        int writeTimeout = client.writeTimeoutMillis();
        boolean connectionRetryEnabled = client.retryOnConnectionFailure();

        try {
            RealConnection resultConnection = this.findHealthyConnection(connectTimeout, readTimeout, writeTimeout, connectionRetryEnabled, doExtensiveHealthChecks);
            Object resultStream;
            if(resultConnection.framedConnection != null) {
                resultStream = new Http2xStream(client, this, resultConnection.framedConnection);
            } else {
                resultConnection.socket().setSoTimeout(readTimeout);
                resultConnection.source.timeout().timeout((long)readTimeout, TimeUnit.MILLISECONDS);
                resultConnection.sink.timeout().timeout((long)writeTimeout, TimeUnit.MILLISECONDS);
                resultStream = new Http1xStream(client, this, resultConnection.source, resultConnection.sink);
            }

            ConnectionPool var9 = this.connectionPool;
            synchronized(this.connectionPool) {
                this.stream = (HttpStream)resultStream;
                return (HttpStream)resultStream;
            }
        } catch (IOException var12) {
            throw new RouteException(var12);
        }
    }
```
