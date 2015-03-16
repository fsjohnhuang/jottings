# @font-face
````
@font-face {
    font-family: 'MicrosoftYaHei';
    src: url('MicrosoftYaHei.eot'); /* IE9 Compat Modes */
    src: url('MicrosoftYaHei.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
             url('MicrosoftYaHei.woff') format('woff'), /* Modern Browsers */
             url('MicrosoftYaHei.ttf')  format('truetype'), /* Safari, Android, iOS */
             url('MicrosoftYaHei.svg#MicrosoftYaHei') format('svg'); /* Legacy iOS */
   }

body{
  font-family: "MicrosoftYaHei";
}
````
**�ŵ㣺**<br/>
 1. ��ʹϵͳû�а�ȫ�����壬Ҳ��ʹ�á�<br/>
**ȱ�㣺**<br/>
 1. ��Ҫ��������⣬��˻������û�������<br/>
 2. ���ػ����ҳ����ӳ١�<br/> 

## �����֧��
IE4���Ѿ�֧���ˣ�<br/>
![](font-face-browers.jpg)<br/>

## �﷨����
````
/* �������� */
@font-face {
      font-family: <YourWebFontName>;
      src: <source> [<format>][,<source> [<format>]]*;
      [font-weight: <weight>];
      [font-style: <style>];
    }
````
`font-family: <YourWebFontName>`���Զ����ֿ����ƣ�һ������Ϊ��������ֿ�������������ʽ��������ͨ�������������ø��ֿ⡣<br/>
`src`����������ļ���·���͸�ʽ��ͨ�����ŷָ��������·���͸�ʽ<br/>
`srouce`������ļ���·���������Ǿ��Ի����URL��<br/>
`format`������ĸ�ʽ����Ҫ���������ʶ��һ�������¼��֡���truetype,opentype,truetype-aat,embedded-opentype,avg�ȡ�<br/>
`font-weight`��`font-style`��֮ǰʹ�õ���һ�µġ�<br/>
���⻹��һ��local(font name)�ֶΣ���ʾ���û�ϵͳ�м������壬ʧ�ܺ�ż���webfont��
````
src: local(font name), url("font_name.ttf");
````

## �����ʽ
����@font-face���ԣ�������������Ǹ����������ʶ��������ʽ������ͬ��
### TrueType��ʽ(.ttf)
 Windows��Mac�ϳ����������ʽ����һ��ԭʼ��ʽ���������û��Ϊ��ҳ�����Ż�������<br/>
 �����֧�֣�<br/>
 IE9+,FireFox3.5+,Chrome4.0+,Safari3+,Opera10+,IOS Mobile Safari4.2+
### OpenType��ʽ(.otf)
 ��TrueTypeΪ������Ҳ��һ��ԭʼ��ʽ�����ṩ����Ĺ��ܡ�<br/>
 �����֧�֣�<br/>
 FireFox3.5+,Chrome4.0+,Safari3.1+,Opera10.0+,IOS Mobile Safari4.2+
### Web Open Font��ʽ(.woff)
 �����ҳ���������Ż��������Web��������Ѹ�ʽ������һ�����ŵ�TrueType/OpenType��ѹ���棬ͬʱ֧��Ԫ���ݰ��ķ��롣<br/>
 �����֧�֣�<br/>
 IE9+, FireFox3.5+, Chrome6+, Safari3.6+,Opera11.1+
### Embedded Open Type��ʽ(.eot)
 IEר�������ʽ�����Դ�TrueType��ʽ�����˸�ʽ���塣
 �����֧�֣�<br/>
 IE4+
### SVG��ʽ(.svg)
 ����SVG������Ⱦ�ĸ�ʽ��
 �����֧�֣�<br/>
 Chrome4+, Safari3.1+, Opera10.0+, IOS Mobile Safari3.2+<br/>
[Paul Irish](http://www.paulirish.com/)д��һ�����ص�@font-face�﷨��ΪBulletproof @font-face������������⡣<br/>
````
 @font-face {
	font-family: 'YourWebFontName';
	src: url('YourWebFontName.eot'); /* IE9 Compat Modes */
	src: url('YourWebFontName.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
             url('YourWebFontName.woff') format('woff'), /* Modern Browsers */
             url('YourWebFontName.ttf')  format('truetype'), /* Safari, Android, iOS */
             url('YourWebFontName.svg#YourWebFontName') format('svg'); /* Legacy iOS */
   }
````

## ��ȡWeb����
### �������嵽���ط�����
(Google Web Fonts)[http://www.google.com/webfonts]<br/>
(Dafont.com)[http://www.dafont.com/]<br/>
���ص�.ttf�ļ���ͨ��(Font Squirrel)[http://www.fontsquirrel.com/tools/webfont-generator]������.woff�������ʽ��<br/>

### ֱ����������
(Webfonts)[http://webfonts.fonts.com/]<br/>
(Typekit)[http://typekit.com/]<br/>
(Kernest)[http://kernest.com/]<br/>
(Niec Web Type)[http://nicewebtype.com/fonts/]<br/>

## ע��
1. �ȶ����ʹ��;<br/>
2. ������������̫����˽����������廹��ʹ��ͼƬ����Ӣ�������������@font-face����ͼƬ;<br/>
3. @font-faceʧЧ�������ֿ�·�������¡�<br/>

## ����
**����1**: FireFox��ʹ��@font-faceʱ����urlΪ���·�����޷���ʾ��<br/>
���þ���·����ɹ���<br/>
����uri schemeΪfile��`file:///`������ô����FireFoxĬ�ϵ�file uri origin����ʮ���ϸ񣬲�ͬ·���ȼ������޷����ʡ��������·����ʧ�ܡ���ͨ��`about:config`������������ý��棬Ȼ��`security.fileuri.strict_origin_policy`����Ϊfalse���ɡ�<br/>
����uri schemeΪhttp��https������Ҫ�޸Ķ�.eot��.ttf��.woff���ļ�����Ӧͷ�м���`Access-Control-Allow-Origin: *`��<br/>

## Font Icon
Webʵ�����ÿ����ĸ�����֡����Ŷ�Ӧһ��Web���š���WebSymbolsRegular<br/>
![](css3-pager-2.png)<br/>
Ȼ��ͺ�ʹ��@font-face�Ĳ�����һ���ġ�<br/>
### ��ѵ� Font Icon����
#### 1. Guifx����
![](webfonticon-2.jpg)
#### 2. websymbols����
#### 3. Font Awesome����
��robmadole��supercodepoet����ʦ��Bootstrap Icon�Ļ����Ͻ�IconͼƬ����font icon��<br/>
![](webfonticon-5.jpg)<br/>

### �Զ���Font Icon
����ʹ�üȶ���Web�������Ҫ����������ⶼ������������ʵ�����õ���Font Icon��ֻ���������ѣ����ͨ���Զ���Font Icon����Ը��Ի��Ҽ�������������<br/>
���ߣ�[Fontomas](http://nodeca.github.com/fontomas/)�ṩ��������������Icon���ֱ���Entypo,IconicFill,IconicStroke,WebSymbolsRegular��<br/>
Fontomas�ṩ����SVG��ʽ���壬���ǿ���ͨ��FontSquirrel��OnlineFontconverter������������ʽ�����塣<br/>

## Ref
http://www.w3cplus.com/content/css3-font-face
http://stackoverflow.com/questions/2856502/css-font-face-not-working-with-firefox-but-working-with-chrome-and-ie
http://www.dynamicdrive.com/forums/showthread.php?63628-font-face-not-working-in-Firefox-5
http://www.w3cplus.com/css3/web-icon-with-font-face
http://www.webhek.com/tag/web-font/
http://www.paulirish.com/