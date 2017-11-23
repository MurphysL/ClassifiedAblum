<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: MurphySL
  Date: 2017/11/22
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><s:property value="tname"/></title>
</head>
<body style="color: blue;">

<s:debug/>


<table border="3px;">
    <tr>
        <th><s:text name="pic"/></th>
        <th><s:text name="pic_size"/></th>
        <th><s:text name="pic_date"/></th>
        <th><s:text name="pic_download"/></th>
    </tr>
    <s:iterator value="#pics" status="st" var="pic">
        <tr <s:if test="#st.odd">style="background-color: #bbbbbb"</s:if> >
            <td><img src="<s:property value="#pic.path"/> "></td>
            <td><s:property value="#pic.size"/></td>
            <td><s:property value="#pic.uploadDate"/></td>
            <td><a href="download.action?inputPath=<s:property value="#pic.path"/>&contentType=image/png&downFileName=<s:property value="#pic.pname"/>"><s:text name="pic_download"/></a> </td>
        </tr>
    </s:iterator>
</table>

</body>
</html>
