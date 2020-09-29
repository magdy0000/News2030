package com.colleg.project.news.Models;

import java.util.List;

public class ModelMediaPhotos {

    private List<AllPhotosBean> all_photos;

    public List<AllPhotosBean> getAll_photos() {
        return all_photos;
    }

    public void setAll_photos(List<AllPhotosBean> all_photos) {
        this.all_photos = all_photos;
    }

    public static class AllPhotosBean {
        /**
         * id : 19
         * photo : https://cizaro.net/2030/uploads/gallery/Screenshot from 2017-08-21 11-24-14.png
         */

        private int id;
        private String photo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
