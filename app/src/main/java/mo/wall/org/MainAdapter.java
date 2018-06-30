package mo.wall.org;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.OnClickListener;
import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * 作者 create by moziqi on 2018/6/30
 * 邮箱 709847739@qq.com
 * 说明
 **/
public class MainAdapter extends Adapter implements OnClickListener {

    Context context;

    List<Entity> items;

    OnItemClickListener onItemClickListener;


    public MainAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_main_item_type, parent, false);
            TypeViewHolder viewHolder = new TypeViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_main_item_content, parent, false);
            ContentViewHolder viewHolder = new ContentViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entity entity = items.get(position);
        if (holder instanceof TypeViewHolder) {
            String title = entity.getTitle();
            ((TypeViewHolder) holder).tv_main_type_title.setText("" + title);
        } else if (holder instanceof ContentViewHolder) {
            holder.itemView.setOnClickListener(this);
            holder.itemView.setTag(position);
            String title = entity.getTitle();
            String content = entity.getContent();
            ((ContentViewHolder) holder).tv_main_content_title.setText("" + title);
            ((ContentViewHolder) holder).tv_main_content_content.setText("" + content);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }


    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        //用于回收
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(List<Entity> items) {
        this.items = items;
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onClick(v, (Integer) v.getTag());
        }
    }

    static class TypeViewHolder extends ViewHolder {
        TextView tv_main_type_title;

        public TypeViewHolder(View itemView) {
            super(itemView);
            tv_main_type_title = itemView.findViewById(R.id.tv_main_type_title);
        }
    }

    static class ContentViewHolder extends ViewHolder {
        TextView tv_main_content_title;
        TextView tv_main_content_content;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tv_main_content_title = itemView.findViewById(R.id.tv_main_content_title);
            tv_main_content_content = itemView.findViewById(R.id.tv_main_content_content);
        }
    }

    public interface OnItemClickListener {
        public void onClick(View view, int pos);
    }
}
