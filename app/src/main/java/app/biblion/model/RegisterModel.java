package app.biblion.model;

public class RegisterModel {


    /**
     * status : 0
     * message : signup successful
     * path : http://192.168.1.200/biblion-API/public/profile_image/
     * id : 41
     * result : {"name":"myname","username":"username","gender":"male","dob":"1988-11-23","mobile":"55566644777","email":"hdpdpfhc@hp.com","password":"4297f44b13955235245b2497399d7a93","device_id":"1234567890","firebase_id":"1234567890","image":"1549013730.jpg","country":"country","state":"state","city":"city"}
     */

    private String status;
    private String message;
    private String path;
    private String id;
    private ResultBean result;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : myname
         * username : username
         * gender : male
         * dob : 1988-11-23
         * mobile : 55566644777
         * email : hdpdpfhc@hp.com
         * password : 4297f44b13955235245b2497399d7a93
         * device_id : 1234567890
         * firebase_id : 1234567890
         * image : 1549013730.jpg
         * country : country
         * state : state
         * city : city
         */

        private String name;
        private String googleImage;
        private String username;
        private String gender;
        private String dob;
        private String mobile;
        private String email;
        private String password;
        private String device_id;
        private String firebase_id;
        private String image;
        private String country;
        private String state;
        private String city;


        public String getGoogleImage() {
            return googleImage;
        }

        public void setGoogleImage(String googleImage) {
            this.googleImage = googleImage;
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
