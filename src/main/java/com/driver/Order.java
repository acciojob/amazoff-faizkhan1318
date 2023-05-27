package com.driver;

import java.sql.Time;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.deliveryTime = TimeUtils.convertDeliveryTime(deliveryTime);
        this.id=id;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }
//    private int convertDeliveryTime(String deliveryTime) {
//        String[] time = deliveryTime.split(":");
//        return Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
//    }//20:30 = 20 * 60 +30 =1230min
//    public String convertDeliveryTime(int deliveryTime){
//        int hh=deliveryTime/60;
//        int mm=deliveryTime%60;
//        String HH=String.valueOf(hh);
//        String MM=String.valueOf(mm);
//        return String.format("%s:%s", HH, MM);
//    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public String getDeliveryTimeAsString(){
        return TimeUtils.convertDeliveryTime(this.deliveryTime);
    }
    public void setDeliveryTime(String deliveryTime){
        this.deliveryTime= TimeUtils.convertDeliveryTime(deliveryTime);
    }
}
