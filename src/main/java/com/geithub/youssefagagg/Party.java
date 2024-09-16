package com.geithub.youssefagagg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Party {
  public static void main(String[] args) {
    File file = new File("/Users/youssef/Downloads/party");
    List<Integer> list =new ArrayList<>();
    for (File f : file.getAbsoluteFile().listFiles()) {
      System.out.println(f.getName());
        list.add(Integer.parseInt(f.getName().substring(f.getName().indexOf("_")+1, f.getName().indexOf("."))));
    }
    list.sort((a,b)->b-a);
    int sum=0;
    for (int i = 1; i < list.size(); i++) {
      if (list.get(i-1)-list.get(i)!=1){
        System.out.println(list.get(i-1));
        System.out.println("Missing files: "+(list.get(i-1)-list.get(i)));
        System.out.println(list.get(i));
        sum+=list.get(i-1)-list.get(i);
        System.out.println(" ******** ");
      }
    }
    System.out.println("Total missing files: "+sum);
  }

}
