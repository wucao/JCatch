<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
    <ul class="pagination">
        <c:if test="${page > 1}">
            <li>
                <a href="#" data-p="${page - 1}">
                    <span>&laquo;</span>
                </a>
            </li>
        </c:if>
        <c:forEach items="${pageList}" var="pageListItem">
            <li <c:if test="${pageListItem == page}">class="active"</c:if>><a  href="#" data-p="${pageListItem}">${pageListItem}</a></li>
        </c:forEach>
        <c:if test="${page < pageCount}">
            <li>
                <a href="#" data-p="${page + 1}">
                    <span>&raquo;</span>
                </a>
            </li>
        </c:if>
    </ul>
</nav>
<script src="//cdn.bootcss.com/URI.js/1.18.1/URI.min.js"></script>
<script>
    $(document).ready(function () {
        $(".pagination a").click(function () {
            var uri = new URI();
            uri.setSearch("p", $(this).attr("data-p"));
            location.href = uri.toString();
            return false;
        });
    });
</script>