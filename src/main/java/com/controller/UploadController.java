package com.controller;

import com.common.utils.*;
import com.constant.ErrorCode;
import com.ibm.icu.util.Calendar;
import com.pojo.Material;
import com.pojo.User;
import com.pojo.UserToken;
import com.service.IMaterialSvc;
import com.service.IUserSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/upload")
public class UploadController {
	private User user;
	@Resource
	IUserSvc userSvcImpl;
	UserToken userToken;
	static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	// 白名单文件类型
	// key 文件后缀；value 文件类型 1 图片，2 音频，3视频，4其他
	private Map<String, String> whitelistMap=new HashMap() {
		{
			put("jpg","1");
			put("jpeg","1");
			put("png","1");
		}
	};

	// 黑名单文件类型
	private Set<String> blacklistSet;
	//水印图片路径
	@Value("${file.iconPath}")
	private String iconPath = "";
	//水印图片路径
	@Value("${file.newIconPath}")
	private String newIconPath = "";
	// 文件存储基础路径
	@Value("${file.basePath}")
	private String fileBasePath = "";
	// 文件类型白名单
	@Value("${file.whitelist}")
	private String fileWhiteList = "jpg-1,png-1";
	// 小图尺寸
	@Value("${image.small.size}")
	private String imageSmallSize = "50x50,150x150";
	
	// 是否允许不用登陆直接上传文件开关 0 不允许，1允许。迎合动态分享功能，需要匿名评论并且可以上传文件需求。张剑提出的要求
	@Value("${file.unAuth}")
	private String FILE_UNAUTH = "0";

	@Autowired
	UserInfoUtil userInfoUtil;

	@Resource
	IMaterialSvc materialSvcImpl;

	@RequestMapping(value = "/multiUpload_UnAuth_2016_01_08")
	@ResponseBody
	public Map<String, Object> multiUpload_UnAuth(HttpServletRequest request, ModelMap model) {
		// 返回上传文件结果至用户
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取用户信息
		 userToken = userInfoUtil.getUserToken(request);
		if (userToken == null) {
		}

		if (FILE_UNAUTH.equals("1")) {
			userToken = new UserToken();
			userToken.setUserId(0L);// 默认匿名用户上传
		} else {
			// 提示上传失败，用户没有认证
			map.put("errorCode", 2202);
			map.put("msg", "上传失败，用户没有认证");
			return map;
		}

		List<UploadResult> uploadResultList = new ArrayList<UploadResult>();
		DefaultMultipartHttpServletRequest dmRequest = (DefaultMultipartHttpServletRequest) request;
		MultiValueMap<String, MultipartFile> fileMap = dmRequest.getMultiFileMap();
		Set<String> set = fileMap.keySet();

		// 失败数量
		int failCount = 0;
		for (String str : set) {
			String gt = dmRequest.getParameter(str + "_gatherTime");// 前端传过来的文件采集时间
			String m_id = dmRequest.getParameter(str + "_materialId");// 前端传递过来的文件id
			String imgIsWater = dmRequest.getParameter("imgIsWater");// 前端传递过来的imgIsWater
			String m_type = dmRequest.getParameter(str + "_type");// 前端传递过来的文件id

			List<MultipartFile> mfList = fileMap.get(str);
			for (MultipartFile mf : mfList) {
				String origialFileName = mf.getOriginalFilename();
				if (origialFileName == null || origialFileName.equals("")) {
					// 没有选择文件的，直接跳过
					continue;
				}
				Date gatherTime = null;
				if (gt != null && !"".equals(gt)) {
					try {
						long time = Long.parseLong(gt);
						if (time > 0) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(time);
							gatherTime = calendar.getTime();
						}
					} catch (NumberFormatException e) {
						logger.error(e.getMessage(), e);
					}
				}
				boolean ret = saveFile(mf,  gatherTime, uploadResultList, m_id,imgIsWater, m_type);
				if (!ret) {
					failCount++;
				}
			}
		}

