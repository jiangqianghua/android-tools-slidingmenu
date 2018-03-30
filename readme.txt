1. 仿qq5.0 侧滑菜单实现

2. 自定义view
（1）onMeasure
决定内部view（子view）的宽和高，以及自己的宽和高
（2）onLayout
决定子view的放置位置
(3)onTouchEvent
实现触摸效果

自定义属性：允许用户设置菜单离屏幕有边距的属性
1. 在xml文件，values/attr.xml
2. 在布局文件中进行使用，注意xmlns
3. 在构造方法中，（3个参数构造方法） 获取我们设置的值
