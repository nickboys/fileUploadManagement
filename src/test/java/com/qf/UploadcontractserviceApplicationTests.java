package com.qf;

import com.qf.common.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadcontractserviceApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(UploadcontractserviceApplicationTests.class);
	@Autowired
	private RedisUtil redisUtil;
	@Test
	public void contextLoads() {
		    redisUtil.del("QYJ20180316110701237245_ftp_upload");
		    redisUtil.del("QYJ20180316144343413914_ftp_upload");
		    redisUtil.del("QYJ20180315151712644496_ftp_upload");
		    logger.info("删除redis缓存成功");

	}

}
