package com.rmyh.recyclerviewsuspend.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rmyh.recyclerviewsuspend.R;
import com.rmyh.recyclerviewsuspend.bean.ConfigBean;
import com.rmyh.recyclerviewsuspend.utils.RadioButtonRestoreUtils;
import com.rmyh.recyclerviewsuspend.utils.TextViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/8/8.
 * <p>
 * https://blog.csdn.net/zhangyiacm/article/details/78223189
 * https://www.jb51.net/article/104061.htm
 * https://blog.csdn.net/baidu_34012226/article/details/52326410
 * <p>
 * RecyclerView性能优化
 * https://www.jianshu.com/p/79c9c70f6502
 */

public class ConfigRecyclerAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ConfigRecyclerAdapter";

    List<ConfigBean> list = new ArrayList<>();

    private RecyclerView recyclerView;

    private Context context;

    private View topItemView;

    private View menuItemView;

    private int currentPos = -1;//表示选择的位置

    private Handler handler = new Handler();

    public ConfigRecyclerAdapter(List<ConfigBean> list) {
        this.list = list;
    }


    /**
     * https://www.jianshu.com/p/be89ebfb215e
     *
     * @param currentPos
     */
    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, 100);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        addOnScrollListener();
    }


    private void addOnScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //Log.i(TAG, "onScrollStateChanged.newState:" + newState);

                //Log.i(TAG, "onScrollStateChanged.topItemView:" + topItemView.getHeight());
                //Log.i(TAG, "onScrollStateChanged.menuItemView:" + menuItemView.getHeight());
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.i(TAG, "onScrollStateChanged.dx:" + dx + "....dy:" + dy);
                //Log.i(TAG, "onScrolled.topItemView:" + topItemView.getHeight());
                //Log.i(TAG, "onScrolled.menuItemView:" + menuItemView.getHeight());
                //int computeVerticalScrollOffset = recyclerView.computeVerticalScrollOffset();
                //Log.i(TAG, "onScrollStateChanged.computeVerticalScrollOffset:" + computeVerticalScrollOffset);
                if (recyclerView != null) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    View view = layoutManager.findViewByPosition(1);
                    if (view == null) {
                        if (menuListener != null) {
                            menuListener.onMenuShow(true);
                        }
                        return;
                    }
                    int top1 = view.getTop();
                    Log.i(TAG, "onScrollStateChanged.view:" + top1);
                    if (top1 <= 0) {
                        //表示广告栏已经没了，需要显示菜单栏
                        if (menuListener != null) {
                            menuListener.onMenuShow(true);
                        }
                    } else {
                        //隐藏菜单栏,在滑动过程中，会突然神经病，从负数马上变成整数
                        //所以拿到这个数据来做衡量
                        View viewData = layoutManager.findViewByPosition(2);
                        if (viewData == null) {
                            if (menuListener != null) {
                                menuListener.onMenuShow(true);
                            }
                            return;
                        }
                        if (menuListener != null) {
                            menuListener.onMenuShow(false);
                        }
                    }
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.i(TAG, "onCreateViewHolder.viewType:" + viewType);
        if (viewType == 0) {
            //只有一个，所有只会实例化一次
            topItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top, parent, false);
            return new TopViewHolder(topItemView);
        } else if (viewType == 1) {
            //只有一个，所有只会实例化一次
            menuItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
            return new MenuViewHolder(menuItemView);
        } else {
            //多个，会实例化多个
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
            return new MyViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            ((TopViewHolder) holder).setData(position);
        } else if (holder instanceof MenuViewHolder) {
            ((MenuViewHolder) holder).setData(position);
            ((MenuViewHolder) holder).mRb_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goTopMenu();
                    showCurrentClick(0);

                }
            });
            ((MenuViewHolder) holder).mRb_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goTopMenu();
                    showCurrentClick(1);
                }
            });
            ((MenuViewHolder) holder).mRb_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goTopMenu();
                    showCurrentClick(2);
                }
            });
            ((MenuViewHolder) holder).mRb_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goTopMenu();
                    showCurrentClick(3);
                }
            });
        } else if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).setData(position);
        }
    }

    private void showCurrentClick(final int pos) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (menuListener != null) {
                    menuListener.currentClick(pos);
                }
            }
        }, 200);
    }

    private void goTopMenu() {
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(1, 0);
            }
        }
    }

    public List<ConfigBean> getDataList() {
        return list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    private MenuListener menuListener;

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }

    class TopViewHolder extends RecyclerView.ViewHolder {

        public TopViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {

        }
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLl_menu;
        private TextView mRb_1;
        private TextView mRb_2;
        private TextView mRb_3;
        private TextView mRb_4;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mLl_menu = (LinearLayout) itemView.findViewById(R.id.ll_menu);
            mRb_1 = (TextView) itemView.findViewById(R.id.rb_1);
            mRb_2 = (TextView) itemView.findViewById(R.id.rb_2);
            mRb_3 = (TextView) itemView.findViewById(R.id.rb_3);
            mRb_4 = (TextView) itemView.findViewById(R.id.rb_4);
        }

        public void setData(int position) {

        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView viewById;

        public MyViewHolder(View itemView) {
            super(itemView);
            viewById = (TextView) itemView.findViewById(R.id.text);
        }

        public void setData(int position) {
            viewById.setText(list.get(position).name);
        }
    }
}