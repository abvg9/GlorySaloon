<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ include file="../jspf/header.jspf"%>

<div class="starter-template">
	
	
    <c:if test="${not empty mensaje}">
    	<strong>${mensaje}</strong>
	</c:if>
	
	<p class="lead">Bienvenido a tu perfil</p>
	<h1>${user.login} </h1>
	<img src="${imagen}${user.id}" />
	
	<strong>Dinero</strong>
	<h1>${user.dinero}</h1>
	
	<strong>Email</strong>
	<h1>${user.email}</h1>
	
	<strong>Pais</strong>
	<h1>${user.nacion}</h1>
	
	<strong>Partidas ganadas</strong>
	<h1>${user.pganadas}</h1>
	
	<strong>Partidas perdidas</strong>
	<h1>${user.pperdidas}</h1>
	
	<strong>Partidas jugadas</strong>
	<h1>${user.pjugadas}</h1>
	
	<strong>Dinero perdido</strong>
	<h1>${user.dperdido}</h1>	
	
	<strong>Dinero ganado</strong>
	<h1>${user.dganado}</h1>
		
	<c:if test="${not empty user.amigos}">
	<strong>Amigos </strong>
	<c:forEach items="${user.amigos}" var="u">
		<tr>
			<td>${u.login}</td>
			<c:if test="${not empty u.partida}">
				Esta jugando en ${u.partida.nombre}
			</c:if>		
		</tr>
	</c:forEach>
	</c:if>
	
	<c:if test="${not empty user.propiedades}">
	<strong>Items ${user.login}</strong>
	<c:forEach items="${user.propiedades}" var="p">
		<tr>
			<td>${p.nombre}</td>
		</tr>
	</c:forEach>
	</c:if>

	<hr />
	<strong>Modificar perfil</strong>
	<form action="/user/modificarPerfil" method="post"
		enctype="multipart/form-data">
		<label for="nombre">Nombre<input name="nombre" /></label> <label
			for="cont">Nueva contrasena<input type="password"
			name="contNueva" /></label> <label for="cont">Contrasena actual<input
			type="password" name="contActual" /></label> <label for="email">Nuevo
			email<input name="email" />
		</label> <label for="nacion">Nueva nacionalidad <select name="nacion">
				<option value="Espana">Espana</option>
				<option value="Francia">Francia</option>
				<option value="Italia">Italia</option>
				<option value="Marruecos">Marruecos</option>
				<option value="Portugal">Portugal</option>
		</select></label> <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Modificar perfil</button>
		</div>
		
	</form>

	<form method="POST" enctype="multipart/form-data"
		action="${imagen}${user.id}">
		Nueva foto de perfil<input type="file" name="photo"><br />
		<input hidden="submit" name="id" value="${user.id}" /> 
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="form-actions">
			<button type="submit" name="upload" value="ok">Subir foto</button>
		</div>
	</form>

	<strong>Introduce el nombre del amigo que deseas anadir.</strong>
	<form action="/user/anadirAmigo" method="post">
		<label for="nombre">Nombre del amigo<input name="nombreA" /></label> <input
			type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Anadir amigos</button>
		</div>
	</form>
	<hr />

	<hr />
	<strong>Introduce el nombre del amigo al que deseas ver el perfil.</strong>
	<form action="/user/pefilAmigo" method="get">
		<label for="nombre">Nombre del amigo<input name="nombre" /></label> <input
		type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Ver perfil</button>
		</div>
	</form>
	<hr />

	<hr />
	<strong>Eliminar cuenta</strong>
	<form action="/user/eliminarCuenta" method="post">
		<c:if test="${fn:contains(user.roles, 'ADMIN')}">
	    	<label for="nombre">Nombre del usuario<input name="nombre" /></label>
	    </c:if>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Eliminar cuenta</button>
		</div>
	</form>
	<hr />

	<hr />
	<strong>Eliminar amigo</strong>
	<form action="/user/eliminarAmigo" method="post">
		<label for="nombre">Nombre del amigo<input name="nombreAmigo" /></label>
		<input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Eliminar amigo</button>
		</div>
	</form>
	<hr />

	<hr />
	<strong>Eliminar item de tu inventario</strong>
	<form action="/user/borrarItem" method="post">
		<label for="id">Identificador del item<input name="id" /></label>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<div class="form-actions">
			<button type="submit" class="btn">Eliminar item</button>
		</div>
	</form>
	<hr />

	<c:if test="${fn:contains(user.roles, 'ADMIN')}">
		<hr/>
	<strong>Crear cuenta</strong>
	<form action="/admin/crearCuenta" method="post">
		<label for="nombre">Nombre<input name="nombre"/></label>
		<label for="cont">Contrasena<input type="password" name="cont"/></label>
		<label for="email">email<input name="email"/></label>
		<label for="isAdmin">is admin?<input type="checkbox" name="isAdmin"></label>
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
	</form>
	<hr/>
	</c:if>

	<hr />
	<h1>Logout</h1>
	<p class="lead">¿Estás seguro que deseas salir?</p>

	<form action="/user/logout" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<button type="submit">Salir</button>
	</form>
	<hr />

</div>

<%@ include file="../jspf/footer.jspf"%>