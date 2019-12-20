在 Glide.with(FragmentActivity).load(URL).into(imageView); 这个过程中，我们在 into 里面传入了 ImageView ，通过查看源码：
```
public ViewTarget<ImageView, TranscodeType> into(@NonNull ImageView view) {
//省去部分代码
return into(
    glideContext.buildImageViewTarget(view, transcodeClass),
    /*targetListener=*/ null,
    requestOptions);
}
```
这里只需要关心主流程，所以直接看最后一句代码，通过 buildImageViewTarget 方法把 ImageView 转换成 ViewTarget 对象：
```
@NonNull
public <X> ViewTarget<ImageView, X> buildImageViewTarget(
        @NonNull ImageView imageView, @NonNull Class<X> transcodeClass) {
   return imageViewTargetFactory.buildTarget(imageView, transcodeClass);
}
```
发现一个 SizeDeterminer 持有了这个 ImageView ：
```
public ViewTarget(T view) {
  this.view = Preconditions.checkNotNull(view);
  sizeDeterminer = new SizeDeterminer(view);
}
```
在 SizeDeterminer 里面的 getSize 方法用于外层提供 ImageView :
```
/**
 * @param cb 外层的回调,用于获取ImageView的宽高
 */
void getSize(SizeReadyCallback cb) {
  //得到控件的宽高值
  int currentWidth = getTargetWidth();
  int currentHeight = getTargetHeight();
  //如果是合理的宽高值
  if(isViewStateAndSizeValid(currentWidth, currentHeight)) {
      cb.onSizeReady(currentWidth, currentHeight);
      return;
    }
   //无法直接获取到控件的宽高值，设置监听来获取
   if(!cbs.contains(cb)) {
       cbs.add(cb);
    }
   if(layoutListener == null) {
      ViewTreeObserver observer = view.getViewTreeObserver();
      layoutListener = new SizeDeterminerLayoutListener(this);
      observer.addOnPreDrawListener(layoutListener);
    }
}
```
SizeReadyCallback 是一个接口，是给外层获取控件的宽高值的：
```
public interface SizeReadyCallback {
  void onSizeReady(int width, int height);
}
```
由于 getTargetWidth 和 getTargetHeight 基本逻辑是一样的，这里就只看一个了：
```
private static final int PENDING_SIZE = 0;

private int getTargetWidth() {
  //获取padding
  int horizontalPadding = view.getPaddingLeft() + view.getPaddingRight();
  //获取 LayoutParams，从而获取到 xml 或后期手动修改设置的宽高数值
  LayoutParams layoutParams = view.getLayoutParams();
  int layoutParamSize = layoutParams != null ? layoutParams.width : PENDING_SIZE;
  //开始计算真正的尺寸值
  return getTargetDimen(view.getWidth(), layoutParamSize, horizontalPadding);
}
```
看到最终是由 getTargetDimen 计算真正的尺寸值的：
```
private static final int PENDING_SIZE = 0;

private int getTargetDimen(int viewSize, int paramSize, int paddingSize) {
  //优先判断 LayoutParams 设置的尺寸参数
  int adjustedParamSize = paramSize - paddingSize;
  if(adjustedParamSize > 0) {
     return adjustedParamSize;
  }

  //view.isLayoutRequested() == true说明当前View还在layout传递阶段
  //此时 LayoutParams 和 viewSize 都不可信
  if(waitForLayout && view.isLayoutRequested()) {
      return PENDING_SIZE;
  }
  //通过 viewSize 来计算显示区域的尺寸
  int adjustedViewSize = viewSize - paddingSize;
  if(adjustedViewSize > 0) {
     return adjustedViewSize;
  }
  //当前 WRAP_CONTENT，那么返回设备屏幕的最大尺寸值
  if (!view.isLayoutRequested() && paramSize == LayoutParams.WRAP_CONTENT) {
       return getMaxDisplayLength(view.getContext());
  }
  return PENDING_SIZE;
}

/**
 * 获取当前设备屏幕的长高中的最大值
 */
private static int getMaxDisplayLength(@NonNull Context context) {
  if(maxDisplayLength == null) {
     WindowManager windowManager =
        (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
     Display display = Preconditions.checkNotNull(windowManager).getDefaultDisplay();
     Point displayDimensions = new Point();
     display.getSize(displayDimensions);
     maxDisplayLength = Math.max(displayDimensions.x, displayDimensions.y);
  }
  return maxDisplayLength;
}
```
上面代码主要逻辑就下面 3 点：

1.优先使用 LayoutParams 获取宽高值，其次是 View.getWidth()/Height()

2.如果给 View 的宽高设置了 WRAP_CONTENT ，那么将会返回设备屏幕宽高中的最大值

3.无法得到具体的宽高值，那么默认返回 PENDING_SIZE，也就是 0

我们给 ImageView 的宽高都设置了 WRAP_CONTENT ，那么我们拿到的尺寸值就都是设备屏幕尺寸的最大值，这个可能会让我们耗时更多的内存去加载图片，因此使用 Glide 尽量不要使用 WRAP_CONTENT 了