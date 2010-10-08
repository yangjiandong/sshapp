遍历大容量map的正确方法
-----------------------

// 第三种：通过Map.entrySet遍历key和value
    System.out.println("通过Map.entrySet遍历key和value:");
    for (Map.Entry<String, String> entry : map.entrySet()) {
      System.out.println("key= " + entry.getKey() + "  and  value= "
          + entry.getValue());
    }