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
        CATEGORY_ALREADY_EXIST("category already exist",400),
        DATE_INVALID("Invalid Date Format", 501),
        TIME_INVALID("invalid Time Format",501),
        FLIGHT_ALREADY_EXIST("Flight of the company for the date,sector and departure time already exist",400),
        COMPANY_ALREADY_ADDED("Company is already added",400),
        PHONE_NUMBER_ALREADY_EXISTS("Phone Number already exists",400),
        FLIGHT_CODE_ALREADY_EXIST("Flight Code Already Exists", 400),
        SECTOR_ALREADY_EXISTS("Sector Code is already Preseant", 400),
        TICKET_CODE_ALREADY_EXIST("Ticket Code Already Taken",400 ), EMAIL_ALREADY_EXITS("Email ALready Exists", 400), INVALID_DETAILS("Invalid Credentials Provided", 401),
        USERNAME_ALREADY_EXIST("Username already Exists", 400),
        TICKET_FOR_FLIGHT_ALREARY_EXISTS("Ticket has already been assigned to the flight",400 ),
        INVALID_NUMBER_OF_TRAVELLERS("The number of travelers should not be zero",400 );
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
