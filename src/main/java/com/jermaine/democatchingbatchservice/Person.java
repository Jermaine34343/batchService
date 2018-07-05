package com.jermaine.democatchingbatchservice;

public class Person {
    private String lastName;
    private String firstName;


    //default constructor
    public Person(){
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + ", lastName: " + lastName;
    }

}

//You can instantiate the Person class either with first and last name through a constructor, or by setting the properties.