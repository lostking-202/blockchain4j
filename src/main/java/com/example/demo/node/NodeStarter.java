package com.example.demo.node;

import com.example.demo.mining.BitcoinMiningGUI;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class NodeStarter {

  private static final Logger LOGGER= Logger.getGlobal();

  public static void main(String[] args) throws IOException {


    /*var path=Paths.get("D:\\Users\\issuser\\java\\blockchain4j\\src\\main\\resources\\mining.log");
    System.out.println(path);*/
    var logHandler=new FileHandler("D:\\Users\\issuser\\java\\blockchain4j\\src\\main\\resources\\mining.log", true);
    LOGGER.addHandler(logHandler);
    LOGGER.info("log 测试");
    var app=new BitcoinMiningGUI();
    app.openGUI();
  }

}
