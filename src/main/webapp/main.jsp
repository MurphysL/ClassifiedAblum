<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: MurphySL
  Date: 2017/11/20
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><s:text name="ablum_welcome"/></title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="css/normalize.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <sb:head/>
    <style>
        body {
            background-color: #eee;
            overflow: hidden;
        }
        canvas {
            background-color: #eee;
            display: block;
            margin: 0 auto;
        }
    </style>
</head>
<body>


<canvas id="canvas" style="position: absolute;z-index: 2"></canvas>

<a href="create_type_default.action" class="btn btn-primary btn-lg" style="position: absolute;z-index: 3; right: 20px; top: 20px;"><s:text name="type_create"/></a>

<div class="container" style="width: 800px; margin: auto; position:absolute; z-index: 3; left: 0;right: 0;top: 140px; bottom: 0; ">
    <s:actionerror theme="bootstrap"/>

    <table class="table table-striped">
        <tr>
            <th>
                <s:text name="main_type"/>
            </th>
            <th>
                <s:text name="main_num"/>
            </th>
            <th>
                <s:text name="type_delete"/>
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
                <td> <a class="btn btn-default" href="delete_type.action?tname=<s:property value="#type.key"/>"><s:text name="type_delete"/></a> </td>
            </tr>
        </s:iterator>
    </table>

    <div style="width:100px; margin: 0 auto;">
        <s:form action="upload_num" method="GET" theme="bootstrap" cssClass="form-horizontal">
            <s:textfield key="upload_num" name="num"/>
            <s:submit key="submit" cssClass="btn btn-default"/>
        </s:form>
    </div>

</div>


<script>
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    var cw = canvas.width = window.innerWidth,
        cx = cw / 2;
    var ch = canvas.height = window.innerHeight,
        cy = ch / 2;

    ctx.fillStyle = "#000";
    var linesNum = 16;
    var linesRy = [];
    var requestId = null;

    function Line(flag) {
        this.flag = flag;
        this.a = {};
        this.b = {};
        if (flag == "v") {
            this.a.y = 0;
            this.b.y = ch;
            this.a.x = randomIntFromInterval(0, ch);
            this.b.x = randomIntFromInterval(0, ch);
        } else if (flag == "h") {
            this.a.x = 0;
            this.b.x = cw;
            this.a.y = randomIntFromInterval(0, cw);
            this.b.y = randomIntFromInterval(0, cw);
        }
        this.va = randomIntFromInterval(25, 100) / 100;
        this.vb = randomIntFromInterval(25, 100) / 100;

        this.draw = function() {
            ctx.strokeStyle = "#ccc";
            ctx.beginPath();
            ctx.moveTo(this.a.x, this.a.y);
            ctx.lineTo(this.b.x, this.b.y);
            ctx.stroke();
        }

        this.update = function() {
            if (this.flag == "v") {
                this.a.x += this.va;
                this.b.x += this.vb;
            } else if (flag == "h") {
                this.a.y += this.va;
                this.b.y += this.vb;
            }

            this.edges();
        }

        this.edges = function() {
            if (this.flag == "v") {
                if (this.a.x < 0 || this.a.x > cw) {
                    this.va *= -1;
                }
                if (this.b.x < 0 || this.b.x > cw) {
                    this.vb *= -1;
                }
            } else if (flag == "h") {
                if (this.a.y < 0 || this.a.y > ch) {
                    this.va *= -1;
                }
                if (this.b.y < 0 || this.b.y > ch) {
                    this.vb *= -1;
                }
            }
        }

    }

    for (var i = 0; i < linesNum; i++) {
        var flag = i % 2 == 0 ? "h" : "v";
        var l = new Line(flag);
        linesRy.push(l);
    }

    function Draw() {
        requestId = window.requestAnimationFrame(Draw);
        ctx.clearRect(0, 0, cw, ch);

        for (var i = 0; i < linesRy.length; i++) {
            var l = linesRy[i];
            l.draw();
            l.update();
        }
        for (var i = 0; i < linesRy.length; i++) {
            var l = linesRy[i];
            for (var j = i + 1; j < linesRy.length; j++) {
                var l1 = linesRy[j]
                Intersect2lines(l, l1);
            }
        }
    }

    function Init() {
        linesRy.length = 0;
        for (var i = 0; i < linesNum; i++) {
            var flag = i % 2 == 0 ? "h" : "v";
            var l = new Line(flag);
            linesRy.push(l);
        }

        if (requestId) {
            window.cancelAnimationFrame(requestId);
            requestId = null;
        }

        cw = canvas.width = window.innerWidth,
            cx = cw / 2;
        ch = canvas.height = window.innerHeight,
            cy = ch / 2;

        Draw();
    };

    setTimeout(function() {
        Init();

        addEventListener('resize', Init, false);
    }, 15);

    function Intersect2lines(l1, l2) {
        var p1 = l1.a,
            p2 = l1.b,
            p3 = l2.a,
            p4 = l2.b;
        var denominator = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y);
        var ua = ((p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x)) / denominator;
        var ub = ((p2.x - p1.x) * (p1.y - p3.y) - (p2.y - p1.y) * (p1.x - p3.x)) / denominator;
        var x = p1.x + ua * (p2.x - p1.x);
        var y = p1.y + ua * (p2.y - p1.y);
        if (ua > 0 && ub > 0) {
            markPoint({
                x: x,
                y: y
            })
        }
    }

    function markPoint(p) {
        ctx.beginPath();
        ctx.arc(p.x, p.y, 2, 0, 2 * Math.PI);
        ctx.fill();
    }

    function randomIntFromInterval(mn, mx) {
        return ~~(Math.random() * (mx - mn + 1) + mn);
    }</script>
</body>
</html>
