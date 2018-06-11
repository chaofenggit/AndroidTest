package com.altopay.lib.utils.logutil.parser;



import com.altopay.lib.utils.logutil.Constant;
import com.altopay.lib.utils.logutil.Parser;
import com.altopay.lib.utils.logutil.utils.ObjectUtil;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by pengwei on 16/3/8.
 */
public class CollectionParse implements Parser<Collection> {
    @Override
    public Class<Collection> parseClassType() {
        return Collection.class;
    }

    @Override
    public String parseString(Collection collection) {
        String simpleName = collection.getClass().getName();
        String msg = "%s size = %d [" + Constant.BR;
        msg = String.format(msg, simpleName, collection.size());
        if (!collection.isEmpty()) {
            Iterator<Object> iterator = collection.iterator();
            int flag = 0;
            while (iterator.hasNext()) {
                String itemString = "[%d]:%s%s";
                Object item = iterator.next();
                msg += String.format(itemString, flag, ObjectUtil.objectToString(item),
                        flag++ < collection.size() - 1 ? "," + LINE_SEPARATOR : LINE_SEPARATOR);
            }
        }
        return msg + "]";
    }
}
