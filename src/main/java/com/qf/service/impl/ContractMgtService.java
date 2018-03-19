package com.qf.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.qf.common.HttpClientUtil;
import com.qf.common.SystemUtil;
import com.qf.contstant.ContractConstant;
import com.qf.pojo.Contract;
import com.qf.pojo.ContractInfoUpload;
import com.qf.service.IContractMgtService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-14
 * Time: 18:55
 */
@Service
public class ContractMgtService implements IContractMgtService {

  private static final Logger logger = LoggerFactory.getLogger(ContractMgtService.class);

  @Value("${contract.system.url}")
  private String contractUrl;

  @Autowired
  private HttpClientUtil httpClientUtil;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private Configuration configuration;

  @Autowired
  private ContractService contractService;


  /**
   * param:
   * describe: 保存合同管理平台的相关合同信息
   * author: JianHuangsh
   * creat_date: 2018/3/14
   **/
  public String queryContractMgtAndSave(Map<String, String> param)
      throws IOException, TemplateException {
    logger.info("开始向合同平台发起下载请求");
    String result = "";
    try {
      Contract contract = new Contract();
      String appId = param.get(ContractConstant.CONTRACT_APPID);
      String contractNo = param.get(ContractConstant.CONTRACT_NO);
      contract.setContractNo(contractNo);
      contract.setAppId(appId);
      Template t = configuration.getTemplate(ContractConstant.TEMP_CONTRACT_DOWNLOAD_REQ);
      String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, contract);
      HttpEntity httpEntity = new HttpEntity<>(content, SystemUtil.contractHttpHeaders());
      logger.info("进件编号{},下载合同地址:{} 合同请求: {}", contract.getAppId(), contractUrl,
          content.replaceAll("\r|\n", ""));
      ResponseEntity<String> responseEntity = restTemplate
          .exchange(contractUrl, HttpMethod.POST, httpEntity,
              String.class);
      logger.info("进件编号{},合同信息返回:{}", contract.getAppId(), responseEntity);
      String filePath = "";
      String fileType = "";
      // 处理返回结果
      result = (String) JSONPath.read(responseEntity.getBody(), "CODE");
      Object data = JSONPath.read(responseEntity.getBody(), "DATA");
      JSONObject jsonObj = (JSONObject) JSONObject.toJSON(data);
      // 得到指定json key对象的value对象
      JSONArray filesObj = (JSONArray) jsonObj.getJSONArray("FILES");
      JSONObject P_MANUAL_FINAL = filesObj.getJSONObject(1);//0:服务协议  1：扣款授权书
      P_MANUAL_FINAL = P_MANUAL_FINAL.getJSONObject("P_MANUAL");
      filePath = P_MANUAL_FINAL.getString("FILE_PATH");
      fileType = P_MANUAL_FINAL.getString("FILE_TYPE");
      ContractInfoUpload contractInfoUpload = new ContractInfoUpload();
      contractInfoUpload.setAppId(appId);
      contractInfoUpload.setContractNo(contractNo);
      contractInfoUpload.setCreateTime(new Date());
      contractInfoUpload.setUpdateTime(new Date());
      contractInfoUpload.setFilePath(filePath);
      contractInfoUpload.setFileType(fileType);
      contractInfoUpload.setDownloadStatus(ContractConstant.CONTRACT_DOWNLOAD_SUCCESS);
      //logger.info("合同地址为:{}", filePath);
      if (result.equals("20000")) {
        //保存到mysql
        logger.info("保存合同信息到数据库");
        contractService.saveOrUpdateContractInfo(contractInfoUpload);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }


}
