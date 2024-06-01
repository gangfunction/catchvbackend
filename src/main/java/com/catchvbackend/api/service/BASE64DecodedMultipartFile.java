package com.catchvbackend.api.service;

import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;

public class BASE64DecodedMultipartFile implements MultipartFile {
  private final byte[] imgContent;
  private final String fileName;

  public BASE64DecodedMultipartFile(byte[] imgContent, String fileName) {
    this.imgContent = imgContent;
    this.fileName = fileName;
  }

  @Override
  public String getName() {
    return fileName;
  }

  @Override
  public String getOriginalFilename() {
    return fileName;
  }

  @Override
  public String getContentType() {
    return null; // 필요에 따라 MIME 타입을 반환하도록 수정
  }

  @Override
  public boolean isEmpty() {
    return imgContent == null || imgContent.length == 0;
  }

  @Override
  public long getSize() {
    return imgContent.length;
  }

  @Override
  public byte[] getBytes() {
    return imgContent;
  }

  @Override
  public InputStream getInputStream() {
    return new ByteArrayInputStream(imgContent);
  }

  @Override
  public void transferTo(File dest) throws IOException, IllegalStateException {
    try (FileOutputStream fos = new FileOutputStream(dest)) {
      fos.write(imgContent);
    }
  }
}
