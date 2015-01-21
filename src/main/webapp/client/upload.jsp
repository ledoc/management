<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<c:url var="uploadFile" value="/client/uploadFile">
		<c:param name="nom" value="${client.nom}" />
		 </c:url>
		<form:form method="POST" action="${uploadFile}" modelAttribute="client"
			enctype="multipart/form-data">
			File to upload: <input type="file" name="file"><br /> Name:
			<form:input name="nom" path="nom" />
			<br /> <br /> <input type="submit" value="Upload"> Press
			here to upload the file!
		</form:form>

	</div>
</body>
</html>