package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
<<<<<<< HEAD
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import beans.Book;
import dao.BookDAO;


import com.sun.research.ws.wadl.Param;
=======
>>>>>>> dev

@Path("resources")
public class Resources {
	/**
	 * 従業員関連のサービス実装。 Servlet/JSPの実装とは異なり、画像についてはバイナリでなくpathベースで扱うものとする。
	 */
	@Path("resources")
	public class EmployeeResource {

<<<<<<< HEAD
	private final BookDAO bookDAO = new BookDAO();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("book")
	public List<Book> findAll() {
		return bookDAO.findAll();
	}


	private final EmployeeDAO empDao = new EmployeeDAO();
	private final PostDAO postDao = new PostDAO();
	private final PhotoDAO photoDao = new PhotoDAO();

	/**
	 * ID指定で従業員情報を取得する。
	 *
	 * @param id
	 *            取得対象の従業員のID
	 * @return 取得した従業員情報をJSON形式で返す。データが存在しない場合は空のオブジェクトが返る。
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee findById(@PathParam("id") int id) {
		return empDao.findById(id);
=======
>>>>>>> dev
	}

	@POST
<<<<<<< HEAD
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Employee create(final FormDataMultiPart form) throws WebApplicationException {
		Employee employee = new Employee();

		employee.setId(0);
		employee.setEmpId(form.getField("empId").getValue());
		employee.setName(form.getField("name").getValue());
		employee.setAge(Integer.parseInt(form.getField("age").getValue()));
		String gender = form.getField("gender").getValue();
		employee.setGender(Gender.valueOf(gender));

		employee.setZip(form.getField("zip").getValue());
		employee.setPref(form.getField("pref").getValue());
		employee.setAddress(form.getField("address").getValue());

		String enterDateStr = form.getField("enterDate").getValue();
		if (enterDateStr != null && !enterDateStr.isEmpty()) {
			employee.setEnterDate(enterDateStr);
>>>>>>> dev
		}

		String retireDateStr = form.getField("retireDate").getValue();
		if (retireDateStr != null && !retireDateStr.isEmpty()) {
			employee.setRetireDate(retireDateStr);
		}

		if (!employee.isValidObject()) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		// Photo関連の処理
		FormDataBodyPart photoPart = form.getField("photo");
		Photo photo = createPhoto(photoPart);
		if (photo.getId() == 0) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		employee.setPhotoId(photo.getId());

		// Post関連の処理
		int postId = Integer.parseInt(form.getField("postId").getValue());
		Post post = postDao.findById(postId);
		if (post == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		employee.setPost(post);

		return empDao.create(employee);
	}

	/**
	 * 指定した情報でDBを更新する。
	 *
	 * @param form
	 *            更新情報を含めた従業員情報
	 * @throws WebApplicationException
	 *             入力データチェックに失敗した場合に送出される。
	 */
	@PUT
	@Path("{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Employee update(@PathParam("id") int id, final FormDataMultiPart form) throws WebApplicationException {
		Employee employee = new Employee();

		employee.setId(id);
		employee.setEmpId(form.getField("empId").getValue());
		employee.setName(form.getField("name").getValue());
		employee.setAge(Integer.parseInt(form.getField("age").getValue()));
		String gender = form.getField("gender").getValue();
		employee.setGender(Gender.valueOf(gender));

		employee.setZip(form.getField("zip").getValue());
		employee.setPref(form.getField("pref").getValue());
		employee.setAddress(form.getField("address").getValue());

		String enterDateStr = form.getField("enterDate").getValue();
		if (enterDateStr != null && !enterDateStr.isEmpty()) {
			employee.setEnterDate(enterDateStr);
		}

		String retireDateStr = form.getField("retireDate").getValue();
		if (retireDateStr != null && !retireDateStr.isEmpty()) {
			employee.setRetireDate(retireDateStr);
=======
	@Path("{login}")
	@Consumes("application/x-www-form-urlencoded")
	// @Produces(MediaType.APPLICATION_JSON)
	public boolean Login(@FormParam("id") String id, @FormParam("pass") String pass) throws WebApplicationException {
		String successId = "test";
		String successPass = "1234";
		System.out.println(id + pass);
		if (id != null && pass != null && id.equals(successId) && pass.equals(successPass)) {
			return true;
		} else {
			return false;
>>>>>>> dev
		}

	}

}
