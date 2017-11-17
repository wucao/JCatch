<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="include/header.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">
                    新增应用
                </h3>
            </div>
            <div class="box-body">
                <form method="post" action="${pageContext.request.contextPath}/app/createOrEdit">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>应用名称：</label>
                                <input type="text" class="form-control" name="name" autocomplete="off" value="<c:out value="${app.name}" />">
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>应用描述：</label>
                                <textarea class="form-control" name="description" style="margin: 0px -6px 0px 0px; height: 85px;"><c:out value="${app.description}" /></textarea>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>订阅者：</label>
                                <input type="text" class="form-control" name="subscriber" autocomplete="off" value="<c:out value="${app.subscriber}" />" placeholder="多条以英文逗号分隔">
                            </div>
                        </div>
                    </div>
                    <div style="text-align: right;">
                        <input type="hidden" name="id" value="<c:out value="${app.id}" />">
                        <button type="submit" class="btn btn-success">提交</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="include/footer.jsp" %>