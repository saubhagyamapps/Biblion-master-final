package app.biblion.model;

import java.util.List;

public class AllSongListModel {

    /**
     * status : 0
     * TotalPages : 4
     * result : [{"id":38,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "},{"id":37,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "},{"id":36,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "},{"id":35,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "},{"id":34,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "},{"id":33,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "},{"id":32,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "},{"id":31,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "},{"id":30,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "},{"id":29,"song_title":"dummy","song_disc":"dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy "}]
     */

    private String status;
    private int TotalPages;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int TotalPages) {
        this.TotalPages = TotalPages;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 38
         * song_title : dummy
         * song_disc : dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy dummy
         */

        private int id;
        private String song_title;
        private String song_disc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSong_title() {
            return song_title;
        }

        public void setSong_title(String song_title) {
            this.song_title = song_title;
        }

        public String getSong_disc() {
            return song_disc;
        }

        public void setSong_disc(String song_disc) {
            this.song_disc = song_disc;
        }
    }
}
