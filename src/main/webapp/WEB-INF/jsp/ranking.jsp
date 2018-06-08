<%@ include file="../jspf/header.jspf"%>

<div class="container">

	<c:if test="${not empty mensaje }">
		<div class="alert alert-info" role="alert">
			<strong>${mensaje} ${classMensaje}</strong>
		</div>
		<br>
	</c:if>
<br>

	<div class="wrapper">

		<table>
			<thead>
				<tr class="myTableHead">
					<th><p style="text-align: center; vertical-align: middle;">
							<strong># PUESTO</strong>
						</p></th>
					<th><p style="text-align: center; vertical-align: middle;">
							<strong>USUARIO</strong>
						</p></th>
					<th><p style="text-align: center; vertical-align: middle;">
							<strong>PUNTOS</strong>
						</p></th>
					<th><p style="text-align: center; vertical-align: middle;">
							<strong>PAIS</strong>
						</p></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ranking}" var="r">

					<tr>
						<td><p style="text-align: center; vertical-align: middle;">
								<strong>1</strong>
							</p></td>
						<td><img class="img-thumbnail maxImgSize"
							src="${imagen}${r.id}"> <strong>${r.login}</strong></td>
						<td><p style="text-align: center; vertical-align: middle;">
								<strong>${r.dinero}</strong>
							</p></td>
						<td><p style="text-align: center; vertical-align: middle;">
								<strong>${r.nacion}</strong>
							</p></td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
	</div>
</div>
<%@ include file="../jspf/footer.jspf"%>