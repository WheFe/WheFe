package com.example.chun.whefe;

/**
 * Created by Chun on 2017-05-03.
 * 장바구니에 들어가는 목록 holder
 */

public class ShoppingList {

        private int id;
        private String name;
        private String hot;
        private String size;
        private String option;
        private String price;
        private int imageResource;

        public ShoppingList(){};

    public ShoppingList(int id, String name, String hot, String size, String option, String price, int imageResource) {
        this.id = id;
        this.name = name;
        this.hot = hot;
        this.size = size;
        this.option = option;
        this.price = price;
        this.imageResource = imageResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
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
        public String getPrice() {
            return price;
        }
        public void setPrice(String price) {
            this.price = price;
        }

}
