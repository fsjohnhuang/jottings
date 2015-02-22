## 伪类 ##
用于向选择器添加特殊效果
### CSS1伪类 ###
:link
:visited
:hover
:active
### CSS2伪类 ###
:focus,获得焦点的元素（存在<!DOCTYPE html>时才有生效）
:lang(lang属性值),向带有指定lang属性的元素添加样式
```例子
	<style type="text/css">
		q:lang(no){
			quotes: "~" "~"
		}
	</style>
	<q lang="no">with lang property</q>
	<q>without lang property</q>
```
:first-child,向作为第一个子元素的添加样式
```例子
	<style type="text/css">
		q:first-child{
			quotes: "~" "~"
		}
	</style>
	<div>
		<q>first child</q> <!-- 这个匹配了css样式 -->
		<q>second child</q>
	</div>
```
### CSS3伪类 ###

## 伪元素 ##
用于向选择器添加特殊效果
### CSS1伪元素 ###
:first-letter,向元素的内部文本的第一个字母添加样式
可设置的CSS属性：
>font
>color
>background
>word-spacing
>letter-spacing
>text-transform
>text-decoration
>vertical-align(仅float为none时有效)
>line-height
>clear
>margin
>padding
>border
>float

:first-line,向元素的内部文本的第一行添加样式
可设置的CSS属性：
>font
>color
>background
>word-spacing
>letter-spacing
>text-transform
>text-decoration
>vertical-align
>line-height
>clear

### CSS2伪元素 ###
:before，在元素之前添加元素并设置样式
:after，在元素之后添加元素并设置样式
实例：
```css
	/* 在H1元素前插入一副图片 */
	h1:before{
		content:url(./img.png);
	}
```
### CSS3伪元素 ###

## 条件注释 ##
**注意：IE10下有效，IE10及以后的浏览器无法识别***
### 等于 ###
```html
	<!--[if IE 6]>
		........
	<![endif] -->

	<!--[if IE 7]>
		........
	<![endif] -->

	<!--[if IE 8]>
		........
	<![endif] -->

	<!--[if IE 9]>
		........
	<![endif] -->
```
### 大于 ###
```html
	<!--[if gt IE 6]>
		........
	<![endif] -->

	<!--[if gt IE 9]>
		........
	<![endif] -->

	<!--[if gt IE 9]>
		........
	<![endif] -->

	<!--[if gt IE 9]>
		........
	<![endif] -->
```
### 小于 ###
```html
	<!--[if lt IE 6]>
		........
	<![endif] -->

	<!--[if lt IE 9]>
		........
	<![endif] -->

	<!--[if lt IE 9]>
		........
	<![endif] -->

	<!--[if lt IE 9]>
		........
	<![endif] -->
```
### 逻辑非 ###
```html
	<!--[!(IE)]>
		......
	<![endif]-->
```
### 逻辑或 ###
```html
	<!--[if (gt IE 9)|!(IE)]>
		.........
	<![endif]-->
```
