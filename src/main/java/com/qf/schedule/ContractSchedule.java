package com.qf.schedule;

import com.qf.contstant.ContractConstant;
import com.qf.service.impl.ContractService;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-14
 * Time: 17:03
 */
@Component
public class ContractSchedule {

  private Logger logger = LoggerFactory.getLogger(ContractSchedule.class);

  @Autowired
  private Executor threadPoolExecutor;

  @Autowired
  private ContractService contractService;


  /**
   * param:
   * describe: download
   * author: JianHuangsh
   * creat_date: 2018/3/14
   **/
  @Scheduled(cron = "${schedule.config.download.cron}")
  public void downloadContract() throws Exception {
    threadPoolExecutor.execute(new Runnable() {
      @Override
      public void run() {
        logger.info("正在执行合同数据查询");
        contractService.downloadContract(ContractConstant.CONTRACT_CREATED);
      }
    });
  }

  /**
   * param:
   * describe: upload
   * author: JianHuangsh
   * creat_date: 2018/3/15
   **/
  @Scheduled(cron = "${schedule.config.upload.cron}")
  public void uploadContract() throws Exception {
    threadPoolExecutor.execute(new Runnable() {
      @Override
      public void run() {
        try {
          logger.info("正在执行合同数据上传");
          contractService.uploadContract();
        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    });
  }

  /**
   * param:
   * describe: 定时处理已经上传过的合同信息避免重复读取
   * author: JianHuangsh
   * creat_date: 2018/3/16
   **/
//  @Scheduled(cron = "${}")
//  public void deleteHaveDoneContract(){
//        threadPoolExecutor.execute(new Runnable() {
//          @Override
//          public void run() {
//
//          }
//        });
//  }
}
