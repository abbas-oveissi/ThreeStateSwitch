[![Release](https://jitpack.io/v/abbas-oveissi/ThreeStateSwitch.svg)](https://jitpack.io/#abbas-oveissi/ThreeStateSwitch)

## ThreeStateSwitch

A simple three-state switch view for Android.

**Please Note:** This library has not been fully tested, so use with a little caution, and submit an issue or better, a pull request if you notice any issues at all.

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
	compile 'com.github.abbas-oveissi:threestateswitch:0.8.2'
}
```

## 2. How to use

Add the ThreeStateSwitch in your layout file and customize it the way you like it.
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
You can set a listener for state changes
```java
threeState.setOnChangeListener(new ThreeStateSwitch.OnStateChangeListener() {
    @Override
    public void OnStateChangeListener(int currentState) {
        //current state=  -1  0  1
        Toast.makeText(MainActivity.this, String.valueOf(currentState), Toast.LENGTH_SHORT).show();
    }
});
```
You can set typeface for texts.
```java
threeState.setNormalTextTypeface( );
threeState.setSelectedTextTypeface( );
```
Get the current state. 
```java
//state=  -1  0  1
threeState.getState();
```
## attributes

| Name | Type | Default | Description |
|:----:|:----:|:-------:|:-----------:|
|background_selected_color|Color|#5bb434|  |
|background_normal_color|Color|#bfbfbf|  |
|text_normal_color|Color|#646464|  |
|text_selected_color|Color|#5bb434|  |
|text_left|String||  |
|text_right|String||  |
|text_normal_size|Dp or Sp|16sp|  |
|text_selected_size|Dp or Sp|16sp|  |
|two_state_after_init|Boolean|false|Converts switch to a two state switch after first interaction  |

# Bugs and features

For bugs, feature requests, and discussion please use GitHub Issues.

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