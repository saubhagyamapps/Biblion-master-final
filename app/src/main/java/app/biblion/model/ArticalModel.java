package app.biblion.model;

import java.util.List;

public class ArticalModel {


    /**
     * status : 0
     * path : http://frozenkitchen.in/biblion/public/images/
     * result : [{"id":1,"image":"dd.png","heading":"heading","title":"title","description":"description"},{"id":2,"image":"legalwriting2.jpg","heading":"heading","title":"title","description":"description"},{"id":3,"image":"avatar.png","heading":"first row","title":"title","description":"description"},{"id":4,"image":"spokesperson1.jpg","heading":"third  heading","title":"third title","description":"description"},{"id":5,"image":"spokesperson2.jpg","heading":"fourth heading","title":"fifth title","description":"description"},{"id":6,"image":"spokesperson3.jpg","heading":"sixth heading","title":"sixth title","description":"description"},{"id":7,"image":"h.jpg","heading":"seventh heading","title":"seventh title","description":"description"},{"id":8,"image":"creative-office-new-1.jpg","heading":"eighth heading","title":"eighth title","description":"description"},{"id":9,"image":"sat4.jpg","heading":"nineth heading","title":"nineth title","description":"description"},{"id":10,"image":"newimg2.jpg","heading":"tenth heading","title":"tenth title","description":"description"}]
     */

    private String status;
    private String path;
    private List<ResultBean> result;

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
         * id : 1
         * image : dd.png
         * heading : heading
         * title : title
         * description : description
         */

        private int id;
        private String image;
        private String heading;
        private String title;
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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
