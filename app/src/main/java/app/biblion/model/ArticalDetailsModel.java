package app.biblion.model;

import java.util.List;

public class ArticalDetailsModel {

    /**
     * status : 0
     * result : [{"id":1,"heading":"heading","title":"title","description":"description"}]
     * image : http://frozenkitchen.in/biblion_demo/public/images/1550041080.jpg
     */

    private String status;
    private String image;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
         * id : 1
         * heading : heading
         * title : title
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
