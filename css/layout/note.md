display
block
inline
none
inline-block����Ҫͨ������hasLayout����IE6��7֧��inline-block��
ע�⣺<br/>
1. vertical-align���Ի�Ӱ�쵽inline-blockԪ�أ�����ܻ������ֵ����Ϊtop��
2. ��Ҫ����ÿһ�еĿ��
3. ���HTMLԴ����Ԫ��֮���пո�����֮��������϶��
flex

[column](http://www.quirksmode.org/css/multicolumn.html)
CSS3�����ԣ�����ʵ�����ֵĶ��в��֡�
IE9-��Opera Mini��֧�֡�
````
.three-column{
  padding: 1em;
  -moz-column-count: 3;
  -moz-column-gap: 1em;
  -webkit-column-count: 3;
  -webkit-column-gap: 1em;
  column-count: 3;
  column-gap: 1em;
}
````

flexbox����ģʽ
�Ķ��ܶ࣬���ͨ��[����](https://css-tricks.com/old-flexbox-and-new-flexbox/)ѧϰ��α��һ�������Ƿ���ڣ����⻹��һ��[���±�׼����ϸ����](http://bocoup.com/weblog/dive-into-flexbox/)��
���в��֣�ˮƽ��ֱ���о����ԡ�


ˮƽ����
````
#main1{
  width: 600px;
  margin: 0 auto;
}

/* IE7+֧��max-width */
#main2{
  max-width: 600px;
  margin: 0 auto;
}
````

box-sizing: border-box.
IE8+֧�֡�


position
static(Ĭ��ֵ)����ʾԪ�ز��ᱻ��λ(positioned)������Ϊ����ֵ���ʾ���ᱻ��λ(positioned)
relative
fixed������Ӵ���λ���ƶ��������fixed��֧�ֶȺܲ[�������](http://bradfrost.com/blog/mobile/fixed-position/)
absolute

float����������Χ��
clear�������
���������clearfix hack
````
.clearfix{
  overflow: auto;
  zoom: 1;
}
````
[�������](http://stackoverflow.com/questions/211383/which-method-of-clearfix-is-best)


��Ӧʽ�����(Responsive Design)����һ������վ��Բ�ͬ��������豸����Ӧ����ͬ��ʾЧ���Ĳ��ԡ�

ý���ѯ
````
@media screen and (min-width:600px) {
  nav {
    float: left;
    width: 25%;
  }
  section {
    margin-left: 25%;
  }
}
@media screen and (max-width:599px) {
  nav li {
    display: inline;
  }
}
````
[meta viewport](https://dev.opera.com/articles/an-introduction-to-meta-viewport-and-viewport/)


�̶����֡����岼��
