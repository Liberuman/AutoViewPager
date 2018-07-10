### 提供的功能

- 自带指示器，可通过属性设置位置，样式，大小，间距；
- 可自动循环播放；
- 可设置Page宽度,间距；
- 提供了丰富的切换动画(缩放，渐变，旋转，反转，层叠，翻书)；
- 支持Fling操作；

详细介绍参考：[自动播放的ViewPager](http://tinycoder.cc/2018/04/28/%E7%82%AB%E9%85%B7%E5%A5%BD%E7%94%A8%E7%9A%84ViewPager/)

### 添加依赖

在项目的build.gradle中添加：

    allprojects {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }

在APP模块下的build.gradle中添加依赖：

	dependencies {
	        implementation 'com.github.JuHonggang:Autoviewpager:v0.1'
	}

### License

	Copyright (c) 2018 Freeman

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
	

