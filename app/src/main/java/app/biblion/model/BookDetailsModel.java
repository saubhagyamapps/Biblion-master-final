package app.biblion.model;

import java.util.List;

public class BookDetailsModel {

    /**
     * status : 0
     * result : [{"id":4,"image":"image4.jpg","category":"Religion","bookname":"BibleBook","title":"","publisher":"","author":"","language":"","year":"","description":"jfhg jfhg fghgg fhg gkj hgj fyt iu hgjhfyhfkj hkjfh fj hkljvffj hgklj vfjgf jkhlkjgjfjhg kgf jfjk gbjhcjh lkjgbvjfjhgkgjhf jgjhjhgk gjfjkghgjgkjhguhgf ijh ijfv juhgjhfuigih hgjk g ujg jhb vgfv fjh gkgfjfgjhghjkgfu gjh gj kjo gj","book":"1550125279.EPUB","created_at":null,"updated_at":null}]
     */

    private String status;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 4
         * image : image4.jpg
         * category : Religion
         * bookname : BibleBook
         * title :
         * publisher :
         * author :
         * language :
         * year :
         * description : jfhg jfhg fghgg fhg gkj hgj fyt iu hgjhfyhfkj hkjfh fj hkljvffj hgklj vfjgf jkhlkjgjfjhg kgf jfjk gbjhcjh lkjgbvjfjhgkgjhf jgjhjhgk gjfjkghgjgkjhguhgf ijh ijfv juhgjhfuigih hgjk g ujg jhb vgfv fjh gkgfjfgjhghjkgfu gjh gj kjo gj
         * book : 1550125279.EPUB
         * created_at : null
         * updated_at : null
         */

        private int id;
        private String image;
        private String category;
        private String bookname;
        private String title;
        private String publisher;
        private String author;
        private String language;
        private String year;
        private String description;
        private String book;


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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
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
