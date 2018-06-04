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
	<div>
		<form action="/user/modificarPerfil" method="post"
			enctype="multipart/form-data" id = "modificarPerfil">
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
			</select></label> 
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn" form="modificarPerfil" value="Submit">Modificar perfil</button>
			
		</form>
	</div>

	<div>
		<form method="post" enctype="multipart/form-data" action="${imagen}${user.id}" id = "SubeFoto">
			Nueva foto de perfil<input type="file" name="photo"><br />
			<input hidden="submit" name="id" value="${user.id}" /> 
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" name="upload" value="ok" form="SubeFoto">Subir foto</button>
		</form>
	</div>

	<div>
		<strong>Introduce el nombre del amigo que deseas anadir.</strong>
		<form action="/user/anadirAmigo" method="post" id = "anadirAmigo" >
			<label for="nombre">Nombre del amigo<input name="nombreA" /></label> 
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn" form="anadirAmigo" value="Submit">Anadir amigos</button>
		</form>
	</div>
	<hr />

	<hr />
	<div>
		<strong>Introduce el nombre del amigo al que deseas ver el perfil.</strong>
		<form action="/user/perfilAmigo" method="get" id = "perfilAmigo">
			<label for="nombre">Nombre del amigo<input name="nombre" /></label> 
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn" form="perfilAmigo" value="Submit">Ver perfil</button>
		</form>
	</div>
	<hr />

	<hr />
	<div>
		<strong>Eliminar cuenta</strong>
		<form action="/user/eliminarCuenta" method="post">
			<c:if test="${fn:contains(user.roles, 'ADMIN')}">
		    	<label for="nombre">Nombre del usuario<input name="nombre" /></label>
		    </c:if>
		    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<button type="submit" class="btn">Eliminar cuenta</button>
		</form>
	</div>
	<hr />

	<hr />
	<div>
		<strong>Eliminar amigo</strong>
		<form action="/user/eliminarAmigo" method="post">
			<label for="nombre">Nombre del amigo<input name="nombreAmigo" /></label>
			<input type="hidden" name="${_csrf.parameterName}"value="${_csrf.token}" />
			<button type="submit" class="btn">Eliminar amigo</button>
		</form>
	</div>
	<hr />

	<hr />
	<div>
		<strong>Eliminar item de tu inventario</strong>
		<form action="/user/borrarItem" method="post">
			<label for="id">Identificador del item<input name="id" /></label>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn">Eliminar item</button>
		</form>
	</div>
	<hr />

	<c:if test="${fn:contains(user.roles, 'ADMIN')}">
		<div>
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
		            
		        <button type="submit" class="btn">Crear usuario</button>		
			</form>
		</div>
	</c:if>

	<hr />
	<h1>Logout</h1>
	<p class="lead">¿Estás seguro que deseas salir?</p>

	<div>
		<form action="/user/logout" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit">Salir</button>
		</form>
	</div>
	<hr />

</div>

<%@ include file="../jspf/footer.jspf"%>