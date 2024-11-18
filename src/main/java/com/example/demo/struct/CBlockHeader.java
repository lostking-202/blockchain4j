package com.example.demo.struct;

import java.math.BigInteger;

public class CBlockHeader {
  public int version;
  public BigInteger prev_block;
  public BigInteger merkle_root;
  public int time;
  public int bits;
  public int nonce;
}
