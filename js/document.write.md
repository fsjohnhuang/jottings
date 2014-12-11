`document.open`,新建一个浏览器输出流并清空当前页面的所有内容。
`document.write`,用于向已有的浏览器输出流写入数据，若浏览器输出流已关闭则会自动新建一个并清空当前页面的所有内容。<Br/>
`document.open`,关闭当球浏览器的输出流。<br/>
页面加载完成后(触发`window.onload事件`后)，浏览器的输出流会自动关闭。因此此时调用`document.write`则会清空当前页面的内容。<Br/>
