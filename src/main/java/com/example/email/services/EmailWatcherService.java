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
package com.example.email.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.email.model.Message;

@Service
public class EmailWatcherService {

    @Value("${stefan.settings.email.warning}")
    private String emailWarningText;
    
    private Logger LOGGER = LoggerFactory.getLogger(EmailWatcherService.class);

    @Value("${stefan.settings.email.internal-domain}")
    private String internalDomain;
    private Set<String> externalEmails = new HashSet<String>();
    private Set<String> toEmails;
    private Set<String> ccEmails;
    private Set<String> bccEmails;

    /**
     * 
     * @param message
     * @return EmailWatcherService
     */
    public EmailWatcherService watchAllRecipients(Message message) {

        toEmails = new HashSet<String>(Arrays.asList(message.getTo().split(",")));
        ccEmails = new HashSet<String>(Arrays.asList(message.getCc().split(",")));
        bccEmails = new HashSet<String>(Arrays.asList(message.getBcc().split(",")));

        return this;
    }

    /**
     * 
     * @param message
     */
    public void logExternalDomainsEmailsAddresses() {
        
        externalEmails.addAll(toEmails.stream().filter(s -> !s.contains(internalDomain)).collect(Collectors.toSet()));
        externalEmails.addAll(ccEmails.stream().filter(s -> !s.contains(internalDomain)).collect(Collectors.toSet()));
        externalEmails.addAll(bccEmails.stream().filter(s -> !s.contains(internalDomain)).collect(Collectors.toSet()));
        String externalEmailsString = String.join(",", externalEmails);
        LOGGER.warn(emailWarningText + externalEmailsString);
    }

    public Set<String> getToEmails() {
        return toEmails;
    }

    public Set<String> getCcEmails() {
        return ccEmails;
    }

    public Set<String> getBccEmails() {
        return bccEmails;
    }

}
