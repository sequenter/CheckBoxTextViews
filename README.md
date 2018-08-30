# CheckBoxTextViews
<p>
  <a href="https://jitpack.io/#onemandan/CheckBoxTextViews" rel="nofollow"><img alt="API" src="https://img.shields.io/badge/API-15%2B-brightgreen.svg" style="max-width:100%;"></a>
  <a href="https://jitpack.io/#onemandan/CheckBoxTextViews" rel="nofollow"><img alt="Release" src="https://jitpack.io/v/onemandan/CheckBoxTextViews.svg" style="max-width:100%;"></a>
  <a href="https://raw.githubusercontent.com/onemandan/CheckBoxTextViews/master/LICENSE" rel="nofollow"><img alt="GitHub license" src="https://img.shields.io/badge/license-MIT-blue.svg" style="max-width:100%;"></a>
</p>


Transforms an array of strings into selectable items inside a FlexBox

<img src="https://raw.githubusercontent.com/onemandan/CheckBoxTextViews/master/Sample.jpg" height="430px"/>

## Installation
To get CheckBoxTextViews into your project, add the repository to your build.gradle.


#### Gradle
1. Add the JitPack repository to your projects build.gradle:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

2. Add the dependency to your modules build.gradle :
```gradle
dependencies {
  implementation 'com.github.onemandan:CheckBoxTextViews:0.0.4'
}
```

#### Maven
1. Add the JitPack repository to your build file:
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

2. Add the dependency:
```xml
<dependency>
  <groupId>com.github.onemandan</groupId>
  <artifactId>CheckBoxTextViews</artifactId>
  <version>0.0.4</version>
</dependency>
```

## Updates

#### 0.0.4
- Release

## Usage
``` xml
<uk.co.onemandan.checkboxtextviews.CheckBoxTextViews
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:cbtv_items="@array/LoremIpsum"
        app:cbtv_selected="false"
        app:cbtv_default_background_color="@color/TintBlack10"
        app:cbtv_default_text_color="@color/colorTextPrimary"
        app:cbtv_selected_background_color="?colorAccent"
        app:cbtv_selected_text_color="@color/colorTextAccent"/>
```


## License
```
MIT License

Copyright (c) 2018 Daniel Hart

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
