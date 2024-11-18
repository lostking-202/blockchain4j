package com.example.demo.struct;

import java.math.BigInteger;
import java.util.ArrayList;

public class CBlock {
  public int nVersion;
  public BigInteger hashPrevBlock;
  public BigInteger hashMerkRoot;
  public int nTime;
  public int nBits;
  public int nNonce;
  public ArrayList<CTransaction> vtx;
}
