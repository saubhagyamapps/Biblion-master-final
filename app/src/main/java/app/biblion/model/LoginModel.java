package app.biblion.model;

import java.util.List;

public class LoginModel {


    /**
     * status : 0
     * messgae : login success
     * result : [{"id":1,"name":"hello","username":"username","gender":"female","dob":"1980-01-01","mobile":"55566644777","email":"hp@hp.com","password":"password","device_id":"1234567890","firebase_id":"1234567890"}]
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
         * id : 1
         * name : hello
         * username : username
         * gender : female
         * dob : 1980-01-01
         * mobile : 55566644777
         * email : hp@hp.com
         * password : password
         * device_id : 1234567890
         * firebase_id : 1234567890
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
    }
}
