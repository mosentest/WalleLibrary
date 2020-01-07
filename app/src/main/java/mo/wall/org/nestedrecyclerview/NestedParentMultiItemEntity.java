package mo.wall.org.nestedrecyclerview;

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
public class NestedParentMultiItemEntity implements MultiItemEntity {

    private int type;

    private NestedParentMultiItemEntity(Builder builder) {
        type = builder.type;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public static final class Builder {
        private int type;

        public Builder() {
        }

        public Builder type(int val) {
            type = val;
            return this;
        }

        public NestedParentMultiItemEntity build() {
            return new NestedParentMultiItemEntity(this);
        }
    }
}
