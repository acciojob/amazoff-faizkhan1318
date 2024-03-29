package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {
    OrderRepository orderRepository = new OrderRepository();
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        orderRepository.addPartner(partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) throws RuntimeException {
        Optional<Order> orderOpt = orderRepository.getOrderById(orderId);
        Optional<DeliveryPartner> partnerOpt = orderRepository.getPartnerById(partnerId);
        if(orderOpt.isEmpty()){
            //log.warn("order id is not present: " + orderId);
            throw new RuntimeException("OrderId is not present: "+ orderId);
        }
        if(partnerOpt.isEmpty()){
            throw new RuntimeException("partnerId is not present: "+partnerId);
        }
        // adding a no. of order for particular deliverypartner
        DeliveryPartner partner = partnerOpt.get();
        partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
        orderRepository.addPartner(partner); // now updated
        orderRepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) throws RuntimeException {
        Optional<Order> orderOpt=orderRepository.getOrderById(orderId);
        if(orderOpt.isPresent()){
            return orderOpt.get();
        }
        throw new RuntimeException("Order Not found");

    }

    public DeliveryPartner getPartnerById(String partnerId) {
        Optional<DeliveryPartner> partnerOpt=orderRepository.getPartnerById(partnerId);
        if(partnerOpt.isPresent()){
            return partnerOpt.get();
        }
        throw new RuntimeException("Order Not found");
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
//        Optional<DeliveryPartner> p = orderRepository(partnerId);
//        if(p.isPresent()){
//            return p.get().getNumberOfOrders();
//        }
//        return 0;

        //OR
        List<String> orders = orderRepository.getOrderForPartner(partnerId);
        return orders.size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrderForPartner(partnerId);
    }

    public List<String> getAllOrder() {
        return orderRepository.getAllOrder();
    }

    public Integer countOfUnassignedOrders() {
        // all order - assinged order = not unassinged orders
        return orderRepository.getAllOrder().size() - orderRepository.getAssignedOrders().size();
    }

    public Integer getOrderLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        List<String> orderIds=orderRepository.getOrderForPartner(partnerId);
        List<Order> orders = new ArrayList<>();
        for(String id : orderIds){
            Order order = orderRepository.getOrderById(id).get();
            if(order.getDeliveryTime()>TimeUtils.convertDeliveryTime(time)){
                orders.add(order);
            }
        }
        return orders.size();
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> orderIds = orderRepository.getAllOrderfromPartner(partnerId);
        int maxTime=0;
        for(String orderId:orderIds){
            int deliveryTime = orderRepository.getOrderById(orderId).get().getDeliveryTime();
            if(deliveryTime>maxTime){
                maxTime=deliveryTime;
            }
        }
        return TimeUtils.convertDeliveryTime(maxTime);
    }

    public void deletePartner(String partnerId) {
        List<String> orderIds= orderRepository.getOrderForPartner(partnerId);
        orderRepository.deletePartner(partnerId);
        for(String id : orderIds){
            orderRepository.unAssingOrder(id);
        }
    }

    public void deleteOrder(String orderId) {
        Optional<String> partnerIdOpt = orderRepository.getPartnerForOrder(orderId);
        orderRepository.deleteOrder(orderId);
        if(partnerIdOpt.isPresent()){
            List<String> orderIds=orderRepository.getOrderForPartner(partnerIdOpt.get());
            orderIds.remove(orderId);
        }
    }
}
