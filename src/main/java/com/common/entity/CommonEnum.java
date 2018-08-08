package com.common.entity;

/**
 * User: gravity
 * Date: 15-7-7
 * Time: 下午4:44
 */
public class CommonEnum {
	 public enum CodeType{
	    	// 验证码类型： 1短信注册，2短信登陆 3短信设置密码
	        REGISTER_CODE((byte) 1),
	    	LOGIN_CODE((byte) 2),
	    	FORGETPWD_CODE((byte) 3);
	       
	    	CodeType(byte value) {
	            this.value = value;
	        }

	        public byte value;
	    }
	 
	 public enum SMSTemplateId {
		 	BINDINGSMS_CODE(352471);
/*		 	REFUND_TO_HANDLE(153483),
		 	PAY_SUCCESS_TO_HANDLE(153480),
		 	VALIDATE_CODE_TO_SHOP(153486),
		 	VALIDATE_CODE_TO_USER(153485);*/
	    	public int value;
	    	
	    	SMSTemplateId(int value) {
	    		this.value = value;
			}
	    }
	 
	 public enum MaterialType {
	        PIC((byte) 1), // 图片
	        VOICE((byte) 2), // 语音
	        VIEDO((byte) 3), // 视频
	        OTHER((byte) 4), // 其他
	        URL((byte) 5), // 网址
	        ;

	        MaterialType(byte value) {
	            this.value = value;
	        }
	        public byte value;
	    }
	 
	 public enum UserType {
		//用户类型：1普通用户  2运营后台
		 COMMON_USER((byte) 1), 
		 BACK_MANAGE_USER((byte) 2); 
		 UserType(byte value) {
	            this.value = value;
	        }
	        public byte value;
	    }
	 public enum AuthAuditState {
			//认证审核状态：1待审核 2审核通过 3审核不通过
		 	AUDITING((byte) 1),
			ADOPT((byte) 2),
			NOT_ADOPT((byte) 3);
		 AuthAuditState(byte value) {
		            this.value = value;
		        }
		        public byte value;
		    }
	 
	 public enum CommodityOrderStatue {
		//订单状态：1未支付 2已支付 3已消费 4已评价  5申请退款 6退款成功 7交易关闭 8取消待支付订单 9其他原因商户系统发起退款请求失败  10用户账户问题导致退款失败  11商品下架退款
		//12 商品下架时间到---->团购订单未使用（过期未使用）
		 NOT_PAY_MONEY((byte) 1), 
		 HAS_PAY_MONEY((byte) 2),
		 HAS_VALIDATE_CODE((byte) 3),
		 HAS_EVALUATE((byte) 4),
		 APPLY_RETURN_MONEY((byte) 5),
		 HAS_RETURN_MONEY((byte) 6),
		 CLOSE_ORDER((byte) 7),
		 CANCAL_WAIT_PAY_ORDER((byte) 8),
		 OTHER_REASON_BACK_ACCESS_TO_RETURN_FALL((byte) 9),
		 USER_ACCOUNT_REASON_TO_RETURN_MONEY_FALL((byte) 10),
		 COMMODITY_OFF_LIN_REFUND((byte) 11),
		 COMMODITY_OVERDUE_ORDER_NOT_USR((byte) 12);
		 CommodityOrderStatue(byte value) {
	            this.value = value;
	        }
	        public byte value;
	    }
	 
	 public enum RefundType{
		 USER_PROPOSE_REFUND((byte) 1),
		 COMMODITY_OFF_REFUND((byte) 2);
		 RefundType(byte value) {
	            this.value = value;
	        }
	        public byte value;
	 }
	 
	 public enum CommodityOrderIsEvaluate {
			//订单是否已评价：1否 2是
			 NOT_EVALUATE((byte) 1), 
			 HAS_EVALUATE((byte) 2);
		 
		 CommodityOrderIsEvaluate(byte value) {
		            this.value = value;
		        }
		        public byte value;
		    }
	 
	 public enum CommodityOrderIsGroupOrder {
			//是否是拼团订单：1否 2是
			 NOT((byte) 1), 
			 YSE((byte) 2);
		 
		 CommodityOrderIsGroupOrder(byte value) {
		            this.value = value;
		        }
		        public byte value;
		    }
	 
	 
	 public enum CType {
		 //类型：1用户关注商店 2用户关注经理 3用户关注技师
		 USER_SHOP((byte) 1), 
		 USER_HANDLE((byte) 2),
		 USER_THERAPIST((byte) 3); 
		 CType(byte value) {
	        this.value = value;
	    }
		  public byte value;
    }
	 
