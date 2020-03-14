 Mac HomeBrew update卡死、慢、替换源无效
网上找了下，替换了源什么的都不行，于是还是自己看看，于是看brew update -v的进度

$ brew update -v
 Checking if we need to fetch /usr/local/Homebrew...
 Checking if we need to fetch /usr/local/Homebrew/Library/Taps/caskroom/homebrew-cask...
 Fetching /usr/local/Homebrew...
 Checking if we need to fetch /usr/local/Homebrew/Library/Taps/homebrew/homebrew-cask...
 Checking if we need to fetch /usr/local/Homebrew/Library/Taps/homebrew/homebrew-core...
 Fetching /usr/local/Homebrew/Library/Taps/homebrew/homebrew-cask...
 Fetching /usr/local/Homebrew/Library/Taps/homebrew/homebrew-core...
 Fetching /usr/local/Homebrew/Library/Taps/caskroom/homebrew-cask...
发现就是卡死在caskroom/homebrew-cask，网上都只是说替换三个git仓库的url，这儿实际上还有一个，cd到目录发现，按homebrew/homebrew-cask设置即可。具体命令：

cd "$(brew --repo)"
git remote set-url origin https://mirrors.ustc.edu.cn/brew.git

cd "$(brew --repo)/Library/Taps/homebrew/homebrew-core"
git remote set-url origin https://mirrors.ustc.edu.cn/homebrew-core.git

cd "$(brew --repo)/Library/Taps/homebrew/homebrew-cask"
git remote set-url origin https://mirrors.ustc.edu.cn/homebrew-cask.git

PS：这里是使用的中科大源：http://mirrors.ustc.edu.cn/

参考：https://mirrors.tuna.tsinghua.edu.cn/help/homebrew/

来自：http://lckiss.com/?p=4252