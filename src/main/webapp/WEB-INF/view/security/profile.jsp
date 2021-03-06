<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<page:template>
    <jsp:body>
        <!-- Page Content -->
        <div class="container">

            <!-- Page Heading/Breadcrumbs -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Работа Spring Security
                        <small>доступ только для зарегистрированных пользователей</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="index.html">Home</a>
                        </li>
                        <li class="active">Spring Security</li>
                    </ol>
                </div>
            </div>
            <!-- /.row -->

            <c:url value="/uploadFile" var="fileUploadControllerURL" />
            <!-- Content Row -->
            <div class="row">

                <div class="col-lg-12">
                    <p>Данные о пользователе из Spring Security. Текст ниже доступен только ролям супер юзеру, админу, или юзеру: </p>
                    <security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_USER', 'ROLE_USER')">
                        <b>Вы вошли как:</b> <security:authentication property="principal.username"/> с ролью: <b><security:authentication property="principal.authorities"/> </b>
                        <br />


                        <br />
                        <br />
                        <p>Ссылка logout имеет атрибут  <span style="color: #0080c0;">/j_spring_security_logout</span>, который прописан в security-config.xml</p>
                        <span style="color: #568C00;"><security:authentication property="principal.username"/></span>
                        <a style="color: red;" href="<c:url value="/j_spring_security_logout"/>">Logout</a>
                    </security:authorize>

                </div>

            </div>
            <!-- /.row -->

            <hr>

        </div>
        <!-- /.container -->
    </jsp:body>
</page:template>
