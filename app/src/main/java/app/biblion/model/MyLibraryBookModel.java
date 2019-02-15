package app.biblion.model;

import java.util.List;

public class MyLibraryBookModel {


    /**
     * status : 0
     * TotalPages : 2
     * path : http://frozenkitchen.in/biblion/public/books/
     * result : [{"id":1,"image":"book1.png","bookname":"BibleBook","description":"dslkjdldfs sldkjhdskj khkjs kdjshkiku ikk  kjghhkj hkjg gk jkj   k"},{"id":2,"image":"book2.png","bookname":"TheHolyBibleKingJames","description":"kjghgjg hgjhg khl hguyk n,mbjh  j,lm bjhv gyf jhm bjhv hg"},{"id":3,"image":"book3.png","bookname":"TheHolyBibleKingJames","description":"kgh gjhgjhgkljhjhg ;ljhyf ;lbjhufgtyfyt khgfytfdydruy uyfdrtyufyt fdd dd ytf f ydytuygujhfytdyt fuyf ydy ufhgdytdyty uyfytdydyt yfufytdytfuy uyfhydyfugtu fydytdy iuguyfydyg "},{"id":4,"image":"book4.png","bookname":"BibleBook","description":"jfhg jfhg fghgg fhg gkj hgj fyt iu hgjhfyhfkj hkjfh fj hkljvffj hgklj vfjgf jkhlkjgjfjhg kgf jfjk gbjhcjh lkjgbvjfjhgkgjhf jgjhjhgk gjfjkghgjgkjhguhgf ijh ijfv juhgjhfuigih hgjk g ujg jhb vgfv fjh gkgfjfgjhghjkgfu gjh gj kjo gj"},{"id":5,"image":"book5.png","bookname":"BibleBook","description":"kjghgjg hgjhg khl hguyk n,mbjh  j,lm bjhv gyf jhm bjhv hg"},{"id":6,"image":"book6.png","bookname":"TheHolyBibleKingJames","description":"lkhjk l lkjhkljh kljhkj hkj hkj hkj hkj lkhkj gig jh kjhguykjhhguy kjh guygikjguygfytfuyhkjgtftgjh gtyfyihhfv tyugyftiuoiuuyftfdtr "},{"id":7,"image":"book7.png","bookname":"BibleBook","description":"lkhjk l lkjhkljh kljhkj hkj hkj hkj hkj lkhkj gig jh kjhguykjhhguy kjh guygikjguygfytfuyhkjgtftgjh gtyfyihhfv tyugyftiuoiuuyftfdtr lkhjk l lkjhkljh kljhkj hkj hkj hkj hkj lkhkj gig jh kjhguykjhhguy kjh guygikjguygfytfuyhkjgtftgjh gtyfyihhfv tyugyftiuoiuuyftfdtr "},{"id":8,"image":"book8.png","bookname":"TheHolyBibleKingJames","description":"kjhkjhkjg kgyhg hjjgjkhkj ujijgkjgh jhfgfjhkjlh jugjhkjgh jjhgjhgjkhkjhkj ujfutgfu kjgjhfjjhgkl jjhgjhgjh "},{"id":9,"image":"book9.png","bookname":"BibleBook","description":"kjhkjhkjg kgyhg hjjgjkhkj ujijgkjgh jhfgfjhkjlh jugjhkjgh jjhgjhgjkhkjhkj ujfutgfu kjgjhfjjhgkl jjhgjhgjh "},{"id":10,"image":"book10.png","bookname":"TheHolyBibleKingJames","description":"kjhkjhkjg kgyhg hjjgjkhkj ujijgkjgh jhfgfjhkjlh jugjhkjgh jjhgjhgjkhkjhkj ujfutgfu kjgjhfjjhgkl jjhgjhgjh "}]
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
         * id : 1
         * image : book1.png
         * bookname : BibleBook
         * description : dslkjdldfs sldkjhdskj khkjs kdjshkiku ikk  kjghhkj hkjg gk jkj   k
         */

        private String id;
        private String image;
        private String bookname;
        private String description;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