	 public enum IdType {
		 //id类型：1商店 2技师
		 SHOP_TYPE((byte) 1), 
		 THERAPIST_TYPE((byte) 2);
		 IdType(byte value) {
	        this.value = value;
	    }
		  public byte value;
    }
	 
	
	public enum OrderIsEvaluate{
		//是否已评价该订单 1否 2是
		NOT_EVALUATE((byte) 1),
		HAS_EVALUATE((byte) 2); 
		 
		OrderIsEvaluate(byte value) {
	        this.value = value;
	    }
		  public byte value;
	}
	 
	
	public enum ShopCommdotiytStatue{
		//商品状态：1待审核 2通过 3不通过 4上架 5下架 6待上线 7强制下架
		AUDITING((byte) 1),
		ADOPT((byte) 2),
		NOT_ADOPT((byte) 3),
		ON_LINE((byte) 4),
		OFF_LINE((byte) 5),
		WAIT_ON_LINE((byte) 6),
		FORCE_OFF_LINE((byte) 7);
		
		ShopCommdotiytStatue(byte value) {
	        this.value = value;
	    }
		  public byte value;
	}
	
	public enum ShopStatus {
    	//商店运行状态：1上线 2下线 
    	ON_LINE((byte) 1),
    	OFF_LINE((byte) 2);
    	
		ShopStatus(byte value) {
    		this.value = value;
    	}
    	
    	public byte value;
    }
	
	 public enum OperateType {
	    	//操作类型：1支付宝 2微信 3IOS内购 4微信公众号
	    	ALIPAY((byte) 1),
	    	APP_WEIXIN((byte) 2),
	    	IOSPAY((byte) 3),
	    	PUBLIC_WEIXIN((byte) 4);
	    	
		 OperateType(byte value) {
	    		this.value = value;
	    	}
	    	
	    	public byte value;
	    }
	 
	 
	 public enum BuyType {
	    	//是否是拼团订单：1否 2是
	    	SIGLE((byte) 1),
	    	TEAM((byte) 2);
	    	
		 BuyType(byte value) {
	    		this.value = value;
	    	}
	    	
	    	public byte value;
	    }
	 
	 public enum VoucherOrderUseStatue {
		 //是否使用：1否 2是
    	NO_USE((byte) 1),
    	HAS_USE((byte) 2);
	    	
		 VoucherOrderUseStatue(byte value) {
	    		this.value = value;
	    	}
	    	
	    	public byte value;
	    }
	 
	 public enum ConsumeCodeStatue{
			//验证码状态：1未使用 2使用 3过期
			NOT_USE((byte) 1),
			HAS_USE((byte) 2),
			 EXPIRE((byte) 3);
			
		 ConsumeCodeStatue(byte value) {
		        this.value = value;
		    }
			  public byte value;
		}
	 
	 public enum CommodityType{
		// 商品类型：1服务型 2代金券型
			SERVICE_TYPE((byte) 1),
			VOUCHER_TYPE((byte) 2);
			
		 CommodityType(byte value) {
		        this.value = value;
		    }
			  public byte value;
		}
	 
	 public enum ShopWaterType{
		 //流水类型：1服务型商品验证收入 2代金券商品验证流水 3提现冻结支出 4提现拒绝解除冻结入账
		 SERVICE_WATER_TYPE((byte) 1),
		 VOUCHER_WATER_TYPE((byte) 2),
		WITHDRAW_WATER_TYPE((byte) 3),
		REFUN_WATER_TYPE((byte) 4);
			
		 ShopWaterType(byte value) {
		        this.value = value;
		    }
			  public byte value;
		}
	 
	 public enum TabType{
		 //标签类目：1颜值 2身材 3服务
		 FACE_TYPE((byte) 1),
		 FIGURE_TYPE((byte) 2),
		 SERVIC_TYPE((byte) 3);
			
		 TabType(byte value) {
		        this.value = value;
		    }
			  public byte value;
		}
	 
	 public enum ShopWokerCreator{
		 //商店员工创建者：1商店 2用户评价创建
		 SHOP_CREATE((byte) 1),
		 USER_CREATE((byte) 2);
		 ShopWokerCreator(byte value) {
		        this.value = value;
		    }
			  public byte value;
		}
	 
	 public enum ShopWokerType{
		 //员工类型：1管理员 2部长 3技师
		 MANAGE_TYPE((byte) 1),
		 MINISTER_TYPE ((byte) 2),
		 ARTIFICER_TYPE((byte) 3);
		 ShopWokerType(byte value) {
		        this.value = value;
		    }
			  public byte value;
		}
	 
	 
	 public enum PlatformOrderStatue {
		//订单状态：1未支付 2已支付 
		 NOT_PAY_MONEY((byte) 1), 
		 HAS_PAY_MONEY((byte) 2); 
	 
		 PlatformOrderStatue(byte value) {
	            this.value = value;
	        }
	        public byte value;
	    }
	 
