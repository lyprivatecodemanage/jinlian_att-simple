package com.xiangshangban.att_simple.utils;

public enum Rank {
	 VIDEO(1, "视频"), AUDIO(2, "音频"), TEXT(3, "文本"), IMAGE(4, "图像");  
    
    int value;  
    String name;  
      
    Rank(int value, String name) {  
        this.value = value;  
        this.name = name;  
    }  
      
    public int getValue() {  
        return value;  
    }  
      
    public String getName() {  
        return name;  
    }  
  
    public int getByValue(int value) {  
        for(Rank typeEnum : Rank.values()) {  
            if(typeEnum.value == value) {  
                return typeEnum.getValue();  
            }  
        }  
        throw new IllegalArgumentException("No element matches " + value);  
    }  
}
