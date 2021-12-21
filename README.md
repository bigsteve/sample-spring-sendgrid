Sample Spring Sendgrid integraton
===============

A generic makefile for starting projects

#Overview:
* Configuration

#How to use:

application.properties:

# Custom config
stefan.settings.email.internal-domain=gmail.com # Your safe internal domain not need to be logged
stefan.settings.email.warning="Emails were sent to external domains: "

# Sendgrid config
spring.sendgrid.api-key=YOUR_SG_API_KEY
spring.sendgrid.api-email-from=YOUR_SG_FROM_EMAIL

external email domains are logged at /emails-warning.log



Endpoint URL:
localhost:8080/api/send-email

json body on post method call (bcc and cc fields not implemented):
{
    "to":"address1@example.com,email2@example.com",
    "cc":"",
    "bcc": "",
    "body":"This is a test message body from postman json",
    "subject":"Test with weather and quote from postman",
    "contentType": "text/plain",
    "enrich": true,
    "test": false
}


* Install and run

Run mvn clean install then run the appication


#Thanks to:
* Spring and Sendgrid developers