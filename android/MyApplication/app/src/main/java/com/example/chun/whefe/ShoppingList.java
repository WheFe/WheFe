package com.example.chun.whefe;

/**
 * Created by Chun on 2017-05-03.
 * 장바구니에 들어가는 목록 holder
 */

public class ShoppingList {

        private String name;
        private String hot;
        private String size;
        private String option;
        private String coupon;
        private String price;

        public ShoppingList(){};
        public ShoppingList(String name, String hot, String size, String option, String coupon, String price) {
            this.name = name;
            this.hot = hot;
            this.size = size;
            this.option = option;
            this.coupon = coupon;
            this.price = price;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getHot() {
            return hot;
        }
        public void setHot(String hot) {
            this.hot = hot;
        }
        public String getSize() {
            return size;
        }
        public void setSize(String size) {
            this.size = size;
        }
        public String getOption() {
            return option;
        }
        public void setOption(String option) {
            this.option = option;
        }
        public String getCoupon() {
            return coupon;
        }
        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }
        public String getPrice() {
            return price;
        }
        public void setPrice(String price) {
            this.price = price;
        }

}
