import com.kata.config.serviceCustomers.models.Customer;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.serviceCustomers.checks.CheckAnswers.*;
import static com.kata.config.serviceCustomers.constants.ConstantsService.*;
import static com.kata.config.serviceCustomers.preparationDataCustomers.ServiceValues.getPhoneNumber;
import static com.kata.config.serviceCustomers.preparationDataCustomers.ServiceValues.randomNumber;
import static com.kata.config.serviceCustomers.preparationResponses.ResponsesApiCustomers.responsePostCustomers;
import static com.kata.config.serviceCustomers.preparationResponses.ResponsesApiCustomers.saveAsCustomer;

@Epic(CUSTOMERS)
@Story("Создание клиента методом POST")
public class PostCreateCustomersTest {

    @Test
    @DisplayName("Создание клиента только с обязательными полями с использованием объекта Customer")
    @Description("Создаем клиента только с обязательными для заполнения полями. Проверяем корректность " +
            "полей в ответе.")
    public void testCreateNewCustomersRequiredFields() {
        Customer customer = new Customer();
        customer.setFirstName("Михаил");
        customer.setLastName("Горшков");
        customer.setPhoneNumber(randomNumber());
        Response response = responsePostCustomers(customer);
        Customer created = saveAsCustomer(response);

        checkSchemaValidate(response);
        checkValidCustomer(customer.getFirstName(), created.getFirstName());
        checkValidCustomer(customer.getLastName(), created.getLastName());
    }

    @Test
    @DisplayName("Создание клиента с именем и фамилией на кириллице")
    @Description("Создание клиента с русскими именем и фамилией. Проверяем корректность заполнения полей в " +
            "ответе")
    public void testCreatedCustomersCyrillicFirstLastName() {
        Response customer = responsePostCustomers("create-customers-cyrillic");
        String phoneNumber = getPhoneNumber(customer);

        checkSchemaValidate(customer);
        checkRequiredFieldsCorrectCreatedCustomers(customer, "Петр", "Петров", phoneNumber);
    }

    @Test
    @DisplayName("Создание клиента со всеми заполненными полями")
    @Description("Проверяем, что сервис игнорирует заполнение полей. Поле shopCode пока заполнять не имеет " +
            "смысла, т.к. без БД этот функционал не реализован.")
    public void testCreateNewCustomersAllFields() {
        Response customer = responsePostCustomers("create-customers-all-fields");
        String phoneNumber = getPhoneNumber(customer);

        checkSchemaValidate(customer);
        checkAllFieldsCorrectCreatedCustomer(
                customer,
                "Petr",
                "Petrov",
                phoneNumber,
                "string@mail.com",
                "2001-01-01"
        );
    }

    @Test
    @DisplayName("Создание клиента, без заполнения обязательных полей")
    @Description("Пытаемся создать клиента без заполнения основных полей. Проверяем сообщения ошибок в " +
            "ответе сервиса, ожидаем 400")
    public void testCreateNewCustomersWithoutRequiredFields() {
        Response response = responsePostCustomers("create-customers-without-required-fields");

        checkStatusCode(response, 400);
        checkErrorMessage(response, MISSING_FIELDS);
    }

    @Test
    @DisplayName("Создание клиента. Вместо firstName/lastName передаем число")
    @Description("Создаем клиента, вместо имени и фамилии передаем числа. Т.к. в документации на это " +
            "ограничений нет, ожидаем корректное создание клиента.")
    public void testCreateNewCustomersFirstNameNumbers() {
        Response customer = responsePostCustomers("create-customers-firstLastName-numbers");
        String phoneNumber = getPhoneNumber(customer);

        checkSchemaValidate(customer);
        checkRequiredFieldsCorrectCreatedCustomers(customer, "123", "123", phoneNumber);
    }

    @Test
    @DisplayName("Создание клиента. В дату рождения передаем данные в формате 0000-00-00")
    @Description("Пытаемся создать клиента с нулевой датой рождения. Ожидаем 400")
    public void testCreateNewCustomersBirthdayZero() {
        Response response = responsePostCustomers("create-customers-birthday-zero");

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }
}
