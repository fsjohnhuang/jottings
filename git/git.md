﻿##git push
1. 基本用法<br/>
`git push <remote repo url> <remote branch>`,表示将当前分支的内容推送到远程仓库的指定分支上<br/>
2. 坑<br/>
  [a].推送时抛如下异常<br/>
>error: The requested URL returned error: 403 while accessing https://github.com/songjinshi/frameworktools.git/info/refsfatal: HTTP request failed<br/>

  解决方法：`git remote set-url origin https://songjinshi@github.com/songjinshi/frameworktools.git`<br/>
3. 设置http代理<br/>
````
git config --global http.proxy http://fsproxy.fs.gmcc.net:8081
````
