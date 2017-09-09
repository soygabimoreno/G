![alt tag](http://gabrielmorenoibarra.com/images/logo_g.png)

This is an **Android Helper Library**.

It consists in a collection of static methods that perform common functionalities.

# Get started
Just add dependency in your `build.gradle` module (`G` is available in jcenter):
```
repositories {
    jcenter()
}
dependencies {
    compile 'com.gabrielmorenoibarra.g:g:0.6.9'
}
```

# Usage
Simply call any method directly over classes in a static way:

```java
G.metodName();
GJavaTools.metodName();
...
```
## ANDROID
### Classes with all statics methods
- `G` Android Tools.
- `GFile` Utilities to deal with files.
- `GGraphics` Static utilities related with graphic side.
- `GLog` Static Log Utilities.
- `GMedia` Static Media Utilities.
- `GPermission` This class contains static methods that use permissions.

### Utils
- `GCameraPreview` Camera preview management.
- `GLocation` Location management.
- `GOnClickAndDoubleClickListener` Touch Listener.
- `GPreLoadWebView` Custom `WebView` for pre-loading management.
- `GShowHideKeyboard` Utility to show or hide the soft keyboard.

## JAVA
### Classes with all statics methods
- `GArray` Array tools.
- `GEmoji` Container with strings that will be parsed as emojis.
- `GJavaTools` Generic Java utilities.
- `GProcessing` Audio Processing Tools.
- `GResampling` Resampling tools: decimate, interpolate.
- `GValidations` Login tools.

### Utils
- `GCrypt` Utility for encrypting/decrypting messages.
- `GISO` Tool for getting the country ISO from country code and vice versa.
- `GLastModified` Obtain last modified date (in ms) of a remote file.
- `GTicToc` Utility to measure times between lines of code during execution.

# Questions and Suggestions
[gabrielmorenoibarra@gmail.com](mailto:gabrielmorenoibarra@gmail.com)

# License
    Copyright 2016-2017 Gabriel Moreno.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
