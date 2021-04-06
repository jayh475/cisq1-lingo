package nl.hu.cisq1.lingo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import nl.hu.cisq1.lingo.security.application.UserService;
import nl.hu.cisq1.lingo.security.data.SpringUserRepository;
import nl.hu.cisq1.lingo.security.data.User;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;


public class SecurityConfiguration {


        public static String getJwtToken(SpringUserRepository userRepository) {
            UserService userService = new UserService(userRepository, mock(PasswordEncoder.class));

            try{
                User user =  userService.loadUserByUsername("jayh475");
                userRepository.delete(user);
            }catch (Exception ignored){};


            userService.register("jayh475", "123456","Jayh","de Cuba");
            return getToken();
        }

        public static String getToken(){
            String username = "jayh475";
            String firstname = "Jayh";
            String lastName = "de Cuba";

            String secret = "\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImp0aSI6IjcyZDMyZTAzLTIxNzUtNGI3Mi1hOTQ5LWRjNzE0ZGE5OTM5YyIsImlhdCI6MTYxNjI0MjA2NiwiZXhwIjoxNjE2MjQ1NjY2fQ.kTXHnKPZDGYyNFpFnZrMA7Az1ScCwnM_5It1jRHEf0U\"";

            System.out.println("secret " + secret);
            int expDateMs = 4600000;
            List<String> roles = List.of("ROLE_USER");

            byte[] signingKey = secret.getBytes();

            return "Bearer " + Jwts.builder()
                    .signWith( Keys.hmacShaKeyFor(signingKey),SignatureAlgorithm.HS512)
                    .setHeaderParam("typ", "JWT")
                    .setIssuer("hu-lingo-api")
                    .setAudience("hu-lingo")
                    .setSubject(username)
                    .setSubject(firstname)
                    .setSubject(lastName)
                    .setExpiration(new Date(System.currentTimeMillis() + expDateMs))
                    .claim("rol", roles)
                    .compact();
        }
    }


