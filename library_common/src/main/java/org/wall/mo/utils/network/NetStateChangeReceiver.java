package org.wall.mo.utils.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

/**
 * https://www.jianshu.com/p/6fa0f1f1ce48
 */
public class NetStateChangeReceiver extends BroadcastReceiver {

    private NetworkType mType = null;


    NetStateChangeObserver observer;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkType networkType = NetworkUtil.getNetworkType(context);
            notifyObservers(networkType);
        }
    }

    public void setNetStateChangeObserver(NetStateChangeObserver netStateChangeObserver) {
        this.observer = netStateChangeObserver;
    }

    public static void registerReceiver(Context context, NetStateChangeReceiver netStateChangeReceiver) {
        if (netStateChangeReceiver != null) {
            netStateChangeReceiver.mType = NetworkUtil.getNetworkType(context);
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(netStateChangeReceiver, intentFilter);
        }
    }

    public static void unRegisterReceiver(Context context, NetStateChangeReceiver netStateChangeReceiver) {
        if (netStateChangeReceiver != null) {
            context.unregisterReceiver(netStateChangeReceiver);
        }
    }

    private void notifyObservers(NetworkType networkType) {
        if (mType != NetworkType.NETWORK_NO // 必须有网络的情况
                && mType == networkType) { //如果网络状态一直的话
            return;
        }
        mType = networkType;
        if (networkType == NetworkType.NETWORK_NO) {
            if (observer != null) {
                observer.onNetDisconnected();
            }
        } else {
            if (observer != null) {
                observer.onNetConnected(networkType);
            }
        }
    }


}
