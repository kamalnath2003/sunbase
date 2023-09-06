<!-- index.jsp -->
<!DOCTYPE html>
<html>
<head>
    <title>API Test</title>
</head>
<body>
    <h1>Welcome to API Test</h1>
    <form action="login" method="POST">
        <label for="login_id">Login ID:</label>
        <input type="text" id="login_id" name="login_id" required>
        <br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <br>
        <input type="submit" value="Login">
    </form>
</body>
</html>
