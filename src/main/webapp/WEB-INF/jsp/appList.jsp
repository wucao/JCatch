<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="include/header.jsp" %>

<div class="row" style="padding-bottom: 20px">
    <div class="col-md-3">
        <a href="${pageContext.request.contextPath}/app/create" class="btn btn-block btn-primary">新建应用</a>
    </div>
</div>
<div class="row">
    <c:forEach items="${list}" var="item">
        <div class="col-md-6">
            <div class="box box-widget">
                <div class="box-header with-border">
                    <div class="user-block">
                        <span style="width: 40px; height: 40px; float: left; border-radius: 50%; display: inline-block; text-align: center; line-height: 40px; background-color: #D1E9E9;">
                            <c:set var="firstChar" value="${fn:substring(item.name, 0, 1)}" />
                            <c:out value="${firstChar}" />
                        </span>
                        <span class="username"><a href="${pageContext.request.contextPath}/exception/index?appId=<c:out value="${item.id}" />"><c:out value="${item.name}" /></a></span>
                        <span class="description" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;"><c:out value="${item.description}" /></span>
                    </div>
                    <div class="box-tools">
                        <button type="button" class="btn btn-box-tool btn-show-key" data-id="<c:out value="${item.id}" />" data-secret-key="<c:out value="${item.secretKey}" />"><i class="fa fa-key"></i></button>
                        <a class="btn btn-box-tool" href="${pageContext.request.contextPath}/app/edit?id=<c:out value="${item.id}" />"><i class="fa fa-edit"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<div class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span></button>
                <h4 class="modal-title">应用ID和密钥</h4>
            </div>
            <div class="modal-body">
                <div class="table-responsive">
                    <table id="export-csv-table" class="table">
                        <tbody>
                        <tr>
                            <th>应用ID:</th>
                            <td class="show-id"></td>
                        </tr>
                        <tr>
                            <th>Secret Key:</th>
                            <td class="show-secret-key"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $(".btn-show-key").click(function () {
            var appId = $(this).attr("data-id");
            var secretKey = $(this).attr("data-secret-key");
            $(".show-id").text(appId);
            $(".show-secret-key").text(secretKey);
            $('.modal').modal('show')
        });
    });
</script>
<%@include file="include/footer.jsp" %>