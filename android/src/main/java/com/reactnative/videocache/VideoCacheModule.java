package com.reactnative.videocache;

import android.content.Context;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.FileNameGenerator;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import java.lang.reflect.Constructor;


public class VideoCacheModule extends ReactContextBaseJavaModule {

    private double MAX_CACHE_SIZE = 1024*1024*512; // 512 MB
    private FileNameGenerator fileNameGenerator = null;
    private double maxCacheSize = MAX_CACHE_SIZE;

    private final ReactApplicationContext reactContext;
    private HttpProxyCacheServer proxy;

    public VideoCacheModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "VideoCache";
    }

    @ReactMethod
    public void setConfig(ReadableMap options) {

        if(options.hasKey("fileNameGeneratorClassName")) {
                FileNameGenerator obj = this.getFileNameGenerator(options.getString("fileNameGeneratorClassName"));
                if(obj != null){
                    this.fileNameGenerator = obj;
                }
            }

        if(options.hasKey("maxCacheSize")){
            this.maxCacheSize = options.getDouble("maxCacheSize");
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String convert(String url) {
        if (this.proxy == null) {
            this.proxy = this.getProxy(this.reactContext);
        }
        return this.proxy.getProxyUrl(url);
    }

    @ReactMethod
    public void convertAsync(String url, Promise promise) {
        if (this.proxy == null) {
            this.proxy = this.getProxy(this.reactContext);
        }
        promise.resolve(this.proxy.getProxyUrl(url));
    }

    public HttpProxyCacheServer getProxy(Context context) {
        return this.proxy == null ? (this.proxy = this.newProxy(context)) : this.proxy;
    }

    private HttpProxyCacheServer newProxy(Context context) {
      System.out.println("MAX CACHE SIZE "+this.maxCacheSize);

      HttpProxyCacheServer.Builder proxyBuilder = new HttpProxyCacheServer.Builder(context)
            .maxCacheSize((long)this.maxCacheSize);
        if(this.fileNameGenerator != null){
            proxyBuilder.fileNameGenerator(this.fileNameGenerator);
        }
        
      return proxyBuilder.build();
    } 

    private FileNameGenerator getFileNameGenerator(String classFullName){
      try{
        Class clazz = Class.forName(classFullName);
        Constructor<?> constructor = clazz.getConstructors()[0];
        Object obj = constructor.newInstance();
        if(obj instanceof FileNameGenerator) {
            return (FileNameGenerator)obj;
        } 
      }catch(Exception e){
          e.printStackTrace();
          return null;
      }
      return null;
    }
}
