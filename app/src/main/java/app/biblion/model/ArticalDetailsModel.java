package app.biblion.model;

import java.util.List;

public class ArticalDetailsModel {


    /**
     * status : 0
     * result : [{"id":10,"heading":"tenth heading","title":"tenth title","description":"description"}]
     * like : 0
     * like_status : 0
     * image : http://frozenkitchen.in/biblion_demo/public/images/newimg2.jpg
     */

    private String status;
    private int like;
    private int like_status;
    private String image;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getLike_status() {
        return like_status;
    }

    public void setLike_status(int like_status) {
        this.like_status = like_status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 10
         * heading : tenth heading
         * title : tenth title
         * description : description
         */

        private int id;
        private String heading;
        private String title;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
