<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ include file="../jspf/header.jspf"%>

<style>
.tarjeta {
	opacity: 1;
	display: block;
	width: 100%;
	height: auto;
	transition: .5s ease;
	backface-visibility: hidden;
}

.middle {
	transition: .5s ease;
	opacity: 0;
	position: absolute;
	top: 30%;
	left: 50%;
	transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	text-align: center;
}

.tarjeta:hover .desvanecer {
	opacity: 0.3;
}

.tarjeta:hover .middle {
	opacity: 1;
}

.text {
	background-color: #4CAF50;
	color: white;
	font-size: 16px;
	padding: 16px 32px;
}
</style>


<div class="container">
	<div id="demo" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ul class="carousel-indicators">
			<li data-target="#demo" data-slide-to="0" class="active"></li>
			<li data-target="#demo" data-slide-to="1"></li>
			<li data-target="#demo" data-slide-to="2"></li>
		</ul>
		<!-- The slideshow -->
		<div class="carousel-inner outerView">
			<div class="carousel-item active">
				<img src="${s}/img/imgRotar1.jpg" alt="Los Angeles" width="1100"
					height="400">
			</div>
			<div class="carousel-item">
				<img src="${s}/img/imgRotar2.jpg" alt="Chicago" width="1100"
					height="400">
			</div>
			<div class="carousel-item">
				<img src="${s}/img/imgRotar3.jpg" alt="New York" width="1100"
					height="400">
			</div>
		</div>
		<!-- Left and right controls -->
		<a class="carousel-control-prev" href="#demo" data-slide="prev"> <span
			class="carousel-control-prev-icon"></span>
		</a> <a class="carousel-control-next" href="#demo" data-slide="next">
			<span class="carousel-control-next-icon"></span>
		</a>
	</div>

	<br> <br> <br>
	<div class="row">
		<div class="col-4">
			<div style="cursor: pointer;" onclick="window.location='/perfil';"
				class="card tarjeta desvanecer">
				<div class="litleMargin desvanecer">
					<img src="${s}/img/perfil.png" alt="Perfil" width="100%"
						height="200px" class="desvanecer"><br>
					<div class="card-block desvanecer" style="text-align: center;">
						<h4>Perfil</h4>
						<br>
						<p>Aquí podrás ver tus datos, así como tu inventario y amigos.
							Añade a tus mejores amigos y ¡mira sus perfiles!</p>
						<br>

					</div>
				</div>
				<div class="middle">
					<div class="text">Entrar</div>
				</div>
			</div>
		</div>
		<div class="col-4">
			<div style="cursor: pointer;" onclick="window.location='/saloon';"
				class="card tarjeta desvanecer">
				<div class="litleMargin">
					<img src="${s}/img/saloon.png" alt="Saloon" width="100%"
						height="200px" class="desvanecer"><br>
					<div class="card-block desvanecer" style="text-align: center;">
						<h4>Saloon</h4>
						<br>
						<p>¿Quieres echar una partida de blackjack? Pásate por nuestro
							Saloon y diviértete con tus amigos</p>
						<br> <br>
					</div>
				</div>
				<div class="middle">
					<div class="text">Entrar</div>
				</div>
			</div>
		</div>
		<div class="col-4">
			<div style="cursor: pointer;" onclick="window.location='/tienda';"
				class="card tarjeta desvanecer">
				<div class="litleMargin">
					<img src="${s}/img/store.jpg" alt="Tienda" width="100%"
						height="200px" class="desvanecer"><br>
					<div class="card-block desvanecer" style="text-align: center;">
						<h4>Tienda</h4>
						<br>
						<p>Compra las provisiones y los suministros necesarios para el
							largo camino que tienes que recorrer. Pero primero ¡házte con
							algunas monedas!</p>
						<br>
					</div>
				</div>
				<div class="middle">
					<div class="text">Entrar</div>
				</div>
			</div>
		</div>
	</div>
	<br> <br>
	<div class="row">
		<div class="col-2"></div>
		<div class="col-8">
			<div style="cursor: pointer;" id=rank 
				class="card tarjeta desvanecer">
				<div style="margin: 2%;">
					<img src="${s}/img/top10.jpg" alt="Ranking" width="100%"
						height="160px" class="desvanecer"><br>
					<div class="card-block desvanecer" style="text-align: center;">
						<h3>Ranking</h3>
						<br>
						<p>No tengas miedo, y ¡mídete con el resto del mundo! Podrás
							además compararte con tus amigos y ver quién está en la cabecera</p>
						<br>
					</div>
				</div>
				<div class="middle">
					<div class="text">Entrar</div>
				</div>
			</div>
		</div>
	</div>
</div>

<form action="/user/verRanking" method="get" id=formRanking>
	<input type="hidden" name="${_csrf.parameterName}"
	value="${_csrf.token}" />
	<input type="hidden" name="busqueda"
	value="global" />
</form>

<script>
	$("#rank").on('click', function() {
		debugger;
		$("#formRanking").submit();
	});
</script>

<%@ include file="../jspf/footer.jspf"%>