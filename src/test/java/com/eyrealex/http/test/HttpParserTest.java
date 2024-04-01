package com.eyrealex.http.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    // Setup method to initialize HttpParser instance before running tests
    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    // Test method for parserHttpRequest
    @Test
    void parseHttpRequest() {
        // Invoke parserHttpRequest with a valid test case
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateValidGETTestCase()
            );
        } catch (HttpParsingException e) {
            fail(e);
        }
        // Assertions can be added here to verify the correctness of parsing
        assertEquals(request.getMethod(), HttpMethod.GET);
    }
    @Test
    void parseHttpRequestBadMethod() {
        // Invoke parserHttpRequest with a valid test case
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodName()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }
    @Test
    void parseHttpRequestBadMethod2() {
        // Invoke parserHttpRequest with a valid test case
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodName2()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }
    @Test
    void parseHttpRequestInvalidNumItems1() {
        // Invoke parserHttpRequest with a valid test case
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseRequestLineInvalidNumItems1()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpEmptyRequestLine() {
        // Invoke parserHttpRequest with a valid test case
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseSendEmptyRequestLine()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpEmptyRequestLineCRnoLF() {
        // Invoke parserHttpRequest with a valid test case
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseRequestLineOnlyCRnoLF()
            );
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    // Helper method to generate a valid test case InputStream
    private InputStream generateValidGETTestCase() {
        // Generate a raw HTTP request string
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "sec-ch-ua: \"Not A(Brand\";v=\"99\", \"Opera GX\";v=\"107\", \"Chromium\";v=\"121\"\r\n" +
                "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"Windows\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36 OPR/107.0.0.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: en-US,en;q=0.9\\r\\n\"" + "\r\n";

        // Convert raw data to InputStream using US_ASCII encoding
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
    private InputStream generateBadTestCaseMethodName() {
        // Generate a raw HTTP request string
        String rawData = "GeT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\\r\\n\"" + "\r\n";

        // Convert raw data to InputStream using US_ASCII encoding
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
    private InputStream generateBadTestCaseMethodName2() {
        // Generate a raw HTTP request string
        String rawData = "GETTT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\\r\\n\"" + "\r\n";

        // Convert raw data to InputStream using US_ASCII encoding
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
    private InputStream generateBadTestCaseRequestLineInvalidNumItems1() {
        // Generate a raw HTTP request string
        String rawData = "GET / AAAA HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\\r\\n\"" + "\r\n";

        // Convert raw data to InputStream using US_ASCII encoding
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
    private InputStream generateBadTestCaseRequestLineOnlyCRnoLF() {
        // Generate a raw HTTP request string
        String rawData = "GET / HTTP/1.1\r" + // <--- No LF
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\\r\\n\"" + "\r\n";

        // Convert raw data to InputStream using US_ASCII encoding
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }
    private InputStream generateBadTestCaseSendEmptyRequestLine() {
        // Generate a raw HTTP request string
        String rawData = "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9\\r\\n\"" + "\r\n";

        // Convert raw data to InputStream using US_ASCII encoding
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );
        return inputStream;
    }

}
