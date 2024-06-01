package com.catchvbackend.api.service;

import com.catchvbackend.api.dto.ImageServiceDTO;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Service;

@Service
public class ImageProcessingQueue {
  private final BlockingQueue<ImageServiceDTO> queue = new LinkedBlockingQueue<>();

  public void addToQueue(ImageServiceDTO imageServiceDto) {
    queue.add(imageServiceDto);
  }

  public ImageServiceDTO takeFromQueue() throws InterruptedException {
    return queue.take();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}
