package com.example.CloudKitchenBackend.Util;

public class CustomException extends RuntimeException {

    private final Type type;

    public CustomException(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public String getMessage() {
        return this.type.getMessage();
    }

    public enum Type {
        CATEGORY_ALREADY_EXIST("category already exist",500),
        DATE_INVALID("Invalid Date Format", 501),
        PHONE_NUMBER_ALREADY_EXISTS("Phone Number already exists",500),
        EMAIL_ALREADY_EXITS("Email Already Exists", 500),
        USERNAME_ALREADY_EXIST("Username already Exists", 500);
        private String message;
        private int code;

        Type(String message, int code) {
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public Type updateMessage(String message, int code) {
            this.message = message;
            this.code = code;
            return this;
        }
    }
}
