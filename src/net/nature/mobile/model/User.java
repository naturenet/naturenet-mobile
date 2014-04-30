package net.nature.mobile.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.google.common.base.Objects;

public class User extends Model {

	@Column(name="Name")
	public String name;
	
	@Column(name="Username")
	public String username;
	
	@Column(name="Email")
	public String email;
	
	@Column(name="UID")
	public Integer id;
	
	public String toString(){
		return Objects.toStringHelper(this).
				add("username", username).
				add("name", name).
				add("email", email).
				add("id", id).toString();
	}
	
}
