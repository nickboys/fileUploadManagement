package com.qf.service.impl;

import com.qf.common.FtpFileUtil;
import com.qf.common.RedisUtil;
import com.qf.contstant.ContractConstant;
import com.qf.dao.ContractRepository;
import com.qf.entity.ContractEntity;
import com.qf.pojo.Contract;
import com.qf.pojo.ContractInfoUpload;
import com.qf.service.IContractService;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-14
 * Time: 17:58
 */
@Service
public class ContractService implements IContractService {

  private static final Logger logger = LoggerFactory.getLogger(ContractService.class);

  @Autowired
  private Executor threadPoolExecutor;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private ContractRepository contractRepository;

  @Autowired
  private ContractMgtService queryContractMgtService;

  @Autowired
  private FtpFileUtil ftpFileUtil;

  @Autowired
  private RedisUtil redisUtil;

  /**
   * param:
   * describe: 查询cobra的contract表已经生成合同的信息
   * author: JianHuangsh
   * creat_date: 2018/3/14
   **/
  public List<Contract> downloadContract(String status) {
    List<Contract> contracts = new ArrayList<>();
    try {
      Query query = Query
          .query(
              Criteria.where(ContractConstant.CONTRACT_STATUS).is(status));
      contracts = mongoTemplate.find(query, Contract.class);
      for (Contract contract : contracts
          ) {
        //downLoad
        downLoadContract(contract);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return contracts;
  }

  /**
   * param: param
   * describe: 保存已经生成的合同的信息到mysql
   * author: JianHuangsh
   * creat_date: 2018/3/15
   **/
  @Transactional(rollbackFor = Exception.class)
  public void saveOrUpdateContractInfo(ContractInfoUpload contractInfoUpload) {
    String appId = contractInfoUpload.getAppId();
    ContractEntity contractEntity = new ContractEntity();
    ContractEntity contractEntityIsExist = new ContractEntity();
    BeanUtils.copyProperties(contractInfoUpload, contractEntity);
    try {
      contractEntityIsExist = contractRepository.findAllByAppId(appId);
      //避免重复保存
      if (contractEntityIsExist == null) {
        contractRepository.saveAndFlush(contractEntity);
      } else {
        //更新合同最新地址 避免过期无法获取合同信息
        contractRepository
            .updateFilePath(contractInfoUpload.getFilePath(), contractInfoUpload.getAppId(),
                new Date());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * param:
   * describe: 下载合同
   * author: JianHuangsh
   * creat_date: 2018/3/15
   **/
  public void downLoadContract(Contract contract) throws Exception {

    Map param = new HashMap();
    if (contract != null) {
      param.put(ContractConstant.CONTRACT_APPID, contract.getAppId());
      param.put(ContractConstant.CONTRACT_NO, contract.getContractNo());
    }
    try {
      //已经上传的避免重复请求以及落库
      logger.info("redis key :{}", contract.getAppId() + ContractConstant.CONTRACT_UPLOAD_KEY);
      if (redisUtil.get(contract.getAppId() + ContractConstant.CONTRACT_UPLOAD_KEY) == null) {
        threadPoolExecutor.execute(new Runnable() {
          @Override
          public void run() {
            try {
              queryContractMgtService.queryContractMgtAndSave(param);
            } catch (Exception e) {
              e.printStackTrace();
            }

          }
        });

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * param:
   * describe: 上传合同到FTP服务器
   * author: JianHuangsh
   * creat_date: 2018/3/15
   **/
  public void uploadContract() throws Exception {
    //读取已经生成的合同信息
    List<ContractEntity> uploadList = contractRepository
        .findAllByDownloadStatus(ContractConstant.CONTRACT_DOWNLOAD_SUCCESS);
    if (uploadList.size() >= 1) {
        for (ContractEntity contractEntity :
            uploadList) {
          //转换成dto
          ContractInfoUpload contractInfoUpload = new ContractInfoUpload();
          BeanUtils.copyProperties(contractEntity, contractInfoUpload);
          try {
            threadPoolExecutor.execute(new Runnable() {
              @Override
              public void run() {
                try {
                  uploadWithClient(contractInfoUpload);
                }catch (Exception e){
                  e.printStackTrace();
                }
              }
            });
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

    } else {
      logger.error("没有可以上传的合同信息");
    }

  }

  /**
   * param:
   * describe: TODO
   * author: JianHuangsh
   * creat_date: 2018/3/15
   **/
  public void uploadWithClient(ContractInfoUpload contractInfoUpload) throws Exception {
    //logger.info("contractInfoUpload：{}", JSONObject.toJSON(contractInfoUpload).toString());
    try {
      if (contractInfoUpload != null) {
        String contractNo = contractInfoUpload.getContractNo();
        //已上传的就不在上传
        if (redisUtil.get(contractInfoUpload.getAppId() + ContractConstant.CONTRACT_UPLOAD_KEY)
            == null) {
          String url = contractInfoUpload.getFilePath();
          URL urls = new URL(url);
          InputStream inputStream = urls.openStream();
          ftpFileUtil.uploadFile(contractNo + "_" + ContractConstant.DOWNLOAD_PDF, inputStream);
          //更新上传状态
          logger.info("redis update  key :{}",
              contractInfoUpload.getAppId() + ContractConstant.CONTRACT_UPLOAD_KEY);
          redisUtil.set(contractInfoUpload.getAppId() + ContractConstant.CONTRACT_UPLOAD_KEY, "on");
          contractInfoUpload.setUploadStatus(ContractConstant.CONTRACT_UPLOAD_SUCCESS);
          ContractEntity contractEntity = new ContractEntity();
          BeanUtils.copyProperties(contractInfoUpload, contractEntity);
          contractRepository.save(contractEntity);
        } else {
          logger.info("已上传不在重复上传");
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // inputStream.close();

  }
}
