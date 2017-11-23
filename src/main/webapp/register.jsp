<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: MurphySL
  Date: 2017/11/20
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><s:text name="register_welcome"/></title>
</head>
<body style="color: blue">

<s:actionerror/>
<s:form action="register" method="POST">
    <table>
        <tr>
            <th>
                <s:text name="login_welcome"/>
            </th>
        </tr>
        <tr><s:textfield name="email" key="email"/></tr>
        <tr><s:textfield name="username" key="login_username"/></tr>
        <tr><s:password name="password" key="password"/></tr>
        <tr><s:password name="confirm" key="login_confirm"/></tr>
        <tr>
            <td><s:textfield name="code" key="register_code"/></td>
            <td><img src="create.action" onclick="this.src='create.action?'+ Math.random()"/></td>
        </tr>
        <tr><s:submit key="login_submit"/></tr>
    </table>
</s:form>


</body>
</html>
