# 一个本地库能不能既关联 GitHub，又关联 Gitee 呢？

> 答案是肯定的，因为 git 本身是分布式版本控制系统，可以同步到另外一个远程库，当然也可以同步到另外两个远程库。
> 使用多个远程库时，我们要注意，git 给远程库起的默认名称是 `origin`，如果有多个远程库，我们需要用不同的名称来标识不同的远程库。

## 配置秘钥

由于你的本地 Git 仓库和 GitHub 仓库之间的传输是通过`SSH`加密的，所以我们需要配置验证信息：

使用以下命令生成 SSH Key：

```
$ ssh-keygen -t rsa -C "youremail@example.com"

ssh-keygen -t rsa -C "17782975312@163.com"
ssh-keygen -t rsa -C "pxzjl123@icloud.com"
```



后面的 your_email@youremail.com 改为你在 Github 或Gitee上注册的邮箱，之后会要求确认路径和输入密码，我们这使用默认的一路回车就行。
成功的话会在 ~/ 下生成 .ssh 文件夹，进去，打开 id_rsa.pub，复制里面的 key。

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230214938954.png" alt="image-20211230214938954" style="zoom:33%;" />

> 注：根据runoob官网，只用在本地~/下.ssh 文件夹中生成一个秘钥就好，但是我根据[思否博主建议](https://segmentfault.com/a/1190000016269686)对每个账户（GitHub、Gitee）生成了一个秘钥然后改名并分别配置到了GitHub和Gitee中，秘钥文件如上图。
>
> id_rsa是私钥 id_rsa.pub是公钥





## 关联GitHub和Gitee

先关联 GitHub 的远程库：

```bash
git remote add github git@github.com:tianqixin/runoob-git-test.git
```

注意，远程库的名称叫 github，不叫 origin 了。

接着，再关联 Gitee 的远程库：

```bash
git remote add gitee git@gitee.com:imnoob/runoob-test.git
```

同样注意，远程库的名称叫 gitee，不叫 origin。

现在，我们用 git remote -v 查看远程库信息，可以看到两个远程库：

```bash
git remote -v
gitee    git@gitee.com:imnoob/runoob-test.git (fetch)
gitee    git@gitee.com:imnoob/runoob-test.git (push)
github    git@github.com:tianqixin/runoob.git (fetch)
github    git@github.com:tianqixin/runoob.git (push)
```

## 推送

先添加和提交：

```bash
git add .
git commit -m "xxx"
```



如果要推送到 GitHub，使用命令：

```bash
git push github master
```

如果要推送到 Gitee，使用命令：

```bash
git push gitee master
```

这样一来，我们的本地库就可以同时与多个远程库互相同步：

![img](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/gitee8.png)



## 最终效果

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230222742330.png" alt="image-20211230222742330" style="zoom:33%;" />



![image-20211230222850270](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230222850270.png)



![image-20211230222954724](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230222954724.png)



## 其它问题



### Github 如何设置 master 为默认分支？

需要在当前项目的setting里面修改默认分支，账户里面修改无效。



### 测试一下本地私钥和网站公钥配置是否成功？

在config文件中，给GitHub网站配置的别名就是github，所以直接使用别名，就是

```bash
ssh -T git@github
```



遇到问题：

```bash
The authenticity of host 'github.com (20.205.243.166)' can't be established.
```

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230195845819.png" alt="image-20211230195845819" style="zoom:33%;" />

解决：

Google之后明白，少了一个known_hosts文件，本来密钥文件应该是三个，现在是两个，便报了这样的错误，此时选择yes回车之后，便可同时生成了缺少了的known_hosts文件。

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230200047967.png" alt="image-20211230200047967" style="zoom:33%;" />



### push：'Github' does not appear to be a git repository

遇到问题

```bash
fatal: 'Github' does not appear to be a git repository
```

解决

```bash
https://blog.csdn.net/chaorwin/article/details/51921294
```



### 万分感谢！！！第一次往github上的Java_notes仓库push大量文件！

https://www.runoob.com/git/git-remote-repo.html

```bash
git add .
git commit -m "添加 README.md 文件"
git remote add origin git@github.com:cat0501/Java_notes.git
git push -u origin master
```

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230210913202.png" alt="image-20211230210913202" style="zoom: 33%;" />



### git中提示 please tell me who you are

需要你登录一下，确认你的身份。

先输入命令`git config user.name "username"`，换行输入`git config user.email "email"`

输入正确的之后就能使用 git add 、commit等命令进行版本控制了。



---



先查看现有远程库的情况：

```bash
git remote --verbose
```



```bash
(base) cat@zhangjilindembp Java_notes % git remote --verbose

origin	https://gitee.com/code0002/Java_notes.git (fetch)
origin	https://gitee.com/code0002/Java_notes.git (push)
(base) cat@zhangjilindembp Java_notes % 
```

可以看到，目前仅有`https://gitee.com/code0002/Java_notes.git`这个远程库地址



git remote add GitHub git@github.com:xcalan/learn_git.git
git remote add Gitee git@gitee.com:xcalan/learn_git.git



分支：

```bash
# 查看本地分支
(base) cat@zhangjilindembp Java_notes % git branch
* master
# 查看远程分支
(base) cat@zhangjilindembp Java_notes % git branch -r
 Gitee/master
```



```bash
git push <远程主机名> <本地分支名>:<远程分支名>
git push Github master:main
```



```
git remote add gitee git@gitee.com:code0002/Java_notes.git
```



秘钥

https://www.jianshu.com/p/be58fa27a704

Git配置多个用户

https://blog.csdn.net/Jumping_code/article/details/108775927

https://blog.csdn.net/weixin_33725515/article/details/86250140?spm=1001.2101.3001.6650.4&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-4.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-4.no_search_link&utm_relevant_index=9

https://blog.csdn.net/cicibi6696/article/details/100686299

https://blog.csdn.net/weixin_47518343/article/details/109047689?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~default-2.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~default-2.no_search_link&utm_relevant_index=5

https://blog.csdn.net/weixin_47518343/article/details/109047689?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-5.no_search_link&spm=1001.2101.3001.4242.4&utm_relevant_index=8

https://blog.csdn.net/zixiao217/article/details/82939654?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0.no_search_link&spm=1001.2101.3001.4242.1&utm_relevant_index=3



https://blog.csdn.net/xiecheng1995/article/details/106570059?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_title~default-1.no_search_link&spm=1001.2101.3001.4242.2&utm_relevant_index=4

https://blog.csdn.net/COCO56/article/details/103818543?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_title~default-5.no_search_link&spm=1001.2101.3001.4242.4&utm_relevant_index=8



# git命令



分支

https://blog.csdn.net/guo_qiangqiang/article/details/88020656

# 终端配置
更新完 zsh 说我目录权限问题的解决   https://www.jianshu.com/p/f2545c1feaec

报错内容如下：
```bash
[oh-my-zsh] For safety, we will not load completions from these directories until
[oh-my-zsh] you fix their permissions and ownership and restart zsh.
[oh-my-zsh] See the above list for directories with group or other writability.

[oh-my-zsh] To fix your permissions you can do so by disabling
[oh-my-zsh] the write permission of "group" and "others" and making sure that the
[oh-my-zsh] owner of these directories is either root or your current user.
[oh-my-zsh] The following command may help:
[oh-my-zsh] compaudit | xargs chmod g-w,o-w

[oh-my-zsh] If the above didn't help or you want to skip the verification of
[oh-my-zsh] insecure directories you can set the variable ZSH_DISABLE_COMPFIX to
[oh-my-zsh] "true" before oh-my-zsh is sourced in your zshrc file.
```
解决方案
```bash
chmod 755 /usr/local/share/zsh
chmod 755 /usr/local/share/zsh/site-functions
```
