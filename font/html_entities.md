## HTML �ַ�ʵ��(character entities)
 ����HTML��ĳЩ�ַ�����<��>�ȣ���Ԥ���ģ���Ҫ��ȷ����ʾԤ���ַ�������Ҫʹ��**�ַ�ʵ��**�����档<Br/>
 ʵ���ַ������ֱ�ʾ��ʽ��ʵ����(entity name)��ʵ�����(entity number)<br/>
````
// ʵ����
&entity_name;

// ʵ�����
&#entity_number;
````
ʵ�������ڼ��䣬���������һ����ʶ������ʵ���������������������ʶ������ʵ����š�<br/>
ע�⣺ʵ�����Դ�Сд���С�<Br/>
�ַ�ʵ��ͷ���ʵ���<br/>
[http://www.w3school.com.cn/tags/html_ref_entities.html](http://www.w3school.com.cn/tags/html_ref_entities.html)<br/>

### ����
�ַ�ʵ���ַ�Ϊ **ASCIIʵ��**��**����ʵ��**��**�ַ�ʵ��**���֡�
����JS�������ΪASCIIʵ��Ϊһ�࣬������ʵ�塢�ַ�ʵ����Ϊ��һ�ࡣ<br/>
#### ����ASCIIʵ��
````
HTMLElement.innerHTML��HTMLElement.outerHTML��ֻ�ܻ�ȡʵ������ʵ����ţ����޷���ȡ�ַ���
IE9+/FF�����ͨ��HTMLElement.textContent����ȡ�ַ���
IE��ͨ��HTMLElement.innerText����ȡ�ַ���
�����������ͨ����Ԫ�ص�value����ȡ�ַ���(��textarea.value)
````
#### ���ڷ���ʵ�塢�ַ�ʵ��
ֻ�ܻ�ȡ�ַ������޷���ȡʵ������ʵ����š�<br/>

## innerText��textContent
����innerText��textContent�ǻ���innerHTML���ݵĲ�ͬ�����õ��Ľ�������Ҫ���˽�innerHTML�����ݡ�<br/>
innerHTML�У����˻�Է���ʵ����ַ�ʵ����д����⣬������HTMLԴ������ݡ�<br/>
````
<style type="text/css">
 .line3, .line4{
   float: left;
 }
 .line5::after{
   content: "test"
 }
</style>
<div id="view">
  <div>line1</div>
  <div>line2</div><br/>
  <div class="line3">line3</div>
  <div class="line4">line4</div>
  <div style="clear:both;"></div>
  <div class="line5">line5</div>
</div>
````

### innerText
�����֧�֣�IE,Chrome.<br/>
��innerHTML�����ݽ������´���<br/>
1. ��HTML��ǩ���н�����<br/>
2. ��CSS��ʽ���д����ƵĽ�������Ⱦ��<br/>
3. ��ASCII�ַ����д���<br/>
4. �޳���ʽ��Ϣ(��\t��\r��\n��)��<br/>
ע�⣺�������������������ĳ̶��ǲ�ͬ�ġ�<br/>
**IE5.5~8**<br/>
ִ�в���1��2��3��4��������ʾ���£�
````
line1
line2

line3line4
line5
````
��innerText���Ϊ��
````
line1
line2

line3line4line5
````
�ڽ���CSS��ʽ��Ⱦʱ����֧��αԪ�غ�clear:both�ȡ�
**IE9~11**<br/>
ִ�в���1��2��3��4��������ʾ���£�
````
line1
line2

line3line4
line5test
````
��innerText���Ϊ��
````
line1

line2


line3

line4



line5
````
�ڽ���CSS��ʽ��Ⱦʱ��ֻ��ʹ��Ԫ�ص�Ĭ����ʽ��<br/>
**Chrome**<br/>
ִ�в���1��2��3��4��������ʾ���£�
````
line1
line2

line3line4
line5test
````
��innerText���Ϊ��
````
line1
line2

line3line4
line5
````
�ڽ���CSS��ʽ��Ⱦʱ����֧��αԪ�صȡ�

### textContent
�����֧�֣�IE9+,FireFox,Chrome.
��textContent�����ݽ������´���<br/>
1. ��HTML��ǩ�����޳���
2. ��ASCII�ַ����д���
**���������**<br/>
������ʾ:
````
line1
line2

line3line4
line5test
````
��textSource���Ϊ��
````

  line1
  line2
  line3
  line4

  line5

````

## ����ֵʱ��innerHTML��innerText��textContent����Ϊ�ص�
���߾�Ϊ�ɶ���д��
````
<div id="target"></div>
<script type="text/javascript">
  var getDesc = function(){ return Object.getOwnPropertyDescriptor.apply(Object, arguments) }
    , get = function(){ return document.getElementById.apply(docuemnt, arguments) }
  var target = get('target');
  console.log(getDesc(target, 'innerHTML'))   // Object {value: "", writable: true, enumerable: true, configurable: true}
  console.log(getDesc(target, 'innerText'))   // Object {value: "", writable: true, enumerable: true, configurable: true}
  console.log(getDesc(target, 'textContent')) // Object {value: "", writable: true, enumerable: true, configurable: true}
</script>
````
1. ����innerHTMLʱ�����ֵ���������жϺ��ٸ�ֵ��α���룩
````
var val
function setInnerHTML(newVal){
  // 1. �ж��Ƿ�Ϊ�ַ�ʵ�壬����ֱ�Ӹ�ֵ
  // 2. ������ת��Ϊ�ַ�ʵ���Ÿ�ֵ
  if (isEntity(newVal)){
    val = newVal
    return
  }
     
  val = convert2Entity(newVal)
}
````
2. ����innerText��textContentʱ�������ж�ֱ��ת��Ϊ�ַ�ʵ���ֵ��α���룩
````
var val
setInnerText = setTextContent = function (newVal){
  // ת��Ϊ�ַ�ʵ���ֵ
  val = convert2Entity(newVal)
}
````

## ��Ԫ�ص�value, innerHTML, innerText, textContent
### textarea

FireFox
1. innerHTML����������Ч����Ӱ��textContent��������ͨ��value����ֵǰ����Ӱ��value��ͨ��value���ú���value��textCotent/innerHTML���޹�ϵ���ˡ�
2. textContent����������Ч����Ӱ��innerHTML��������ͨ��value����ֵǰ����Ӱ��value��ͨ��value���ú���value��textCotent/innerHTML���޹�ϵ���ˡ�
3. value����������Ч��

Chrome
1. innerText����������Ч����Ӱ��innerHTML,textContent��������ͨ��value����ֵǰ����Ӱ��value��ͨ��value���ú���value��textCotent/innerHTML���޹�ϵ���ˡ�
��ȡֵʱ����innerText��Զ���ؿ��ַ�����
2. innerHTML����������Ч����Ӱ��textContent��������ͨ��value����ֵǰ����Ӱ��value��ͨ��value���ú���value��textCotent/innerHTML���޹�ϵ���ˡ�
3. textContent����������Ч����Ӱ��innerHTML��������ͨ��value����ֵǰ����Ӱ��value��ͨ��value���ú���value��textCotent/innerHTML���޹�ϵ���ˡ�
4. value����������Ч��

IE9~11
innerHTML��value��innerText,textContent������������Ч��һֱ���໥��Ӧ��

IE5.5~8
innerHTML��value��innerText������������Ч��һֱ���໥��Ӧ��


### intput[type="text"]
Chrome
1. ��ȻinnerText�ǿ�д����ʵ��д��ʱ�ᱨ��
````
<input type="text" id="target">
<script type="text/javascript">
  var getDesc = function(){ return Object.getOwnPropertyDescriptor.apply(Object, arguments) }
    , get = function(){ return document.getElementById.apply(docuemnt, arguments) }
  var target = get('target');
  console.log(getDesc(target, 'innerText'))   // Object {value: "", writable: true, enumerable: true, configurable: true}
  target.innerText = "1" // NoModificationAllowedError: Failed to set the 'innerText' property on 'HTMLElement': The 'input' element does not support text insertion.
</script>
````

2. innerHTML���ò��ᱨ����������Ч�����ֿ��ַ�����

3. textContent������������Ч��������Ӱ��value,innerHTML��innerText

4. value�������ã�������Ӱ��textContent,innerHTML��innerText

IE5.5~8
1. innerText��������Ч����Ӱ��value�����޷��ٴ�ͨ��innerText����ȡ�ղ����õ�ֵ��innerText��ֵʼ��Ϊ���ַ�����innerText��ֵ�ᱻֱ��ת���ַ�ʵ���ٸ�ֵ��

2. ��ȻinnerHTML�ǿ�д����ʵ��д��ʱ�ᱨ����ȡʱ��Զ�ǿ��ַ���
````
<input type="text" id="target">
<script type="text/javascript">
  var getDesc = function(){ return Object.getOwnPropertyDescriptor.apply(Object, arguments) }
    , get = function(){ return document.getElementById.apply(docuemnt, arguments) }
  var target = get('target');
  console.log(getDesc(target, 'innerHTML'))   // Object {value: "", writable: true, enumerable: true, configurable: true}
  target.innerText = "1" // δ֪������ʱ����
</script>
````

3. value�������ã�������Ӱ��innerHTML��innerText.

IE9+
1. innerText�����ã��һ�Ӱ��value������Ӱ��innerHTML��textContent��innerTextȡֵ��innerHTML������
2. innerHTML����������Ч��Ӱ��innerText��textContent����Ӱ��value��
3. textContent����������Ч����Ӱ��innerHTML��ȡֵʹ��innerHTML��ֵ������ɡ�
4. value����������Ч������Ӱ��innerHTML,innerText��textContent��

FireFox
1. innerHTML����������Ч��Ӱ���textContent����Ӱ��value��
2. textContent����������Ч����Ӱ��innerHTML��ȡֵʹ��innerHTML��ֵ������ɡ�
3. value����������Ч������Ӱ��innerHTML��textContent��

