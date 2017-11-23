<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: MurphySL
  Date: 2017/11/20
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主界面</title>
</head>
<body>

<%-- 必须写 <td> GTMD Struts --%>
<table border="3px;" width="300px;">
    <tr>
        <th>
            <s:text name="main_type"/>
        </th>
        <th>
            <s:text name="main_num"/>
        </th>
    </tr>
    <s:iterator value="#session.types" var="type" status="st">
        <tr <s:if test="#st.odd">style="background-color: #bbbbbb;"</s:if> >
            <td>
                <s:a href="check?tname=%{#type.key}">
                    <s:property value="#type.key"/>
                </s:a>
            </td>
            <td><s:property value="#type.value"/></td>
        </tr>
    </s:iterator>
    <tr>
        <s:form action="upload_num" method="GET">
            <td><s:textfield key="upload_num" name="num"/></td>
            <td><s:submit key="submit"/></td>
        </s:form>
    </tr>
</table>

</body>
</html>
