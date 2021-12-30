

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



git remote rm origin



git remote add GitHub git@github.com:xcalan/learn_git.git
git remote add Gitee git@gitee.com:xcalan/learn_git.git

1

测试一下本地私钥和网站公钥配置是否成功，

在config文件中，给GitHub网站配置的别名就是github，所以直接使用别名，就是

```bash
ssh -T git@github
```



遇到问题：

```bash
The authenticity of host 'github.com (20.205.243.166)' can't be established.
```

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230195845819.png" alt="image-20211230195845819" style="zoom:33%;" />

Google之后明白，少了一个known_hosts文件，本来密钥文件应该是三个，现在是两个，便报了这样的错误，此时选择yes回车之后，便可同时生成了缺少了的known_hosts文件：

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230200047967.png" alt="image-20211230200047967" style="zoom:33%;" />



分支：

```bash
# 查看本地分支
(base) cat@zhangjilindembp Java_notes % git branch
* master
# 查看远程分支
(base) cat@zhangjilindembp Java_notes % git branch -r
 Gitee/master
```



push：

遇到问题

```bash
fatal: 'Github' does not appear to be a git repository
```

解决

```bash
https://blog.csdn.net/chaorwin/article/details/51921294
```



```bash
git push <远程主机名> <本地分支名>:<远程分支名>
git push Github master:main
```



万分感谢！！！

https://www.runoob.com/git/git-remote-repo.html

```bash
git add .
git commit -m "添加 README.md 文件"
git remote add origin git@github.com:cat0501/Java_notes.git
git push -u origin master
```

<img src="https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20211230210913202.png" alt="image-20211230210913202" style="zoom: 33%;" />



github设置默认分支

```bash
```









