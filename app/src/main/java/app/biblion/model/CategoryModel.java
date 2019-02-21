package app.biblion.model;

import java.util.List;

public class CategoryModel {

    /**
     * status : 0
     * TotalPages : 1
     * path : http://frozenkitchen.in/biblion_demo/public/images/
     * result : [{"id":3,"image":"image3.jpg","category":"Old Testament","bookname":"TheHolyBibleKingJames","description":"kgh gjhgjhgkljhjhg ;ljhyf ;lbjhufgtyfyt khgfytfdydruy uyfdrtyufyt fdd dd ytf f ydytuygujhfytdyt fuyf ydy ufhgdytdyty uyfytdydyt yfufytdytfuy uyfhydyfugtu fydytdy iuguyfydyg ","book":"1550125279.EPUB"},{"id":8,"image":"image3.jpg","category":"Old Testament","bookname":"TheHolyBibleKingJames","description":"kjhkjhkjg kgyhg hjjgjkhkj ujijgkjgh jhfgfjhkjlh jugjhkjgh jjhgjhgjkhkjhkj ujfutgfu kjgjhfjjhgkl jjhgjhgjh ","book":"1550121733.EPUB"},{"id":10,"image":"image5.jpg","category":"Old Testament","bookname":"TheHolyBibleKingJames","description":"kjhkjhkjg kgyhg hjjgjkhkj ujijgkjgh jhfgfjhkjlh jugjhkjgh jjhgjhgjkhkjhkj ujfutgfu kjgjhfjjhgkl jjhgjhgjh ","book":"1550121733.EPUB"},{"id":15,"image":"1549964167.jpg","category":"Old Testament","bookname":"testing","description":"testing","book":"1550124716.EPUB"},{"id":27,"image":"1550297195.jpg","category":"Old Testament","bookname":"Testing booksssssTesting booksssss","description":"Testing booksssss","book":"1550297195.EPUB"}]
     */

    private String status;
    private int TotalPages;
    private String path;
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
         * id : 3
         * image : image3.jpg
         * category : Old Testament
         * bookname : TheHolyBibleKingJames
         * description : kgh gjhgjhgkljhjhg ;ljhyf ;lbjhufgtyfyt khgfytfdydruy uyfdrtyufyt fdd dd ytf f ydytuygujhfytdyt fuyf ydy ufhgdytdyty uyfytdydyt yfufytdytfuy uyfhydyfugtu fydytdy iuguyfydyg
         * book : 1550125279.EPUB
         */

        private String id;
        private String image;
        private String category;
        private String bookname;
        private String description;
        private String book;

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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bookname) {
            this.bookname = bookname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBook() {
            return book;
        }

        public void setBook(String book) {
            this.book = book;
        }
    }
}
