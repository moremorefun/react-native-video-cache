# react-native-video-cache

Forked from https://github.com/zhigang1992/react-native-video-cache

Boost performance on online video loading and caching

Use following libraries to do the heavy lifting.

- iOS: https://github.com/ChangbaDevs/KTVHTTPCache
- Android: https://github.com/danikula/AndroidVideoCache

## Getting started

`$ yarn add https://github.com/vino4all/react-native-video-cache.git`

### Mostly automatic installation

`$ react-native link https://github.com/vino4all/react-native-video-cache.git`

## Usage

```javascript
import convertToProxyURL from 'react-native-video-cache';
...
<Video source={{uri: convertToProxyURL(originalURL)}} />
```

## Set Options (Android Only)
Add this java file to the main application `<project-root>/android/src/main/java/<package>/MyFileNameGenerator.java`

```java
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

```

```javascript
import convertToProxyURL, {setConfig} from 'react-native-video-cache';
...

const maxCacheSize = 1024 * 1024 * 1024; // 1GB
const fileNameGeneratorClassName =
    'com.videocacheproject.MyFileNameGenerator';
  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  setConfig({fileNameGeneratorClassName, maxCacheSize});

...
<Video source={{uri: convertToProxyURL(originalURL)}} />
```

### Gradle dependency
Add this dependency to the `dependencies` section of `app/build.gradle` file.
```groovy
implementation 'com.danikula:videocache:2.7.1'
```
Check https://github.com/danikula/AndroidVideoCache for the latest version