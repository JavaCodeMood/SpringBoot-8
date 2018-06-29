package com.qingguohd.red.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kakazhuan.common.BaseController;
import com.kakazhuan.common.Pager;
import com.kakazhuan.dao.redis.RedisUtil;
import com.kakazhuan.domain.KaApprenticeEventConfig;
import com.kakazhuan.domain.KaApprenticeEventRecord;
import com.kakazhuan.domain.KaMember;
import com.kakazhuan.domain.KaMemberApprenticeCash;
import com.kakazhuan.domain.KaMemberApprenticeIssueRecord;
import com.kakazhuan.domain.KaMemberGoldRecord;
import com.kakazhuan.domain.KaMemberSubCount;
import com.kakazhuan.dto.ApprenticeTopNewsDto;
import com.kakazhuan.dto.KaApprenticeContribuDto;
import com.kakazhuan.dto.KaApprenticeRewardDto;
import com.kakazhuan.dto.PrivilegeDto;
import com.kakazhuan.dto.request.ApprenticeTopNews;
import com.kakazhuan.front.service.KaApprenticeEventRecordService;
import com.kakazhuan.front.service.KaMemberService;
import com.kakazhuan.front.web.vo.json.JSONResponseBody;
import com.kakazhuan.util.validate.Validator;

import net.sf.json.JSONArray;

/**
 * @author: YuGenHai
 * @name: KaApprenticesNewController.java
 * @creation: 2018年6月5日 上午10:45:38
 * @notes: 4.1.1 收徒新活动
 */
@Controller
@RequestMapping(value = "apprenticesnew", produces = "application/json;charset=utf-8")
public class KaApprenticesNewController {

	
	
	@Resource
	private KaApprenticeEventRecordService kaApprenticeEventRecordService;// 收徒相关

	@Resource
	private KaMemberService kaMemberService;// 用户注册表

	@Resource
	private RedisUtil redisUtil;
	

	/**
	 * @author yugenhai
	 * Returns a string representation of the integer argument as an
     * unsigned integer in base&nbsp;16.
	 */
	private static final long APPRENTICES_HEX_1 = 0x01;
	private static final long APPRENTICES_HEX_2 = 0x02;
	private static final long APPRENTICES_HEX_24 = 0x18;
	private static final long APPRENTICES_HEX_48 = 0x30;
	/**
	 * 特权师傅
	 */
	private static final String APPRENTICESTEQUAN = "kamember_tequan_";
	/**
	 * 邀请码
	 */
	private static final String APPRENTICESINVITECODE = "kamember_inviteCode_";
	/**
	 * 注册时间
	 */
	private static final String APPRENTICESREGTIME = "kamember_regTime_";
	/**
	 * 收徒金额
	 */
	private static final String APPRENTICESCASH = "apprentices_Cash_";
	
	/**
	 * 活动开始时间
	 */
	private static final String EVENTSTARTTIME = "apprentices_event_startdate_";
	/**
	 * 收徒奖励
	 */
	private static final String APPRENTICESREWARD = "apprentices_reward_";
	/**
	 * 徒弟贡献
	 */
	private static final String APPRENTICESCONTRIBU = "apprentices_contribu_";
	/**
	 * 徒弟表
	 */
	private static final String APPRENTICESINVITECOUNT = "apprentices_invite_count_";
	/**
	 * 用户活动时效
	 */
	private static final String APPRENTICESEVENTREND = "apprentices_event_endTime_";
	/**
	 * 231弹窗
	 */
	private static final String NEWAPPRENTICES231 = "new_apprentice_231_";
	/**
	 * 233弹窗
	 */
	private static final String NEWAPPRENTICES233 = "new_apprentice_233_";
	/**
	 * 232弹窗
	 */
	private static final String NEWAPPRENTICES232 = "new_apprentice_232_";
	/**
	 * 置顶消息轮播
	 */
	private static final String ISSUERECORDRANDOMTOP = "new_apprentice_price";
	
