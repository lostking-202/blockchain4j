package com.example.demo.mining;

import com.example.demo.util.HexStringUtil;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Logger;

public class BitcoinMining implements Runnable{

  private static final Logger LOGGER=Logger.getLogger("BitcoinMining");

  private BigInteger difficulty;
  private int nonce;
  private BigInteger target;
  private String input;
  private volatile boolean stopRequested=false;
  private static final int TARGET_TIMESPAN = 15;
  private static final int DIFFICULTY_RATE=4;
  private static final String MAX_TARGET="0x00000000ffffffffffffffffffffffffffffffffffffffffffffffffffffffff";

  public BitcoinMining(BigInteger difficulty,int nonce,BigInteger target,String input){
    this.difficulty=difficulty;
    this.nonce=nonce;
    this.target=target;
    this.input=input;
  }
  @Override
  public void run() {
    LOGGER.info("try mining...");
    mining();
  }

  public void requestStop(){
    stopRequested=true;
  }

  public void mining(){

    try {
      var digest= MessageDigest.getInstance("SHA-256");

      System.out.println("target:"+target.toString());
      // 获取最新的数据

      while(!stopRequested){
        var data=input+nonce;
        var hashBytes=digest.digest(data.getBytes());
        var hash=this.bytes2Hex(hashBytes);
        var hashValue=new BigInteger(1,hashBytes);
        if(hashValue.compareTo(target)==-1){
          //todo 挖矿成功 广播
          // 需要更新区块吗，需要调整挖矿难度吗，需要更新nonce吗
          LOGGER.info("mining success...");

          break;
        }else{
          nonce++;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * 计算下一个区块的难度
   *
   * @param actualTimespan 上一个区块实际的时间跨度
   * @return 下一个区块的难度
   */
  public BigInteger calculateNextDifficulty(long actualTimespan){
    var newDifficulty=difficulty.multiply(BigInteger.valueOf(actualTimespan)).divide(BigInteger.valueOf(TARGET_TIMESPAN));

    // 难度调整限制，最多只能调整4倍
    var maxAdjustment=difficulty.multiply(BigInteger.valueOf(DIFFICULTY_RATE));
    if(newDifficulty.compareTo(maxAdjustment)>0){
      newDifficulty=maxAdjustment;
    }else if(newDifficulty.compareTo(difficulty.divide(BigInteger.valueOf(DIFFICULTY_RATE)))<0){
      newDifficulty.divide(BigInteger.valueOf(DIFFICULTY_RATE));
    }
    return newDifficulty;
  }

  public BigInteger getNextMiningTarget(long actualTimespan){
    var newDifficulty=calculateNextDifficulty(actualTimespan);
    var newTarget= new BigInteger(HexStringUtil.tryFromHexString(MAX_TARGET),16).divide(newDifficulty);
    return newTarget;
  }

  private String bytes2Hex(byte[] hashBytes){
    var hexString=new StringBuilder(2*hashBytes.length);
    for(byte b:hashBytes){
      var hex=Integer.toHexString(0xff&b);
      if(hex.length()==1){
        hexString.append(hex);
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

}
