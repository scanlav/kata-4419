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
@Story("Удаление клиента методом GET")
public class DeleteCustomersTest {

    @Test
    @DisplayName("Удаление клиента")
    @Description("Проверка корректного удаления клиента.")
    public void testDeleteCustomers() {
        String phoneNumber = randomNumber();
        Response customer = responsePostCustomers("create-customers", phoneNumber);
        String id = getId(customer);

        checkRequiredFieldsCorrectCreatedCustomers(customer, "Petr", "Petrov", phoneNumber);

        Response responseDeleteCustomers = responseDeleteCustomers(id);
        checkStatusCode(responseDeleteCustomers, 204);

        Response responseGetCustomers = responseGetCustomersId(id);
        checkStatusCode(responseGetCustomers, 404);
    }

    @Test
    @DisplayName("Удаление несуществующего клиента.")
    @Description("Попытка удалить несуществующего клиента. Ожидаем 404")
    public void testDeleteCustomersNotFound() {
        Response response = responseDeleteCustomers(0);

        checkStatusCode(response, 404);
        checkErrorMessage(response, NOT_FOUND);
    }

    @Test
    @DisplayName("Получение клиента с передачей в поле Id строкового значения")
    @Description("Попытка получить клиента, передав в поле ID строковое значение. Ожидаем 400")
    public void testDeleteCustomersIdString() {
        Response response = responseGetCustomersId("str");

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }

    @Test
    @DisplayName("Получение клиента с передачей в поле id спецсимвола")
    @Description("Пробуем получить клиента, передав в поле ID спецсимвол &. Ожидаем 400")
    public void testDeleteCustomersIdChar() {
        Response response = responseDeleteCustomers('&');

        checkStatusCode(response, 400);
        checkErrorMessage(response, BAD_REQUEST);
    }
}
