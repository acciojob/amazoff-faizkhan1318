package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    Map<String, Order> orderMap=new HashMap<>();
    Map<String, DeliveryPartner> partnerMap = new HashMap<>();
    Map<String, ArrayList<String>> partnerOrderMap = new HashMap<>();
    Map<String, String> orderPartnerMap = new HashMap<>();
    public void addOrder(Order order) {
        orderMap.put(order.getId(), order);
    }

    public void addPartner(DeliveryPartner partner) {
        partnerMap.put(partner.getId(), partner);
    }

    public Optional<Order> getOrderById(String orderId) {
        if(orderMap.containsKey(orderId)){
            return Optional.of(orderMap.get(orderId));
        }
        return Optional.empty();
    }

    public Optional<DeliveryPartner> getPartnerById(String partnerId) {
        if(partnerMap.containsKey(partnerId)){
            return Optional.of(partnerMap.get(partnerId));
        }
        return Optional.empty();
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        ArrayList<String> orders = partnerOrderMap.getOrDefault(partnerId, new ArrayList<>());
        orders.add(orderId);
        partnerOrderMap.put(partnerId, orders);
        orderPartnerMap.put(orderId, partnerId);

    }

//    public List<String> getOrderFromPartner(String partnerId) {
//        return partnerOrderMap.getOrDefault(partnerId, new ArrayList<>());
//    }

    public List<String> getOrderForPartner(String partnerId) {
        return partnerOrderMap.getOrDefault(partnerId, new ArrayList<>());
    }

    public List<String> getAllOrder() {
        return new ArrayList<>(orderMap.keySet());
    }

    public List<String> getAssignedOrders() {
        return new ArrayList<>(orderPartnerMap.keySet()); // it will give the assinged order list size
    }

    public void deletePartner(String partnerId) {
        partnerMap.remove(partnerId);
        partnerOrderMap.remove(partnerId);
    }

    public void unAssingOrder(String id) {
        orderPartnerMap.remove(id);
    }

    public Optional<String> getPartnerForOrder(String orderId) {
        if(orderPartnerMap.containsKey(orderId)){
            return Optional.of(orderPartnerMap.get(orderId));
        }
        return Optional.empty();
    }

    public void deleteOrder(String orderId) {
        orderPartnerMap.remove(orderId);
        orderMap.remove(orderId);
    }

    public void removeOrderForPartner(String partnerId, String orderId) {
        ArrayList<String> orderIds = partnerOrderMap.get(partnerId);
        orderIds.remove(orderId);
        partnerOrderMap.put(partnerId, orderIds);
    }
}
