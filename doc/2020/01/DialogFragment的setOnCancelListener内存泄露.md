[Android : DialogFragment 内存泄露](https://segmentfault.com/q/1010000017286787)
[dialog.setOnDismissListener(null)过程分析](https://www.imooc.com/article/287963)

Android Lollipop 之前使用 AlertDialog 可能会导致内存泄漏! ...handler 问题


最终解决方案，重写onActivityCreated方法，并在super.onActivityCreated(savedInstanceState);修改dialog显示

[DialogFragment内存泄露最强解决方案](https://www.jianshu.com/p/f2d6e6bc4b77)
```
  private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (getShowsDialog()) {
            setShowsDialog(false);
        }
        super.onActivityCreated(savedInstanceState);
        setShowsDialog(true);

        View view = getView();
        if (view != null) {
            if (view.getParent() != null) {
                throw new IllegalStateException(
                        "DialogFragment can not be attached to a container view");
            }
            getDialog().setContentView(view);
        }
        final Activity activity = getActivity();
        if (activity != null) {
            getDialog().setOwnerActivity(activity);
        }
        if (savedInstanceState != null) {
            Bundle dialogState = savedInstanceState.getBundle(SAVED_DIALOG_STATE_TAG);
            if (dialogState != null) {
                getDialog().onRestoreInstanceState(dialogState);
            }
        }
    }

    public void onDestroy() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
```