		map.put("uploadResultList", uploadResultList);
		if (failCount == 0 && uploadResultList.size() > 0) {
			map.put("errorCode", 1);
			map.put("msg", "上传成功");
		} else if (uploadResultList.size() == failCount) {
			map.put("errorCode", 2201);
			map.put("msg", "上传失败");
		} else {
			map.put("errorCode", 2203);
			map.put("msg", "部分上传成功");
		}
		return map;
	}

	@RequestMapping(value = "/multiUpload")
	@ResponseBody
	public Map<String, Object> multiUpload(HttpServletRequest request, ModelMap model) {
		// 返回上传文件结果至用户
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取用户信息
		 userToken = userInfoUtil.getUserToken(request);
		if (userToken == null) {
			// 提示上传失败，用户没有认证
			map.put("errorCode", ErrorCode.USER_LOGIN_ERROR_UNLOGIN);
			map.put("msg", "上传失败，用户没有登录");
			return map;
		}
		logger.info("upload userId=" + userToken.getUserId());
		List<UploadResult> uploadResultList = new ArrayList<UploadResult>();
		DefaultMultipartHttpServletRequest dmRequest = (DefaultMultipartHttpServletRequest) request;
		MultiValueMap<String, MultipartFile> fileMap = dmRequest.getMultiFileMap();
		Set<String> set = fileMap.keySet();

		// 失败数量
		int failCount = 0;
		for (String str : set) {
			String gt = dmRequest.getParameter(str + "_gatherTime");// 前端传过来的文件采集时间
			String m_id = dmRequest.getParameter(str + "_materialId");// 前端传递过来的文件id
			String m_type = dmRequest.getParameter(str + "_type");// 前端传递过来的文件类型
			String imgIsWater = dmRequest.getParameter("imgIsWater");// 前端传递过来的imgIsWater

			List<MultipartFile> mfList = fileMap.get(str);
			for (MultipartFile mf : mfList) {
				String origialFileName = mf.getOriginalFilename();
				if (origialFileName == null || origialFileName.equals("")) {
					// 没有选择文件的，直接跳过
					continue;
				}
				Date gatherTime = null;
				if (gt != null && !"".equals(gt)) {
					try {
						long time = Long.parseLong(gt);
						if (time > 0) {
							Calendar calendar = Calendar.getInstance();
							calendar.setTimeInMillis(time);
							gatherTime = calendar.getTime();
						}
					} catch (NumberFormatException e) {
						logger.error(e.getMessage(), e);
					}
				}
				boolean ret = saveFile(mf, gatherTime, uploadResultList, m_id,imgIsWater, m_type);
				if (!ret) {
					failCount++;
				}
			}
		}
		String resultStr = "";
		for (UploadResult ur : uploadResultList) {
			resultStr = resultStr + ur.origialFileName + ";" + ur.materialId + ";" + ur.isSuccess;
		}
		logger.info("failCount=" + failCount + " result:" + resultStr);
		map.put("uploadResultList", uploadResultList);
		if (failCount == 0 && uploadResultList.size() > 0) {
			map.put("errorCode", 1);
			map.put("msg", "上传成功");
		} else if (uploadResultList.size() == failCount) {
			map.put("errorCode", 2201);
			map.put("msg", "上传失败");
		} else {
			map.put("errorCode", 2203);
			map.put("msg", "部分上传成功");
		}
		return map;
	}

	@RequestMapping(value = "/saveRecord")
	@ResponseBody
	public Map<String, Object> saveRecord(@RequestParam("fileUrl") String fileUrl,
			@RequestParam("origialFileName") String origialFileName, @RequestParam("gatherTime") String gatherTime,
			@RequestParam("fileType") String fileType, @RequestParam(value = "width", required = false) Integer width,
			@RequestParam(value = "height", required = false) Integer height, HttpServletRequest request) {
		logger.info("wps saveRecord, fileUrl=" + fileUrl + ", origialFileName=" + origialFileName + ", fileType="
				+ fileType + ", width=" + width + ", height=" + height + ", gatherTime=" + gatherTime);
		// 返回保持结果至用户
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取用户信息
		// userToken = userInfoUtil.getUserToken(request);
		if (userToken == null) {
			// 提示上传失败，用户没有认证
			map.put("errorCode", 2202);
			map.put("msg", "文件记录保持失败，用户没有认证");
			return map;
		}

		boolean isSuccess = false;
		String materialId = "";
		try {
			materialId = fileUrl.replace("/", "_").replace(".", "_");
			Material m = materialSvcImpl.getMaterial(materialId).get(0);
			if (m != null) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("materialId", m.getMaterialId());
				data.put("origialFileName", m.getMaterialName());
				map.put("data", data);
				map.put("errorCode", 1);
				map.put("msg", "记录保存成功");
				return map;
			}

			// 保存附件记录至db
			Material material = new Material();
			DateFormat df = new SimpleDateFormat("yyyyMMdd");

			Date gt = null;
			try {
				long time = Long.parseLong(gatherTime);
				if (time > 0) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(time);
					gt = calendar.getTime();
				}
			} catch (NumberFormatException e) {
				logger.error(e.getMessage(), e);
			}

			if (gt == null) {
				gt = new Date();
			}
			// if(fileType.equals("3")){
			// saveRecordVideoCover(fileUrl);//视频类型的，需要从wsp下载视频封面
			// }
			material.setGatherTime( new Timestamp(gt.getTime()));
			material.setGatherDay(df.format(gt));
			material.setMaterialId(materialId);
			material.setMaterialName(origialFileName);
			material.setIsdel((byte) 1);
			material.setMaterialFormate(Byte.valueOf(fileType));
			Date date = new Date();
			material.setCreateTime( new Timestamp(date.getTime()));
			material.setLastUpdateTime( new Timestamp(date.getTime()));
			if (width != null) {
				material.setWidth(width);
			} else {
				material.setWidth(0);
			}
			if (height != null) {
				material.setHeight(height);
			} else {
				material.setHeight(0);
			}
			materialSvcImpl.insertMaterial(material);
			isSuccess = true;
		} catch (Exception e) {
			// 失败记录
			isSuccess = false;
			logger.error(e.getMessage(), e);
		}
		Map<String, String> data = new HashMap<String, String>();
		data.put("materialId", materialId);
		data.put("origialFileName", origialFileName);
		map.put("data", data);
		if (isSuccess) {
			map.put("errorCode", 1);
			map.put("msg", "记录保存成功");
		} else {
			map.put("errorCode", 2203);
			map.put("msg", "记录保存失败");
		}
		return map;
	}

	

	private boolean saveFile(MultipartFile mf, Date gatherTime,
			List<UploadResult> uploadResultList, String m_id, String imgIsWater,String m_type) {
		boolean isSuccess = false;
		// -- 生成目标文件id和路径名称 --
		String origialFileName = mf.getOriginalFilename();
		// 文件后缀
		String suffix = origialFileName.substring(origialFileName.lastIndexOf(".") + 1);
		// 文件后缀忽略大小写比较
		suffix = suffix.toLowerCase();
		// 检查文件后缀是否为黑名单
		if (this.isBlackList(suffix)) {
			// 文件为黑名单，禁止上传
			isSuccess = false;
			uploadResultList.add(new UploadResult(origialFileName, "", 3));
		} else {
			// 检查文件后缀是否为白名单
			if (this.isWhiteList(suffix)) {
				// 生成用户下面唯一资料名称
				String fileName = FileUtil.generalFileName();
				// 文件id
				String materialId = null;
				if (m_id != null && !m_id.equals("")) {
					materialId = m_id;
				} else {
					// 生成用户下唯一资料Id，格式为：userId_资料名称_文件后缀
					materialId = FileUtil.generalMaterialId(userToken.getUserId(), fileName, suffix);
				}
				// 文件相对路径
				String relativePath = materialId.replaceFirst("_", "/").replace("_", ".");
				// 文件全路径
				String destFilePath = FileUtil.buildFilePath(fileBasePath, relativePath);

				try {
					// 保存目标文件
					FileUtil.copyFile(mf.getInputStream(), destFilePath);


					//给上传的图片打水印  iconPath：水印图片路径 ，srcImgPath：源图片路径，targerPath：目标图片路径
					// imgIsWater:上传的图片是否打水印 1否2是
					if (!StringUtils.isEmpty(imgIsWater) && imgIsWater.equals("1")) {
						int[] imageWidthHeight = ImageWarterUtil.getImageWidthHeight(destFilePath);
						logger.info("wight=" + imageWidthHeight[0] +",height=" + imageWidthHeight[1]);
						ImageWarterUtil.setImageMarkOptions(1f, (imageWidthHeight[0]+imageWidthHeight[1])/200+10,
								(imageWidthHeight[0]+imageWidthHeight[1])/200, null, null);
					}else{
						user=userSvcImpl.getUserById((int)userToken.getUserId());
						//获取原图长度宽度
						int[] imageWidthHeight = ImageWarterUtil.getImageWidthHeight(destFilePath);
						logger.info("wight=" + imageWidthHeight[0] +",height=" + imageWidthHeight[1]);
						ImageWarterUtil.setImageMarkOptions(1f, (imageWidthHeight[0]+imageWidthHeight[1])/200+10,
								(imageWidthHeight[0]+imageWidthHeight[1])/200, null, null);
						ImageWarterUtil.addWaterMark(destFilePath,destFilePath,"http://www.youwen520.com");
						//ImageWarterUtil.markImageByText(iconPath, destFilePath, destFilePath);
						ImageWarterUtil.markImageByIcon(user.getUserId(),newIconPath,iconPath, destFilePath, destFilePath,true,0);
					}

					// 文件类型判断
					String fileType = whitelistMap.get(suffix);
					if (m_type != null) {
						fileType = m_type;
					}
					// 保存附件记录至db
					Material material = new Material();
					// 记录图片截取后的宽高
					int[] widthHeight = null;
					if (fileType.equals("1") || fileType.equals("11")) {
						// 图片类型的话，需要读取图片采集时间,旋转角度等信息
						Object[] infoArray = ImageUtil.getImgDateTime(destFilePath);
						// 如果文件采集时间客户端没有上传，服务器需要读取文件采集时间或最后修改时间
						if (gatherTime == null) {
							if (infoArray[0] != null) {
								// 优先使用文件拍摄时间
								gatherTime = (Date) infoArray[0];
							} else if (infoArray[1] != null) {
								// 期次使用文件最后修改时间
								gatherTime = (Date) infoArray[1];
							}
						}
						String orientation = (String) infoArray[2];
						if (orientation != null) {
							// 图片存在图片旋转信息，则需要重新对图片进行旋转为正常的角度
							if (!orientation.equals("1")) {
								// (6 向左，8向右，1 向上，3向下) 其中1是向上的，其他的都不正常，需要旋转
								rotateImg(destFilePath, destFilePath, orientation);
							}
						}
						// 图片类型的话，需要做图片截取几张不同尺寸的照片
						widthHeight = createSmallImage(destFilePath);
					} 
					// 没有获取到文件拍摄时间，或最后修改时间，就取当前服务器时间作为采集时间
					if (gatherTime == null) {
						gatherTime = new Date();
					}
					DateFormat df = new SimpleDateFormat("yyyyMMdd");
					material.setGatherTime( new Timestamp(gatherTime.getTime()));
					material.setGatherDay(df.format(gatherTime));
					material.setMaterialId(materialId);
					material.setMaterialName(origialFileName);
					material.setIsdel((byte) 1);
					material.setMaterialFormate(Byte.valueOf(fileType));
					Date date = new Date();
					material.setCreateTime( new Timestamp(date.getTime()));
					material.setLastUpdateTime( new Timestamp(date.getTime()));
					// 设置大图片宽和高，如果不是图片，widthHeight==null
					if (widthHeight == null) {
						material.setWidth(0);
						material.setHeight(0);
					} else {
						material.setWidth(widthHeight[0]);
						material.setHeight(widthHeight[1]);
					}

					materialSvcImpl.insertMaterial(material);
					uploadResultList.add(new UploadResult(origialFileName, materialId, 0, material.getWidth(),
							material.getHeight()));
					isSuccess = true;
				} catch (IOException e) {
					// 失败记录
					isSuccess = false;
					uploadResultList.add(new UploadResult(origialFileName, "", 1));
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					// 失败记录
					isSuccess = false;
					uploadResultList.add(new UploadResult(origialFileName, "", 1));
					logger.error(e.getMessage(), e);
				}
			} else {
				// 文件为非白名单，禁止上传
				// 失败记录
				isSuccess = false;
				uploadResultList.add(new UploadResult(origialFileName, "", 2));
			}
		}
		return isSuccess;
	}


	// 对原图进行旋转
	private void rotateImg(String srcImgPath, String destImgPath, String orientation) {
		int degree = 0;
		// 6 向左，8向右，1 向上，3向下
		if (orientation.equals("3")) {
			degree = 180;
		} else if (orientation.equals("6")) {
			degree = 90;
		} else if (orientation.equals("8")) {
			degree = 270;
		} else {
			return;
		}
		try {
			ImageUtil.rotateImg(srcImgPath, destImgPath, degree);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 生成小图
	 * 
	 * @param srcImagePath
	 */
	private int[] createSmallImage(String srcImagePath) {
		int[] widthHeight = null;
		String[] imageSizeArray = imageSmallSize.split(",");
		for (int i = 0; i < imageSizeArray.length; i++) {
			String[] imageSize = imageSizeArray[i].split("x");
			int width = Integer.parseInt(imageSize[0]);
			int height = Integer.parseInt(imageSize[1]);
			String str = "";
			if (i == 0) {
				str = "small";
			} else {
				str = "big";
			}
			String destImagePath = srcImagePath.replace(".", "_" + str + ".");
			try {
				int[] w_h = ImageUtil.resizeImage(srcImagePath, destImagePath, width, height);
				if (str.equals("big")) {
					widthHeight = w_h;
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return widthHeight;
	}

	/**
	 * 生成视频小图
	 * 
	 * @param srcImagePath
	 */
	private void createVideoSmallImage(String srcImagePath, String suffix) {
		String[] imageSizeArray = imageSmallSize.split(",");
		for (int i = 0; i < imageSizeArray.length; i++) {
			String imgSize = imageSizeArray[i];
			String str = "";
			if (i == 0) {
				str = "small";
			} else {
				str = "big";
			}
			// 缩略图文件后缀
			String imgFilePath = srcImagePath.replace("." + suffix, "_" + str + ".jpg");
			boolean ret = CommandUtil.exec(srcImagePath, imgFilePath, imgSize);
		}
	}

	/**
	 * 对上传文件后缀进行校验，是否在白名单中
	 * 
	 * @param suffix
	 * @return
	 */
	private boolean isWhiteList(String suffix) {
		if (whitelistMap == null) {
			String[] strArray = fileWhiteList.split(",");
			whitelistMap = new HashMap<String, String>();
			for (String str : strArray) {
				String[] array = str.split("-");
				whitelistMap.put(array[0], array[1]);
			}
		}
		if (whitelistMap.containsKey(suffix)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 对上传文件后缀进行校验，是否在黑名单中
	 * 
	 * @param suffix
	 * @return
	 */
	private boolean isBlackList(String suffix) {
		if (blacklistSet == null) {
			blacklistSet = new HashSet<String>();
			blacklistSet.add("jsp");
			blacklistSet.add("asp");
			blacklistSet.add("aspx");
			blacklistSet.add("php");
			blacklistSet.add("sh");
			blacklistSet.add("ksh");
			blacklistSet.add("csh");
			blacklistSet.add("bash");
			blacklistSet.add("py");
		}
		if (blacklistSet.contains(suffix)) {
			return true;
		} else {
			return false;
		}
	}

	// 文件上传结果
	class UploadResult {
		private String materialId;// 文件唯一Id
		private String origialFileName;// 文件原始名称
		private int isSuccess;// 文件是否上传成功 0成功，1失败
		private int width;// 大图片宽
		private int height;// 大图片高

		public UploadResult(String origialFileName, String materialId, int isSuccess) {
			this.origialFileName = origialFileName;
			this.materialId = materialId;
			this.isSuccess = isSuccess;
			this.width = 0;
			this.height = 0;
		}

		public UploadResult(String origialFileName, String materialId, int isSuccess, int width, int height) {
			this.origialFileName = origialFileName;
			this.materialId = materialId;
			this.isSuccess = isSuccess;
			this.width = width;
			this.height = height;
		}

		public String getMaterialId() {
			return materialId;
		}

		public void setMaterialId(String materialId) {
			this.materialId = materialId;
		}

		public String getOrigialFileName() {
			return origialFileName;
		}

		public void setOrigialFileName(String origialFileName) {
			this.origialFileName = origialFileName;
		}

		public int getIsSuccess() {
			return isSuccess;
		}

		public void setIsSuccess(int isSuccess) {
			this.isSuccess = isSuccess;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}
}
