package org.example.learn.spring.commons.service;

public class OrderService {

    public void buy(int amt, int price) {
        System.out.println(this.getClass() + " buy");
        book(amt);
        pay(amt * price);
    }

    public void pay(int amt) {
        System.out.println(this.getClass() + " pay");
    }

    public void book(int amt) {
        System.out.println(this.getClass() + " book");
    }
}
