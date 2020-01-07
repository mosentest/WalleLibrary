package mo.wall.org.nestedrecyclerview.fragment;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 21:33
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedChildMultiItemEntity implements MultiItemEntity {

    public int type;

    public String title;

    private NestedChildMultiItemEntity(Builder builder) {
        type = builder.type;
        title = builder.title;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public static final class Builder {
        private int type;
        private String title;

        public Builder() {
        }

        public Builder type(int val) {
            type = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public NestedChildMultiItemEntity build() {
            return new NestedChildMultiItemEntity(this);
        }
    }
}
