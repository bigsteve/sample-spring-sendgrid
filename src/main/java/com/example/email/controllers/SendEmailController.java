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
package com.example.email.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.email.httpclients.HttpClientDailyQuoteImpl;
import com.example.email.httpclients.HttpClientWeatherImpl;
import com.example.email.model.Message;
import com.example.email.model.payload.ApiResponse;
import com.example.email.services.EmailWatcherService;
import com.example.email.services.SendGridMailerService;

@RestController
@RequestMapping("/api")
public class SendEmailController {

    @Autowired
    HttpClientDailyQuoteImpl dailyQuoteClient;

    @Autowired
    HttpClientWeatherImpl weatherClient;

    @Autowired
    SendGridMailerService sendGridMailerService;

    @Autowired
    EmailWatcherService emailWatcherService;

    private Logger LOGGER = LoggerFactory.getLogger(SendEmailController.class);

    /**
     * 
     * @param message
     * @return ResponseEntity
     */
    @PostMapping("/send-email")
    @ResponseBody
    public ResponseEntity<ApiResponse> sendEmail(@RequestBody Message message) {

        // TODO: use interceptors?
        dailyQuoteClient.mockHttpCall();
        weatherClient.setLocation("Carlsbad, CA").mockHttpCall();
        emailWatcherService.watchAllRecipients(message).logExternalDomainsEmailsAddresses();

        String sgResponse = "This was a test and no emails will be send.";

        if (!message.isTest()) {
            try {
                sgResponse = sendGridMailerService.sendTextEmail(message);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.ok(new ApiResponse(true, sgResponse, HttpStatus.EXPECTATION_FAILED.value()));

            }
        }

        LOGGER.info(sgResponse);

        return ResponseEntity.ok(new ApiResponse(true, sgResponse, HttpStatus.ACCEPTED.value()));
    }

}
