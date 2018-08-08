package com.constant;

/**
 * User: gravity Date: 15-7-22 Time: 上午9:52
 */
public class ErrorCode {

	public static int OPERATE_SUCCESS_CODE = 1; // 用户操作成功返回值

	// ===================== 0、系统错误，10000以下======================
	public static int STANDARD_ERROR_CODE = -1; // 标准错误(一般错误)
	public static int PARAM_IS_NULL = -2; // 参数为空
	public static int CREATE_FAIL = -3; // 创建错误
	public static int PARAM_ERROR = -4; // 参数错误
	public static int ACCESS_HAS_MAX = -5; // 访问已达上限
	
	
	

	// ===================== 0.5、微信错误10开头======================
	public static int WX_FAIL = -10; // 微信调用失败错误
	

	// ===================== 1、用户错误，10000开头======================
	public static int USER_LOGIN_ERROR_UNLOGIN = 10001;// 还未登录
	public static int USER_LOGIN_ERROR_UTOKEN = 10002;// 无效的token
	public static int USER_AUTH_ERROR= 10003;//权限错误
	public static int USER_AGENT_WX_ISEXIST = 10004;// 该微信号已绑定手机号
	public static int USER_NOT_EXIST = 10005;// 用户不存在
	public static int USER_TELPHONE_FORMAT_ERROR = 10006;// 手机号码格式错误
	public static int USER_ERROR_SMS_CODE = 10007;// 短信验证码错误
	public static int USER_REPEAT_TELEPHONE = 10008;// 手机号码重复
	public static int USER_USERNAME_FORMAT_ERROR = 10009;// 用户不是店主
	public static int USER_USERNAME_EXIST_ERROR = 10010;//该用户没有绑定商店
	public static int USER_SEND_SMS_EXCEED_COUNT = 10011;//用户没有账目
	public static int USER_NICKNAME_EXIST_ERROR = 10012;//用户经验值不满足组团/开团条件
	public static int UPDATE_USER_DETAIL_ERROR = 10013;//修改用户基本信息失败
	public static int INVITE_CODE_NOT_ISEXIST = 10014;//邀请码不存在
	public static int USER_TELEPHONE_EXIST_ERROR = 10015;//该手机号已被其他店添加
	public static int USER_TELEPHONE_NOT_EXIST_ERROR = 10016;//你拥有商铺，不能够再次申请
	public static int IMAGE_CODE_NOT_EXIST_ERROR = 10017; //图片验证码过期
	public static int IMAGE_CODE_ERROR = 10018; //图片验证码错误
	public static int USER_PASSWORD_FORMATE_ERROR = 10019; //用户密码格式错误
	public static int USER_AND_PASSWORD_ERROR = 10020;// 用户账号密码不正确：认证失败
	public static int IS_BLACK_USER = 10021;		// 黑名单用户
	public static int NOT_ACCESS_POWER = 10022;// 还未登录
	
	
	
	// ===================== 2、essay错误，20000开头======================
	public static int ESSAY_CREATE_ERROR = 20001;//创建失败
	public static int PAGE_NOT_EXIST = 20002;//页码不存在
	public static int ESSAY_NOT_EXIST = 20003;
	public static int ESSAY_UPDATE_ERROR = 20004;
	public static int ESSAY_DELETE_ERROR = 20005;
	public static int RECOMMEND_ERROR = 20006;//商店商品已下架
	public static int Comment_RECOMMEND_ERROR = 20007;//团购订单参与时间已到
	public static int ESSAY_NOT_PUBLISHED = 20008;//商店技师已存在
	public static int SHOP_ACCOUNT_NOT_EXIST = 20009;//商店资金账目不存在
	public static int REPEAT_OPERATION_SIGN_TELEPHONE_FALL = 2010; //已标记过
	public static int JOIN_OR_OPEN_TEAM_UPPER_LIMIT = 2011; //当天开团或入团已达上限
	public static int TELEPHONE_THIS_TYPE_IS_SIGN = 2012; //该手机号该类型已经标记过
	public static int WORKER_ISEXIST = 2013; //员工不存在
	public static int WORKER_NOT_APPLY_WITHDRAWAL = 2014; //员工没有权限提现
	public static int YOU_HAS_JOIN_TEAM = 2015; //你已经加入该团
	public static int PRICE_ERROR = 2016; //价格有误
	public static int WORKER_SHOP_ERROR = 2017; //商店员工不匹配
	public static int USER_WORKER_SHOP_EXIST= 2018; //用户商店员工关系记录不存在
	public static int MANAGE_DETAIL_NOT_EDIT= 2019; //管理员信息部能编辑
	public static int SHOP_OFF_LINE= 2020; //商店已下架
	public static int SERVICE_ITEMS_NOT_EXIST= 2021; //项目服务部存在
	public static int HAS_ONLINE_GOODS_NOT_DEL_ALL= 2022; //有在售商品在不能删除全部的服务项目
	
	
	// ===================== 3、申请，30000开头======================
	public static int APPLICATION_GOLD_NOT_ENOUGH = 30001;//金币余额不足
	public static int APPLICATION_AUTH_NOT_IDENTIFY = 30002;//未认证
	
	// ===================== 4、消息错误，40000开头======================
	public static int MSG_NOT_EXIST = 40001;
	public static int MSG_DELETE_FAILED = 40002;
	public static int ORDER_CODE_NOT_VALIDATE  = 40003; //订单状态不等于2 无法对其进行验证
	public static int ORDER_IS_EXPIRE  = 40004; //订单已过期
	public static int ORDER_SHOP_NOT_MATCHING   = 40005; //商店id不匹配
	public static int ORDER_CANNOT_EVALUATE = 40006; //消费订单不处于可以评价状态
	public static int ORDER_STATUE_NOT_WAIT_PAY = 40007; //消费订单不处于待付款状态
	
	// ===================== 5、comment，50000开头======================
	public static int VOUCHERORDER_NOT_EXIST= 50001;//订单不存在
	public static int VOUCHERORDER_LOSE_EFFICACY= 50002;//抵用券过期
	
	// ===================== 6、消费验证码错误，60000开头======================
	public static int CONSUME_CODE_NOT_EXIST= 60001;//消费验证码不存在
	public static int CONSUME_CODE_LOSE_EFFICACY= 60002;//消费验证码过期
	
	// ===================== 7、组团单错误，70000开头======================
	public static int TEAM_ORDER_NOT_EXIST= 70001;//组团单不存在
	
	
	// ===================== 8、平台商品错误，80000开头======================
	public static int PLATFORM_PRODUCT_NOT_EXIST= 80001;//平台商品不存在
	

}
