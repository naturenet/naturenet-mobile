package net.nature.mobile.tests;

import java.util.Date;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import net.nature.mobile.model.Activity;
import net.nature.mobile.model.User;
import net.nature.mobile.rest.Sync;
import android.test.AndroidTestCase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SyncTest extends AndroidTestCase {
	
	private Sync sync;

	@Override
	protected void setUp() throws Exception{
		super.setUp();
		sync = new Sync();
	}

	public void testPullNewRemoteUser(){
		User user = new User();
		String newName = "remote" + (new Date()).getTime();
		user.username = newName;
		
		sync.pull(user);
		
		User u = (new Select()).from(User.class).where("username = ?", user.username).executeSingle();		
		assertThat(u, notNullValue());
		assertThat(u.username, equalTo(newName));
	}
	
	public void testPullExistingRemoteUser(){
		User user = (new Select()).from(User.class).executeSingle();
		
		int countBefore = (new Select()).from(User.class).execute().size();
						
		sync.pull(user);
		
		int countAfter = (new Select()).from(User.class).execute().size();
						
		assertThat(countBefore, equalTo(countAfter));
		
	}
	
	public void testPullAllUsers(){
		
		(new Delete()).from(User.class).execute();
		int countBefore = (new Select()).from(User.class).execute().size();
				
		sync.pullAllUsers();
		
		int countAfter = (new Select()).from(User.class).execute().size();
		
		// assuming at least 5 accounts, this must hold
		assertThat(countAfter, greaterThan(countBefore + 5));
		
		sync.pullAllUsers();
		
		int countAfterAgain = (new Select()).from(User.class).execute().size();
		
		assertThat(countAfterAgain, equalTo(countAfter));
	}
	
	public void testPullAllActivities(){
		
		(new Delete()).from(Activity.class).execute();
		int countBefore = (new Select()).from(Activity.class).execute().size();
				
		sync.pullAllActivities();
		
		int countAfter = (new Select()).from(Activity.class).execute().size();
	
		assertThat(countAfter, greaterThan(countBefore + 3));
		
		sync.pullAllActivities();
		
		int countAfterAgain = (new Select()).from(Activity.class).execute().size();
		
		assertThat(countAfterAgain, equalTo(countAfter));
	}
}
