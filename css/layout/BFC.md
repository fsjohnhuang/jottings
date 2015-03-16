# [BFC](http://www.w3.org/TR/CSS21/visuren.html#block-formatting)(Block Formatting Context���鼶��ʽ������)
CSS2.1�ĸ��CSS3�������Ķ���Ϊ[flow root](http://www.w3.org/TR/css3-box/#block-level0)��<br/>
IE6��7��֧��BFC�����ǲ���hasLayout��<br/>

## ��Ϊ����
### 1. �����ڵĿ鼶Ԫ�ؾ������˸��Ե�BFCʱ�������߲�λ��ͬһ��BFC���������ǵ�֮��Ĵ�ֱ��߾ࣨmargin-top��margin-bottom�����ᷢ�����ӣ�[Collapsing margins](http://www.w3.org/TR/CSS21/box.html#collapsing-margins)��������ᷢ�����ӡ�<br/>
**����**<br/>
````
<style type="text/css">
  .o{
    float: left;
  }
  .i{
    width: 100px;
    height: 50px;
  }
  .i1{
    border: solid 10px red;
    margin-bottom: 20px;
  }
  .i2{
    border: solid 10px blue;
    margin-top: 20px;
  }
</style>
<div class="i i1">
</div>
<div class="i i2">
</div>
<br/>
<br/>
<br/>
<div class="o">
  <div class="i i1">
  </div>
  <div class="i i2">
  </div>
</div>
````
![](collapsedmargin.png)<br/>
**������**<br/>
````
<style type="text/css">
  .o{
    float: left;
    width: 120px;
  }
  .i{
    width: 100px;
    height: 50px;
    float: left;
  }
  .i1{
    border: solid 10px red;
    margin-bottom: 20px;
  }
  .i2{
    border: solid 10px blue;
    margin-top: 20px;
  }
</style>
<div class="o">
  <div class="i i1">
  </div>
  <div class="i i2">
  </div>
</div>
````
![](noncollapsedmargin.png)<br/>

### 2. ӵ��BFC��Ԫ�أ���߿�������Ԫ�ص���߾��ص���
����ͨ�����޷���ʾBFC����һ���ԣ�Ҫͨ��������λ������ʾ��
````
<style type="text/css">
  .o{
    border: solid 5px blue;
  }
  .without-bfc{
  }
  .with-bfc{
    float: left;
  }
  .i{
    float: left;
    width: 100px;
    height: 100px;
    margin: 10px;
    border: solid 5px red;
  }
</style>
<h3>û�д���BFC</h3>
<div class="o without-bfc">
  <div class="i"></div>
</div>
<br clear="all"/>
<h3>����BFC</h3>
<div class="o with-bfc">
  <div class="i"></div>
</div>
````
![](bfc2.png)<br/>

## [BFC�߶ȵļ��㷽ʽ](http://www.w3.org/TR/CSS2/visudet.html#root-height)
��BFC��ֻ��������Ԫ�أ�inline-level��ʱ����BFC�߶�Ϊ�������Ԫ�ص��ϱ߿���׵�����Ԫ�ص��±߿�ľ��롣<br/>
��BFC�ڰ����鼶Ԫ�أ�block-level��ʱ����BFC�߶�Ϊ��Ŀ鼶Ԫ�ص�����߿���׵Ŀ鼶Ԫ�ص�����߿�ľ��롣<br/>
````
<style type="text/css">
 .o{
   border: solid 5px blue;
   margin: 50px;
 }
 .with-bfc{
    float: left;
 }
 .i{
    width: 100px;
    height: 100px;
    border: solid 5px red;
  }
 .i1{
    margin-bottom: -50px;
  }
 .i2{
    margin-top: -50px;
  }
</style>
<div class="o with-bfc">
  <div class="i i1"></div>
</div>
<div class="o with-bfc">
  <div class="i i2"></div>
</div>
````
![](bfcheight.png)<br/>

ע�⣺<br/>
 1. ���Զ�λ��absolutely positioned children������Ԫ���ڼ���BFC�߶�ʱ�ᱻ���ԣ�<br/>
 2. ��Զ�λ��relatively positioned children������Ԫ���ڼ���BFC�߶�ʱ�ᱻ���룬���������ƫ��ֵ��
````
<style type="text/css">
 .o{
   border: solid 5px blue;
   margin: 50px;
 }
 .with-bfc{
    float: left;
 }
 .i{
    width: 100px;
    height: 100px;
    border: solid 5px red;
    float: left;
  }
 .i1{
    position: relative;
    top: -50px;
  }
 .i2{
  }
</style>
<div class="o with-bfc">
  <div class="i i1"></div>
</div>>
````
![](bfcrelativeheight.png)<br/>
3. ������λ����Ԫ���ڼ���BFC�߶�ʱҲ�����롣<br/>

## ����BFC
1. ������ͨ���еĿ鼶Ԫ�أ�ͨ��`overflow����visible�����ֵ��hidden��auto��scroll��`��������<br/>
2. ��Ԫ������Ϊ������λ��`float��Ϊnone`���;��Զ�λ��`position: absolute`��`position: fixed`��ʱ��Ҳ�ᴥ��BFC��<br/>
3. �鼶�����磨`display:inline-block/table-cell/table-caption/flex/inline-flex`����Ϊ����BFC;<br/>
4. fieldsetԪ��Ҳ�ᴥ��BFC��<br/>

### [������](http://www.w3.org/TR/CSS21/tables.html#anonymous-boxes)����table-cell����BFC������

#### CSS table model
����HTML4 table model��û�й涨�ĵ�����Ԥ������table��ص�Ԫ�أ�����ͨ��`display`��ʽ����������table��ʽ�������Ԫ�ء�<br/>
````
table{ display: table; }
tr{ display: table-row; }
thead{ display: table-header-group; }
tbody{ display: table-row-group; }
tfoot{ display: table-footer-group; }
col{ display: table-column; }
colgroup{ display: table-column-group; }
td, th{ display: table-cell; }
caption{ display: table-caption; }
````
��table���Ԫ���������������Իᱻ���ԡ���Ϊtable���Ԫ�ػ���������㷨������ġ�<br/>
#### Anonymous table objects
��HTML4�ⲻ�����е��ĵ����԰�������table���Ԫ�ء����Զ�����ȱʧ�ı��Ԫ�أ�����Ҫ��`table/inline-table`��`table-row`��`table-cell`����Ԫ�ء�<br/>
�������裺<br/>
1. ��������Ԫ�� 
2. ����ȱʧ����Ԫ��
3. ����ȱʧ�ĸ�Ԫ��

## Formatting Context����ʽ�������ģ�
��CSS2.1�淶��һ�������ʾҳ���е�һ����Ⱦ���򣬲�����һ����Ⱦ������������Ԫ�ؽ���ζ�λ���Լ�������Ԫ�صĹ�ϵ���໥���á�<br/>
CSS2.1�а���BFC��IFC����CSS3�л�������GFC��FFC��<br/>
CSS���ֵĶ���ͻ�����λ��box��ҳ���ж��box��ɡ�����Ԫ�ص����ͺ�display����������box�����͡�block-level box���Բ��뵽BFC�У���inline-level box�ɲ��뵽IFC���С���FC�������������box����Ⱦ����<br/>
