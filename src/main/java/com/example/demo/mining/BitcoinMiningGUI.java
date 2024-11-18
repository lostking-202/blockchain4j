package com.example.demo.mining;

import com.example.demo.util.HexStringUtil;
import java.awt.Dimension;
import java.math.BigInteger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.junit.Test;

public class BitcoinMiningGUI {
  private BitcoinMining miner;
  private boolean isMining=false;
  public void openGUI(){
    SwingUtilities.invokeLater(()->{
      var jframe=new JFrame("Bitcoin Miner Simulator");
      var startMininginingButton=new JButton("Start mining");
      var stopMininginingButton=new JButton("Stop mining");
      var  statusLabel=new JLabel("Not mining");
      jframe.setPreferredSize(new Dimension(400, 300));
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setLayout(new BoxLayout(jframe.getContentPane(),BoxLayout.Y_AXIS));
      jframe.add(statusLabel);
      jframe.add(startMininginingButton);
      jframe.add(stopMininginingButton);
      jframe.pack();
      jframe.setVisible(true);

      startMininginingButton.addActionListener(e -> {
        if(isMining==true){
          statusLabel.setText("Already mining...");
          return;
        }
        //todo 从附近节点获取
        miningInitialize();
        var difficulty=BigInteger.valueOf(1);
        var nonce=1;
        var target= BigInteger.ONE;
        var input="hello world";
        statusLabel.setText("Mining");
        miner=new BitcoinMining(difficulty,nonce,target,input);
        Thread miningThread=new Thread(miner);
        miningThread.start();
        isMining=true;
      });

      stopMininginingButton.addActionListener(e -> {
        //
        if(isMining==false){
          statusLabel.setText("Not mining...");
          return;
        }
        isMining=false;
        if(miner!=null){
          miner.requestStop();
        }
        statusLabel.setText("Mining stopped.");
      });
    });
  }

  public void miningInitialize(){
    // todo
  }

  @Test
  public void test(){
    var difficulty=BigInteger.valueOf(1);
    var nonce=0;
    var target="0x00000000FFFF0000000000000000000000000000000000000000000000000000000";
    var targetValue=new BigInteger(HexStringUtil.tryFromHexString(target),16);
    var input="hello world";
    BitcoinMining miner=new BitcoinMining(difficulty,nonce,targetValue,input);
    miner.mining();
    /*Thread thread=new Thread(miner);
    thread.start();*/
  }
}
