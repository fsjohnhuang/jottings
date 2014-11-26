#Filters
## IE Filters
仅IE4~IE9支持<Br/>
官网：http://msdn.microsoft.com/zh-cn/library/ms532853(v=VS.85).aspx<br/>
CSS中的使用格式：`filter:滤镜名([属性=属性值[,属性=属性值]*]?)`<Br/>
滤镜分为两种：filters和transitions<Br/>
**Filters**<br/>
filters细分为:Procedural Surfaces和Static Filters<br/>
1. Procedural Surfaces，是一层位于元素背景和内容之间的颜色层，该颜色层中每像素的RGB值和alpha值均为动态计算。而元素背景和内容均不受影响。具体滤镜如下：<Br/>
````
AlphaImageLoader
Gradient
````
2. Static Filtes, 改变元素整体的输出效果，而且为静态效果。具体滤镜如下：<br/>
````
Alph
BasicImage
Blur
Chroma
Compositor
DropShadow
Emboss
Engrave
Glow
ICMFilter
Light
MaskFilter
MotionBlur
Shadow
Wave
````

**Transitions**<br/>
动态改变元素整体输出效果。具体滤镜如下：<Br/>
````
Barn
Blinds
CheckerBoard
Fade
GradientWipe
Inset
Iris
Pixelate
RadialWipe
RandomBars
RandomDissolve
Slide
Spiral
Stretch
Strips
Wheel
Zigzag
````

