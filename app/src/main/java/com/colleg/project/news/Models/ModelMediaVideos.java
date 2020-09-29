package com.colleg.project.news.Models;

import java.util.List;

public class ModelMediaVideos {

    private List<AllVideosBean> all_videos;

    public List<AllVideosBean> getAll_videos() {
        return all_videos;
    }

    public void setAll_videos(List<AllVideosBean> all_videos) {
        this.all_videos = all_videos;
    }

    public static class AllVideosBean {
        /**
         * video : https://youtu.be/K7rSX8YNhTM
         */

        private String video;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
}
