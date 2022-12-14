<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="../components/head.jsp"></jsp:include>
<body class="hold-transition sidebar-mini layout-fixed">
	<div class="wrapper">
		<!-- Preloader -->
		<div
			class="preloader flex-column justify-content-center align-items-center">
			

		</div>
		<jsp:include page="../components/nav-bar.jsp"></jsp:include>
		<jsp:include page="../components/side-bar.jsp"></jsp:include>
		<div class="content-wrapper" style="min-height: 1299.69px;">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-12">
							<h1 class="text-center">User Manager</h1>
						</div>
					</div>
				</div>
				<!-- /.container-fluid -->
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<!-- /.card -->
							<div class="card">
								<!-- /.card-header -->
								<div class="card-body">
									<div id="example1_wrapper"
										class="dataTables_wrapper dt-bootstrap4">
										<div class="row">
											<div class="col-sm-12">
												<table id="example1"
													class="table table-bordered table-striped dataTable dtr-inline"
													role="grid" aria-describedby="example1_info">
													<thead>
														<tr role="row">
															<th class="sorting text-center" tabindex="0" aria-controls="example1"
																rowspan="1" colspan="1">Id</th>
															<th class="sorting text-center" tabindex="0" aria-controls="example1"
																rowspan="1" colspan="1">Username</th>
															<th class="sorting text-center" tabindex="0" aria-controls="example1"
																rowspan="1" colspan="1">Email</th>
															<th class="sorting text-center" tabindex="0" aria-controls="example1"
																rowspan="1" colspan="1">Status</th>
															<th class="sorting text-center" tabindex="0" aria-controls="example1"
																rowspan="1" colspan="1">Role</th>
															<!-- <th class="sorting text-center" tabindex="0" aria-controls="example1"
																rowspan="1" colspan="1">Action</th> -->
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${userList}" var="u">
															<tr>
																<td class="text-center">${u.id}</td>
																<td class="text-center">
																	<a href="${pageContext.servletContext.contextPath}/admin/user/detail?id=${u.id}">${u.username}
																	</a>
																</td>
																<td class="text-center">${u.email}</td>
																<td class="text-center position-relative"><c:choose>
																		<c:when test="${u.enabled == true}">
																			<a
																				class="my-btn-state rounded-circle btn btn-sm btn-success">
																				<i class="fas fa-check"></i>
																			</a>
																		</c:when>

																		<c:otherwise>
																			<a
																				class="my-btn-state rounded-circle btn btn-sm btn-danger">
																				<i class="fas fa-minus"></i>
																			</a>
																		</c:otherwise>
																	</c:choose>
																</td>
																<td class="text-center">${fn:substring(u.role, 5, fn:length(u.role))}</td>
																<!-- <td class="text-center">
<%--																	<a--%>
<%--																	href="${pageContext.servletContext.contextPath}/admin/user/detail?id=${u.id}"--%>
<%--																	class="rounded-circle btn btn-sm btn-secondary"--%>
<%--																	title="Detail"> <i class="fas fa-info"></i>--%>
<%--																</a> --%>
																	<a
																	href="${pageContext.request.contextPath}/admin/user/edit?id=${u.id}"
																	id="${u.id}" class="rounded-circle btn btn-sm btn-info"
																	title="Edit"> <i class="fas fa-pencil-alt"></i>
																</a></td> -->
															</tr>
														</c:forEach>




													</tbody>
													<tfoot>
													</tfoot>
												</table>
											</div>
										</div>
									</div>
								</div>
								<!-- /.card-body -->
							</div>
							<!-- /.card -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</section>
			<!-- /.content -->
		</div>
		<jsp:include page="../components/footer.jsp"></jsp:include>
		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark">
			<!-- Control sidebar content goes here -->
		</aside>
		<!-- /.control-sidebar -->
	</div>
	<!-- ./wrapper -->
	<jsp:include page="../components/script.jsp"></jsp:include>
	<script>
		$(function() {
			$("#example1").DataTable(
					{
						"responsive" : true,
						"lengthChange" : false,
						"autoWidth" : false,
						"buttons" : [ "copy", "csv", "excel", "pdf", "print",
								"colvis" ]
					}).buttons().container().appendTo(
					'#example1_wrapper .col-md-6:eq(0)');
		});
	</script>
</body>
</html>