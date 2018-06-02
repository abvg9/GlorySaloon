<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ include file="../jspf/header.jspf"%>

<div class="starter-template">
	<h1>Crear cuenta</h1>
	<p class="lead">Rellena todos los campos</p>

	<hr/>

	<form action="/user/crearCuenta" method="post">
		<label for="nombre">Nombre<input name="nombre"/></label>
		<label for="cont">Contrasena<input type="password" name="cont"/></label>
		<label for="email">email<input name="email"/></label>
		<label for="nacion" >nacion
		<select name="nacion">
  			<option value="Espana" >Espana </option>
  			<option value="Francia">Francia</option>
  			<option value="Italia">Italia</option>
  			<option value="Marruecos">Marruecos</option>
  			<option value="Portugal">Portugal</option>
		</select>
		</label>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            
            <div class="form-actions">
                <button type="submit" class="btn">Crear usuario</button>
            </div>
            
            <c:if test="${not empty mensaje}">
            	<strong>${mensaje}</strong>
    		</c:if>
    		
	</form>
	<hr/>

	<!--<%@ include file="../jspf/authinfo.jspf"%>	-->	
</div>

<%@ include file="../jspf/footer.jspf"%>