	/**
	 * 收徒中心-- 邀请收徒
	 * @author yugenhai
	 * @param request
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("apprenticedetailsa")
	public String apprenticeDetailsA(HttpServletRequest request, ModelMap map) {
		Long uid = Long.valueOf(request.getParameter("uid"));
		if(null == uid) {
			return "error";
		}
		logger.info("收徒中心 ================>开始  parameters 用户id : " +  uid +"_"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		long start = System.currentTimeMillis();
		try {
			String privileKey = APPRENTICESTEQUAN + uid;
			PrivilegeDto privilegeDto = redisUtil.getValue(privileKey, PrivilegeDto.class);
			if(null == privilegeDto) {
				privilegeDto = kaMemberService.getPrivilegeData(uid);
				redisUtil.setValue(privileKey, 5*60 , privilegeDto);
			}
			KaApprenticeEventConfig eventConfig = new KaApprenticeEventConfig();
			String eventStartKey = EVENTSTARTTIME;
			Date date = redisUtil.getValue(eventStartKey, Date.class);
			if (null == date) {
				eventConfig = kaApprenticeEventRecordService.getApprenticeEventConfig();
				redisUtil.setValue(eventStartKey, 72 * 60 * 60, eventConfig.getEventStartTime());
			}else {
				eventConfig.setEventStartTime(date);
			}
			String inviteCountKey = APPRENTICESINVITECOUNT + uid;
			KaMemberSubCount inviteCount = redisUtil.getValue(inviteCountKey, KaMemberSubCount.class);
			if(null == inviteCount) {
				inviteCount = kaApprenticeEventRecordService.getApprenticeInviteConut(uid);
				redisUtil.setValue(inviteCountKey, 5 * 60, inviteCount);
			}
			Date eventTime = eventConfig.getEventStartTime();
			String inviteCodeKey = APPRENTICESINVITECODE + uid;
			String regTimeKey = APPRENTICESREGTIME + uid;
			String inviteValue = redisUtil.getValue(inviteCodeKey, String.class);
			Date regDate = redisUtil.getValue(regTimeKey, Date.class);
			if(Validator.isStrEmpty(inviteValue) || null == regDate) {
				KaMember kaMember = kaMemberService.selectByUid(uid);
				inviteValue = kaMember.getInviteCode();
				regDate = kaMember.getRegistTime();
				redisUtil.setValue(inviteCodeKey, inviteValue);
				redisUtil.setValue(regTimeKey, regDate);
			}
			Date regTime = regDate;
			String eventRecordKey = APPRENTICESEVENTREND + uid;
			KaApprenticeEventRecord eventRecord = redisUtil.getValue(eventRecordKey, KaApprenticeEventRecord.class);
			if(null == eventRecord) {
				eventRecord =  kaApprenticeEventRecordService.getByUidForEventRecord(uid);
				redisUtil.setValue(eventRecordKey, 10*60*60, eventRecord);
			}
			Date eventDate = null;
			if(null != eventRecord) {
				eventDate = eventRecord.getUserEndTime();
			}else {
				eventDate = new Date();
				KaApprenticeEventRecord record = new KaApprenticeEventRecord();
				int add = addApprenticeEventRecord(record,uid);
				logger.info("当前用户：" + uid + " 是老用户，数据写入状态：" + add);
			}
			Date nowUserTime = new Date();
			String cashKey = APPRENTICESCASH + uid;
			Double cashValue = redisUtil.getValue(cashKey, Double.class);
			if(null == cashValue) {
				KaMemberApprenticeCash apprenticeCash = kaApprenticeEventRecordService.getApprenticeCashByUid(uid);
				cashValue = apprenticeCash.getRemainPrice();
				redisUtil.setValue(cashKey, 3 * 60, cashValue);
			}
			if (regTime.after(eventTime) && isTimeOver(eventDate, nowUserTime)) {
				if (inviteCount.getSubCount() <= 2) {
					map.addAttribute("ads", "2日内收3位徒弟赚24元，可立即提现");
					map.addAttribute("inviteCode", inviteValue);
					if("0.0".equals(uidFlushTime(eventDate, nowUserTime)) ) {
						map.addAttribute("state", "2");
						map.addAttribute("takeMoney", cashValue);
						String state1 = redisUtil.getValue(NEWAPPRENTICES231 + uid, String.class);
						if(Validator.isStrEmpty(state1)) {
							map.addAttribute("msg", "no");
							redisUtil.setValue(NEWAPPRENTICES231 + uid, "1");
						}
						map.addAttribute("privilege", privilegeDto);
						logger.info("收徒中心 ================>失效一分钟结束  parameters 用户id : " +  uid + ", 参数：" + map.toString());
						return "apprentice/inviteapprentice";
					}else {
						map.addAttribute("restTime", uidFlushTime(eventDate, nowUserTime));
					}
					map.addAttribute("takeMoney", cashValue);
					map.addAttribute("state", "1");
				} else if (inviteCount.getSubCount() >= 3) {
					map.addAttribute("ads", "每收一名徒弟赚5元，可立即提现");
					map.addAttribute("inviteCode", inviteValue);
					map.addAttribute("takeMoney", cashValue);
					map.addAttribute("state", "3");
					String state3 = redisUtil.getValue(NEWAPPRENTICES233 + uid, String.class);
					if(Validator.isStrEmpty(state3)) {
						map.addAttribute("msg", "yes");
						redisUtil.setValue(NEWAPPRENTICES233 + uid, "3");
					}
				}
			} else if (regTime.after(eventTime) && !isTimeOver(eventDate, nowUserTime) && inviteCount.getSubCount() < 2) {
				map.addAttribute("ads", "首次收2位徒弟赚14元，当天即可提现");
				map.addAttribute("inviteCode", inviteValue);
				map.addAttribute("takeMoney", cashValue);
				map.addAttribute("state", "2");
				String state2 = redisUtil.getValue(NEWAPPRENTICES232 + uid, String.class);
				if(Validator.isStrEmpty(state2)) {
					map.addAttribute("msg", "no");
					redisUtil.setValue(NEWAPPRENTICES232 + uid, "2");
				}
			} else if (inviteCount.getSubCount() < 2) {
				map.addAttribute("ads", "首次收2位徒弟赚14元，当天即可提现");
				map.addAttribute("inviteCode", inviteValue);
				map.addAttribute("takeMoney", cashValue);
				map.addAttribute("state", "2");
			} else {
				map.addAttribute("ads", "每收一名徒弟赚5元，可立即提现");
				map.addAttribute("inviteCode", inviteValue);
				map.addAttribute("takeMoney", cashValue);
				map.addAttribute("state", "3");
			}
			map.addAttribute("privilege", privilegeDto);
			List<ApprenticeTopNewsDto> topNewsDtos = new ArrayList<ApprenticeTopNewsDto>();
			String topNewKey = ISSUERECORDRANDOMTOP;
			List<ApprenticeTopNews> topKey = (List<ApprenticeTopNews>)redisUtil.getValue(topNewKey, List.class);
			if (null != topKey) {
				map.addAttribute("topNews", JSONArray.fromObject(topKey));
			}else {
				List<ApprenticeTopNews> topNews = kaApprenticeEventRecordService.getApprenticeIssueRecordRandomTop();
				if(null != topNews && topNews.size() > 0) {
					for(ApprenticeTopNews topNews2 : topNews) {
						KaMember topKamember = kaMemberService.selectByUid(Long.valueOf(topNews2.getParent1()));
						ApprenticeTopNewsDto newsDto = new ApprenticeTopNewsDto();
						newsDto.setNickName(topKamember.getNickname());
						newsDto.setIssuePrice(String.valueOf((int)Math.ceil(topNews2.getIssuePrice())));
						newsDto.setMsg("刚刚");
						topNewsDtos.add(newsDto);
					}
					if (null != topNewsDtos && topNewsDtos.size() > 0) {
						redisUtil.setValue(topNewKey, 2 * 60 * 60, topNewsDtos);
					}
				}
				map.addAttribute("topNews", JSONArray.fromObject(topNewsDtos));
			}
		} catch (Exception e) {
			logger.error("徒弟活动邀请三个阶段查询失败："  + e);
			return "error";
		}
		long end = System.currentTimeMillis();
		logger.info("收徒中心 ================>结束  parameters 用户id : " +  uid +"_"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ ", 参数：" + map.toString());
		logger.info("结束时间：" + (end - start));
		return "apprentice/inviteapprentice";
	}
	
	

	
	/**
	 * @author yugenhai
	 * 老用户进入收徒中心
	 * @param eventRecord
	 * @param uid
	 * @return
	 */
	private int addApprenticeEventRecord(KaApprenticeEventRecord eventRecord, Long uid) {
		eventRecord.setUserAcceptTime(new Date());
		eventRecord.setUserEndTime(new Date());
		eventRecord.setState("0");
		eventRecord.setUid(uid);
		eventRecord.setEventId(1L);
		eventRecord.setCreateTime(new Date());
		int add = kaApprenticeEventRecordService.addApprenticeEventRecord(eventRecord);
		return add;
	}
	
	
	
