<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
	<title>Login Page</title>
	<style>
		.failed{
			color:red;
		}
	</style>
</head>

<body>
	<h3>My custom login page</h3>

	<form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="POST">
	
		<c:if test="${param.error != null}">
			<i class="failed">Sorry! Invalid username/password</i>
		</c:if>
	
		<p>
			User Name: <input type="text" name="username" />
		</p>
		
		<p>
			Password: <input type="password" name="password" />
		</p>
		
		<input type="submit" value="Login" />
	
	</form:form>

</body>

</html>