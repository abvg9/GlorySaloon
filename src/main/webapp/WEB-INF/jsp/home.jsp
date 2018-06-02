<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ include file="../jspf/header.jspf"%>


<div class="starter-template">

	<div class="topHome">
		<a href="/saloon"><img class="imgHome" src="${s}/img/Saloon.jpg"></a>
	</div>
	<div class="midHome">
		<div class="leftHome">
			<div class="left1Home">
				<a href="/ranking"><img class="imgHome" src="${s}/img/Ranking.jpg"></a>
			</div>
			<div class="left2Home">
				<a href="/compras"><img class="imgHome" src="..${s}/img/Tienda.jpg"></a>
			</div>
		</div>
		<div class="rightHome">
			<a href="/reglas"><img class="imgHome" src="${s}/img/Reglas.jpg"></a>
		</div>
	</div>
	<div class="botHome">
		<a href="/foro"><img class="imgHome" src="${s}/img/Foro.jpg"></a>
	</div>
</div>

<%@ include file="../jspf/footer.jspf"%>
