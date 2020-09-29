package com.colleg.project.news.Models;

import java.util.List;

public class ModelOfSearchResult {

    private List<PostsBean> posts;

    public List<PostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsBean> posts) {
        this.posts = posts;
    }

    public static class PostsBean {
        /**
         * post_id : 2
         * category_post : روشيتة
         * post_title : محمد صلاح يلقب ب ايجبشن كنج
         * post_img : https://cizaro.net/2030/uploads/blog/posts/post-15563272715cc3ab67381bc.jpg
         * description : محمد صلاح يسجل 5 اهداففى اخر مباره
         */

        private int post_id;
        private String category_post;
        private String post_title;
        private String post_img;
        private String description;

        public int getPost_id() {
            return post_id;
        }

        public void setPost_id(int post_id) {
            this.post_id = post_id;
        }

        public String getCategory_post() {
            return category_post;
        }

        public void setCategory_post(String category_post) {
            this.category_post = category_post;
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
