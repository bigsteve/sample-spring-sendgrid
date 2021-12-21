/**

Copyright (c) 2021 Stefan Miller

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

 */
package com.example.email;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SendEmailControllerTests {

    @Autowired
    private MockMvc mockMvc;
    private final String validRequestJson = "{\r\n"
            + "    \"to\":\"test@example.com\",\r\n"
            + "    \"cc\":\"\",\r\n"
            + "    \"bcc\": \"\",\r\n"
            + "    \"body\":\"test body\",\r\n"
            + "    \"subject\":\"test\",\r\n"
            + "    \"contentType\": \"text/plain\",\r\n"
            + "    \"enrich\": true,\r\n"
            + "    \"test\": true\r\n"
            + "}";
    private final String invalidRequestJson = "\r\n"
            + "    \"to\":\"bla\",\r\n"
            + "    \"enrich\": true,\r\n"
            + "    \"test\": true\r\n"
            + "}";

    @Test
    public void validJsonBodySendEmailUrlTest() throws Exception {
        this.mockMvc.perform(post("/api/send-email").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.validRequestJson))
                .andExpect(status().isOk());
    }
    
    @Test
    public void invalidJsonBodySendEmailUrlTest() throws Exception {
        this.mockMvc.perform(post("/api/send-email").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.invalidRequestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void noJsonBodySendEmailUrlTest() throws Exception {
        this.mockMvc.perform(post("/api/send-email")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void inexistentUrlTest() throws Exception {
        this.mockMvc.perform(post("/api/send-email/no-such-url")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void inexistentGetMethodSendEmailUrlTest() throws Exception {
        this.mockMvc.perform(get("/api/send-email")).andDo(print()).andExpect(status().isMethodNotAllowed());
    }

}
