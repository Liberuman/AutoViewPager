### 背景

ViewPager是v4包中提供的一个组件，目前大多数的APP主Activity的都采用ViewPager+Fragment进行构建的。作为Fragment的容器可以认为是它最基本的用法，直接设置Adapter即可。其实它还常用于广告位的展示。为了让ViewPager在加载广告时更简单，动画效果更炫酷，对其常用需求进行了封装，从而简化使用过程。

### 提供的功能

- 自带指示器，可通过属性设置位置，样式，大小，间距；
- 可自动循环播放；
- 可设置Page宽度,间距；
- 提供了丰富的切换动画(缩放，渐变，旋转，反转，层叠，翻书)；
- 支持Fling操作；

#### 指示器

在展示轮播广告时，通常会在底部显示圆点指示器，这里为了更好的扩展性，采用了属性的方式，只需要简单的属性设置，即可实现指示器的展示，相关属性如下：

    <!-- 是否显示indicator -->
    <attr name="showIndicator" format="boolean"/>
    <!-- indicator未选中时的样式-->
    <attr name="indicatorNormalIcon" format="reference"/>
    <!-- indicator选中时的样式-->
    <attr name="indicatorSelectedIcon" format="reference"/>
    <!-- indicator的大小-->
    <attr name="indicatorIconSize" format="dimension"/>
    <!-- indicator距离底部或顶部的距离 -->
    <attr name="indicatorLayoutMargin" format="dimension"/>
    <!-- indicator之间的距离 -->
    <attr name="indicatorGap" format="dimension"/>
    <!-- indicator的位置 -->
    <attr name="indicatorGravity">
        <enum name="top" value="1"/>
        <enum name="bottom" value="2"/>
    </attr>
    
#### 自动播放

轮播广播通常需要自动播放，这里通过Handler进行定时播放，支持通过属性控制自动播放和自动播放时间。默认情况下，ViewPager不支持循环播放，这里通过将Adapter的getCount()中返回Integer.MAX_VALUE()，并在instantiateItem进行取余加载数据。核心代码如下：

    @Override
    public int getCount() {
        if (mIsAutoPlay || mLoopPlay) {
            return Integer.MAX_VALUE;
        } else {
            return mItemCount;
        }
    }
    
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return itemView.getView(container, position % mItemCount);
    }
    
相关属性如下：

    <!-- 自动播放时间 -->
    <attr name="autoSwitchTime" format="integer"/>
    <!-- 是否可自动播放 -->
    <attr name="isAutoPlay" format="boolean"/>
    <!-- 是否可循环播放 -->
    <attr name="loopPlay" format="boolean"/>
        
#### 设置Page宽度和间距
默认情况下，ViewPager中Item宽度为：

    itemWidth = viewPager.getWidth() - getPaddingLeft() - getPaddingRight();
    
这样就无法在ViewPager中同时展示多页，其实PagerAdpater中提供了设置Item宽度的方法：

    // 返回Item宽度占ViewPager宽度的百分比
    public float getPageWidth(int position);
所以只需要修改getPageWidth的返回值，即可修改Item的宽度，从而实现在ViewPager的可视区域内展示多个Item的需求，相关的属性如下：

    <!-- Item之间的间距 -->
    <attr name="pagerMargin" format="dimension"/>
    <!-- viewPager左边的Padding -->
    <attr name="pagerPaddingLeft" format="dimension"/>
    <!-- viewPager右边的Padding -->
    <attr name="pagerPaddingRight" format="dimension"/>
    <!-- Item的宽度 -->
    <attr name="pagerWidth" format="dimension"/>
        
#### 提供丰富的切换动画

ViewPager除了可以在OnPageChangeListener的onPageScrolled方法中设置动画，还可通过setPageTransformer来实现ViewPager的切换动画，相比而言，使用setPageTransformer更简单，这里为了简化动画的实现过程，内置了丰富的动画效果，包括：渐变，缩放，旋转，翻书，翻转等。只需要一行代码即可实现需要的切换效果。相关属性如下：

    <attr name="transformer">
        <enum name="fade" value="1"/>
        <enum name="scale" value="2"/>
        <enum name="rotate" value="3"/>
        <enum name="flip" value="4"/>
        <enum name="square" value="5"/>
        <enum name="turntable" value="6"/>
        <enum name="cascading" value="7"/>
    </attr>
    
#### 支持Fling操作

默认情况下，ViewPager在手指抬起的时候，最多只能滑动一页，这里为了支持根据惯性滑动多页，ViewPager的源码进行了修改，只需要设置水平方向的速度阀值，即可实现滑动多页的需求。

    // 设置3000以上
    viewPager.setVelocityLimit(velocityLimit);
    

### 源码地址：
[AutoViewPager](https://github.com/JuHonggang/AutoViewPager)

### 效果展示

![image](https://im3.ezgif.com/tmp/ezgif-3-95e3d271af.webp)

