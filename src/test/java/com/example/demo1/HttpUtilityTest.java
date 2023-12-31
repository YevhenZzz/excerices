package com.example.demo1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest
class HttpUtilityTest {

    @Mock
    private HttpResponse<String> httpResponse;

    @Test
    void test_sendGetRequest() throws IOException, InterruptedException {
        String url = "https://example.com/api";
        String expectedResponse = "{\"foo\": \"bar\"}";

        Mockito.when(httpResponse.body()).thenReturn(expectedResponse);
        HttpClient httpClientMock = Mockito.mock(HttpClient.class);

        try (MockedStatic<HttpClient> httpClientMockedStatic = Mockito.mockStatic(HttpClient.class)) {
            httpClientMockedStatic.when(HttpClient::newHttpClient).thenReturn(httpClientMock);
            Mockito.when(httpClientMock.send(Mockito.any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                    .thenReturn(httpResponse);

            String actualResponse = com.example.demo1.http_manipulation.HttpUtility.sendGetRequest(url);

            Assertions.assertEquals(expectedResponse, actualResponse);
        }

    }


}
