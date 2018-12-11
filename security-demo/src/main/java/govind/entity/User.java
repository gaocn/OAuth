package govind.entity;


import com.fasterxml.jackson.annotation.JsonView;
import govind.annotations.CheckPasswd;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.sql.Date;


@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {

	public interface UserSimpleView{};
	public interface UserDetailView  extends UserSimpleView{};

	private int userid;

	@NotBlank
	private String  username;

	@CheckPasswd
	private String passwd;

	/**
	 * 标注时间为过去
	 */
	@Past
	private Date birthday;

	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonView(UserDetailView.class)
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	@JsonView(UserDetailView.class)
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@JsonView(UserSimpleView.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
