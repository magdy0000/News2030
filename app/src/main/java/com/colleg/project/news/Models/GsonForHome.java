package com.colleg.project.news.Models;

import java.util.List;

public class GsonForHome {


    private List<NewsBean> news;

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public static class NewsBean {
        /**
         * category_id : 3
         * category_title : أخبار
         * category_posts : [{"post_id":8,"post_title":"حادث مريب","post_img":"https://cizaro.net/2030/uploads/blog/posts/","description":""},{"post_id":9,"post_title":"FREE SHIPPING","post_img":"https://cizaro.net/2030/uploads/blog/posts/","description":"gfdsfbbnfhgds"}]
         */

        private int category_id;
        private String category_title;
        private List<CategoryPostsBean> category_posts;

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_title() {
            return category_title;
        }

        public void setCategory_title(String category_title) {
            this.category_title = category_title;
        }

        public List<CategoryPostsBean> getCategory_posts() {
            return category_posts;
        }

        public void setCategory_posts(List<CategoryPostsBean> category_posts) {
            this.category_posts = category_posts;
        }

        public static class CategoryPostsBean {
            /**
             * post_id : 8
             * post_title : حادث مريب
             * post_img : https://cizaro.net/2030/uploads/blog/posts/
             * description :
             */

            private int post_id;
            private String post_title;
            private String post_img;
            private String description;

            public int getPost_id() {
                return post_id;
            }

            public void setPost_id(int post_id) {
                this.post_id = post_id;
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
}
