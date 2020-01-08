首先我定义了一个基类，重写了一遍fragment的相关方法
```
public abstract class AbsV4Fragment extends Fragment {

    private Context context;

    private final static String TAG = AbsV4Fragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WLog.i(TAG, getName() + ".onAttach");
        this.context = context;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        WLog.i(TAG, getName() + ".onAttachFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WLog.i(TAG, getName() + ".onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WLog.i(TAG, getName() + ".onCreateView");
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(getLayoutId(), container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WLog.i(TAG, getName() + ".onViewCreated");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WLog.i(TAG, getName() + ".onActivityCreated savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
        initView();
        initData(savedInstanceState);
        initClick();
    }

    @Override
    public void onStart() {
        super.onStart();
        WLog.i(TAG, getName() + ".onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        WLog.i(TAG, getName() + ".onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        WLog.i(TAG, getName() + ".onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        WLog.i(TAG, getName() + ".onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WLog.i(TAG, getName() + ".onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WLog.i(TAG, getName() + ".onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        WLog.i(TAG, getName() + ".onDetach");
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        WLog.i(TAG, getName() + ".onSaveInstanceState outState is " + StringUtils.isNULL(outState));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        WLog.i(TAG, getName() + ".onViewStateRestored savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        WLog.i(TAG, getName() + ".onConfigurationChanged");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WLog.i(TAG, getName() + ".onRequestPermissionsResult");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        WLog.i(TAG, getName() + ".onActivityResult");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        WLog.i(TAG, getName() + ".onLowMemory");
    }

    /**
     * 获取onAttach的context
     *
     * @return
     */
    public Context getCtx() {
        return context;
    }

    public String getName() {
        return getClass().getSimpleName();
    }


    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData(Bundle savedInstanceState);

    public abstract void initClick();
}
```

activity基础类

```
public abstract class AbsAppCompatActivity extends AppCompatActivity {

    private final static String TAG = AbsAppCompatActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WLog.i(TAG, getName() + ".onCreate savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
        setContentView(getLayoutId());
        initView();
        initData(savedInstanceState);
        initClick();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        WLog.i(TAG, getName() + ".onRestart");
    }


    @Override
    protected void onStart() {
        super.onStart();
        WLog.i(TAG, getName() + ".onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        WLog.i(TAG, getName() + ".onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        WLog.i(TAG, getName() + ".onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        WLog.i(TAG, getName() + ".onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WLog.i(TAG, getName() + ".onDestroy");
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        WLog.i(TAG, getName() + ".onAttachFragment");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        WLog.i(TAG, getName() + ".onSaveInstanceState outState is " + StringUtils.isNULL(outState));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        WLog.i(TAG, getName() + ".onRestoreInstanceState savedInstanceState is " + StringUtils.isNULL(savedInstanceState));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        WLog.i(TAG, getName() + ".onActivityResult");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WLog.i(TAG, getName() + ".onRequestPermissionsResult");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        WLog.i(TAG, getName() + ".onConfigurationChanged");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        WLog.i(TAG, getName() + ".onLowMemory");
    }

    public String getName() {
        return getClass().getSimpleName();
    }


    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData(Bundle savedInstanceState);

    public abstract void initClick();
}

```
首先是打开了一个activity带fragment的页面，Logcat的log打印
```
AbsAppCompatActivity: StatusbarActivity.onCreate savedInstanceState is true
AbsV4Fragment: StatusbarFragment.onAttach
AbsAppCompatActivity: StatusbarActivity.onAttachFragment fragment is StatusbarFragment
AbsV4Fragment: StatusbarFragment.onCreate
AbsV4Fragment: StatusbarFragment.onCreateView
AbsV4Fragment: StatusbarFragment.onViewCreated
AbsV4Fragment: StatusbarFragment.onActivityCreated savedInstanceState is true
AbsV4Fragment: StatusbarFragment.onViewStateRestored savedInstanceState is true
AbsV4Fragment: StatusbarFragment.onStart
AbsAppCompatActivity: StatusbarActivity.onStart
AbsAppCompatActivity: StatusbarActivity.onResume
AbsV4Fragment: StatusbarFragment.onResume
```
当我旋转了下屏幕（为了模拟activity重建），原本的fragment会销毁，同时创建了2个新的frament添加进来，但是只有一个onResume执行了，创建第二个的时候，第一个fragment执行了onDestroyView
```
AbsV4Fragment: StatusbarFragment.onConfigurationChanged
AbsAppCompatActivity: StatusbarActivity.onConfigurationChanged
AbsV4Fragment: StatusbarFragment.onPause
AbsAppCompatActivity: StatusbarActivity.onPause
AbsV4Fragment: StatusbarFragment.onSaveInstanceState outState is false
AbsAppCompatActivity: StatusbarActivity.onSaveInstanceState outState is false
AbsV4Fragment: StatusbarFragment.onPause
AbsAppCompatActivity: StatusbarActivity.onStop
AbsV4Fragment: StatusbarFragment.onDestroyView
AbsV4Fragment: StatusbarFragment.onDestroy
AbsV4Fragment: StatusbarFragment.onDetach
AbsAppCompatActivity: StatusbarActivity.onDestroy
------优雅的分割线-----
AbsV4Fragment: StatusbarFragment.onAttach
AbsAppCompatActivity: StatusbarActivity.onAttachFragment fragment is StatusbarFragment
AbsV4Fragment: StatusbarFragment.onCreate
AbsAppCompatActivity: StatusbarActivity.onCreate savedInstanceState is false
AbsV4Fragment: StatusbarFragment.onCreateView
AbsV4Fragment: StatusbarFragment.onViewCreated
AbsV4Fragment: StatusbarFragment.onActivityCreated savedInstanceState is true
AbsV4Fragment: StatusbarFragment.onViewStateRestored savedInstanceState is true
AbsV4Fragment: StatusbarFragment.onAttach
AbsAppCompatActivity: StatusbarActivity.onAttachFragment fragment is StatusbarFragment
AbsV4Fragment: StatusbarFragment.onCreate
AbsV4Fragment: StatusbarFragment.onDestroyView
AbsV4Fragment: StatusbarFragment.onCreateView
AbsV4Fragment: StatusbarFragment.onViewCreated
AbsV4Fragment: StatusbarFragment.onActivityCreated savedInstanceState is true
AbsV4Fragment: StatusbarFragment.onViewStateRestored savedInstanceState is true
AbsV4Fragment: StatusbarFragment.onStart
AbsAppCompatActivity: StatusbarActivity.onStart
AbsAppCompatActivity: StatusbarActivity.onRestoreInstanceState savedInstanceState is false
AbsAppCompatActivity: StatusbarActivity.onResume
AbsV4Fragment: StatusbarFragment.onResume
```
当时的activity代码
```
class StatusbarActivity : AbsAppCompatActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_statusbar;
    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
                .addToBackStack("") //回退栈
                .replace(R.id.fragment_container, StatusbarFragment())
                .commit();
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initClick() {

    }
}
```

在fragment加了这个setRetainInstance(true)也是这样的log打印
```

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        WLog.i(TAG, getName() + ".onAttach");
        this.context = context;
        setRetainInstance(true);
    }

```
