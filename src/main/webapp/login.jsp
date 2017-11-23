<%@ page import="com.opensymphony.xwork2.ActionContext" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: MurphySL
  Date: 2017/11/20
  Time: 19:53
  To change this template use File | Settings | File Templates.

////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//            佛祖保佑       永无BUG     永不修改                 //
////////////////////////////////////////////////////////////////////
--%>

<%@ page contentType="text/html;charset=UTF-8" %>


<html>
<head>
    <title><s:text name="login_welcome"/></title>
</head>
<body style="color: blue">

<s:actionerror/>
<s:form action="login" method="POST">
    <table>
        <tr>
            <th>
                <s:text name="login_welcome"/>
            </th>
        </tr>
        <tr><s:textfield name="email" key="email"/></tr>
        <tr><s:password name="password" key="password"/></tr>
        <tr>
            <td><s:text name="login_type"/></td>
            <td>
                <select name="type">
                    <option value="user"><s:text name="login_type_user"/></option>
                    <option value="admin"><s:text name="login_type_admin"/></option>
                </select>
            </td>
        </tr>
        <tr><s:submit key="submit"/></tr>
    </table>
</s:form>

<a href="register.jsp"><s:text name="login_register"/></a>

<%--<td><img src="../71ec8a90-b15f-48e6-954e-a5f672999aef.png" width="100px" height="100px"></td>--%>


<%-- GTMD EL OGNL--%>
<%
    String local = String.valueOf(ActionContext.getContext().getLocale());
    if(local == null || local.equals("zh_CN")){
%>
<div>
    <a href="locale_default?request_locale=en_US">ENGLISH</a>
</div>
<%
    }else{
%>
<div>
    <a href="locale_default?request_locale=zh_CN">中文</a>
</div>
<%
    }
%>

</body>
</html>

