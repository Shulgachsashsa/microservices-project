package demo.authservice.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailCodeService {

    public String generateCode() {
        return new Random()
                .ints(48, 58)
                .limit(6)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
