package com.colleg.project.news.Models;

public class ModelOfRejestraion {


    /**
     * user_info : {"UserId":15,"username":"abdullah","token":"8gfxTPkzJbtcN2WGXrqFCpshoItSI8Qp","email":"abdo77o@gmail.com"}
     */

    private UserInfoBean user_info;

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public static class UserInfoBean {
        /**
         * UserId : 15
         * username : abdullah
         * token : 8gfxTPkzJbtcN2WGXrqFCpshoItSI8Qp
         * email : abdo77o@gmail.com
         */

        private int UserId;
        private String username;
        private String token;
        private String email;

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
