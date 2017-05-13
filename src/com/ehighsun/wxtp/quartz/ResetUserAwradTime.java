package com.ehighsun.wxtp.quartz;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import com.ehighsun.wxtp.dao.BaseDao;
import com.ehighsun.wxtp.pojo.User;


//重置用户每天的奖品次数
@Component("resetUserAwradTime")
public class ResetUserAwradTime{
	@Resource
	private BaseDao<User> baseDao;
	
//	@Autowired
//	private HttpSession session;
	
	@Scheduled(cron = "0 55 23 * * ? ")
	public void resetMethod() {
		//每天抽奖次数重置开始
		System.out.println("定时器开启");
		baseDao.executeSql("update user set awardTime = 0");
		baseDao.executeSql("update user set pollStatus = 0");
		baseDao.executeSql("update award set todaySendOutNumber = 0");
		baseDao.executeSql("update user set pollStatusShuiShouPai = 0");
		//每天抽奖次数重置结束
		System.out.println("定时器结束");
	}

	
	
}
