![alt tag](http://gabrielmorenoibarra.com/images/logo_g.png)

This is an **Android Helper Library**.

It consists in a collection of static methods that perform common functionalities.

## Get started
Just add dependency in your `build.gradle` module (`G` is available in jcenter):
```
repositories {
    jcenter()
}
dependencies {
    compile 'com.gabrielmorenoibarra.g:g:0.1.0'
}
```

## Use
Simply call any method directly over `G` or `GJavaTools` classes in a static way:

```java
G.metodName();
```

Or use easily any of the other classes included:

- `GCrypt` Utility for encrypting/decrypting messages.
- `GEmoji` Container with strings that will be parsed as emojis.
- `GISO` Tool for getting the country ISO from country code and vice versa.
- `GLastModified` Obtain last modified date (in ms) of a remote file.
- `GProcessing` Audio Processing Tools.
- `GTicToc` Utility to measure times between lines of code during execution.

## Questions and Suggestions
[gabrielmorenoibarra@gmail.com](mailto:gabrielmorenoibarra@gmail.com)

## License
    Copyright 2016 Gabriel Moreno.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
