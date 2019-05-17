package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

public class Resources {
	/**
	 * 従業員関連のサービス実装。 Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
	 */
	@Path("resources")
	public class EmployeeResource {

	}
	@POST
	@Path("{login}")
	@Consumes("application/x-www-form-urlencoded")
	//@Produces(MediaType.APPLICATION_JSON)
	public boolean Login (@FormParam("id") String id,@FormParam("pass") String pass) throws WebApplicationException {
		String successId ="test";
		String successPass = "1234";
		System.out.println(id+pass);
		if( id != null && pass != null && id.equals(successId) && pass.equals(successPass)){
			return true;
		}else{
			return false;
		}



	}




















//	private final EmployeeDAO empDao = new EmployeeDAO();
//	private final PostDAO postDao = new PostDAO();
//	private final PhotoDAO photoDao = new PhotoDAO();
//
//	/**
//	 * ID指定で従業員情報を取得する。
//	 *
//	 * @param id
//	 *            取得対象の従業員のID
//	 * @return 取得した従業員情報をJSON形式で返す。データが存在しない場合は空のオブジェクトが返る。
//	 */
//	@GET
//	@Path("{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Employee findById(@PathParam("id") int id) {
//		return empDao.findById(id);
//	}
//
//	/**
//	 * クエリパラメータ指定による検索を実施する。 何も指定しない場合は全件検索になる。
//	 *
//	 * @param postId
//	 *            部署ID。指定しない場合は0が入る。
//	 * @param empId
//	 *            ログイン用の従業員ID。指定しない場合はnullが入る。
//	 * @param nameParam
//	 *            名前の一部を指定するためのパラメータ。指定しない場合はnullが入る。
//	 * @return 取得した従業員情報のリストをJSON形式で返す。データが存在しない場合は空のオブジェクトが返る。
//	 */
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Employee> findByParam(@QueryParam("postId") int postId, @QueryParam("empId") String empId,
//			@QueryParam("nameParam") String nameParam) {
//		Param param = new Param(postId, empId, nameParam);
//		return empDao.findByParam(param);
//	}
//
//	/**
//	 * 指定した従業員情報を登録する。
//	 *
//	 * @param form
//	 *            従業員情報（画像含む）を収めたオブジェクト
//	 * @return DB上のIDが振られた従業員情報
//	 * @throws WebApplicationException
//	 *             入力データチェックに失敗した場合に送出される。
//	 */
//	@POST
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Employee create(final FormDataMultiPart form) throws WebApplicationException {
//		Employee employee = new Employee();
//
//		employee.setId(0);
//		employee.setEmpId(form.getField("empId").getValue());
//		employee.setName(form.getField("name").getValue());
//		employee.setAge(Integer.parseInt(form.getField("age").getValue()));
//		String gender = form.getField("gender").getValue();
//		employee.setGender(Gender.valueOf(gender));
//
//		employee.setZip(form.getField("zip").getValue());
//		employee.setPref(form.getField("pref").getValue());
//		employee.setAddress(form.getField("address").getValue());
//
//		String enterDateStr = form.getField("enterDate").getValue();
//		if (enterDateStr != null && !enterDateStr.isEmpty()) {
//			employee.setEnterDate(enterDateStr);
//		}
//
//		String retireDateStr = form.getField("retireDate").getValue();
//		if (retireDateStr != null && !retireDateStr.isEmpty()) {
//			employee.setRetireDate(retireDateStr);
//		}
//
//		if (!employee.isValidObject()) {
//			throw new WebApplicationException(Response.Status.BAD_REQUEST);
//		}
//
//		// Photo関連の処理
//		FormDataBodyPart photoPart = form.getField("photo");
//		Photo photo = createPhoto(photoPart);
//		if (photo.getId() == 0) {
//			throw new WebApplicationException(Response.Status.BAD_REQUEST);
//		}
//		employee.setPhotoId(photo.getId());
//
//		// Post関連の処理
//		int postId = Integer.parseInt(form.getField("postId").getValue());
//		Post post = postDao.findById(postId);
//		if (post == null) {
//			throw new WebApplicationException(Response.Status.BAD_REQUEST);
//		}
//		employee.setPost(post);
//
//		return empDao.create(employee);
//	}
//
//	/**
//	 * 指定した情報でDBを更新する。
//	 *
//	 * @param form
//	 *            更新情報を含めた従業員情報
//	 * @throws WebApplicationException
//	 *             入力データチェックに失敗した場合に送出される。
//	 */
//	@PUT
//	@Path("{id}")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Employee update(@PathParam("id") int id, final FormDataMultiPart form) throws WebApplicationException {
//		Employee employee = new Employee();
//
//		employee.setId(id);
//		employee.setEmpId(form.getField("empId").getValue());
//		employee.setName(form.getField("name").getValue());
//		employee.setAge(Integer.parseInt(form.getField("age").getValue()));
//		String gender = form.getField("gender").getValue();
//		employee.setGender(Gender.valueOf(gender));
//
//		employee.setZip(form.getField("zip").getValue());
//		employee.setPref(form.getField("pref").getValue());
//		employee.setAddress(form.getField("address").getValue());
//
//		String enterDateStr = form.getField("enterDate").getValue();
//		if (enterDateStr != null && !enterDateStr.isEmpty()) {
//			employee.setEnterDate(enterDateStr);
//		}
//
//		String retireDateStr = form.getField("retireDate").getValue();
//		if (retireDateStr != null && !retireDateStr.isEmpty()) {
//			employee.setRetireDate(retireDateStr);
//		}
//
//		if (!employee.isValidObject()) {
//			throw new WebApplicationException(Response.Status.BAD_REQUEST);
//		}
//
//		// Photo関連の処理
//		String photoIdSrc = form.getField("photoId").getValue();
//		int photoId = Integer.parseInt(photoIdSrc);
//		FormDataBodyPart photoPart = form.getField("photo");
//		if (!photoPart.getContentDisposition().getFileName().isEmpty()) {
//			updatePhoto(photoId, photoPart);
//		}
//		employee.setPhotoId(photoId);
//
//		// Post関連の処理
//		int postId = Integer.parseInt(form.getField("postId").getValue());
//		Post post = postDao.findById(postId);
//		if (post == null) {
//			throw new WebApplicationException(Response.Status.BAD_REQUEST);
//		}
//		employee.setPost(post);
//
//		return empDao.update(employee);
//	}
//
//	/**
//	 * 指定したIDの社員情報を削除する。同時に画像データも削除する。
//	 *
//	 * @param id
//	 *            削除対象の社員情報のID
//	 */
//	@DELETE
//	@Path("{id}")
//	public void remove(@PathParam("id") int id) {
//		Employee employee = empDao.findById(id);
//		empDao.remove(id);
//		photoDao.remove(employee.getPhotoId());
//	}
//
//	@GET
//	@Path("csv")
//	@Produces(MediaType.APPLICATION_OCTET_STREAM)
//	public Response downloadCsv() {
//		Param param = new Param(0, "", "");
//		List<Employee> list = empDao.findByParam(param);
//
//		String header = "ID,社員番号,名前,年齢,性別,写真ID,郵便番号,都道府県,住所,所属部署ID,入社日付,退社日付" + System.getProperty("line.separator");
//		StringBuffer csvContents = new StringBuffer(header);
//
//		for (Employee employee : list) {
//			String line = employee.toString() + System.getProperty("line.separator");
//			csvContents.append(line);
//		}
//
//		return Response.status(Status.OK).entity(csvContents.toString())
//				.header("Content-disposition", "attachment; filename=employee.csv").build();
//	}
//
//	/**
//	 * Formから渡されたデータを使用してPhotoデータを登録する。
//	 *
//	 * @param photoPart
//	 *            Formから渡されたPhotoデータ
//	 * @return 登録されてIDが振られたPhotoインスタンス
//	 */
//	private Photo createPhoto(FormDataBodyPart photoPart) {
//		Photo photo = build(photoPart);
//
//		return photoDao.create(photo);
//	}
//
//	/**
//	 * Formから渡されたデータを使用してPhotoデータを更新する。
//	 *
//	 * @param photoId
//	 *            更新対象のPhotoのID
//	 * @param photoPart
//	 *            Formから渡されたPhotoデータ
//	 * @return 正常に更新された場合はtrue、失敗した場合はfalse
//	 */
//	private boolean updatePhoto(int photoId, FormDataBodyPart photoPart) {
//		Photo photo = build(photoPart);
//		photo.setId(photoId);
//		return photoDao.update(photo);
//	}
//
//	/**
//	 * formから渡されたデータを使用してPhotoインスタンスを構築する。
//	 *
//	 * @param photoPart
//	 *            Formから渡されたPhotoデータ
//	 * @return ID以外のフィールドに値がセットされたPhotoインスタンス
//	 */
//	private Photo build(FormDataBodyPart photoPart) {
//		Photo photo = new Photo();
//		ContentDisposition photoInfo = photoPart.getContentDisposition();
//
//		photo.setFileName(photoInfo.getFileName());
//
//		photo.setContentType(photoPart.getMediaType().toString());
//
//		BodyPartEntity bodyPartEntity = (BodyPartEntity) photoPart.getEntity();
//		InputStream in = bodyPartEntity.getInputStream();
//		photo.setPhoto(in);
//
//		photo.setEntryDate(new Date(System.currentTimeMillis()));
//		return photo;
//	}

}
