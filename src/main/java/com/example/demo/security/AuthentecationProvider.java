// package com.example.demo.security;

// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;

// import io.jsonwebtoken.lang.Arrays;

// public class AuthentecationProvider implements AuthenticationProvider{

//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//         String userName=authentication.getName();
//         String password=authentication.getCredentials().toString();

//         if("".equals(userName)&&"".equals(password)){
//             return new UsernamePasswordAuthenticationToken(userName, password,Arrays.asList());
//         }
//         else{
//             throw new BadCredentialsException("Invalid email or password");
//         }
//         // throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
//     }

//     @Override
//     public boolean supports(Class<?> authentication) {
//         return authentication.equals(UsernamePasswordAuthenticationToken.class);
//         // throw new UnsupportedOperationException("Unimplemented method 'supports'");
//     }


    
// }
