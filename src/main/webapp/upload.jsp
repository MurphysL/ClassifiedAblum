<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: MurphySL
  Date: 2017/11/22
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="color: blue;">

<s:fielderror/>
<s:form action="upload_file" method="POST" enctype="multipart/form-data">

    <s:iterator begin="1" end="num" var="i" status="st">
        <s:file name="pic"/>
    </s:iterator>
    <s:submit key="submit"/>

</s:form>


</body>
</html>
