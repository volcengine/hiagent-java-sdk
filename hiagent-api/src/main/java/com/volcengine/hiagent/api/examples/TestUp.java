// Copyright (c) 2024 Bytedance Ltd. and/or its affiliates
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.volcengine.hiagent.api.examples;

import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.hiagent.api.UpClient;
import com.volcengine.hiagent.api.model.*;
import com.volcengine.sign.Credentials;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class TestUp {
  public static void main(String[] args) {
    String ak = System.getenv("VOLC_ACCESSKEY");
    String sk = System.getenv("VOLC_SECRETKEY");
    String region = "cn-north-1";
    String uploadEndpoint = System.getenv("HIAGENT_UP_UPLOAD_ENDPOINT");

    // Create upload client
    ApiClient uploadApiClient = new ApiClient()
        .setCredentials(Credentials.getCredentials(ak, sk))
        .setRegion(region)
        .setEndpoint(uploadEndpoint)
        .setDisableSSL(true);
    // .setDebugging(true); // Disable debugging for cleaner output

    UpClient upUploadClient = new UpClient(uploadApiClient);

    try {
      // 1. Upload file
      String testFilePath = "test_data/test.txt";
      File testFile = new File(testFilePath);
      String testFileHash = calculateSHA256(testFile);

      UploadRawRequest uploadReq = new UploadRawRequest();
      uploadReq.setId(UUID.randomUUID().toString().replace("-", ""));
      uploadReq.setContentType("plain/text");
      uploadReq.setExpire("15h");
      uploadReq.setSha256(testFileHash);

      System.out.println("Upload request - ID: " + uploadReq.getId());
      System.out.println("Upload request - Expire: " + uploadReq.getExpire());
      System.out.println("Upload request - SHA256: " + uploadReq.getSha256());
      System.out.println("Upload request - ContentType: " + uploadReq.getContentType());

      UploadRawResponse uploadResp = upUploadClient.uploadRaw(uploadReq, testFile);
      assert uploadResp.getSha256().equals(testFileHash) : "SHA256 mismatch";
      System.out.println("Upload successful. Path: " + uploadResp.getPath());

      // 2. Long live file (persist)
      String id = UUID.randomUUID().toString().replace("-", "");
      LongLiveRequest longLiveReq = new LongLiveRequest();
      longLiveReq.setPath(uploadResp.getPath());
      longLiveReq.setId(id);

      LongLiveResponse longLiveResp = upUploadClient.longLive(longLiveReq);
      assert longLiveResp.getPath().equals(uploadResp.getPath()) : "Path mismatch";
      System.out.println("Long live successful. Path: " + longLiveResp.getPath());

      // 3. Get download key
      DownloadKeyRequest downloadKeyReq = new DownloadKeyRequest();
      downloadKeyReq.setPath(uploadResp.getPath());
      downloadKeyReq.setSha256(testFileHash);

      DownloadKeyResponse downloadKeyResp = upUploadClient.downloadKey(downloadKeyReq);
      assert downloadKeyResp.getKey().length() > 10 : "Key length should be greater than 10";
      System.out.println("Download key obtained: " + downloadKeyResp.getKey());

      // 4. Download file
      String saveTo = "test_data/download.txt";
      DownloadRequest downloadReq = new DownloadRequest();
      downloadReq.setPath(uploadResp.getPath());
      downloadReq.setKey(downloadKeyResp.getKey());
      downloadReq.setSaveTo(saveTo);

      upUploadClient.download(downloadReq);

      File downloadedFile = new File(saveTo);
      String downloadedFileHash = calculateSHA256(downloadedFile);
      assert downloadedFileHash.equals("5d631c350f57f9024d9a2f1aa84dc4ac86c555aa066e6e52621a7e230a88e442")
          : "SHA256 mismatch for downloaded file";
      System.out.println("Download successful. Saved to: " + saveTo);

      // 5. Delete file
      DeleteRequest deleteReq = new DeleteRequest();
      deleteReq.setSha256(testFileHash);
      deleteReq.setId(id);

      upUploadClient.delete(deleteReq);
      System.out.println("Delete successful.");

    } catch (ApiException e) {
      System.err.println("API调用发生异常:");
      System.err.println("错误码: " + e.getCode());
      System.err.println("错误信息: " + e.getMessage());
      System.err.println("响应体: " + e.getResponseBody());
      System.err.println("完整堆栈跟踪:");
      e.printStackTrace();
    } catch (Exception e) {
      System.err.println("发生异常:");
      e.printStackTrace();
    }
  }

  /**
   * Calculate SHA256 hash of a file
   */
  private static String calculateSHA256(File file) throws IOException, NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    FileInputStream fis = new FileInputStream(file);
    byte[] byteArray = new byte[1024];
    int bytesCount;

    while ((bytesCount = fis.read(byteArray)) != -1) {
      digest.update(byteArray, 0, bytesCount);
    }
    fis.close();

    byte[] bytes = digest.digest();
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}
