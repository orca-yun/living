package ag.orca.living.common;

public class CacheConst {

    /**
     * 免审词缓存
     */
    public static String EXEMPT_PREFIX = "ORG:EXEMPT:";


    /**
     * 敏感词审词缓存
     */
    public static String SENSITIVE_PREFIX = "ORG:SENSITIVE:";

    /**
     * [value] 房间
     * - prefix + roomId
     */
    public static String ROOM_PREFIX = "LR:ROOM:";


    /**
     * [value] 房间权限
     * - prefix + roomId
     */
    public static String ROOM_PERMISSION_PREFIX = "LR:PERMISSION:";

    /**
     * [value] 房间页面
     * - prefix + roomId
     */
    public static String ROOM_PAGE_PREFIX = "LR:PAGE:";

    /**
     * [value] 房间互动
     * - prefix + roomId
     */
    public static String ROOM_INTERACT_PREFIX = "LR:INTERACT:";

    /**
     * [value] 房间商品
     * - prefix + roomId
     */
    public static String ROOM_MARKET_GOODS_PREFIX = "LR:GOODS:";

    /**
     * [zset] 黑名单 [blackUserInfo]
     * - prefix + roomId
     */
    public static final String BLACKLIST_PREFIX = "LR:LIST:BLACK:";
    /**
     * [set] 黑名单 [long]
     * - prefix + roomId
     */
    public static final String BLACKLIST_UID_PREFIX = "LR:LIST:BLACK:UID:";

    /**
     * [zset] 禁言列表 [muteUserInfo]
     * - prefix + roomId
     */
    public static final String MUTE_LIST_PREFIX = "LR:LIST:MUTE:";

    /**
     * [set] 禁言列表 [long]、
     * - prefix + roomId
     */
    public static final String MUTE_LIST_UID_PREFIX = "LR:LIST:MUTE:UID:";


    /**
     * [value] 房间开播场景记录ID
     * - prefix + roomId
     */
    public static final String RECORD_ID_PREFIX = "LR:RECORD:";


    /**
     * [zset] 助理在线列表 [authInfo]
     * - prefix + roomId
     */
    public static final String ASSI_ONLINE_PREFIX = "LR:LIST:ASSI:";

    /**
     * [zset] 用户在线列表 [authInfo]
     * - prefix + roomId
     */
    public static final String SHARE_ONLINE_PREFIX = "LR:LIST:SHARE:";


    public static final String SHARE_INFO_PREFIX = "LR:SHARE:INFO:";

    public static final String CROSS_INFO_PREFIX = "LR:CROSS:INFO:";

    public static final String SHARE_INFO_PREFIX_TOKEN = "LR:SHARE:TOKEN:INFO:";


    /**
     * [value] 微信登录回调keyId->userId映射
     */
    public static final String KEY_USER_MAPPING = "LR:SHARE:KEY:USER:INFO:";


}
