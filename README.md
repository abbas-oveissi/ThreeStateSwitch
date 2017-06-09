[![Release](https://jitpack.io/v/abbas-oveissi/ThreeStateSwitch.svg)](https://jitpack.io/#abbas-oveissi/ThreeStateSwitch)

## ThreeStateSwitch
ThreeStateSwitch is an android library

**Project Setup and Dependencies**
- MinSDK 14

**Highlights**
- supports showing text on both sides of view
- supports customization in color or size


# Preview

![](https://raw.githubusercontent.com/abbas-oveissi/ThreeStateSwitch/master/assets/demo.gif)

# Setup
## 1. Provide the gradle dependency
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
Add the dependency:
```gradle
dependencies {
	compile 'com.github.abbas-oveissi:threestateswitch:0.8.1'
}
```

## 2. Add your code
add ThreeStateSwitch to UI layout
```xml
<ir.oveissi.threestateswitch.ThreeStateSwitch
    android:id="@+id/threeState"
    android:layout_width="200dp"
    android:layout_height="wrap_content"
    app:background_selected_color="#5bb434"
    app:background_normal_color="#bfbfbf"
    app:text_left="چپ"
    app:text_right="راست"
    app:text_selected_color="#5bb434"
    app:text_normal_color="#646464"
    app:text_normal_size="16sp"
    app:text_selected_size="20sp"/>
```

## attributes

| Name | Type | Default | Description |
|:----:|:----:|:-------:|:-----------:|
|background_selected_color|Color|#5bb434|  |
|background_normal_color|Color|#bfbfbf|  |
|text_normal_color|Color|#646464|  |
|text_selected_color|Color|#5bb434|  |
|text_left|String|چپ|  |
|text_right|String|راست|  |
|text_normal_size|Dp or Sp|16sp|  |
|text_selected_size|Dp or Sp|16sp|  |


# Developed By

* Abbas Oveissi - [@abbas_oveissi](https://twitter.com/abbas_oveissi)


# License

    Copyright 2017 Abbas Oveissi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.