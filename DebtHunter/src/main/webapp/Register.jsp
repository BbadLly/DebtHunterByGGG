<%-- 
    Document   : Register
    Created on : Oct 13, 2020, 8:57:45 PM
    Author     : GuideKai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Debt Hunter | Sign Up</title>
    </head>
    <body>
        <form action="Login" method="POST">
            <p>E-mail  <input type="email" name="email" required value="${email}" /> </p>
            <p>Password  <input type="password" name="password" id="password" value="${password}" required/> </p>
            <p>Confirmed Password  <input type="password" name="confirmed" id="confirmed" onkeyup="check();" required/> </p>
            <span id='message'></span>
<!--            <script>
                var check = function () {
                    if (document.getElementById('password').value ===
                            document.getElementById('confirmed').value) {
                        document.getElementById('message').style.color = 'green';
                        document.getElementById('message').innerHTML = 'matching';
                    } else {
                        document.getElementById('message').style.color = 'red';
                        document.getElementById('message').innerHTML = 'not matching';
                    }
                };
            </script>-->
            <p>First Name  <input type="text" name="firstname" value="${fname}" required/> </p>
            <p>Last Name  <input type="text" name="lastname" value="${lname}" required/> </p>
            <p>Tel.  <input type="number" name="tel" value="${tel}"/> </p>                
            <p><input type="submit" name="Accept" /> </p>                
        </form>
    </body>
</html>
