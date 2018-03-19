package com.qf.common;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description:
 * User: JianHuangsh
 * Date: 2018-03-15
 * Time: 15:58
 */
@Component
public class FtpFileUtil {

  private static final Logger logger = LoggerFactory.getLogger(FtpFileUtil.class);
  //ftp服务器ip地址
  @Value("${ftp.upload.url}")
  private String FTP_ADDRESS;
  //端口号
  @Value("${ftp.upload.port}")
  private int FTP_PORT;
  //用户名
  @Value("${ftp.upload.username}")
  private String FTP_USERNAME;
  //密码
  @Value("${ftp.upload.password}")
  private String FTP_PASSWORD ;
  //图片路径
  @Value("${ftp.upload.filepath}")
  private String FTP_BASEPATH ;

  public boolean uploadFile(String originFileName, InputStream input) {
    logger.info("下载输出流内容为:{}", input.toString());
    boolean success = false;
    FTPClient ftp = new FTPClient();
    ftp.setControlEncoding("GBK");
    try {
      int reply;
      ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
      ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
      reply = ftp.getReplyCode();
      if (!FTPReply.isPositiveCompletion(reply)) {
        ftp.disconnect();
        return success;
      }
      ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//      ftp.makeDirectory(FTP_BASEPATH);
      ftp.changeWorkingDirectory(FTP_BASEPATH);
      ftp.storeFile(originFileName, input);
      input.close();
      ftp.logout();
      success = true;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (ftp.isConnected()) {
        try {
          ftp.disconnect();
        } catch (IOException ioe) {
        }
      }
    }
    return success;
  }


}
