<%@ include file="../jspf/header.jspf"%>
<div class="container">

	<c:if test="${not empty mensaje }">
		<div class="alert alert-info" role="alert">
			<strong>${mensaje} ${classMensaje}</strong>
		</div>
		<br>
	</c:if>
	<ul id="myOwnColor" class="nav nav-pills nav-fill">
		<li class="nav-item"><a class="nav-link active nav-link-color"
			data-toggle="tab" href="#blackjack" style="font-size: 25px;">BlackJack</a></li>
	</ul>

	<div class="tab-content">
		<br> <br> <br> <br> <br>

		<div id="blackjack" class="tab-pane active readable">

			<p>Del juego de BlackJack existen dos variantes, la americana y
				la europea. La mesa, el objetivo del juego y las apuestas de ambas
				modalidades son pr&aacute;cticamente las mismas. En esta gu&iacute;a
				se utiliza el BlackJack Europeo para explicar c&oacute;mo jugar al
				BlackJack, introducciendo los aspectos imprescindibles para empezar
				a jugar, se ha elegido la modalidad europea porque es a la que se
				juega en los casinos de Europa y en especial en los casinos de
				Espa&ntilde;a.</p>
			<h2>Aspecto de la mesa del juego</h2>
			<p>Una mesa de Blackjack tiene forma semicircular. En la parte
				circular hay espacio hasta para 7 jugadores, enfrente a los
				jugadores se coloca el crupier. La siguiente imagen muestra el
				aspecto de una mesa de BlackJack.</p>

			<h2>Objetivo del juego</h2>
			<p>En el BlackJack cada jugador juega contra la banca. El
				objetivo del juego es conseguir sumar 21 puntos o al menos conseguir
				sin pasarse un valor m&aacute;s cercano a 21 que el crupier. Los
				valores de las cartas en el BlackJack son los siguientes: las cartas
				del 2 al 10 valen su valor, las figuras valen 10 y el AS vale 1 u 11
				dependiendo de lo que le convenga al jugador.</p>
			<p>La jugada m&aacute;xima del juego es &ldquo;BlackJack&rdquo;,
				un jugador tendr&aacute; &ldquo;BlackJack&rdquo; cuando sus dos
				cartas iniciales sumen 21, es decir, sean un AS y una carta con
				valor 10 (10 o cualquier figura). La suma de 21 con m&aacute;s de
				dos cartas no es &ldquo;BlackJack&rdquo;.</p>
			<h2>C&oacute;mo se juega al BlackJack y las apuestas del juego</h2>
			<p>Al inicio de la partida cada jugador deber&aacute; realizar su
				apuesta inicial, despu&eacute;s de que todos los jugadores hayan
				realizados sus apuestas el crupier dir&aacute; &ldquo;no va
				m&aacute;s&rdquo; para cerrar la ronda de apuestas y empezar con el
				reparto de cartas. El crupier repartir&aacute; dos cartas
				descubiertas a cada jugador y una a s&iacute; mismo, tambi&eacute;n
				descubierta. Despu&eacute;s del reparto de cartas el crupier le
				dar&aacute; paso al jugador situado m&aacute;s a su izquierda para
				que juegue su mano, despu&eacute;s de que este jugador juegue su
				mano, el crupier le dar&aacute; paso al siguiente jugador y
				as&iacute; sucesivamente hasta que finalmente sea el crupier quien
				juegue su mano.</p>
			<p>Los jugadores que se queden m&aacute;s lejos de 21 que el
				crupier o se hayan pasado pierden sus apuestas. Los jugadores que
				empaten con el crupier no ganan ni pierden, recuperan su apuesta.
				Las apuestas ganadoras con la jugada BlackJack se pagan 3 x 2 (que
				es lo mismo que decir 1,5 x 1), el resto de apuestas ganadoras,
				jugadores que se hayan quedado m&aacute;s cerca de 21 que el
				crupier, se pagaran 1 x 1.</p>
			<p>Cada jugador solo dispone de un turno para jugar su mano, en
				su turno podr&aacute;:</p>
			<ul>
				<li><p>
						<strong>Pedir Carta</strong>: un jugador podr&aacute; las cartas
						que deseen mientras su mano este por debajo de los 21 puntos. Si
						al pedir carta el jugador se pasa de 21, el crupier el
						retirar&aacute; las cartas y las recoger&aacute; sus apuestas.
					</p></li>
				<li><p>
						<strong>Plantarse</strong>: un jugador se puede plantar en
						cualquier momento.
					</p></li>
				<li><p>
						<strong>Doblar</strong>: un jugador puede doblar su apuesta. Solo
						se puede doblar la apuesta al principio del turno, despu&eacute;s
						de recibir las cartas iniciales. Para doblar la apuesta se debe
						colocar otra apuesta del mismo valor que la apuesta inicial.
						Cuando un jugador dobla su apuesta solo recibir&aacute; una carta
						m&aacute;s. En algunos casinos se puede doblar con cualquier
						puntuaci&oacute;n, sin embargo, hay casinos que solo dejan doblar
						cuando la mano inicial suma 9, 10 u 11.
					</p></li>
				<li><p>
						<strong>Separar</strong>: cuando las dos cartas iniciales tiene el
						mismo valor (un 10 y una figura se pueden separar, son cartas con
						el mismo valor) se podr&aacute; separar la mano en dos manos
						independientes. Para ello, el jugador deber&aacute; hacer una
						apuesta igual a la inicial, de esta forma cada mano tendr&aacute;
						su apuesta. El jugador jugar&aacute; cada mano de manera
						independiente. Si despu&eacute;s de separar una mano se consigue
						la puntuaci&oacute;n de 21 con dos cartas no se considera
						Blackjack, en las manos separadas nunca se podr&aacute; obtener
						BlackJack.
					</p></li>
			</ul>
			<p>Cuando se separan dos Ases solo se repartir&aacute; una carta
				a cada mano separada.</p>
			<p>
				<strong>Apuestas de Seguro &ndash; Carta descubierta del
					crupier es un AS</strong>
			</p>
			<p>Si la carta descubierta de crupier es un AS, el crupier
				inmediatamente despu&eacute;s del reparto de cartas, antes de dar
				paso a que los jugadores jueguen su mano, ofrecer&aacute; a los
				jugadores la posibilidad de realizar las apuestas de seguro. Con la
				apuesta de seguro un jugador se asegura ante un posible BlackJack
				del crupier, para ello se deber&aacute; realizar la apuesta de
				seguro colocando como m&aacute;ximo la mitad de la apuesta inicial
				en la l&iacute;nea de seguro de la mesa de BlackJack. Si el crupier
				consigue BlackJack pagara a cada jugador que realizo la apuesta de
				seguro el doble (el pago de la apuesta de seguro es a raz&oacute;n
				de 2 x 1) de lo que aposto en la l&iacute;nea de seguro. Si el
				crupier no consigue BlackJack recoge todas las apuestas de seguro.</p>
			<p>En el BlackJack Americano, el crupier se reparte inicialmente
				dos cartas, una descubierta y otra tapada, esto afecta a como se
				ejecuta la partida si la carta descubierta del crupier es un AS, un
				10 o un valor distinto a 10 o a un AS. Los detalles sobre
				c&oacute;mo se juega al BlackJack americano se explica en el
				art&iacute;culo correspondiente.</p>

		</div>

	</div>
</div>
<%@ include file="../jspf/footer.jspf"%>
