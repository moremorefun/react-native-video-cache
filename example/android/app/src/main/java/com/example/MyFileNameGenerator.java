package com.videocacheproject;

import android.net.Uri;
import com.danikula.videocache.file.FileNameGenerator;

  public class MyFileNameGenerator implements FileNameGenerator {

    // Url may contain mutable parts (parameter 'sessionToken') and stable video's id (parameter 'videoId').
    // e. g. http://example.com/videos/123?videoId=abcqaz&sessionToken=xyz987
    public String generate(String url) {

        Uri uri = Uri.parse(url);
        String[] segments = uri.getPath().split("/");
        String pathName = segments[segments.length-1];  // 123

        String videoId = uri.getQueryParameter("videoId");  // abcqaz
        return pathName+"_"+videoId + ".mp4";   // Add your own implementation
    }
}
