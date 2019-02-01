package app.biblion.model;

import java.util.List;

public class EditProfileModel {


    /**
     * status : 0
     * message : update successful
     * result : [{"id":1,"name":"odeara keshu","username":"username","gender":"female","dob":"1995-02-12","mobile":"55566644777","email":"abcda@gmail.com","password":"4297f44b13955235245b2497399d7a93","device_id":"1234567890","firebase_id":"1234567890","image":"","googleimage":null,"country":"indiaa","state":"gujarat","city":"ahmedbad"}]
     */

    private String status;
    private String message;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
         * name : odeara keshu
         * username : username
         * gender : female
         * dob : 1995-02-12
         * mobile : 55566644777
         * email : abcda@gmail.com
         * password : 4297f44b13955235245b2497399d7a93
         * device_id : 1234567890
         * firebase_id : 1234567890
         * image :
         * googleimage : null
         * country : indiaa
         * state : gujarat
         * city : ahmedbad
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
        private Object googleimage;
        private String country;
        private String state;
        private String city;

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

        public Object getGoogleimage() {
            return googleimage;
        }

        public void setGoogleimage(Object googleimage) {
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
    }
}
