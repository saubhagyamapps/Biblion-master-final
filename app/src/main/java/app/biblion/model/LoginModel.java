package app.biblion.model;

import java.util.List;

public class LoginModel {


    /**
     * status : 0
     * messgae : login success
     * result : [{"id":15,"name":"abcd","username":"abcd","gender":"Male","dob":"2019-01-23","mobile":1234567891,"email":"abcd@gmail.com","password":"f5bb0c8de146c67b44babbf4e6584cc0","device_id":"13b7029855e22101","firebase_id":"fgdjsgjgha","image":"","googleimage":null,"country":"","state":"","city":""}]
     */

    private String status;
    private String messgae;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 15
         * name : abcd
         * username : abcd
         * gender : Male
         * dob : 2019-01-23
         * mobile : 1234567891
         * email : abcd@gmail.com
         * password : f5bb0c8de146c67b44babbf4e6584cc0
         * device_id : 13b7029855e22101
         * firebase_id : fgdjsgjgha
         * image :
         * googleimage : null
         * country :
         * state :
         * city :
         */

        private String id;
        private String name;
        private String username;
        private String gender;
        private String dob;
        private String mobile;
        private String email;
        private String password;
        private String device_id;
        private String firebase_id;
        private String image;
        private String googleimage;
        private String country;
        private String state;
        private String city;
        private String langauge;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getFirebase_id() {
            return firebase_id;
        }

        public void setFirebase_id(String firebase_id) {
            this.firebase_id = firebase_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGoogleimage() {
            return googleimage;
        }

        public void setGoogleimage(String googleimage) {
            this.googleimage = googleimage;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLangauge() {
            return langauge;
        }

        public void setLangauge(String langauge) {
            this.langauge = langauge;
        }
    }
}
