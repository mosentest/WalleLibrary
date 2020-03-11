虽然在以前 枚举 会占增加包体，每个枚举类型就是一个实例，占用内存多

但是在最新的官方文档没提及到enum占内存的这个问题了，但是我们还是按常规这些编写会比较好


[Overview of memory management](https://developer.android.google.cn/topic/performance/memory-overview)

```
@IntDef({
      Color.RED,
      Color.GREEN,
      Color.BLACK,
      Color.YELLOW
})
@Retention(RetentionPolicy.SOURCE)
public @interface Color {
   int RED = 1;
   int GREEN = 2;
   int BLACK = 3;
   int YELLOW = 4;
}
```

添加依赖
在build.gradle文件中的依赖块中添加：
```
dependencies { implementation 'androidx.annotation:annotation:1.1.0' }
```
参考
[Android中避免使用枚举类（Enum）](https://www.jianshu.com/p/05ab8bd3c713)