公司的gitlab,定期要求更改密码，更改后Macbook 下Sourcetree需要更新密码
删除Sourcetree 缓存文件(只需要删密码文件)，文件位置：
Mac：
~/Library/Application Support/SourceTree
Windows：
C:\Users\USERNAME\AppData\Local\Atlassian\SourceTree

比如我的密码文件是ajiao@STAuth-gitlab.qiyi.domain，把这个文件删掉，push或者pull代码的时候，就会自动弹框让重新输入密码，完美解决

还有报一下错误
git-credential-sourcetree died of signal 9
删除以上密码文件
重启电脑
更新sourcetree
各种操作一番就好了，感觉应该是删掉密码文件后重启再输入密码就好了

作者：阿姣_0405
链接：https://www.jianshu.com/p/da60ea7fbdd0
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。