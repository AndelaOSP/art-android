
import com.andela.art.securitydashboard.presentation.FirebasePresenter;
import com.andela.art.securitydashboard.presentation.SerialView;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by zack on 4/5/18.
 */
public class FirebasePresenterTest {

    FirebasePresenter firebasePresenter;

    @Mock
    FirebaseAuth firebaseAuth;

    @Mock
    FirebaseAuth.AuthStateListener authStateListener;

    @Mock
    SerialView serialView;

    /**
     * Test set up method.
     * @throws Exception - exception if error occurs.
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        firebasePresenter = new FirebasePresenter(firebaseAuth, authStateListener);
        firebasePresenter.attachView(serialView);
    }

    /**
     * Test that user details are displayed.
     */
    @Test
    public void testUserDetailsAreDisplayed() {
        firebasePresenter.onAuthStateChanged();
        verify(serialView).setAccountDetails(anyString(), anyString(), anyString());
    }

    /**
     * Test that user is redirected if they are not logged in.
     */
    @Test
    public void testUserIsRedirectedWhenLoggedOut() {
        firebasePresenter.dummyUser = null;
        firebasePresenter.onAuthStateChanged();
        verify(serialView).redirectLoggedOutUser();
    }
}
