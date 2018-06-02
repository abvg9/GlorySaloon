<%@ include file="../jspf/header.jspf"%>
<div class="container">
	<ul class="nav nav-tabs nav-justified">
		<li class="active"><a data-toggle="tab" href="#poker">Poker</a></li>
		<li><a data-toggle="tab" href="#blackjack">BlackJack</a></li>
		<li><a data-toggle="tab" href="#mus">Mus</a></li>
	</ul>

	<div class="tab-content">
		<div id="poker" class="tab-pane fade in active">
			<h1>The Rules of Poker</h1>
			<p>Poker is a game of chance. However, when you introduce the
				concept of betting, poker gains quite a bit of skill and psychology.
				(This isn't to say that there isn't skill at poker when nothing is
				at risk, there just isn't nearly as much). This is meant as a very
				basic primer into the rules of poker, for more information, get a
				book on the game (or start playing with a group of people who know
				how. It's more expensive than reading a book, but the group won't
				mind. *Snicker*).</p>
			<p>This list is currently broken into several parts:</p>
			<ol>
				<li><p>
						<em><a href="#verybasics">The Very Basics</a></em>
					</p></li>
				<li><p>
						<em><a href="#howranks">How the Hands are Ranked</a></em>
					</p></li>
				<li><p>
						<em><a href="#handranks">Descriptions of Hand Ranks</a></em>
					</p></li>
				<li><p>
						<em><a href="#betting">Betting</a></em>
					</p></li>
				<li><p>
						<em><a href="#5card">An Example 5-Card Draw Hand</a></em>
					</p></li>
			</ol>

			<h2 id="verybasics">The Very Basics</h2>

			<p>Poker is played from a standard pack of 52 cards. (Some
				variant games use multiple packs or add a few cards called jokers.)
				The cards are ranked (from high to low) Ace, King, Queen, Jack, 10,
				9, 8, 7, 6, 5, 4, 3, 2, Ace. (Ace can be high or low, but is usually
				high). There are four suits (spades, hearts, diamonds and clubs);
				however, no suit is higher than another. All poker hands contain
				five cards, the highest hand wins.</p>
			<p>Some games have Wild Cards, which can take on whatever suit
				and rank their possessor desires. Sometimes jokers will be used as
				wild cards, other times, the game will specify which cards are wild
				(dueces, one-eyed jacks, or whatever).</p>

			<h2 id="howranks">How the hands are ranked</h2>

			<p>Hands are ranked as follows (from high to low):</p>
			<ul>
				<li>Straight Flush</li>
				<li>Four of a Kind</li>
				<li>Full House</li>
				<li>Flush</li>
				<li>Straight</li>
				<li>Three of a Kind</li>
				<li>Two Pair</li>
				<li>Pair</li>
				<li>High Card</li>
			</ul>

			<h2 id="handranks">Descriptions of Hand Ranks</h2>

			<br>
			<h3>Straight Flush</h3>
			<p>A straight flush is the best natural hand. A straight flush is
				a straight (5 cards in order, such as 5-6-7-8-9) that are all of the
				same suit. As in a regular straight, you can have an ace either high
				(A-K-Q-J-T) or low (5-4-3-2-1). However, a straight may not
				'wraparound'. (Such as K-A-2-3-4, which is not a straight). An Ace
				high straight-flush is called a Royal Flush and is the highest
				natural hand.</p>

			<h3>Four of a Kind</h3>

			<p>Four of a kind is simply four cards of the same rank. If there
				are two or more hands that qualify, the hand with the higher-rank
				four of a kind wins. If, in some bizarre game with many wild cards,
				there are two four of a kinds with the same rank, then the one with
				the high card outside the four of the kind wins. General Rule: When
				hands tie on the rank of a pair, three of a kind, etc, the cards
				outside break ties following the High Card rules.</p>
			<h3>Full House</h3>

			<p>A full house is a three of a kind and a pair, such as
				K-K-K-5-5. Ties are broken first by the three of a kind, then pair.
				So K-K-K-2-2 beats Q-Q-Q-A-A, which beats Q-Q-Q-J-J. (Obviously, the
				three of a kind can only be similiar if wild cards are used.)</p>
			<h3>Flush</h3>

			<p>A flush is a hand where all of the cards are the same suit,
				such as J-8-5-3-2, all of spades. When flushes ties, follow the
				rules for High Card.</p>
			<h3>Straight</h3>
			<p>A straight is 5 cards in order, such as 4-5-6-7-8. An ace may
				either be high (A-K-Q-J-T) or low (5-4-3-2-1). However, a straight
				may not 'wraparound'. (Such as Q-K-A-2-3, which is not a straight).
				When straights tie, the highest straight wins. (AKQJT beats KQJT9
				down to 5432A). If two straights have the same value (AKQJT vs
				AKQJT) they split the pot.
			<p>
			<h3>Three of a Kind</h3>
			<p>Three cards of any rank, matched with two cards that are not a
				pair (otherwise it would be a Full House . Again, highest three of a
				kind wins. If both are the same rank, then the compare High Cards.</p>
			<h3>Two Pair</h3>
			<p>This is two distinct pairs of card and a 5th card. The highest
				pair wins ties. If both hands have the same high pair, the second
				pair wins. If both hands have the same pairs, the high card wins.</p>
			<h3>Pair</h3>
			<p>One pair with three distinct cards. High card breaks ties.</p>
			<h3>High Card</h3>
			<p>This is any hand which doesn't qualify as any one of the above
				hands. If nobody has a pair or better, then the highest card wins.
				If multiple people tie for the highest card, they look at the second
				highest, then the third highest etc. High card is also used to break
				ties when the high hands both have the same type of hand (pair,
				flush, straight, etc).</p>
			<h2 id="betting">Betting</h2>
			<p>So, how do you bet? Poker is, after all, a gambling game. In
				most games, you must 'ante' something (amount varies by game, our
				games are typically a nickel), just to get dealt cards. After that
				players bet into the pot in the middle. At the end of the hand, the
				highest hand (that hasn't folded) wins the pot. Basically, when
				betting gets around to you (betting is typically done in clockwise
				order), you have one of three choices:</p>
			<ul>
				<li><h3>Call</h3>
					<p>When you call, you bet enough to match what has been bet
						since the last time you bet (for instance, if you bet a dime last
						time, and someone else bet a quarter, you would owe fifteen
						cents).</p></li>
				<li><h3>Raise</h3>
					<p>When you raise, you first bet enough to match what has been
						bet since the last time you bet (as in calling), then you 'raise'
						the bet another amount (up to you, but there is typically a
						limit.) Continuing the above example, if you had bet a dime, the
						other person raised you fifteen cents (up to a quarter), you might
						raise a quarter (up to fifty cents). Since you owed the pot 15
						cents for calling and 25 for your raise, you would put 40 cents
						into the pot.</p></li>
				<li>
					<h3>Fold</h3>
					<p>When you fold, you drop out of the current hand (losing any
						possibility of winning the pot), but you don't have to put any
						money into the pot. Betting continues until everyone calls or
						folds after a raise or initial bet.</p>
				</li>
			</ul>
			<p>
				<b> Some Standard Betting Rules</b><br> In the group I play in,
				we ante a nickel. The maximum first bet is fifty cents, and the
				maximum raise is fifty cents. However, during one round of betting,
				raises may total no more than one dollar.
			</p>

			<h2 id="5card">An Example Five Card Draw Hand.</h2>
			<p>
				Five card draw is one of the most common types of poker hands. Each
				player is dealt five cards, then a round of betting follows. Then
				each player may discard up to 3 cards (4 if your last card is an ace
				or wild card, in some circles) and get back (from the deck) as many
				cards as he/she discarded. Then there is another round of betting,
				and then hands are revealed (the showdown) and the highest hand wins
				the pot. So you are the dealer at a five card draw game (against
				four other players, Alex, Brad, Charley and Dennis (seated in that
				order to your left). Everyone puts a nickel into the pot (Ante) and
				you deal out 5 cards to each player.<br> You deal yourself a
				fairly good hand Ks-Kd-Jd-5c-3d. A pair of kings isn't bad off the
				deal (not great, but not bad). Then the betting starts...
			</p>
			<p>
				Alex 'Checks' (checking is basically calling when you don't owe
				anything to the pot).<br> Brad bets a dime.<br> Charley
				calls (and puts a dime into the pot).<br> Dennis raises a dime
				(and puts twenty cents into the pot).<br> Well, it's your turn.
				Twenty cents to you. You can fold, call or raise. Like I said
				before, pair of kings isn't bad, not good but not bad. You call and
				put twenty cents into the pot.<br> Back to Alex, who grumbles
				and tosses his cards into the center of the table, folding. (Note,
				when folding, never show your cards to anyone).<br> Brad calls.
				The total bet is twenty cents, but he had already bet a dime, so he
				owes a dime, which he tosses into the pot.<br> Charley is in
				the same position as brad, and tosses a dime into the pot.<br>
				The round of betting is over. After Dennis's raise, everyone else
				folded or called (there weren't any raises) so, everyone is all
				square with the pot.<br> Now everyone can discard up to 3
				cards. Brad discards 3 cards, Charley discards one card, Dennis
				discards two cards. (You deal replacements to everyone) and now it's
				your turn. You have a pair of kings, three spades, and no chance for
				a straight. It's best to just keep the two kings and hope to get a
				3rd or fourth king. You discard three cards, and your new hand is:
				Ks-Kd-Kc-4c-8h. Three Kings! A nice little hand.<br> What do
				you suppose the others were trying for? Well, Brad kept two cards,
				so he probably had a pair (just like you) but it probably wasn't
				aces, so even if brad got a three of a kind, you probably beat him.
				Charley kept four cards, so he was probably trying for a straight or
				flush. (If Charley had four of a kind, he might have bet much
				harder). The big problem is Dennis. He raised earlier, and only drew
				two cards. He might be bluffing, but he could have had three of a
				kind off the deal... In any case, the second round of betting starts
				(with dealers left).<br>
			</p>
			<p>
				Brad bets a nickel.<br> Charley folds (I guess he didn't get
				his straight or flush).<br> Dennis raises twenty cents (to a
				quarter total).<br> You call.<br> Brad looks at his cards,
				then calls (betting twenty cents).<br> Again, everyone called
				Dennis's raise, so the round of betting is over.<br> Well, the
				betting is over, everyone reveals his hand:<br>
			</p>
			<ul>
				<li>You had Ks-Kd-Kc-4c-8h.</li>
				<li>Brad had Jh-Jd-3c-3s-Ah.</li>
				<li>Dennis had Qh-Qs-Qd-As-7s.</li>

			</ul>
			<p>Well, the highest hand is three of a kind, and the highest
				three of a kind is your three kings. You win!</p>


		</div>




		<div id="blackjack" class="tab-pane fade">
			<h1>BlackJack</h1>
			<hr />
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

		<div id="mus" class="tab-pane fade">
			<h1>Mus</h1>
			<p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem
				accusantium doloremque laudantium, totam rem aperiam.</p>






			<h3>Origen e historia</h3>

			<p>El mus es un juego de naipes de baraja espanola sobre cuyo
				origen vasco no hay grandes discusiones entre autores. Existe ya una
				primera referencia documental que se remonta al ano 1745 en el
				diccionario trilingüe (Euskera-Castelleno-Latín), donde lo cita el
				filólogo vasco y jesuita Don Manuel de Larramendi.</p>

			Descripción

			<p>El mus suele disputarse entre 4 jugadores que compiten entre
				sí por parejas dispuestas en torno a una mesa de juego, situándose
				en el orden de juego alternativamente jugadores de distinta pareja.
				También es posible jugar de manera individual, o formando equipos de
				3 jugadores, si bien estas modalidades son poco frecuentes.</p>
			<p>Se trata de un juego de duración media-larga teóricamente, y
				en cualquier caso es un juego que nunca puede terminar en una sola
				mano o reparto de cartas, sino cuya consecución vendrá dada por la
				suma de puntos como se detalla en el apartado correspondiente. Es
				costumbre, según regiones, y previo acuerdo de los contrincantes,
				indicarse entre los jugadores de una misma pareja las cartas que
				tiene cada uno mediante gestos faciales: jugar con senas.</p>

			<p>Si se juega con senas, están sólo y estrictamente permitidas
				aquellas reconocidas por todos los jugadores, de modo que los
				jugadores contrarios tengan posibilidad de entenderlas si es que las
				vieran. En el mus a 8 reyes existen dos peculiaridades respecto a la
				composición de la baraja: 3 como rey A todos los efectos los cuatro
				treses de la baraja se tendrán por reyes. Esto supone como resultado
				que se jugará con una baraja que tiene ocho reyes. 2 como as A todos
				los efectos los cuatro doses de la baraja se tendrán por ases. Esto
				supone como resultado que se jugará con una baraja que tiene ocho
				ases. Por tratarse de una opción estrechamente relacionada con el
				hecho de jugar cara a cara con otras personas, en la versión del
				juego que aquí se ofrece se ha descartado la posibilidad de jugar
				con senas.</p>
			<h3>Objetivo del juego</h3>
			<p>En el mus vence la pareja que antes llegue a ganar tres juegos
				de 30 ó 40 tantos cada uno (cabría decirse que se juega al mejor de
				cinco juegos, es decir, una partida consta de un mínimo de tres
				juegos y de un máximo de cinco).</p>

			<p>Para alcanzar estos 30 o 40 puntos que han de obtenerse en
				cada uno de esos juegos por parte de una pareja, se jugarán a su vez
				todas las manos o juegos parciales (en definitiva repartos de
				cartas) que sea preciso, acumulándose los puntos en base a los
				criterios y normas que se describen en el apartado correspondiente.
				Tanteo La llevanza del tanteo en cada uno de los juegos hasta
				alcanzar 40 se hará mediante tantos o piedras (garbanzos, alubias o
				piedras generalmente) que irá acumulando uno de los jugadores de
				cada pareja. Cada vez que el jugador que lleva los tantos sume 5 de
				ellos, tendrá que volver a depositarlos en el platillo central del
				que salieron y sustituirlos por uno que sumará su companero por
				valor de 5.</p>
			<p>Así pues como resultado, un miembro de la pareja tendrá a su
				lado tantos por valor de 5 o amarrecos y su companero tantos por
				valor de uno. Por lo tanto un juego finalizará cuando una pareja
				haya conseguido sumar 8 amarrecos.</p>

			<h3>Desarrollo del juego</h3>

			<p>Una vez que se ha explicado la división de una partida en 3 a
				5 juegos, y cada uno de esos juegos en un número indeterminado de
				manos, se pasa a continuación a explicar el desarrollo de cada una
				de esas manos como unidad del juego del mus, y la forma en que se
				van adquiriendo los ansiados tantos.</p>

			<h3>Descartes</h3>
			<p>Un jugador baraja el mazo de cartas y se lo da a cortar al que
				está situado a su izquierda. Seguidamente reparte cartas una a una a
				cada uno de los otros jugadores, comenzando por el que está a su
				derecha, siguiendo este mismo sentido de izquierda a derecha hasta
				llegar a sí mismo, y hasta un total de cuatro cartas por jugador.
				Del jugador que está a la derecha del que reparte se dice que tiene
				la mano o es mano (se verá qué consecuencias tiene esta
				circunstancia), y tiene la opción de anunciar su voluntad de
				descartarse de una o más cartas para cambiarlas por otras de las del
				mazo.</p>
			<p>Tal voluntad la expresará diciendo la palabra mus. Si su
				voluntad fuera la de no cambiar ninguna de sus cartas lo anunciará
				mediante la expresión no hay mus, corto o similares. Sucesivamente
				todos los jugadores, en el mismo orden de izquierda a derecha, irán
				expresando su voluntad o no de descarte del mismo modo. Desde el
				momento en que uno solo de los jugadores expresa su deseo de no
				descartarse (corta el mus), resulta irrelevante la voluntad
				favorable del resto hacia el descarte, y no habrá posibilidad de
				hacerlo por parte de ninguno. Si todos y cada uno de los cuatro
				jugadores hubieran pedido descartarse (mus), procederán a arrojar el
				número de cartas que desearan al centro del tapete en el mismo orden
				en que han hablado. Seguidamente quien reparte volverá a hacerlo,
				pero en esta ocasión entregando a cada jugador de una sola vez
				tantas cartas como hubiera desechado. Esta opción de descartes se
				podrá repetir tantas veces como desearan los jugadores, y repitiendo
				las pautas anteriormente descritas, hasta el momento en que uno de
				ellos anunciase su voluntad de cortar el mus. Si se acabasen las
				cartas del mazo y todavía fuesen necesarias más para repartir entre
				los jugadores tras un descarte o sucesivos, se procederá a tomar las
				cartas desechadas en anteriores descartes, barajarlas y repartirlas
				como si fueran las del mazo.</p>
			<h3>Mus corrido</h3>
			<p>Sólo de forma excepcional y para dilucidar quién será el
				jugador que sea mano por primera vez, y por lo tanto sólo aplicable
				al primer reparto de cartas de toda una partida, se aplica esta
				regla. Una vez que se sientan los jugadores ante la mesa se echa a
				suertes quién es el que reparte por primera vez las cartas
				(generalmente mediante el método de repartir una carta vista a cada
				uno y ver quién tiene la más alta). A partir de ahí quien reparte
				hace una primera entrega de cuatro cartas a cada jugador del modo
				descrito anteriormente. Seguidamente pasará el mazo de cartas, al
				que está a su derecha. Si éste quisiera descartarse (mus) pasará el
				mazo de cartas a su vez al de su derecha sin necesidad de anunciar
				verbalmente su voluntad de mus, y así sucesivamente hasta que el
				mazo vuelva a quien repartió o hasta que alguien corte el mus por el
				simple método de no pasar el mazo a su derecha. Si el mazo volviese
				al punto de partida, se procedería a hacer un primer descarte de
				todos los jugadores, y así sucesivamente hasta que alguien cortase
				el mus, persona que automáticamente pasaría a ser mano. A partir de
				ahí la mano iría cambiando a lo largo de toda la partida de titular
				en las sucesivas jugadas o manos, avanzando de izquierda a derecha.</p>

			<h3>Lances</h3>
			<p>El mus es un juego de apuestas o lances que se irán haciendo
				sucesivamente a lo largo de una mano. Una vez que uno de los
				jugadores ha decidido cortar el mus se comenzará a hablar. Los
				conceptos por los que se apuesta o lances son cuatro:
			<p>
			<h4>Grande</h4>
			<p>Cortado el mus quien es mano comenzará a manifestar su
				intención de apostar a grande o no (más adelante se indicará el modo
				de realizar las apuestas). La apuesta a grande o mayor se hace sobre
				la base de considerar qué jugador es el que tiene cartas más altas
				según la numeración de los índices de las propias cartas: Rey,
				Caballo, Sota, siete, seis, cinco, cuatro, tres dos, As. La
				comparación se realizará en base a la carta con mayor numeración de
				cada uno de los jugadores. En caso de igualdad en base a la segunda
				carta más alta, y así sucesivamente. Esta combinación: Vence a esta
				otra: La comprobación de quién vence en este lance como en los
				sucesivos se hace al final de la mano, esto es, precisamente, cuando
				han finalizado los cuatro lances.</p>

			<h4>Pequena</h4>

			<p>Pasado el lance de grande los jugadores deberán pronunciarse
				sobre su intención de realizar apuestas a pequena o chica, que es
				exactamente el mismo concepto que la jugada de grande pero
				invirtiendo el orden en la valoración de las cartas (As, dos, tres,
				...). Vencería el jugador que tuviera la carta más baja.</p>
			<h4>Pares</h4>
			<p>Tras la jugada de pequena se pasa a la de pares. En ella antes
				de realizar ninguna apuesta, los jugadores, uno a uno, y empezando
				por quien es mano, irán anunciando verazmente si tuvieran una o más
				cartas del mismo número con expresiones tales como par sí, pares sí
				o simplemente sí o negaciones análogas respectivamente (resulta
				imprescindible saber si por lo menos un jugador de los de cada
				pareja tiene pares ya que, de no ser así, no podría ni tan siquiera
				realizarse apuesta alguna en la fase siguiente del lance). Una vez
				que todos los jugadores hayan anunciado si tienen pares o no, y
				resultara que por lo menos uno de cada pareja los tuviera, se
				procederá a la fase de apuesta por pares. Esto lo harán los
				jugadores manifestando su voluntad o no de hacer alguna puesta en
				concepto de pares, comenzando a manifestar tal voluntad el que fuera
				mano, si los tuviera, o en su defecto el siguiente hacia su derecha,
				y así sucesivamente. En el lance de pares el orden jerárquico de las
				jugadas, de mayor a menor es el siguiente:</p>
			<h5>Duples</h5>
			<p>Se llama así a la combinación de cuatro cartas que pudiera
				tener un jugador, en la que las cuatro son del mismo número, o en la
				que están emparejadas dos a dos (lo que en póker sería un póker o
				una doble pareja). En caso de que dos jugadores contrarios tuvieran
				vencerá aquel que tuviera la carta más alta, según el mismo orden
				que para grande (no hay superioridad jerárquica entre duples de
				cuatro cartas iguales o duples de dobles parejas, por el simple
				hecho de serlo, esto es, por ejemplo, que dos caballos y dos seises
				ganan a cuatro sotas). Los duples de reyes y ases valen más que las
				de caballos y caballos.</p>



			<h5>Medias</h5>
			<p>Así se denomina a la combinación de cartas que tuviera un
				jugador en la cual tres de ellas fuesen del mismo número (en póker,
				un trío). Si dos oponentes tuvieran medias vence el que las tuviera
				de la carta más alta según la jerarquía empleada para grande. A
				efectos de desempatar, la cuarta carta es como si no existiese: no
				forma parte de las medias.</p>
			<h5>Pareja</h5>
			<p>Se trata de la combinación de cartas de valor más bajo de
				quien tuviera pares. En ella tan sólo dos de las cartas de las
				cuatro de un jugador están emparejadas (en póker, una pareja). Si
				dos contrincantes tuvieran pareja, vencerá el que la tuviera de la
				carta más elevada según la misma jerarquía empleada en grande. Al
				igual que en medias las otras dos cartas no emparejadas no se
				tendrán en cuenta en ningún caso.</p>

			<h4>Juego</h4>
			<p>Tras finalizar la jugada de pares, los jugadores, al igual que
				en ese lance, y antes de comenzar a apostar por este nuevo concepto,
				deberán manifestar si tuvieran juego mediante frases tales como
				juego sí, sí tengo, llevo juego o simplemente sí.</p>
			<p>Tiene juego aquel jugador que, sumando el valor de sus cartas,
				alcanzase el número de 31 o uno superior. Para realizar tal computo
				las figuras (Sota, Caballo y Rey) se valorarán igualmente con diez
				cada una, y el resto de las cartas según su propio índice.</p>
			<p>Tras manifestar quiénes tienen juego, al igual que en pares,
				siempre que por lo menos uno de cada pareja lo tuviera, los que sí
				lo tuvieran, y en el orden habitual (empezando por la mano y hacia
				la derecha hasta el que reparte), deberán manifestar su intención de
				apostar por este concepto o no. El orden de jerarquía de las
				diferentes posibles combinaciones en las que se tiene juego es el
				siguiente: 31; 32; 40; 37; 36; 35; 34; 33.</p>

			<h4>Punto</h4>

			<p>Sólo subsidiariamente, y si ninguno de los cuatro jugadores
				tuviera juego, se abriría un nuevo lance llamado punto, íntimamente
				ligado con el juego, ya que consiste en apostar por quien, sin tener
				juego, se acerca más a él. Por lo tanto, el mejor punto sería el del
				jugador que contase 30. Apuestas Se describen a continuación las
				reglas comunes a todos los lances del juego respecto a la forma de
				realizar las apuestas: En cada uno de los lances los jugadores
				deberán ir manifestando uno a uno si quisieran apostar algo por ese
				concepto o si se abstuvieran de hacerlo en cuyo caso lo dirán
				mediante la expresión paso (como ya se ha dicho, en pares y juego
				sólo podrán y deberán optar aquellos que tuvieran pares o juego
				respectivamente).</p>

			<p>Si un jugador optase por hacer una apuesta en una jugada, lo
				manifestará haciendo la apuesta concreta a sus contrincantes, con un
				mínimo posible de 2 piedras. Esta apuesta mínima de 2 tantos se
				denomina envite y quien la lanza deberá expresarla diciendo envido
				(el verbo concreto es envidar). También se puede empezar con una
				apuesta superior, en cuyo caso quien la lanza lo hará diciendo el
				número concreto de tantos que quiere apostar sin más, o precederlo
				del verbo envido (por ejemplo se diría 10 o bien envido 10).</p>

			<h4>Respuesta a la apuesta</h4>
			<p>Ante una apuesta lanzada por un jugador, cualquiera de sus
				oponentes (en pares y juego sólo quienes estuvieran en posesión de
				tales jugadas) podrá optar por tres cosas diferentes:</p>

			<h5>Aceptar la apuesta</h5>
			<p>En este caso lo manifestará mediante expresiones afirmativas
				(la más evidente y usada, lógicamente, es un parco sí).</p>

			<h5>Declinar la apuesta</h5>
			<p>Declinar la apuesta lanzada por el contrario se hará mediante
				un no, tomando inmediatamente para sí la pareja que hizo la puesta
				una piedra a modo de penalización para quien no la aceptó.</p>







		</div>
	</div>
</div>
<%@ include file="../jspf/footer.jspf"%>
