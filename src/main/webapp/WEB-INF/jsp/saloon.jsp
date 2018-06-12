<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ include file="../jspf/header.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<c:if test="${not empty mensaje }">
		<div class="alert alert-info" role="alert">
			<strong>${mensaje} ${classMensaje}</strong>
		</div>
		<br>
	</c:if>


	<c:if test="${empty user.partida}">

		<script>
			window.onload = function() {

				var socket = new WebSocket("${endpoint}");
				$("#escrito").submit(function(e) {
					var t = $("#texto").val();
					socket.send(t);
					$("#texto").val("");
					e.preventDefault();
				});
				socket.onmessage = function(e) {
					var ta = $("#recibido");
					ta.val(ta.val() + '\n' + e.data);
				}
			}
		</script>

		<div class="row">
			<div class="col-md-3">

				<form action="/user/verSaloon" method="get" id="verSaloon"
					class="
					form-horizontal" role="form">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<div style="text-align: center;">
						<h2>Buscar partidas</h2>
						<hr>
					</div>
					<div class="row">
						<div class="col-md-4 field-label-responsive">
							<label for="nombrePar">Nombre</label>
						</div>
						<div class="col-md-8">
							<div class="form-group">
								<div class="input-group mb-2 mr-sm-2 mb-sm-0">
									<div class="input-group-addon" style="width: 2.6rem">
										<i class="fa fa-user"></i>
									</div>
									<input type="text" name="nombrePar" class="form-control"
										id="name" placeholder="Nombre partida" required autofocus>
								</div>
							</div>
						</div>

					</div>


					<div class="row">
						<div class="col-md-6"></div>
						<div class="col-md-6">
							<input type="hidden" name="juego" value="Amigos" />
							<button type="submit" class="btn btn-success" form="verSaloon"
								value="Submit">
								<i class="fa fa-user-plus"></i> Buscar
							</button>
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-1"></div>
			<div class="col-md-4">
				<form action="/user/crearPartida" class="form-horizontal"
					role="form" method="post" id="crearPartida"
					oninput="range1value.value = maxJugadores.valueAsNumber">
					<div style="text-align: center;">
						<h2>Nueva partida</h2>
						<hr>
					</div>
					<div class="row">
						<div class="col-md-6 field-label-responsive">
							<label for="nombrePar">Nombre</label>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<div class="input-group mb-2 mr-sm-2 mb-sm-0">
									<div class="input-group-addon" style="width: 2.6rem">
										<i class="fa fa-user"></i>
									</div>
									<input type="text" name="nombrePar" class="form-control"
										id="name" placeholder="Nombre partida" required autofocus>
								</div>
							</div>
						</div>

					</div>

					<div class="row">
						<div class="col-md-6 field-label-responsive">
							<label for="cont">Password</label>
						</div>
						<div class="col-md-6">
							<div class="form-group has-danger">
								<div class="input-group mb-2 mr-sm-2 mb-sm-0">
									<div class="input-group-addon" style="width: 2.6rem">
										<i class="fa fa-key"></i>
									</div>
									<input type="password" name="cont" class="form-control"
										id="password" placeholder="Vacío = abierta">
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 field-label-responsive">
							<label for="maxJugadores">Jugadores</label>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<div class="input-group mb-2 mr-sm-2 mb-sm-0">
									<div class="input-group-addon" style="width: 2.6rem">
										<i class="fa fa-user"></i>
									</div>
									<input type="text" name="maxJugadores" class="form-control"
										placeholder="(2-7)" required autofocus>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6"></div>
						<div class="col-md-6">
							<input type="hidden" name="juego" value="BlackJack" /> <input
								type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<button type="submit" class="btn btn-success" form="crearPartida"
								value="Submit">
								<i class="fa fa-user-plus"></i> Register
							</button>
						</div>
					</div>
				</form>
			</div>
			<div class="fixedChat">
				<textarea id="recibido" cols="40" rows="10"
					style="background-color: rgba(255, 255, 255, 0.7);" disabled>
				</textarea>
				<form id="escrito">
					<input id="texto" class="form-control"
						placeholder="escribe algo y pulsa enter para enviarlo" />
				</form>
			</div>
		</div>
	</c:if>

	<c:if test="${not empty saloon and empty user.partida}">
		<c:forEach items="${saloon}" var="p">
			Nombre:&nbsp&nbsp<td>${p.nombre}</td>
			<br>Juego:&nbsp&nbsp<td>${p.juego}</td>
			<br>
			<td>${fn:length(p.jugadores)}/${p.maxJugadores}</td>
		
			Jugadores
			<c:forEach items="${p.jugadores}" var="j">
				<br>
				<td>${j.login}</td>
			</c:forEach>

			<c:choose>
				<c:when test="${p.abierta == true}">
		        Abierta 
		        <form action="/user/unirsePartida" method="post"
						id="unirsePartida">
						<input hidden="submit" name="id_p" value="${p.id}" />
						<c:choose>
							<c:when test="${p.pass == 'no'}">
					        Sin contraseña 
					    </c:when>
							<c:otherwise>
					        Con Contraseña	        
					        <label for="pass">Contraseña <input type="password"
									name="pass" />
								</label>
							</c:otherwise>
						</c:choose>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

						<button type="submit" class="btn" form="unirsePartida"
							value="Submit">Unirse</button>
					</form>
				</c:when>
				<c:otherwise>
		        Cerrada 
		    </c:otherwise>
			</c:choose>

		</c:forEach>
	</c:if>

	<c:if test="${not empty user.partida}">

		<script>
			window.onload = function() {
				setInterval(function() {
					document.forms["formEmpezar"].submit();
				}, 5000);
			}
		</script>

		<div>
			<form action="/user/empezarPartida" id=formEmpezar method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>

		<div>
			<form action="/user/salirDeLaPartida" method="post"
				id="salirDeLaPartida">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<button type="submit" class="btn" form="salirDeLaPartida"
					value="Submit">Salir de la partida</button>
			</form>
		</div>
	</c:if>


</div>
<%@ include file="../jspf/footer.jspf"%>
