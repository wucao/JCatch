<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="include/header.jsp" %>
<link href="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker.min.css" rel="stylesheet">
<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/js/bootstrap-datepicker.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap-datepicker/1.6.4/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script>
    $(function () {
        // 日期选择
        $(".xxg-datepicker").datepicker({
            format: "yyyy-mm-dd",
            language: "zh-CN"
        });
    });
</script>
<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <div class="pull-right box-tools">
                    <button type="button" class="btn bg-teal btn-sm pull-right" data-widget="collapse" data-toggle="tooltip" title="Collapse" style="margin-right: 5px;">
                        <i class="fa fa-minus"></i>
                    </button>
                </div>
                <h3 class="box-title">
                    查询
                </h3>
            </div>
            <div class="box-body">
                <form method="get" action="${pageContext.request.contextPath}/exception/index">
                    <input type="hidden" name="appId" value="<c:out value="${param.appId}" />">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>创建时间开始：</label>
                                <input type="text" class="form-control xxg-datepicker" name="startDate" value="<c:out value="${param.startDate}" />" autocomplete="off">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>创建时间结束：</label>
                                <input type="text" class="form-control xxg-datepicker" name="endDate" value="<c:out value="${param.endDate}" />" autocomplete="off">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>IP：</label>
                                <input type="text" class="form-control" name="remoteAddr" value="<c:out value="${param.remoteAddr}" />" autocomplete="off">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>异常：</label>
                                <input type="text" class="form-control" name="exceptionName" value="<c:out value="${param.exceptionName}" />" autocomplete="off">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Message：</label>
                                <input type="text" class="form-control" name="message" value="<c:out value="${param.message}" />" autocomplete="off">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>类名：</label>
                                <input type="text" class="form-control" name="className" value="<c:out value="${param.className}" />" autocomplete="off">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>方法名：</label>
                                <input type="text" class="form-control" name="methodName" value="<c:out value="${param.methodName}" />" autocomplete="off">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>文件名：</label>
                                <input type="text" class="form-control" name="fileName" value="<c:out value="${param.fileName}" />" autocomplete="off">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>行数：</label>
                                <input type="text" class="form-control" name="lineNumber" value="<c:out value="${param.lineNumber}" />" autocomplete="off">
                            </div>
                        </div>
                    </div>
                    <div style="text-align: right;">
                        <button type="submit" class="btn btn-success">查询</button>
                    </div>
                </form>
            </div>
        </div>
        <div class="box">
            <div class="box-body" style="min-height: 400px;">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th style="white-space: nowrap;">IP</th>
                            <th style="white-space: nowrap;">异常</th>
                            <th style="white-space: nowrap;">位置</th>
                            <th style="white-space: nowrap;">提交次数</th>
                            <th style="white-space: nowrap;">最后提交</th>
                            <th style="white-space: nowrap;">查看详情</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list}" var="item">
                            <tr>
                                <td style="white-space: nowrap;"><c:out value="${item.remoteAddr}" /></td>
                                <td style="word-break: break-all;"><c:out value="${item.exceptionName}" /></td>
                                <td style="word-break: break-all;"><c:out value="${item.fileName}" />第<c:out value="${item.lineNumber}" />行</td>
                                <td style="white-space: nowrap;"><c:out value="${item.occurrenceNumber}" /></td>
                                <td style="white-space: nowrap;"><fmt:formatDate value="${item.lastSubmitTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                <td style="white-space: nowrap;">
                                    <a href="${pageContext.request.contextPath}/exception/detail?id=${item.id}" class="btn btn-primary btn-sm">查看详情</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="box-footer">
                <%@include file="include/pager.jsp" %>
            </div>
        </div>
    </div>
</div>
<%@include file="include/footer.jsp" %>