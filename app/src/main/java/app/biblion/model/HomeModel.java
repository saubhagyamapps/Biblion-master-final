package app.biblion.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeModel {


    /**
     * status : 0
     * Total Pages : 2
     * path : http://frozenkitchen.in/biblion/public/images/
     * result : [{"id":1,"image":"image1.png","description":"sdlkdf lsdhjds lkhfkjsd kjhfkjsd khkjfsh kjhk iu hiu kjh kjghi hgi hkj hkj"},{"id":2,"image":"image2.png","description":"kgi g hgjk hh gy uyhk jlhjuh gujh kljh hg gk jhh gjh gujh gujhg j ojh gjhg jhg jg jg ohgjh gujh gjhg jh"},{"id":3,"image":"image3.png","description":"lkj kh jkgjhg ugjhgfhgfhg jhg fgf ghfh gkjg jhfgu jh gjhjfhg hgjhkjhjhfyt fyf dhgjhhgd tdyfhfyfdt dfd"},{"id":4,"image":"image4.png","description":"lkj kh jkgjhg ugjhgfhgfhg jhg fgf ghfh gkjg jhfgu jh gjhjfhg hgjhkjhjhfyt fyf dhgjhhgd tdyfhfyfdt dfd"},{"id":5,"image":"image5.png","description":"lkj kh jkgjhg ugjhgfhgfhg jhg fgf ghfh gkjg jhfgu jh gjhjfhg hgjhkjhjhfyt fyf dhgjhhgd tdyfhfyfdt dfd"},{"id":6,"image":"image6.png","description":"lkj kh jkgjhg ugjhgfhgfhg jhg fgf ghfh gkjg jhfgu jh gjhjfhg hgjhkjhjhfyt fyf dhgjhhgd tdyfhfyfdt dfd"},{"id":7,"image":"image7.png","description":"lkj kh jkgjhg ugjhgfhgfhg jhg fgf ghfh gkjg jhfgu jh gjhjfhg hgjhkjhjhfyt fyf dhgjhhgd tdyfhfyfdt dfd"},{"id":8,"image":"image8.png","description":"lkj kh jkgjhg ugjhgfhgfhg jhg fgf ghfh gkjg jhfgu jh gjhjfhg hgjhkjhjhfyt fyf dhgjhhgd tdyfhfyfdt dfd"},{"id":9,"image":"image9.png","description":"lkj kh jkgjhg ugjhgfhgfhg jhg fgf ghfh gkjg jhfgu jh gjhjfhg hgjhkjhjhfyt fyf dhgjhhgd tdyfhfyfdt dfd"},{"id":10,"image":"image10.png","description":"lkj kh jkgjhg ugjhgfhgfhg jhg fgf ghfh gkjg jhfgu jh gjhjfhg hgjhkjhjhfyt fyf dhgjhhgd tdyfhfyfdt dfd"}]
     */

    private String status;
    @SerializedName("Total Pages")
    private int _$TotalPages169; // FIXME check this code
    private String path;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int get_$TotalPages169() {
        return _$TotalPages169;
    }

    public void set_$TotalPages169(int _$TotalPages169) {
        this._$TotalPages169 = _$TotalPages169;
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
         * image : image1.png
         * description : sdlkdf lsdhjds lkhfkjsd kjhfkjsd khkjfsh kjhk iu hiu kjh kjghi hgi hkj hkj
         */

        private int id;
        private String image;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
