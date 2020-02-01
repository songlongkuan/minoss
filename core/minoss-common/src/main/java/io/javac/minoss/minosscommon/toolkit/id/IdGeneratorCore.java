package io.javac.minoss.minosscommon.toolkit.id;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * Created by SongLongKuan on 2018/7/7/007.
 * ID 生成控制中心
 *
 * @author SongLongKuan
 */
public class IdGeneratorCore {

//    private static final IdGenerator idGenerator = new IdGenerator(1, 1);


    public static Long generatorId() {
        return IdWorker.getId();
    }


    /**
     * 生成一个UUID
     *
     * @return
     */
    public static String generatorUUID() {
        return IdWorker.get32UUID();
    }

}