<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="include/header.jsp" %>

<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-body">
                <div class="table-responsive">
                    <table id="export-csv-table" class="table">
                        <tbody>
                        <tr>
                            <th>IP:</th>
                            <td><c:out value="${detail.remoteAddr}" /></td>
                        </tr>
                        <tr>
                            <th>异常:</th>
                            <td style="word-break: break-all;"><c:out value="${detail.exceptionName}" /></td>
                        </tr>
                        <tr>
                            <th>Message:</th>
                            <td style="word-break: break-all; white-space: pre-wrap;"><c:out value="${detail.message}" /></td>
                        </tr>
                        <tr>
                            <th>StackTrace:</th>
                            <td style="word-break: break-all; white-space: pre-wrap;"><c:out value="${detail.stackTrace}" /></td>
                        </tr>
                        <tr>
                            <th>类名:</th>
                            <td style="word-break: break-all;"><c:out value="${detail.className}" /></td>
                        </tr>
                        <tr>
                            <th>方法名:</th>
                            <td style="word-break: break-all;"><c:out value="${detail.methodName}" /></td>
                        </tr>
                        <tr>
                            <th>文件名:</th>
                            <td style="word-break: break-all;"><c:out value="${detail.fileName}" /></td>
                        </tr>
                        <tr>
                            <th>行号:</th>
                            <td>${detail.lineNumber}</td>
                        </tr>
                        <tr>
                            <th>创建时间:</th>
                            <td><fmt:formatDate value="${detail.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        <tr>
                            <th>最后提交:</th>
                            <td><fmt:formatDate value="${detail.lastSubmitTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        <tr>
                            <th>提交次数:</th>
                            <td>${detail.occurrenceNumber}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="include/footer.jsp" %>