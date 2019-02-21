package app.biblion.model;

import java.util.List;

public class SearchModel {

    /**
     * status : 0
     * path : http://frozenkitchen.in/biblion_demo/public/images/
     * result : [{"id":34,"image":"1550661683.png","bookname":"Bibilon Book"}]
     */

    private String status;
    private String path;
    private List<ResultBean> result;
    private int TotalPages;

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 34
         * image : 1550661683.png
         * bookname : Bibilon Book
         */

        private String id;
        private String image;
        private String bookname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bookname) {
            this.bookname = bookname;
        }
    }
}
