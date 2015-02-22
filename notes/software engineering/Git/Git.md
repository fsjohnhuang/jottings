## 学习资源 ##
[GoGithub](http://www.worldhello.net/gotgithub/ "GoGithub")
[Github通关全攻略](http://fancyoung.com/blog/githug-cheat-sheet/ "Github通关全攻略")
[分支管理策略](http://www.ruanyifeng.com/blog/2012/07/git.html "分支管理策略")
[Git常用命令备忘](http://robbinfan.com/blog/34/git-common-command "如Git一样思考")
[如Git一样思考](http://think-like-a-git.net/ "如Git一样思考")
[图解git最常用命令](http://www.admin10000.com/document/1631.html "图解git最常用命令")
[Git Community Book 中文版](http://gitbook.liuhui998.com/ Git Community Book 中文版)

## 代码托管云 ##
[github.com](github.com "github.com")
[bitbucket.com](bitbucket.com "bitbucket.com")
[git.oschina.net](git.oschina.net "git.oschina.net")

#* From the Top *#
## 特性 ##
1. 只做加法，不做减法——只增加数据，不删除数据（即使表面删除了文件）
2. 分布式代码版本管理系统，没有主从服务器之分，靠规则和约定来协作管理

## 概念 ##
1. 本地仓库组成：Working Directory（工作目录），Index/Stage(缓存区)，History/Head（本地仓库）
2. <a id="file_status">文件状态</a>：untracked（未受监听）、unstage（位于工作目录）、deleted（被删除，位于缓存目录或工作目录）、stage（位于缓存区）、commit(位于本地仓库)

## 基础配置(\*号表示必须配置) ##
`git config -l`：查看配置信息
\*`git config --global user.name <user name>`：配置身份验证信息的用户名
\*`git config --global user.email <email>`：配置身份验证信息的邮箱地址
\*`git config --global receive.denyCurrentBranch ignore`：配置可push到本地repo的分支上，默认是不禁用的
`git config --global alias.<command alias> <command>`：为git shell命令起别名
`git config --global core.editor <editor>`：指定默认文本编辑器，一般指定vim
`git config --global color.ui true`：配置彩色界面
`git config format.pretty oneline`：配置`git log`每条记录占一行
`git config --global alias.lg "log --color --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit --"`：配置一个好用的日志格式

## 帮助文档 ##
`git help <cmd>`：查看命令的帮助文档

## 初始化本地仓库 ##
`git init`：在当前目录初始化本地仓库

## 查看当前分支状态 ##
`git status`

## 查看日志 ##
`git log [filePath]*`：查看当前分支的提交到本地仓库的日志
`git log --pretty=oneline`：查看当前分支的提交到本地仓库的日志，并且每条信息只占一行
`git show [SHA-1]`：显示当前分支最近的提交节点（或指定提交节点）的信息

## 提交文件、回滚文件、删除文件、重命名文件 ##
![提交回滚](./imgs/add_commit.jpg)
![跨区域提交回滚](./imgs/add_commit_immediate.jpg)
1. **文件提交**
   1. *工作目录到缓存区*
	`git add <filePath> [filePath]*`：将工作目录的某文件提交到缓存区;
    `git add -A`：将工作区的文件提交到缓存区
	`git add -p`：进入交互模式（查看变更对比信息后再输入提交与否）
   2. *缓存区到本地仓库*
   	`git commit -m "<log>" [filePath]*`:将缓存区的某文件或所有文件提交到本地仓库，并提交单行日志信息
    `git commit [filePath]*`:将缓存区的某文件或所有文件提交到本地仓库，进入编辑模式提交多行日志信息
	`git commit -m "<log>" --amend [filePath]*`：将缓存区的某文件或所有文件提交到本地仓库，并提交单行日志信息，**注意：选项--amend表示该版本会替换掉本地仓库的最近一个提交版本，所以使用时要小心**
   3. *工作目录到本地仓库*
   	`git commit -a -m "<log>" [filePath]*`:将工作目录的文件提交到本地仓库，并提交单行日志信息
	`git commit -a [filePath]*`:将工作目录的文件提交到本地仓库，进入编辑模式提交多行日志信息
	`git commit -a -v [filePath]*`将工作目录的文件提交到本地仓库，进入编辑模式查看变更对比信息并提交多行日志信息
2. **文件回滚**
   1. *仅回滚[文件状态] [1]（从缓存区到工作目录），不回滚文件内容*
   	`git reset -- <filePath> [filePath]*`：撤销最后一次`git add`或`git rm`操作，仅回滚[文件状态] [1] 但不回滚内容（原理是将当前分支的最近一个本地仓库版本的文件复制到缓存区）
	`git reset -p`:进入交互模式（**显示当前文件与本地仓库中最近版本内容**的变更对比信息，在输入执行与否）
   2. *撤销工作目录文件的操作，回滚到最近一个本地仓库版本*
	`git checkout -- <filePath> [filePath]*`：对工作目录的文件进行撤销操作，回滚到最近一个本地仓库版本
	`git checkout -p`：进入交互模式（对工作目录的文件进行撤销操作，回滚到最近一个本地仓库版本）
   3. *回滚[文件状态] [1]（从缓存区到工作目录）,并撤销工作目录文件的操作，回滚到最近一个本地仓库版本*
   	`git checkout HEAD -- <filePath> [filePath]*`：原理是切换到当前分支的最近一个本地仓库版本上，当在分支合并出现冲突时该命令无法回滚操作
	`git reset --hard`：原理是将当前分支的最近一个本地仓库版本的文件复制到缓存区和工作目录中，当在分支合并出现冲突时该命令只能使用该命令执行回滚操作
   4. *回滚[文件状态] [1]（从缓存区到工作目录）,并撤销工作目录文件的操作，回滚到最近第二个本地仓库版本*
   	`git checkout HEAD~ -- <filePath> [filePath]*`
3. **文件删除**
  1. *git命令执行删除*
  	`git rm <filePath> [filePath]*`：将本地仓库文件的[文件状态][2]修改为deleted且放入缓存区，并对文件执行物理删除
  	`git rm --cache <filePath> [filePath]*`：将本地仓库文件的[文件状态][2]修改为deleted且放入缓存区，而不对文件执行物理删除
  2. *从文件系统执行删除*
  	从文件系统删除文件后，在git shell中通过`git status`可看到该文件已变为deleted状态，且位于工作目录
  3. *提交删除操作*
  	1和2点的操作均未提交到本地仓库，只需按照**提交**章节的命令即可提交删除到本地仓库
  4. *回滚删除操作*
    按照**回滚**章节的命令即可回滚删除操作，即使是通过文件系统执行删除，一样可以回滚
4. **文件重命名**
`git mv <old name> <new name>`

## 变更对比 ##
![变更对比](./imgs/diff.jpg)
  `git diff [filePath]*`：工作目录的文件与缓存区的文件作对比
  `git diff --cached [filePath]*`：本地仓库最近一次提交版本与缓存区的文件作对比
  `git diff HEAD [filePath]*`：本地仓库最近一次提交版本与工作目录的文件作对比
  `git diff <branchName>`：某分支的本地仓库最近一次提交版本与当前工作目录的文件作对比
  `git diff <SHA1>`：某本地仓库提交版本与当前工作目录的文件作对比

[1]: #file_status "stage（位于缓存区）--->unstage（位于工作目录）"
[2]: #file_status "commit(位于本地仓库) -->deleted（位于工作目录）"

## 分支 ##
1. *基本操作*
  `git branch`：分支数量和名称
  `git branch -v`：查看各分支最后的提交信息
  `git branch --merged`：查看已合并到当前分支的分支数量和名称
  `git branch --no-merged`：查看未合并到当前分支的分支数量和名称
  `git checkout [old branch name | tag | SHA-1] -b <"new branch name">`:从当前分支或指定分支创建新分支，并且新分支指向与旧分支一样的提交节点
  `git checkout <branch name>`：切换分支
  `git checkout -d <branch name>`：仅欲删除的分支已合并到当前分支后，才能删除该分支
  `git checkout -D <branch name>`：强行删除分支
2. *合并分支*
  `git merge <branch name | tag | SHA-1>`：将其他分支以fast-forward的方式合并到当前分支。
  `git merge --no-ff <branch name | tag | SHA-1>`：将其他分支以非fast-forward的方式合并到当前分支。
**什么是fast-forward**
  若当前分支的提交版本是被合并分支提交版本的祖父节点，那么就会将当前分支直接指向被合并分支的提交版本节，而不会为合并后结果生成新的提交版本节点
![fast-forward](./imgs/fast-forward.jpg "fast-forward")
  非fast-forward是将两个分支的提交版本节点合并，并且为合并后结果自动或手动生成新的提交版本节点，完整体现分支的版本变更历史
**Rebase**
![git rebase <branch name>](./imgs/rebase.jpg "git rebase <branch name>")
![git rebase --onto <branch name> <SHA-1>](./imgs/rebase_onto.jpg "git rebase --onto <branch name> <SHA-1>")
  `git rebase <branch name>`：将当前分支的提交节点合并到被合并的分支的提交节点链中
  `git rebase --onto <branch name> <SHA-1>`：将当前分支的提交节点合并到被合并的分支的提交节点链中,其中`<SHA-1>`指定合并当前分支`<SHA-1>`后的提交节点
3. *冲突处理*
  执行分支合并操作时，出现版本内容冲突是不能避免的，Git不会自动处理冲突，而是提示你手工处理。通过文本编辑器修改冲突文件后，执行`git commit`即完成合并操作
  在出现冲突时想执行回滚操作，输入`git reset --hard`即可
4. *匿名分支*
![切换到匿名分支](./imgs/detached_Head.jpg "切换到匿名分支")
![在匿名分支进行版本提交](./imgs/detached_Head_Commit.jpg "在匿名分支进行版本提交")
![切换到命名分支后刚才提交的版本丢失](./imgs/detached_Head_Checkout.jpg "切换到命名分支后刚才提交的版本丢失")
![为匿名分支起名防止提交的版本丢失](./imgs/detached_Head_Checkout_b.jpg "为匿名分支起名防止提交的版本丢失")
  `git checkout <tagName | remote branch name | SHA-1>`：切换到匿名分支（detached HEAD）,用于实验性的操作和在各个提交版本中自由移动和查看信息
  **注意：可以在匿名分支上进行修改、删除文件等操作并提交版本到本地仓库，但因提交的版本不属于已有的命名分支，所以当切换分支后刚才提交的版本就会丢失（类似于内存泄露），解决方法：通过`git checkout -b <new branch name>`将匿名分支变为命名分支即可**
5. *复制提交版本*
![复制提交版本](./imgs/cherry-pick.jpg "复制提交版本")
  `git cherry-pick <SHA-1>`：复制其他提交版本到当前分支并进行合并操作（当然有可能出现合并冲突问题），并自动或手动完成一次新的版本提交

## 标签 ##
1. *分类*：
  1. 轻量级（lightweight）:是指向某提交节点的引用（指针）
  2. 含附注（annotated）：存储在本地仓库的独立对象，含校验和信息、标签名字、邮件信息、标签说明和允许使用GNU Privary Guard来签署和验证。一般情况使用该类型的标签
2. 命令
  `git tag`：罗列已有的标签，列表仅按字符排序，不是按创建时间来排序的
  `git tag -l "搜索条件"`：根据搜索条件罗列已有的标签，列表仅按字符排序，不是按创建时间来排序的
  `git tag <tag identifier>`：为当前提交节点创建轻量级标签
  `git tag -a <tag identifier> -m <tag instructions>`：为当前提交节点创建含附注的标签
  `git tag -a <tag identifier> <SHA-1>`：为SHA-1指定的提交节点创建含附注的标签
  `git show <tag identifier>`：显示标签关联的提交节点信息
3. 提交标签信息到远程服务器
  `git push <remote server name> <tag identifier>`：提交指定的标签
  `git push <remote server name> --tags`：提交所有

## 版本回滚 ##
`git revert <SHA-1>`：回滚到当前分支的<SHA-1>指向的提交节点，并自动生成一个新的提交节点
`git revert HEAD`：回滚到当前分支的最近第二个提交节点，并自动生成一个新的提交节点

## 其他 ##
HEAD指向当前分支的最近提交节点
HEAD~指向当前分支的最近第2个提交节点
HEAD~2指向当前分支的最近第3个提交节点
其他如此类推

## GUI客户端 ##
[Tortoise Git](https://code.google.com/p/tortoisegit/) 4 windows
[GitX(L)](http://gitx.laullon.com/) 4 Mac OS X
[SourceTree](http://www.sourcetreeapp.com/) 4 windows and Mac
[git-cola](http://git-cola.github.io/) an open source software
[Tower](http://www.git-tower.com/) 4 Mac

#* 2 the Deep *#
## Git本地仓库结构 ##
![](./imgs/git.jpg)
ed489是最近的提交节点，指向上一个提交节点，从而形成一条提交节点链。
## reset命令的背后 ##
![](./imgs/reset_hard.jpg)
1. `git reset`：从当前分支的最近提交版本复制文件到缓存区
2. `git reset HEAD~`：从当前分支的最近第二个提交版本复制文件到缓存区
3. `git reset --hard`：从当前分支的最近提交版本复制文件到缓存区和工作目录
4. `git reset --hard --soft`：从当前分支的最近提交版本复制文件到工作目录
5. `git reset --hard HEAD^`：从当前分支的最近第二个提交版本复制文件到缓存区和工作目录，并删除当前提交节点（也就是整个提交版本回滚，不建议使用该方式）


#* Setup 4 Workbench *#
