package ru.swap.rest.model;

import lombok.Data;

@Data
public class RestUserModel {

    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;

    public RestUserModel() {

    }

    /**
     * specification says that inner class must be static. But it work fine without it.
     */
    @Data
    public class Address {
        private String street;
        private String suite;
        private String zipcode;
        private Geo geo;


        @Data
        public class Geo {
            private Double lat;
            private Double lng;

        }
    }
}
