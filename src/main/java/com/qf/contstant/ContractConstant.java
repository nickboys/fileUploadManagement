package com.qf.contstant;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-14
 * Time: 18:21
 */
public class ContractConstant {

  public static final String CONTRACT_STATUS = "status";
  public static final String CONTRACT_CREATED = "CONTRACT_CREATED";
  public static final String CONTRACT_PENDING = "CONTRACT_PENDING";
  public static final String CONTRACT_FAILED = "CONTRACT_FAILED";
  public static final String CONTRACT_APPID = "appId";
  public static final String CONTRACT_NO = "contractNo";
  public static final String CONTRACT_DOWNLOAD_SUCCESS = "1";
  public static final String CONTRACT_DOWNLOAD_FAIL = "0";
  public static final String CONTRACT_UPLOAD_SUCCESS = "1";
  public static final String CONTRACT_UPLOAD_FAIL = "0";
  // 已上传标志存储在redis
  public static final String UPLOAD_REDIS_STATUS_ON = "on";
  public static final String CONTRACT_UPLOAD_KEY = "_ftp_upload";
  public static final String COOPERATE_COMPANY_RONGYI = "RONGYI";
  public static final String DOWNLOAD_PDF = ".pdf";

  // 合同查看请求模板
  public static final String TEMP_CONTRACT_VIEW_REQ = "contractViewReq.json";
  // 合同下载请求模板
  public static final String TEMP_CONTRACT_DOWNLOAD_REQ = "contractDownloadReq.json";
}