	/**
	 * 收徒banner提醒
	 * @author yugenhai
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "apprenticestobanner", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String apprenticesToBanner(@RequestParam(value = "uid") Long uid) {
		JSONResponseBody json = new JSONResponseBody();
		try {
			logger.info("收徒banner提醒 ----->开始  parameters 用户id : " +  uid );
			KaApprenticeEventConfig eventConfig = new KaApprenticeEventConfig();
			String key = EVENTSTARTTIME;
			Date date = (Date) redisUtil.getValue(key);
			if (null != date) {
				eventConfig.setEventStartTime(redisUtil.getValue(key, Date.class));
			} else {
				eventConfig = kaApprenticeEventRecordService.getApprenticeEventConfig();
				redisUtil.setValue(key, 72 * 60 * 60, eventConfig.getEventStartTime());
			}
			KaMember kaMember = kaMemberService.selectByUid(uid);
			Date regTime = kaMember.getRegistTime();
			Date eventTime = eventConfig.getEventStartTime();
			KaMemberSubCount inviteCount = kaApprenticeEventRecordService.getApprenticeInviteConut(uid);
			Date nowUserTime = new Date();
			KaApprenticeEventRecord eventRecord = kaApprenticeEventRecordService.getByUidForEventRecord(uid);
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String restTime = null;
			Date eventDate = null;
			if(null != eventRecord) {
				eventDate = eventRecord.getUserEndTime();
			}else {
				eventDate = new Date();
			}
			if(null != eventRecord) {
				restTime = format.format(eventRecord.getUserEndTime());
			}
			if (regTime.after(eventTime) && isTimeOver(eventDate, nowUserTime)) {
				if (inviteCount.getSubCount() < 3) {
					json.addDataValue("banner", "1");// 2日内收3位徒弟赚24元，可立即提现
					json.addDataValue("restTime", restTime);
				}else {
					json.addDataValue("banner", "3");
				}
			} else if (inviteCount.getSubCount() >= 0 && inviteCount.getSubCount() < 2) {
				json.addDataValue("banner", "2");// 首次收2位徒弟赚14元，当天即可提现
			} else {
				json.addDataValue("banner", "3");
			}
			logger.info("收徒banner提醒 ----->结束  parameters 用户id : " +  uid + " ,参数： " + json.toString());
			return json.toString();
		} catch (Exception e) {
			logger.error("收徒banner提醒返回异常 ：" + e);
			return JSONResponseBody.ERROR_CODE_500;
		}
	}

	
	/**
	 * 收徒奖励
	 * @author yugenhai
	 * @param parent1
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "apprenticesreward", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String apprenticesReward(@RequestParam(value = "parent1") Long parent1,
			@RequestParam(value = "startNum" , defaultValue = "1") Integer page) {
		JSONResponseBody json = new JSONResponseBody();
		try {
			logger.info("徒弟奖励列表 ----->开始  parameters 用户id : " +  parent1 );
			String key = APPRENTICESREWARD + page + "_" + parent1;
			String redisValue = (String) redisUtil.getValue(key);
			if (!Validator.isStrEmpty(redisValue)) {
				return redisValue;
			}
			Pager p = new Pager();
			p.setPageIndex(page);
			List<KaMemberApprenticeIssueRecord> issueRecords = kaApprenticeEventRecordService.getApprenticesByParent1(parent1, p);
			List<KaApprenticeRewardDto> rewardDtos = new ArrayList<KaApprenticeRewardDto>();
			Double currentPriceSum = 0D;
			Double expectedPriceSum = 0D;
			if (null != issueRecords && issueRecords.size() > 0) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				for (KaMemberApprenticeIssueRecord issueRecord : issueRecords) {
					KaMember kaMember = kaMemberService.selectByUid(issueRecord.getUid());
					KaApprenticeRewardDto rewardDto = new KaApprenticeRewardDto();
					rewardDto.setSubUid(String.valueOf(kaMember.getUid()));
					rewardDto.setNickName(kaMember.getNickname());
					rewardDto.setAcceptApprenticeTime(format.format(issueRecord.getCreateTime()));
					rewardDto.setCurrentPrice(String.valueOf(issueRecord.getIssuedPrice()));
					rewardDto.setExpectedPrice(String.valueOf(issueRecord.getIssuePrice()));
					currentPriceSum += issueRecord.getIssuedPrice();
					expectedPriceSum += issueRecord.getIssuePrice();
					rewardDtos.add(rewardDto);
				}
			}
			json.addDataValue("apprenticeNum", issueRecords.size());
			json.addDataValue("currentPriceSum", currentPriceSum);
			json.addDataValue("expectedPriceSum", expectedPriceSum);
			json.addListToData(rewardDtos);
			if (null != rewardDtos && rewardDtos.size() > 0) {
				redisUtil.setValue(key, 5, json.toString());
			}
			logger.info("徒弟奖励列表 ----->结束  parameters 用户id : " +  parent1 + ", 参数：" + json.toString());
			return json.toString();
		} catch (Exception e) {
			logger.error("收徒奖励查询失败：" + e);
			return JSONResponseBody.ERROR_CODE_500;
		}
	}
	
	

	/**
	 * 徒弟贡献
	 * @author yugenhai
	 * @param parent1
	 * @param type
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "apprenticescontribu", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public String apprenticesContribu(@RequestParam(value = "u") Long parent1,
			@RequestParam(value = "t" , defaultValue = "1") Integer type,
			@RequestParam(value = "startNum" , defaultValue = "1") Integer page) {
		JSONResponseBody json = new JSONResponseBody();
		try {
			logger.info("徒弟贡献 ----->开始  parameters 用户id : " +  parent1 + " _ " + page);
			String key = APPRENTICESCONTRIBU + type + "_" + page + "_" + parent1 ;
			String redisValue = (String) redisUtil.getValue(key);
			if (!Validator.isStrEmpty(redisValue)) {
				return redisValue;
			}
			Pager p = new Pager();
			p.setPageIndex(page);
			List<KaMemberGoldRecord> goldRecords = kaApprenticeEventRecordService.getGoldRecordByApprenticeContribu(parent1, type, p);
			List<KaApprenticeContribuDto> contribuDtos = new ArrayList<KaApprenticeContribuDto>();
			Integer goldSum = 0;
			if (null != goldRecords && goldRecords.size() > 0) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				for (KaMemberGoldRecord goldRecord : goldRecords) {
					KaMember kaMember = kaMemberService.selectByUid(goldRecord.getSubUid());
					KaApprenticeContribuDto contribuDto = new KaApprenticeContribuDto();
					contribuDto.setSubUid(String.valueOf(kaMember.getUid()));
					contribuDto.setNickName(kaMember.getNickname());
					contribuDto.setContribuTime(format.format(goldRecord.getCreateTime()));
					contribuDto.setGold(String.valueOf(goldRecord.getGold()));
					goldSum += goldRecord.getGold();
					contribuDtos.add(contribuDto);
				}
			}
			json.addDataValue("goldSum", goldSum);
			json.addListToData(contribuDtos);
			if (null != contribuDtos && contribuDtos.size() > 0) {
				redisUtil.setValue(key, 60, json.toString());
			}
			logger.info("徒弟贡献 ----->结束  parameters 用户id : " +  parent1 + " _ " + json.toString());
			return json.toString();
		} catch (Exception e) {
			logger.error("收徒贡献查询失败：" + e);
			return JSONResponseBody.ERROR_CODE_500;
		}
	}

	
	/**
	 * 用户每次进来刷新过期的时间差
	 * @author yugenhai
	 * @param endDate
	 * @param nowDate
	 * @return
	 */
	private static String uidFlushTime(Date endDate, Date nowDate) {
		long day = 24 * 60 * 60 * 1000;
		long hour = 60 * 60 * 1000;
		long min = 60 * 1000;
		long mills = endDate.getTime() - nowDate.getTime();// 毫秒差
		long days = mills / day;
		long hours = mills % day / hour;
		long mins = mills % day % hour / min;
		long sendHours = 0x00;
		if (days == APPRENTICES_HEX_1) {
			sendHours = APPRENTICES_HEX_24;
		} else if (days == APPRENTICES_HEX_2) {
			sendHours = APPRENTICES_HEX_48;
		}
		String restTime = sendHours + hours + "." + mins;
		LoggerFactory.getLogger(KaApprenticesNewController.class).info("当前用户收徒活动第一阶段剩余时间：" + restTime);
		return restTime;
	}

	
	/**
	 * 是否已经过期2天时间
	 * @author yugenhai
	 * @param regTime
	 * @param nowUserTime
	 * @return
	 */
	private static boolean isTimeOver(Date regTime, Date nowUserTime) {
		String str = uidFlushTime(regTime, nowUserTime);
		if (str.contains("-")) {
			return false;
		}
		return true;
	}

	

}
