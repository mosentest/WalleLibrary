【MAC】brew install XXX一直卡在Updating Homebrew…的解决办法
运行命令brew install pip3，结果界面一直卡在Updating Homebrew…上

解决办法是替换brew源：


替换brew.git:
cd "$(brew --repo)"
git remote set-url origin https://mirrors.ustc.edu.cn/brew.git

替换homebrew-core.git:
cd "$(brew --repo)/Library/Taps/homebrew/homebrew-core"
git remote set-url origin https://mirrors.ustc.edu.cn/homebrew-core.git

重置brew.git:
cd "$(brew --repo)"
git remote set-url origin https://github.com/Homebrew/brew.git

重置homebrew-core.git:
cd "$(brew --repo)/Library/Taps/homebrew/homebrew-core"
git remote set-url origin https://github.com/Homebrew/homebrew-core.git


另外的一个博客地址
https://blog.csdn.net/huyangyamin/article/details/88633727

————————————————
版权声明：本文为CSDN博主「DZh_Ming」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/dzh_ming/article/details/81208030