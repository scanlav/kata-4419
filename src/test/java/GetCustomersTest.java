import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.config.serviceCustomers.checks.CheckAnswers.*;
import static com.kata.config.serviceCustomers.constants.ConstantsService.*;
import static com.kata.config.serviceCustomers.preparationDataCustomers.ServiceValues.*;
import static com.kata.config.serviceCustomers.preparationResponses.ResponsesApiCustomers.*;

@Epic(CUSTOMERS)
@Story("Получение клиентов методом GET")
public class GetCustomersTest {

    @Test
    @DisplayName("Получение всех клиентов")
    @Description("Проверяем корректную работу метода получения всех клиентов в базе. Ожидаем 200")
    public void testGetAllCustomers() {
       Response response = responseGetAllCustomers();

       checkStatusCode(response, 200);
    }

    @Test
    @DisplayName("Получение клиента по id")
    @Description("Получаем клиента по корректному ID. Сначала создаем клиента, получаем его ID и получаем " +
            "этого же клиента с помощью метода. ")
    public void testGetCustomersId() {
        Response customer = responsePostCustomers("create-customers");
        String id = getId(customer);
        String phoneNumber = getPhoneNumber(customer);
        Response response = responseGetCustomersId(id);

        checkSchemaValidate(response);
        checkRequiredFieldsCorrectCreatedCustomers(response, "Petr", "Petrov", phoneNumber);
    }

    @Test
    @DisplayName("Получение клиента с передачей в поле id строки")
    @Description("Пытаемся получить клиента с передачей в поле ID строкового значения. Ожидаем 400")
    public void testGetCustomersString() {
        Response response = responseGetCustomersId("str");

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }

    @Test
    @DisplayName("Получение клиента c передачей спецсимвола в поле Id")
    @Description("Пытаемся получить клиента с передачей спецсимвола & в поле ID. Ожидаем 400")
    public void testGetCustomersCharId() {
        Response response = responseGetCustomersId('&');

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }

    @Test
    @DisplayName("Превышение максимального значения поля id при получении клиента")
    @Description("Согласно документации, тип данных поля id int32. Но по факту сервис принимает и большее " +
            "значение")
    public void testGetCustomersMaxValueExcessId() {
        Response response = responseGetCustomersId(Integer.MAX_VALUE);

        checkErrorMessage(response, BAD_REQUEST);
    }

    @Test
    @DisplayName("Получение несуществующего клиента")
    @Description("Пытаемся получить несуществующего клиента. Что бы проверка была корректной, сначала " +
            "создаем клиента, получаем его ID, затем удаляем, и пробуем получить с помощью метода. Ожидаем " +
            "404")
    public void testGetUnknownCustomers() {
        Response customer = responsePostCustomers("create-customers");
        String id = getId(customer);
        responseDeleteCustomers(id);
        Response response = responseGetCustomersId(id);

        checkStatusCode(response, 404);
        checkErrorMessage(response, NOT_FOUND);
    }
}
