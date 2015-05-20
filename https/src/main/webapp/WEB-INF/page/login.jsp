<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <form action="./login" method="post">
  	userName:<input name="userName" type="text"><br>
  	password:<input name="password" type="password"><br>
  	redirect:${url}<input hidden="hidden" type="text" value="${url}" name="url"><br>
  	JSESSIONID:${JSESSIONID}<input hidden="hidden" type="text" value="${JSESSIONID}" name="JSESSIONID">
  	<button type="submit">submit</button>
  </form>
</html>