	 public enum UserWaterType {
		//奖罚类型：1单人订单核销奖励经验 2拼团订单核销奖励经验 3单人订单评价奖励vip 4拼团订单评价奖励vip 5拼团订单扣经验 6拼团订单扣vip 7邀请好友奖励vip 8邀请好友奖励平台抵用券  9vip充值  10后台直接修改vip天数
		 SIGN_REWARD_POINTS((byte) 1), 
		 TEAM_REWARD_POINTS((byte) 2), 
		 SIGN_REWARD_VIP((byte) 3),
		 TEAM_REWARD_VIP((byte) 4),
		 TEAM_PUNISH_POINTS((byte) 5),
		 TEAM_PUNISH_VIP((byte) 6),
		 INVITE_REWARD_VIP((byte) 7),
		 INVITE_REWARD_VOUCHER((byte) 8),
		 VIP_RECHARGE((byte) 9),
		 BACK_OPERATE_VIP((byte) 10); 
	 UserWaterType(byte value) {
	            this.value = value;
	        }
	        public byte value;
	    }
	 
	 public enum SanctionType {
		//奖罚类型：1单人订单核销奖励经验 2拼团订单核销奖励经验 3单人订单评价奖励vip 4拼团订单评价奖励vip 5拼团订单扣经验 6拼团订单扣vip 7邀请好友奖励vip 8邀请好友奖励平台抵用券
		 SIGN_REWARD_POINTS((byte) 1), 
		 TEAM_REWARD_POINTS((byte) 2), 
		 SIGN_REWARD_VIP((byte) 3),
		 TEAM_REWARD_VIP((byte) 4),
		 TEAM_PUNISH_POINTS((byte) 5),
		 TEAM_PUNISH_VIP((byte) 6),
		 INVITE_REWARD_VIP((byte) 7),
		 INVITE_REWARD_VOUCHER((byte) 8);
		  
		 
	 SanctionType(byte value) {
		            this.value = value;
		       }
		 public byte value;
	 }
	 
	 public enum MsgType {
			//消息类型：101订单核销    102订单退款     201会员通知    301平台活动通知
		 	 CONSUM_ORDER_MSG(101), 
			 RETURN_ORDER_MSG(102),
			 MEMBER_MSG(201),
			 AD_ACTIVITY_MSG(301); 
		 MsgType(int value) {
		            this.value = value;
		        }
		        public int value;
		    }
	 public enum MsgJumpType {
			//跳转类型：1内容详情 2链接
			 JUMP_TO_CONTENT_DETAIL((byte) 1), 
			 JUMP_TO_URL((byte) 2); 
		 
		 MsgJumpType(byte value) {
		            this.value = value;
		        }
		        public byte value;
		    }
	 public enum MsgContentType {
			//内容类型：1纯文字 2文字加图片 3文字+图片+链接
			 ONLY_WORDS((byte) 1), 
			 WORDS_ADD_IMAGE((byte) 2),
			 WORDS_ADD_IMAGE_ADD_URL((byte) 3); 
		 MsgContentType(byte value) {
		            this.value = value;
		        }
		        public byte value;
		    }
	 
	 
	 public enum ShopReleaseCommodityOperate {
		//操作状态：1发布新品 2修改商品 3重新上架 4延时
		 RELEASE_COMMODITY((byte) 1), 
		 UPDATE_COMMODITY((byte) 2),
		 AFRESH_ONLINE((byte) 3); 
		 ShopReleaseCommodityOperate(byte value) {
		            this.value = value;
		        }
		        public byte value;
		    }
	 
	 public enum VersionAppType {
	    	/*
			 * 版本更新app类型, 1：android(com.reds.tanxing)：a1.0,
			 * 2：ios公司号(13113637993@163.com)(com.reds.tanxingcompany): c1.0,
			 */
	    	VERSION_ANDROID((byte) 1),
	    	VERSION_IOS_C((byte) 2);
	    	
	    	VersionAppType(byte value) {
	    		this.value = value;
	    	}
	    	
	    	public byte value;
	    }
	 
	 public enum TeamOrderIsEndType {
			//结束状态：1未结束 2时间自动到期结束 3商品强制下架强制结束
			 NOT_END((byte) 1), 
			 AOTU_END((byte) 2),
			 FORCE_END((byte) 3); 
		 TeamOrderIsEndType(byte value) {
			            this.value = value;
			        }
			        public byte value;
			    }
	 
	 public enum CommodityAttributeType {
	    	/**
	    	 * 商品属性：1样品（审核商品） 2发售商品
	    	 */
		 	SAMPLE((byte) 1),
		 	QUALITY_GOOD((byte) 2);
	    	
		 CommodityAttributeType(byte value) {
	    		this.value = value;
	    	}
	    	
	    	public byte value;
	    }
}
