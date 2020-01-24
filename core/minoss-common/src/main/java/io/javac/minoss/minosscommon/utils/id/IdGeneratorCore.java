package io.javac.minoss.minosscommon.utils.id;

import java.util.UUID;

/**
 * Created by SongLongKuan on 2018/7/7/007.
 * ID 生成控制中心
 *
 * @author SongLongKuan
 */
public class IdGeneratorCore {

    /**
     * 常规行为ID 生成器
     */
    private static final IdGenerator idGenerator = new IdGenerator(1, 1);

    /**
     * 生成一个行为ID
     *
     * @return
     */
    public static Long generatorId() {
        return idGenerator.nextId();
    }


    /**
     * 生成一个UUID
     *
     * @return
     */
    public static String generatorUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}