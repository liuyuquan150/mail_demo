# Project Title
'mail_demo' is a demo of sending mail via 'spring-boot-starter-mail', 
with a small extension to allow multi-platform multi-account mail sending (which is not a very difficult point)

Of course, 'mail_demo' is a demo, but it is coded according to the enterprise development model. 
For non-primary business like mail sending, which is often done asynchronously, don't forget to provide a retry mechanism, so I resorted to 'spring-retry'.

'mail_demo' is not involved with any business (using 'EventListener'), 
in real development you can change 'EventListener' to 'TransactionalEventListener' if you need to hold some kind of relationship between the main business and the sending mail business (e.g. the mail business is only carried out if the main business is successful).

# Development
'mai_demo' provides a completed test unit with several values that need to be changed before you can run this test unit, as follows:

* Modify the values of 'spring.mail.username' and 'spring.mail.password' in the 'application-dev.yml' file, which are 'sender's email' and 'platform authorization code' respectively.
* Modify the values of 'mail.configs.username' and 'mail.configs.password' in the 'custom/application-dev-custom.yml' file, which are 'sender's email' and 'platform authorization code' respectively.
* Modify the value of the 'to' property in the 'IMailServiceTest' test unit, which is the recipient's email.