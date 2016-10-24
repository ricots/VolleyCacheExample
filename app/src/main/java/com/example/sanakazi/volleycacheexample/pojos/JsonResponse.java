package com.example.sanakazi.volleycacheexample.pojos;

import java.util.ArrayList;

/**
 * Created by SanaKazi on 10/21/2016.
 */
public class JsonResponse {
   private String statusCode;
   private Data data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data{
        private ArrayList<Categories> categories ;

        public ArrayList<Categories> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<Categories> categories) {
            this.categories = categories;
        }
    }

    public class Categories{
        int id, count;
        String name,image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
