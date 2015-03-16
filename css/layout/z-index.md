# Layered presentation
## z-index
CSS2.1�д�3��ά������λÿ��box������ˮƽ�ʹ�ֱά���⣬����һ��������z-axis�ѵ���ά�ȡ�����z-axis��Ϊ���stacking context����z-index��ֵ��Ϊ����ڵ�ǰstacking context�����ֵ��<br/>
��Ԫ��(html/body)Ĭ�ϻᴴ��һ��root stacking context�����¿ɰ������stacking context����ÿ��stacking contextҲ���԰���0~N��stacking context��<br/>

## box�ѵ�����
1. ��ͬһ��stacking context�£�z-index��ͬ������box��Ӧ��Ԫ����document tree�µ�˳�򣬺���λ��ǰ�ߵ����棨back-to-front����
````
<!-- ��������£�d2������d1�ĺ��棬���d2��z-axis��λ��d1������ -->
<div id="d1">
  <div id="d2">
  </div>
</div>

<div id="d1">
</div>
<div id="d2">
</div>
````
2. ��ͬһ��stacking context�£�z-index��ͬ����ֵ��λ�����档
````
<!-- d1��z-indexΪ12����d2��z-indexΪ0������d1��d2������ -->
<div id="d1" style="position:relative;z-index: 12;">
</div>
<div id="d2" style="z-index: 0;margin-top:-20px;">
</div>
````
3. �ڲ�ͬ��stacking context�£�������box����ֱ������boxλ��ͬһ��stacking context�£��ڱȽ����ߵ�z-index����ֵ��Ȼ����ѭ��ֵ��λ�����档
````
<div>
  <div id="d1" style="position:relative; z-index:10;">
	<div id="d4" style="background:red; width:100px; height:100px;position:relative; z-index:9999;">d3</div> 
  </div>
  <div id="d2" style="background:blue; width:50px; height:50px; position:relative; top: -120px; z-index:9;">d2</div>
  <div id="d3" style="background:green; width:50px; height:50px; position:relative; top: -80px; position:relative; z-index:11;">d3</div>
</div>
````
!()[zIndex2.png]<br/>
4. �ڲ�ͬ��stacking context�£���stacking context�е�boxΪλ�ڸ�stacking context�е�box֮�ϡ�
````
<div style="background:blue; width:100px; height:100px; position:relative; z-index:10;">
  <div style="background:red; width:50px; height:50px; position:relative; z-index:-10;"></div>
</div>
````
!()[zIndex3.png]

## ����non-positioned box
z-index����Ϊ0����ʹ���óɹ�����ʵ����ȾʱҲ��0��
````
<div id="test1" style="z-index:12;width:100px;height:100px;background:red;"></div>
<div id="test2" style="z-index:10;width:100px;height:100px;background:blue;margin-top:-20px;"></div>

<script type="text/javascript">
  window.getComputedStyle = window.getComputedStyle || function(el){
    return  el.currentStyle
  }
  var zIndex = window.getComputedStyle(document.getElementById('test1'))['zIndex']
  console.log(zIndex) // ��ʾ12
</script>
````
![](zIndex1.png)

## ����positioned box
z-index��������ʵ����Ⱦʱ�������õ�z-indexֵ�������ѵ���λ�á���z-index����0ʱ����û��ڸ�box�ڴ���һ���µ�stacking context�����µ�stacking context�ĸ�stacking context����box������stacking context��

## ������
IE6��7�в���positioned box+z-index��Ϊ0�Ŵ���stacking context������positioned box�ͻᴴ��stacking context��
````
<style>
    .parent{width:200px; height:200px; padding:10px;}
    .sub{text-align:right; font:15px Verdana;width:100px; height:100px;}
    .lt50{left:50px;top:50px;}
</style>
 
<div style="position:absolute; background:lightgrey;" class="parent">
  <div style="position:absolute;z-index:20;background:darkgray;" class="sub">20</div>
  <div style="position:absolute;z-index:10;background:dimgray;" class="sub lt50">10</div>
</div>
 
<div style="position:absolute;left:80px;top:80px;background:black;" class="parent">
  <div style="position:absolute;z-index:2;background:darkgray;" class="sub">2</div>
  <div style="position:absolute;z-index:1;background:dimgray;" class="sub lt50">1</div>
</div>
````
����W3C��׼�ģ�<br/>
![](IE8+.png)<br/>
IE6��7�ģ�<br/>
![](zIndexIE67.png)<br/>

