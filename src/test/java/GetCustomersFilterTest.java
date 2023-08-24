import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.serviceCustomers.checks.CheckAnswers.*;
import static com.kata.config.serviceCustomers.constants.ConstantsService.*;
import static com.kata.config.serviceCustomers.preparationDataCustomers.ServiceValues.getPhoneNumber;
import static com.kata.config.serviceCustomers.preparationResponses.ResponsesApiCustomers.responseGetCustomerByPhoneNumber;
import static com.kata.config.serviceCustomers.preparationResponses.ResponsesApiCustomers.responsePostCustomers;

@Epic(CUSTOMERS + "/filter")
@Story("Получение клиента по номеру телефона методом GET")
public class GetCustomersFilterTest {

    @Test
    @DisplayName("Получение клиента по номеру телефона")
    @Description("Получение клиента по номеру телефона. Создаем клиента, проверяем корректность заполнения " +
            "полей при получении клиента с помощью метода.")
    public void testGetCustomersByPhoneNumber() {
        Response customer = responsePostCustomers("create-customers");
        String phoneNumber = getPhoneNumber(customer);
        Response response = responseGetCustomerByPhoneNumber(phoneNumber);

        checkSchemaValidate(response);
        checkRequiredFieldsCorrectCreatedCustomers(
                response,
                "Petr",
                "Petrov",
                phoneNumber
        );
    }

    @Test
    @DisplayName("Получение клиента с передачей в номер телефона лишних цифр")
    @Description("Получение клиента с номером телефона нестандартной длины. Из теста видно, что ограничение" +
            " на длину не установлено, что некорректно. Но в документации длина не указана, за ошибку " +
            "считать нельзя.")
    public void testGetCustomersByPhoneNumberConcat() {
        Response customer = responsePostCustomers("create-customers");
        String phoneNumber = getPhoneNumber(customer);
        Response response = responseGetCustomerByPhoneNumber(phoneNumber + 123);

        checkErrorMessage(response, NOT_FOUND);
    }

    @Test
    @DisplayName("Получение клиента по несуществующему номеру телефона")
    @Description("Пытаемся получить клиента по несуществующему номеру телефона. Ожидаем 404")
    public void testGetCustomersByPhoneNumberInt() {
        Response response = responseGetCustomerByPhoneNumber("+0000000000");

        checkErrorMessage(response, NOT_FOUND);
        checkStatusCode(response, 404);
    }

    @Test
    @DisplayName("Получение клиента с передачей спецсимволов вместо номера телефона")
    @Description("Сервис должен возвращать код 400, но вместо этого он пытается найти клиента.")
    public void testGetCustomersByPhoneNumberChar() {
        Response response = responseGetCustomerByPhoneNumber('&');

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }
}
