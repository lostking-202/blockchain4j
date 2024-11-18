package com.example.demo.struct;

import java.util.ArrayList;

public class CTransaction {
  public int version;
  public ArrayList<CTxIn> vin;
  public ArrayList<CTxOut> vout;
  public int nLockTime;
}
