package com.colleg.project.news.Models;

import java.util.List;

public class ModelOfAllFavourite {


    private List<FavoritesBean> favorites;

    public List<FavoritesBean> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoritesBean> favorites) {
        this.favorites = favorites;
    }

    public static class FavoritesBean {
        /**
         * post_id : 2
         * categoty_name : روشيتة
         * post_title : محمد صلاح يلقب ب ايجبشن كنج
         * post_img : https://cizaro.net/2030/uploads/blog/posts/post-15563272715cc3ab67381bc.jpg
         * description : محمد صلاح يسجل 5 اهداففى اخر مباره
         */

        private int post_id;
        private String categoty_name;
        private String post_title;
        private String post_img;
        private String description;

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }

        public String getCategoty_name() {
            return categoty_name;
        }

        public void setCategoty_name(String categoty_name) {
            this.categoty_name = categoty_name;
        }

        public String getPost_title() {
            return post_title;
        }

        public void setPost_title(String post_title) {
            this.post_title = post_title;
        }

        public String getPost_img() {
            return post_img;
        }

        public void setPost_img(String post_img) {
            this.post_img = post_img;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
