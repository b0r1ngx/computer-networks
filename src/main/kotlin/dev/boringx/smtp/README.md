## SMTP Client

### Run

To run the `Client`, execute the following command in a repository's root directory:

```

```

### Client logs

#### Successfully

```
220 smtp.gmail.com ESMTP u11-20020a056512128b00b005194c422b0fsm250205lfs.177 - gsmtp
HELO response: 250 smtp.gmail.com at your service
STARTTLS response: 220 2.0.0 Ready to start TLS
AUTH PLAIN response: 235 2.7.0 Accepted
MAIL FROM response: 250 2.1.0 OK u11-20020a056512128b00b005194c422b0fsm250205lfs.177 - gsmtp
RCPT TO response: 250 2.1.5 OK u11-20020a056512128b00b005194c422b0fsm250205lfs.177 - gsmtp
DATA response: 354  Go ahead u11-20020a056512128b00b005194c422b0fsm250205lfs.177 - gsmtp
QUIT response: 250 2.0.0 OK  1713373761 u11-20020a056512128b00b005194c422b0fsm250205lfs.177 - gsmtp
```

#### Empty credentials

```
220 smtp.gmail.com ESMTP c7-20020ac24147000000b00516c5eef5c7sm18630lfi.243 - gsmtp
HELO response: 250 smtp.gmail.com at your service
STARTTLS response: 220 2.0.0 Ready to start TLS
AUTH PLAIN response: 535-5.7.8 Username and Password not accepted. For more information, go to
MAIL FROM response: 535 5.7.8  https://support.google.com/mail/?p=BadCredentials c7-20020ac24147000000b00516c5eef5c7sm18630lfi.243 - gsmtp
RCPT TO response: 530-5.7.0 Authentication Required. For more information, go to
DATA response: 530 5.7.0  https://support.google.com/mail/?p=WantAuthError c7-20020ac24147000000b00516c5eef5c7sm18630lfi.243 - gsmtp
QUIT response: 530-5.7.0 Authentication Required. For more information, go to
```

### Conclusion

- TODO!