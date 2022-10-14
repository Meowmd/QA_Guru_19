package tests;

import com.github.javafaker.Faker;

public class UserData {
    Faker faker = new Faker();

    public String
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            password = faker.internet().password();

}